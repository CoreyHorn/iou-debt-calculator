package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.models.Account;

import java.util.List;

public interface NewTransactionPresenter {

    /**
     * Grab a list of Account representing all accounts in the database.
     * @return List of AccountMetadata
     */
    List<Account> getAccounts();


    /**
     * Should return a list of currencies to display to the user.
     * Should have the default currency as the first item in the list.
     * @return List of available currencies
     */
    List<String> getAvailableCurrencies();

    /**
     * Asks the presenter to validate the input and save the transaction if possible.
     * Should notify the view on how to proceed.
     */
    void validateInputAndSave();

}
