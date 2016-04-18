package com.ioudebtcalculator.repository.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Account;

import javax.inject.Inject;

public class InsertAccountsTask extends AsyncTask<Account, Void, Void> {

    @Inject
    SQLiteImpl sqLite;

    public InsertAccountsTask() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(Account... accounts) {
        SQLiteDatabase database = sqLite.openDatabase();
        database.beginTransaction();
        for (Account account : accounts) {
            database.insert(SQLiteImpl.TABLE_ACCOUNTS, null, account.getContentValues());
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        sqLite.closeDatabase();
        return null;
    }
}