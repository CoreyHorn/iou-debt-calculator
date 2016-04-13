package com.ioudebtcalculator.repository;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;

import java.util.List;

public interface DataRepositoryListener {
    void onAccountListAvailable(List<Account> accounts);

    void onTransactionListAvailable(List<Transaction> transactions);
}
