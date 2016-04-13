package com.ioudebtcalculator.repository.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Transaction;

import javax.inject.Inject;

public class InsertTransactionsTask extends AsyncTask<Transaction, Void, Void> {

    @Inject
    SQLiteDatabase database;

    public InsertTransactionsTask() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(Transaction... transactions) {
        database.beginTransaction();
        for (Transaction transaction : transactions) {
            database.insert(SQLiteImpl.TABLE_TRANSACTIONS, null, transaction.getContentValues());
        }
        database.endTransaction();
        return null;
    }
}
