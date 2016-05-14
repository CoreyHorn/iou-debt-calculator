package com.ioudebtcalculator.newtransaction;

import com.ioudebtcalculator.models.Account;

public interface NewTransactionView {

    void close();

    int getBorrowOrLoanCheckedId();
    String getAmountEntered();
    Account getSelectedAccount();
    String getSelectedCurrency();
    String getDescription();

    void setBorrowOrLoanError();
    void setTransactionAmountError();
    void setSelectedAccountError();
    void setConversionError();
}
