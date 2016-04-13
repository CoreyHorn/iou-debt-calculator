package com.ioudebtcalculator.models;

import android.content.ContentValues;

import com.ioudebtcalculator.repository.sqlite.SQLiteImpl;

public class Account {

    private int id;
    private String currentBalance;
    private String currencyCode;
    private String name;
    private String imageUri;
    private String description;
    private long createdTimestamp;
    private long dueDateTimestamp;
    private boolean deleted = false;

    public Account(String currentBalance, String currencyCode, String name, String imageUri,
                   String description, long createdTimestamp, long dueDateTimestamp) {
        this.currentBalance = currentBalance;
        this.currencyCode = currencyCode;
        this.name = name;
        this.imageUri = imageUri;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.dueDateTimestamp = dueDateTimestamp;
    }

    public Account(int id, String currentBalance, String currencyCode, String name, String imageUri,
                   String description, long createdTimestamp, long dueDateTimestamp,
                   boolean deleted) {
        this.id = id;
        this.currentBalance = currentBalance;
        this.currencyCode = currencyCode;
        this.name = name;
        this.imageUri = imageUri;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.dueDateTimestamp = dueDateTimestamp;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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

    public long getDueDateTimestamp() {
        return dueDateTimestamp;
    }

    public void setDueDateTimestamp(long dueDateTimestamp) {
        this.dueDateTimestamp = dueDateTimestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteImpl.FIELD_CURRENT_BALANCE, getCurrentBalance());
        contentValues.put(SQLiteImpl.FIELD_CURRENCY_CODE, getCurrencyCode());
        contentValues.put(SQLiteImpl.FIELD_NAME, getName());
        contentValues.put(SQLiteImpl.FIELD_IMAGE_URI, getImageUri());
        contentValues.put(SQLiteImpl.FIELD_DESCRIPTION, getDescription());
        contentValues.put(SQLiteImpl.FIELD_CREATED_TIMESTAMP, getCreatedTimestamp());
        contentValues.put(SQLiteImpl.FIELD_DUE_DATE_TIMESTAMP, getDueDateTimestamp());
        //TODO: May need to refactor this next line based on boolean handling.
        contentValues.put(SQLiteImpl.FIELD_DELETED, isDeleted());
        return contentValues;
    }
}
