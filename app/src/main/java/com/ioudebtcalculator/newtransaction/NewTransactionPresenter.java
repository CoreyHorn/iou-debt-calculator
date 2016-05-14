package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.repository.DataRepositoryListener;

public interface NewTransactionPresenter {

    /**
     * Grab a list of Account representing all accounts in the database.
     * Passes data back through the listener.
     */
    void getAccounts(DataRepositoryListener listener);

    /**
     * Asks the presenter to validate the input and save the transaction if possible.
     * Should notify the view on how to proceed.
     */
    void validateInputAndSave();

    void setView(NewTransactionView view);

}
