package com.ioudebtcalculator.models;

public class Transaction {

    private int id;
    private int accountId;
    private String amount;
    private String currencyCode;
    private String postConversionAmount;
    private long createdTimestamp;
    private boolean deleted;

    public Transaction(int accountId, String amount, String currencyCode, long createdTimestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.createdTimestamp = createdTimestamp;
        this.deleted = false;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPostConversionAmount() {
        return postConversionAmount;
    }

    public void setPostConversionAmount(String postConversionAmount) {
        this.postConversionAmount = postConversionAmount;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
