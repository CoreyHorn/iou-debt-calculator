package com.ioudebtcalculator.repository.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class QueryAccountsTask extends AsyncTask<String, Void, List<Account>> {

    @Inject
    SQLiteImpl sqLite;

    private DataRepositoryListener listener;

    public QueryAccountsTask(DataRepositoryListener listener) {
        this.listener = listener;
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected List<Account> doInBackground(String... queries) {
        List<Account> accounts = new ArrayList<>();
        SQLiteDatabase database = sqLite.openDatabase();
        database.beginTransaction();
        for (String query : queries) {
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst())
                do {
                    Account account = new Account(
                            cursor.getInt(cursor.getColumnIndex(
                                    SQLiteImpl.KEY_ID
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_CURRENT_BALANCE
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_CURRENCY_CODE
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_NAME
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_IMAGE_URI
                            )),
                            cursor.getString(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_DESCRIPTION
                            )),
                            cursor.getLong(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_CREATED_TIMESTAMP
                            )),
                            cursor.getLong(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_DUE_DATE_TIMESTAMP
                            )),
                            cursor.getInt(cursor.getColumnIndex(
                                    SQLiteImpl.FIELD_DELETED
                            )) > 0
                    );
                    accounts.add(account);
                } while (cursor.moveToNext());
            cursor.close();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        sqLite.closeDatabase();
        return accounts;
    }

    @Override
    protected void onPostExecute(List<Account> accounts) {
        super.onPostExecute(accounts);
        listener.onAccountListAvailable(accounts);
    }
}
