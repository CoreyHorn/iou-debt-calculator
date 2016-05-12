package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.network.CurrencyConverterService;
import com.ioudebtcalculator.network.CurrencyResult;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTransactionPresenterImpl implements NewTransactionPresenter {

    private NewTransactionView view;
    private DataRepository dataRepository;
    private CurrencyConverterService currencyConverterService;

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
    public List<String> getAvailableCurrencies() {
        /**
         * TODO: Convert this method to return the list of currency symbols / strings
         * returned from SharedPreferences.
         */
        Set<Currency> currencySet = Currency.getAvailableCurrencies();
        List<String> currencySymbols = new ArrayList<>();
        for (Currency currency : currencySet) {
            currencySymbols.add(currency.getSymbol());
        }
        return currencySymbols;
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
                //TODO: Create method to \/\/\/
                //TODO: Handle instant creation of Transaction, add to database, close fragment.
            } else {
                Call<CurrencyResult> currencyResultCall = currencyConverterService
                        .getCurrencyResult(view.getSelectedCurrency(),
                                view.getSelectedAccount().getCurrencyCode());
                currencyResultCall.enqueue(new CurrencyConversionListener());
            }
        }
    }

    private class CurrencyConversionListener implements Callback<CurrencyResult> {

        @Override
        public void onResponse(Call<CurrencyResult> call, Response<CurrencyResult> response) {
            if (response.isSuccess()) {
                CurrencyResult result = response.body();
                //TODO: Create the transaction, save to database, close fragment.
            }
        }

        @Override
        public void onFailure(Call<CurrencyResult> call, Throwable t) {
            //TODO: Handle notifying the user of error and allow retry attempts.
        }
    }
}
