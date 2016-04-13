package com.ioudebtcalculator.repository.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Transaction;

import javax.inject.Inject;

public class InsertTransactionsTask extends AsyncTask<Transaction, Void, Void> {

    @Inject
    SQLiteImpl sqLite;

    public InsertTransactionsTask() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(Transaction... transactions) {
        SQLiteDatabase database = sqLite.openDatabase();
        database.beginTransaction();
        for (Transaction transaction : transactions) {
            database.insert(SQLiteImpl.TABLE_TRANSACTIONS, null, transaction.getContentValues());
        }
        database.endTransaction();
        sqLite.closeDatabase();
        return null;
    }
}
