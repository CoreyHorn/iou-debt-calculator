package com.ioudebtcalculator.newaccount;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

public class NewAccountPresenterImpl implements NewAccountPresenter {

    NewAccountView view;

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
        if (view.getBorrowOrLoanCheckedId() == -1) {
            view.setBorrowOrLoanError();
        }
        if (view.getNameEntered().equals("")) {
            view.setAccountNameError();
        }
        if (view.getAmountEntered().equals("")) {
            view.setAmountError();
        }
        //TODO: Handle saving the account and closing the fragment.
    }

    @Override
    public void setView(NewAccountFragment view) {
        this.view = view;
    }
}
