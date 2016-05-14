package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.network.CurrencyConverterService;
import com.ioudebtcalculator.network.CurrencyResult;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;
import com.ioudebtcalculator.repository.sqlite.DatabaseOperationListener;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTransactionPresenterImpl implements NewTransactionPresenter {

    private NewTransactionView view;
    private DataRepository dataRepository;
    private CurrencyConverterService currencyConverterService;

    private DatabaseOperationListener operationListener = new DatabaseOperationListener() {
        @Override
        public void onOperationComplete() {
            view.close();
        }
    };

    public NewTransactionPresenterImpl(DataRepository dataRepository,
                                       CurrencyConverterService currencyConverterService) {
        this.dataRepository = dataRepository;
        this.currencyConverterService = currencyConverterService;
    }

    @Override
    public void setView(NewTransactionView newTransactionView) {
        this.view = newTransactionView;
    }

    @Override
    public void getAccounts(DataRepositoryListener listener) {
        dataRepository.getAccounts(listener);
    }

    @Override
    public void validateInputAndSave() {
        boolean error = false;
        if (view.getBorrowOrLoanCheckedId() == -1) {
            view.setBorrowOrLoanError();
            error = true;
        }
        if (view.getAmountEntered().equals("")) {
            view.setTransactionAmountError();
            error = true;
        }
        if (view.getSelectedAccount() == null) {
            view.setSelectedAccountError();
            error = true;
        }
        if (!error) {
            if (view.getSelectedAccount().getCurrencyCode().equals(view.getSelectedCurrency())) {
                createAndSaveTransaction(view.getAmountEntered());
            } else {
                Call<CurrencyResult> currencyResultCall = currencyConverterService
                        .getCurrencyResult(view.getSelectedCurrency(),
                                view.getSelectedAccount().getCurrencyCode());
                currencyResultCall.enqueue(new CurrencyConversionListener());
            }
        }
    }

    private void createAndSaveTransaction(String postConversionAmount) {
        Transaction transaction = new Transaction(
                view.getSelectedAccount().getId(),
                view.getAmountEntered(),
                view.getSelectedCurrency(),
                postConversionAmount,
                view.getDescription(),
                System.currentTimeMillis()
        );
        dataRepository.saveTransaction(transaction, operationListener);
        String newBalance = new BigDecimal(view.getSelectedAccount().getCurrentBalance())
                .add(new BigDecimal(transaction.getPostConversionAmount())).toString();
        dataRepository.setAccountBalance(view.getSelectedAccount(), newBalance);
        view.close();
    }

    private class CurrencyConversionListener implements Callback<CurrencyResult> {

        @Override
        public void onResponse(Call<CurrencyResult> call, Response<CurrencyResult> response) {
            if (response.isSuccess()) {
                CurrencyResult result = response.body();
                Double rate = result.getRates().get(view.getSelectedAccount()
                        .getCurrencyCode());
                BigDecimal conversionRate = BigDecimal.valueOf(rate);
                BigDecimal postConversionAmount = new BigDecimal(view.getAmountEntered())
                        .multiply(conversionRate);
                createAndSaveTransaction(postConversionAmount.toString());
            }
        }

        @Override
        public void onFailure(Call<CurrencyResult> call, Throwable t) {
            view.setConversionError();
        }
    }
}
