package com.aeondynamics.cnit_355_project;
//UPDATED 11/17/24

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
    private static final String COLUMN_MONTHLY_PAYMENT = "monthlyPayment";
    private static final String COLUMN_INTEREST_RATE = "interestRate";
    private static final String COLUMN_LOAN_MATURITY = "loanMaturity";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_DOB + " TEXT)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLE_DEBT_PAYOFF + " (" +
                COLUMN_USER_ID + "TEXT PRIMARY KEY," +
                COLUMN_NAME + "TEXT PRIMARY KEY, " +
                COLUMN_DESCRIPTION + "TEXT," +
                COLUMN_MONTHLY_PAYMENT + "TEXT," +
                COLUMN_INTEREST_RATE + "TEXT," +
                COLUMN_LOAN_MATURITY + "TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBT_PAYOFF);
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

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // TODO make sure we can't have duplicate users
        //  this might be handled by the username primary key column
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " +
                COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        // Check to see if there is a user with that information in the database
        int rowCount = 0;

        // Used moved to ensure that the cursor is actually moving to a row of not-blank data
        boolean moved = true;
        while (moved && rowCount < 1 && !cursor.isLast()) {
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

    public boolean addDebtItem(String name, String description, double monthlyPayment, double interestRate, int loanMaturity) {
        return false;
        // TODO fill out this method
    }

    public boolean addExpenses(String userId, ArrayList<HashMap<String, String>> expenses) {
        // Check to see if the expenses table exists
        //  create it if it doesn't exist

        // Then for each expense in the expenses list, add that to the expenses table for the given user (by their userId)
        // should be inserting all of the items at once

        // Pass in a null for the expenseId for it to autoincrement,
        // as per: https://stackoverflow.com/questions/7905859/is-there-auto-increment-in-sqlite



        // return true if all inserts were successful, false if any failed


        // TODO finish this method
        return false;
    }
}
