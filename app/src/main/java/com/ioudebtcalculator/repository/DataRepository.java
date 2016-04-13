package com.ioudebtcalculator.repository;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;

import java.util.List;

public interface DataRepository {
    void getAccounts(DataRepositoryListener listener);
    void getAccountsBySearch(String searchString, DataRepositoryListener listener);
    void getAccount(int accountId, DataRepositoryListener listener);
    void setAccountBalance(Account account, String newBalance);
    void deleteAccount(Account account);
    void saveAccount(Account account);

    void getTransactions(int accountId, DataRepositoryListener listener);
    void getTransaction(int transactionId, DataRepositoryListener listener);
    void saveTransaction(Transaction transaction);
}
