package com.ioudebtcalculator.repository;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;

import java.util.List;

public interface DataRepository {
    List<Account> getAccounts();
    List<Account> getAccountsBySearch(String searchString);
    Account getAccount(int accountId);
    void setAccountBalance(int accountId, String newBalance);
    void deleteAccount(int accountId);
    void saveAccount(Account account);

    List<Transaction> getTransactions(int accountId);
    Transaction getTransaction(int transactionId);
    void saveTransaction(Transaction transaction);
}
