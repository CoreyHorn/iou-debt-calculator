package com.ioudebtcalculator.newaccount;

import java.util.List;

public interface NewAccountPresenter {

    void setView(NewAccountFragment view);

    /**
     * Should return a list of currencies to display to the user.
     * Should have the default currency as the first item in the list.
     * @return List of available currencies
     */
    List<String> getAvailableCurrencies();

    /**
     * Should allow the user to choose a contact to associate with the account.
     */
    void setContact();

    /**
     * Asks the presenter to validate the input and save the transaction if possible.
     * Should notify the view on how to proceed.
     */
    void validateInputAndSave();
}
