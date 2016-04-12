package com.ioudebtcalculator.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.ioudebtcalculator.models.Account;
import com.ioudebtcalculator.models.Transaction;

import java.util.List;

public class SQLiteImpl extends SQLiteOpenHelper implements DataRepository {

    /**
     * Variables related to the database as a whole.
     */
    private static final String DATABASE_NAME = "programDB";
    private static final int DATABASE_VERSION = 2;

    /**
     * Variables related to both tables within the database.
     */
    private static final String KEY_ID = "key_id";
    private static final String FIELD_CREATED_TIMESTAMP = "createdTimestamp";
    private static final String FIELD_CURRENCY_CODE = "currencyCode";
    private static final String FIELD_DELETED = "deleted";

    /**
     * Variables related to the Account table.
     */
    private static final String TABLE_ACCOUNTS = "table_accounts";
    private static final String FIELD_CURRENT_BALANCE = "currentBalance";
    private static final String FIELD_NAME = "accountName";
    private static final String FIELD_IMAGE_URI = "imageUri";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_DUE_DATE_TIMESTAMP = "dueDateTimestamp";

    /**
     * Variables related to the Transaction table.
     */
    private static final String TABLE_TRANSACTIONS = "table_transactions";
    private static final String FIELD_ACCOUNT_ID = "accountId";
    private static final String FIELD_AMOUNT = "amount";
    private static final String FIELD_POST_CONVERSION_AMOUNT = "postConversionAmount";

    public SQLiteImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAccountsTableSQL =
                "CREATE TABLE " + TABLE_ACCOUNTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_CURRENT_BALANCE + " TEXT NOT NULL, "
                + FIELD_CURRENCY_CODE + " TEXT NOT NULL, "
                + FIELD_NAME + " TEXT NOT NULL, "
                + FIELD_IMAGE_URI + " TEXT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_CREATED_TIMESTAMP + " INTEGER NOT NULL, "
                + FIELD_DUE_DATE_TIMESTAMP + " INTEGER, "
                + FIELD_DELETED + " INTEGER NOT NULL)";

        String createTransactionsTableSQL =
                "CREATE TABLE " + TABLE_TRANSACTIONS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIELD_ACCOUNT_ID + " INTEGER NOT NULL, "
                + FIELD_AMOUNT + " TEXT NOT NULL, "
                + FIELD_CURRENCY_CODE + " TEXT NOT NULL, "
                + FIELD_POST_CONVERSION_AMOUNT + " TEXT NOT NULL, "
                + FIELD_CREATED_TIMESTAMP + " INTEGER NOT NULL, "
                + FIELD_DELETED + " INTEGER)";

        db.beginTransaction();
        db.execSQL(createAccountsTableSQL);
        db.execSQL(createTransactionsTableSQL);
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Returns a list of all Account objects in the database.
     * @return List of all accounts.
     */
    @Override
    public List<Account> getAccounts() {
        return null;
    }

    /**
     * Returns a list of Account objects containing the searchString in either the description
     * or name.
     * @param searchString string of characters to search for.
     * @return List of matching accounts.
     */
    @Override
    public List<Account> getAccountsBySearch(String searchString) {
        return null;
    }

    /**
     * Gets an account by account id.
     * @param accountId accountId to get.
     * @return matching Account object.
     */
    @Override
    public Account getAccount(int accountId) {
        return null;
    }

    /**
     * Changes an Account objects balance within the database.
     * @param accountId accountId of Account to change.
     * @param newBalance new balance of Account object.
     */
    @Override
    public void setAccountBalance(int accountId, String newBalance) {

    }

    /**
     * Marks an account as deleted in the database.
     * @param accountId accountId to mark as deleted.
     */
    @Override
    public void deleteAccount(int accountId) {

    }

    /**
     * Save account to database.
     * @param account account to save.
     */
    @Override
    public void saveAccount(Account account) {

    }

    /**
     * Get a list of Transaction objects related to given account.
     * @param accountId accountId to retrieve transactions for.
     * @return List of matching Transaction objects.
     */
    @Override
    public List<Transaction> getTransactions(int accountId) {
        return null;
    }

    /**
     * Get a Transaction object with the given transactionId.
     * @param transactionId transactionId to get.
     * @return matching Transaction object.
     */
    @Override
    public Transaction getTransaction(int transactionId) {
        return null;
    }

    /**
     * Save Transaction object to database.
     * @param transaction Transaction object to save.
     */
    @Override
    public void saveTransaction(Transaction transaction) {

    }
}
