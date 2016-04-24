package com.ioudebtcalculator.accountlist;

import com.ioudebtcalculator.models.Account;

import java.util.List;

public interface AccountListView {

    void displayAccounts(List<Account> accountList);
}
