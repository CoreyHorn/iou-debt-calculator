package com.ioudebtcalculator.models;

public class Account {

    private int id;
    private String currentBalance;
    private String currencyCode;
    private String name;
    private String imageUri;
    private String description;
    private long createdTimestamp;
    private long dueDateTimestamp;
    private boolean deleted;

    public Account(String currentBalance, String currencyCode, String name, String imageUri,
                   String description, long createdTimestamp, long dueDateTimestamp) {
        this.currentBalance = currentBalance;
        this.currencyCode = currencyCode;
        this.name = name;
        this.imageUri = imageUri;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.dueDateTimestamp = dueDateTimestamp;
        this.deleted = false;
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
}
