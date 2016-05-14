package com.ioudebtcalculator.repository.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Transaction;

import javax.inject.Inject;

public class InsertTransactionsTask extends AsyncTask<Transaction, Void, Void> {

    @Inject
    SQLiteImpl sqLite;

    DatabaseOperationListener operationListener;

    public InsertTransactionsTask(DatabaseOperationListener operationListener) {
        App.getInstance().getAppComponent().inject(this);
        this.operationListener = operationListener;
    }

    @Override
    protected Void doInBackground(Transaction... transactions) {
        SQLiteDatabase database = sqLite.openDatabase();
        database.beginTransaction();
        for (Transaction transaction : transactions) {
            database.insert(SQLiteImpl.TABLE_TRANSACTIONS, null, transaction.getContentValues());
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        sqLite.closeDatabase();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        operationListener.onOperationComplete();
    }
}
