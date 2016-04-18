package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.R;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class NewTransactionPresenterImpl implements NewTransactionPresenter{

    @Inject
    DataRepository dataRepository;

    NewTransactionView view;

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
        if (view.getBorrowOrLoanCheckedId() == -1) {
            view.showErrorMessage(App.getInstance().getResources()
                    .getString(R.string.new_transaction_borrow_or_loan_error));
        }
        if (view.getTransactionAmountEntered().equals("")) {
            view.setTransactionAmountError(App.getInstance().getResources()
                    .getString(R.string.new_transaction_amount_error));
        }
    }
}
