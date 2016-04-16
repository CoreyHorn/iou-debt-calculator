package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class NewTransactionPresenterImpl implements NewTransactionPresenter{

    NewTransactionView view;

    public NewTransactionPresenterImpl(NewTransactionView view) {
        this.view = view;
    }

    @Override
    public List<Account> getAccounts(DataRepositoryListener listener) {
        return null;
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

    }
}
