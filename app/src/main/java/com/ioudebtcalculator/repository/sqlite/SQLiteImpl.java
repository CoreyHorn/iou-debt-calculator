package com.ioudebtcalculator.repository.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;
import com.ioudebtcalculator.repository.DataRepository;
import com.ioudebtcalculator.repository.DataRepositoryListener;

import java.util.concurrent.atomic.AtomicInteger;

public class SQLiteImpl extends SQLiteOpenHelper implements DataRepository {

    private AtomicInteger openReferenceCount = new AtomicInteger(0);
    private SQLiteDatabase database;

    /**
     * Variables related to the database as a whole.
     */
    private static final String DATABASE_NAME = "programDB";
    private static final int DATABASE_VERSION = 2;

    /**
     * Variables related to both tables within the database.
     */
    public static final String KEY_ID = "key_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CREATED_TIMESTAMP = "createdTimestamp";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_DELETED = "deleted";

    /**
     * Variables related to the Account table.
     */
    public static final String TABLE_ACCOUNTS = "table_accounts";
    public static final String FIELD_CURRENT_BALANCE = "currentBalance";
    public static final String FIELD_NAME = "accountName";
    public static final String FIELD_IMAGE_URI = "imageUri";
    public static final String FIELD_DUE_DATE_TIMESTAMP = "dueDateTimestamp";

    /**
     * Variables related to the Transaction table.
     */
    public static final String TABLE_TRANSACTIONS = "table_transactions";
    public static final String FIELD_ACCOUNT_ID = "accountId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_POST_CONVERSION_AMOUNT = "postConversionAmount";

    public SQLiteImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountsTableSQL =
                "CREATE TABLE '" + TABLE_ACCOUNTS + "' ('"
                + KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '"
                + FIELD_CURRENT_BALANCE + "' TEXT NOT NULL, '"
                + FIELD_CURRENCY_CODE + "' TEXT NOT NULL, '"
                + FIELD_NAME + "' TEXT NOT NULL, '"
                + FIELD_IMAGE_URI + "' TEXT, '"
                + FIELD_DESCRIPTION + "' TEXT, '"
                + FIELD_CREATED_TIMESTAMP + "' INTEGER NOT NULL, '"
                + FIELD_DUE_DATE_TIMESTAMP + "' INTEGER, '"
                + FIELD_DELETED + "' INTEGER NOT NULL);";

        String createTransactionsTableSQL =
                "CREATE TABLE '" + TABLE_TRANSACTIONS + "' ('"
                + KEY_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT, '"
                + FIELD_ACCOUNT_ID + "' INTEGER NOT NULL, '"
                + FIELD_AMOUNT + "' TEXT NOT NULL, '"
                + FIELD_CURRENCY_CODE + "' TEXT NOT NULL, '"
                + FIELD_DESCRIPTION + "' TEXT, '"
                + FIELD_POST_CONVERSION_AMOUNT + "' TEXT NOT NULL, '"
                + FIELD_CREATED_TIMESTAMP + "' INTEGER NOT NULL, '"
                + FIELD_DELETED + "' INTEGER);";

        db.beginTransaction();
        db.execSQL(createAccountsTableSQL);
        db.execSQL(createTransactionsTableSQL);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized SQLiteDatabase openDatabase() {
        if (openReferenceCount.incrementAndGet() == 1)
            database = getWritableDatabase();
        return database;
    }

    public synchronized void closeDatabase() {
        if (openReferenceCount.decrementAndGet() == 0)
            database.close();
    }

    /**
     * Returns a list of all Account objects in the database.
     * @param listener listener to return result through.
     */
    @Override
    public void getAccounts(DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS;
        new QueryAccountsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Returns a list of all Account objects with an amount >= 0
     * @param listener listener to return result through.
     */
    @Override
    public void getLoans(DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS
                + " WHERE " + FIELD_CURRENT_BALANCE + " >= 0";
        new QueryAccountsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Returns a list of all Account objects with an amount <= 0
     * @param listener listener to return result through.
     */
    @Override
    public void getDebts(DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS
                + " WHERE " + FIELD_CURRENT_BALANCE + " >= 0";
        new QueryAccountsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Returns a list of Account objects containing the searchString in either the description
     * or name.
     * @param searchString string of characters to search for.
     * @param listener listener to return result through.
     */
    @Override
    public void getAccountsBySearch(String searchString, DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE "
                + FIELD_NAME + " LIKE '%" + searchString + "%' OR "
                + FIELD_DESCRIPTION + " LIKE '%" + searchString + "%'";
        new QueryAccountsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Gets an account by account id.
     * @param accountId accountId to get.
     * @param listener listener to return result through.
     */
    @Override
    public void getAccount(int accountId, DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE "
                + KEY_ID + " = " + String.valueOf(accountId);
        new QueryAccountsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Changes an Account objects balance within the database.
     * @param account account with updated values.
     */
    @Override
    public void setAccountBalance(Account account, String newBalance) {
        account.setCurrentBalance(newBalance);
        new UpdateAccountsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, account);
    }

    /**
     * Marks an account as deleted in the database.
     * @param account account to delete.
     */
    @Override
    public void deleteAccount(Account account) {
        account.setDeleted(true);
        new UpdateAccountsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, account);
    }

    /**
     * Save account to database.
     * @param account account to save.
     */
    @Override
    public void saveAccount(Account account) {
        new InsertAccountsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, account);
    }

    /**
     * Get a list of Transaction objects related to given account.
     * @param accountId accountId to retrieve transactions for.
     * @param listener listener to return result through.
     */
    @Override
    public void getTransactions(int accountId, DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE "
                + FIELD_ACCOUNT_ID + " = " + String.valueOf(accountId);
        new QueryTransactionsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Get a Transaction object with the given transactionId.
     * @param transactionId transactionId to get.
     * @param listener listener to return result through.
     */
    @Override
    public void getTransaction(int transactionId, DataRepositoryListener listener) {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE "
                + KEY_ID + " = " + String.valueOf(transactionId);
        new QueryTransactionsTask(listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    /**
     * Save Transaction object to database.
     * @param transaction Transaction object to save.
     */
    @Override
    public void saveTransaction(Transaction transaction) {
        new InsertTransactionsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, transaction);
    }
}
