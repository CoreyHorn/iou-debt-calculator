package com.ioudebtcalculator.repository.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.models.Account;

import javax.inject.Inject;

public class UpdateAccountsTask extends AsyncTask<Account, Void, Void> {

    @Inject
    SQLiteDatabase database;

    public UpdateAccountsTask() {
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    protected Void doInBackground(Account... accounts) {
        database.beginTransaction();
        for (Account account : accounts) {
            database.update(SQLiteImpl.TABLE_ACCOUNTS, account.getContentValues(), SQLiteImpl.KEY_ID + " =?",
                    new String[]{String.valueOf(account.getId())});
        }
        database.endTransaction();
        return null;
    }
}
