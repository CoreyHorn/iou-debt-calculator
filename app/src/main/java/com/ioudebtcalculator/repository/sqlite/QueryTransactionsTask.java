package com.ioudebtcalculator.repository.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QueryTransactionsTask extends AsyncTask<String, Void, List<Transaction>> {

    @Inject
    SQLiteImpl sqLite;

    private DataRepositoryListener listener;

    public QueryTransactionsTask(DataRepositoryListener listener) {
        this.listener = listener;
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected List<Transaction> doInBackground(String... queries) {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase database = sqLite.openDatabase();
        database.beginTransaction();
        for (String query : queries) {
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst())
                do {
                    Transaction transaction = new Transaction(
                            cursor.getInt(cursor.getColumnIndex(
                                    SQLiteImpl.KEY_ID
                            )),
                            cursor.getInt(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_ACCOUNT_ID
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_AMOUNT
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_CURRENCY_CODE
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_POST_CONVERSION_AMOUNT
                            )),
                            cursor.getLong(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_CREATED_TIMESTAMP
                            )),
                            cursor.getInt(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_DELETED
                            )) > 0
                    );
                    transactions.add(transaction);
                } while (cursor.moveToNext());
            cursor.close();
        }
        database.endTransaction();
        sqLite.closeDatabase();
        return transactions;
    }

    @Override
    protected void onPostExecute(List<Transaction> transactions) {
        super.onPostExecute(transactions);
        listener.onTransactionListAvailable(transactions);
    }
}
