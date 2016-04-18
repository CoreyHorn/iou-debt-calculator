package com.ioudebtcalculator.newtransaction;

public interface NewTransactionView {

    void showErrorMessage(String errorMessage);

    /**
     * Used to close the view.
     */
    void close();

    int getBorrowOrLoanCheckedId();

    String getTransactionAmountEntered();

    void setTransactionAmountError(String error);


}
