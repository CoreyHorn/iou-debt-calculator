package com.ioudebtcalculator.accountlist;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.List;

import javax.inject.Inject;

public class AccountListPresenterImpl implements AccountListPresenter {

    @Inject
    DataRepository dataRepository;

    private AccountListView view;
    private List<Account> currentAccounts;

    private DataRepositoryListener dataRepositoryListener = new DataRepositoryListener() {
        @Override
        public void onAccountListAvailable(List<Account> accounts) {
            if (accounts != currentAccounts) {
                view.displayAccounts(accounts);
                currentAccounts = accounts;
            }
        }

        @Override
        public void onTransactionListAvailable(List<Transaction> transactions) {

        }
    };

    @Override
    public void setView(AccountListView view) {
        this.view = view;
    }

    @Override
    public void refreshAccounts(String type) {
        switch(type) {
            case AccountListFragment.TYPE_DEFAULT:
                dataRepository.getAccounts(dataRepositoryListener);
                break;
            case AccountListFragment.TYPE_DEBTS:
                dataRepository.getDebts(dataRepositoryListener);
                break;
            case AccountListFragment.TYPE_LOANS:
                dataRepository.getLoans(dataRepositoryListener);
                break;
        }

    }
}
