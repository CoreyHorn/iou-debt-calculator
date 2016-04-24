package com.ioudebtcalculator.newaccount;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.repository.DataRepository;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class NewAccountPresenterImpl implements NewAccountPresenter {

    @Inject
    DataRepository dataRepository;

    NewAccountView view;

    public NewAccountPresenterImpl() {
        App.getInstance().getAppComponent().inject(this);
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
        if (view.getNameEntered().equals("")) {
            view.setAccountNameError();
            error = true;
        }
        if (view.getAmountEntered().equals("")) {
            view.setAmountError();
            error = true;
        }
        if (!error) {
            //TODO: Handle Due Date entry.
            Account account = new Account(
                    view.getAmountEntered(),
                    view.getCurrencyCode(),
                    view.getNameEntered(),
                    null,
                    view.getDescriptionEntered(),
                    System.currentTimeMillis(),
                    null
                    );
            dataRepository.saveAccount(account);
            view.close();
        }
    }

    @Override
    public void setView(NewAccountFragment view) {
        this.view = view;
    }
}
