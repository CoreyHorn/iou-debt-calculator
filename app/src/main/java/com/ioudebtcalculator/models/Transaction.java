package com.ioudebtcalculator.models;

import android.content.ContentValues;

import com.ioudebtcalculator.repository.sqlite.SQLiteImpl;

public class Transaction {

    private int id;
    private int accountId;
    private String amount;
    private String currencyCode;
    private String postConversionAmount;
    private String description;
    private long createdTimestamp;
    private boolean deleted;

    public Transaction(int accountId, String amount, String currencyCode, String postConversionAmount,
                       String description, long createdTimestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.postConversionAmount = postConversionAmount;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.deleted = false;
    }

    public Transaction(int id, int accountId, String amount, String currencyCode,
                       String postConversionAmount, long createdTimestamp, boolean deleted) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.postConversionAmount = postConversionAmount;
        this.createdTimestamp = createdTimestamp;
        this.deleted = deleted;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteImpl.FIELD_ACCOUNT_ID, getAccountId());
        contentValues.put(SQLiteImpl.FIELD_AMOUNT, getAmount());
        contentValues.put(SQLiteImpl.FIELD_CURRENCY_CODE, getCurrencyCode());
        contentValues.put(SQLiteImpl.FIELD_DESCRIPTION, getDescription());
        contentValues.put(SQLiteImpl.FIELD_POST_CONVERSION_AMOUNT, getPostConversionAmount());
        contentValues.put(SQLiteImpl.FIELD_CREATED_TIMESTAMP, getCreatedTimestamp());
        contentValues.put(SQLiteImpl.FIELD_DELETED, isDeleted());
        return contentValues;
    }
}
