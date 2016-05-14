package com.ioudebtcalculator.newaccount;

public interface NewAccountPresenter {

    void setView(NewAccountFragment view);

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
