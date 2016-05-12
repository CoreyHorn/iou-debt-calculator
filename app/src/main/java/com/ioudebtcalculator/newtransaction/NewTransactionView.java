package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.models.Account;

public interface NewTransactionView {

    /**
     * Used to close the view.
     */
    void close();

    int getBorrowOrLoanCheckedId();
    String getAmountEntered();
    Account getSelectedAccount();
    String getSelectedCurrency();

    void setBorrowOrLoanError();
    void setTransactionAmountError();
    void setSelectedAccountError();
}
