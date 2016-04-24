package com.ioudebtcalculator.accountlist;

public interface AccountListPresenter {

    void setView(AccountListView view);

    void refreshAccounts(String type);

}
