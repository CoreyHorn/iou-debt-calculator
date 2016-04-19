package com.ioudebtcalculator.newaccount;

public interface NewAccountView {

    void close();

    /**
     * Input Validation
     */
    int getBorrowOrLoanCheckedId();
    String getAmountEntered();
    String getNameEntered();
    String getDescriptionEntered();

    void setAmountError();
    void setBorrowOrLoanError();
    void setAccountNameError();

}
