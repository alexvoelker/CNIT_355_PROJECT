package com.aeondynamics.cnit_355_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DOB = "dob";

    private static final String TABLE_DEBT_PAYOFF = "debt_payoff";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ORIGINAL_COST = "monthlyPayment";
    private static final String COLUMN_INTEREST_RATE = "interestRate";
    private static final String COLUMN_LOAN_MATURITY = "loanMaturity";
    private static final String COLUMN_EXPENSE_ITEM_TYPE = "expenseItemType";
    private static final String COLUMN_EXPENSE_PRICE = "expensePrice";
    private static final String TABLE_EXPENSES = "expenseTable";
    private static final String COLUMN_EXPENSE_ID = "expenseId";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable;
        createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_DOB + " TEXT NOT NULL)";
        db.execSQL(createTable);

       createTable = "CREATE TABLE " + TABLE_DEBT_PAYOFF + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_USER_ID + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_ORIGINAL_COST + " TEXT NOT NULL," +
                COLUMN_INTEREST_RATE + " TEXT NOT NULL," +
                COLUMN_LOAN_MATURITY + " TEXT NOT NULL)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLE_EXPENSES + " (" +
                COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY," +
                COLUMN_USER_ID + " TEXT NOT NULL," +
                COLUMN_EXPENSE_ITEM_TYPE + " TEXT NOT NULL," +
                COLUMN_EXPENSE_PRICE + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBT_PAYOFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }


    public boolean addUser(String username, String password, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_DOB, dob);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Return true if insertion is successful
    }

    public boolean checkExistingUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        // Check to see if there is a user with that information in the database
        int rowCount = 0;

        // Used moved to ensure that the cursor is actually moving to a row of not-blank data
        boolean moved = cursor.moveToFirst();
        while (moved && rowCount < 1) {
            rowCount++;
            moved = cursor.moveToNext();
        }

        boolean userExists = rowCount > 0;
        cursor.close();
        db.close();
        return userExists;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " +
                COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        // Check to see if there is a user with that information in the database
        int rowCount = 0;

        // Used moved to ensure that the cursor is actually moving to a row of not-blank data
        boolean moved = cursor.moveToFirst();
        while (moved && rowCount < 1) {
            rowCount++;
            moved = cursor.moveToNext();
        }

        // The user is valid if there is only one of them per username.
        // Checking for the combination of username and password would show (if correct)
        // that there is one record present with that combination, indicating a correct login.
        // If there are zero of these combinations, the password is not correct for the username.
        // If there is somehow more than one of these combinations present in the database,
        // something's wrong and this is defensibility a wrong login
        boolean validUserExists = rowCount == 1;

        cursor.close();
        db.close();
        return validUserExists; // Return true if username/password is valid
    }

    public boolean addDebtItem(String user_id, String name, String description, double monthlyPayment, double interestRate, int loanMaturity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_USER_ID, user_id);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_ORIGINAL_COST, "" + monthlyPayment);
        values.put(COLUMN_INTEREST_RATE, "" + interestRate);
        values.put(COLUMN_LOAN_MATURITY, "" + loanMaturity);
        long result = db.insert(TABLE_DEBT_PAYOFF, null, values);

        db.close();

        return result != -1;
    }

    public boolean addExpenses(String userId, ArrayList<HashMap<String, String>> expenses) {
        // TODO finish this method
        // Check to see if the expenses table exists
        //  create it if it doesn't exist

        // Then for each expense in the expenses list, add that to the expenses table for the given user (by their userId)
        // should be inserting all of the items at once

        // Pass in a null for the expenseId for it to autoincrement,
        // as per: https://stackoverflow.com/questions/7905859/is-there-auto-increment-in-sqlite
        boolean success;
        SQLiteDatabase db = this.getWritableDatabase();

        for (HashMap<String, String> expense : expenses) {
            String type = expense.get("type");
            String cost = expense.get("cost");

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_EXPENSE_ITEM_TYPE, type);
            values.put(COLUMN_EXPENSE_PRICE, cost);
            success = db.insert(TABLE_EXPENSES, null, values) != -1;

            if (!success)
                return false;
        }

        // return true if all inserts were successful, false if any failed

        return true;
    }

    // TODO delete this later
    public void openDB() {
        SQLiteDatabase db = this.getWritableDatabase();
    }
}
