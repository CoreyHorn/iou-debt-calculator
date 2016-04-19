package com.ioudebtcalculator.newaccount;

import java.util.List;

public interface NewAccountPresenter {

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

    void setView(NewAccountFragment view);
}
