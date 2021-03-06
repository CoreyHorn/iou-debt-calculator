package com.ioudebtcalculator.newaccount;

public interface NewAccountView {

    void close();

    /**
     * Input Validation
     */
    int getBorrowOrLoanCheckedId();
    String getAmountEntered();
    String getNameEntered();
    String getCurrencyCode();
    String getDescriptionEntered();
    String getImageUri();
    long getDueDate();

    void setAmountError();
    void setBorrowOrLoanError();
    void setAccountNameError();
}
