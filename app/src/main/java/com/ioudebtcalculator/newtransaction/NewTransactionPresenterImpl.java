package com.ioudebtcalculator.newtransaction;

import android.util.Log;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.network.CurrencyConverterService;
import com.ioudebtcalculator.network.CurrencyResult;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewTransactionPresenterImpl implements NewTransactionPresenter {

    @Inject
    DataRepository dataRepository;

    private NewTransactionView view;

    public NewTransactionPresenterImpl() {
        App.getInstance().getAppComponent().inject(this);
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
        if (!error) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CurrencyConverterService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CurrencyConverterService currencyConverterService = retrofit
                .create(CurrencyConverterService.class);
            Call<CurrencyResult> currencyResultCall = currencyConverterService.getCurrencyResult("USD", "DKK");
            currencyResultCall.enqueue(new RetroListener());
//            Transaction transaction = new Transaction(
//                view.getSelectedAccountId(),
//                view.getAmountEntered(),
//                view.get
//            );
        }
        //TODO: Handle saving the transaction and closing the fragment.
    }

    private class RetroListener implements Callback<CurrencyResult> {

        @Override
        public void onResponse(Call<CurrencyResult> call, Response<CurrencyResult> response) {
            if (response.isSuccess()) {
                CurrencyResult result = response.body();
                for (String key : result.rates.keySet()) {
                    Log.d("stuff", "Key: " + key + ", Value: " + result.rates.get(key));
                }
            }
        }

        @Override
        public void onFailure(Call<CurrencyResult> call, Throwable t) {

        }
    }
}
