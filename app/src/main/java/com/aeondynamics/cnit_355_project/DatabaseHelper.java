package com.aeondynamics.cnit_355_project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database fields
    private static final String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 1;

    // TABLE_USERS fields
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_DOB = "dob";

    // TABLE_DEBT_PAYOFF fields
    private static final String TABLE_DEBT_PAYOFF = "debt_payoff";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DEBT_ID = "debtId";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ORIGINAL_COST = "monthlyPayment";
    private static final String COLUMN_INTEREST_RATE = "interestRate";
    private static final String COLUMN_LOAN_MATURITY = "loanMaturity";

    // TABLE_EXPENSES fields (also uses COLUMN_USER_ID in table)
    private static final String TABLE_EXPENSES = "expenseTable";
    private static final String COLUMN_EXPENSE_ID = "expenseId";
    private static final String COLUMN_EXPENSE_ITEM_TYPE = "expenseItemType";
    private static final String COLUMN_EXPENSE_PRICE = "expensePrice";

    // TABLE_BILLS fields (also uses COLUMN_USER_ID in table)
    private static final String TABLE_BILLS = "billTable";
    private static final String COLUMN_BILL_ID = "billId";
    private static final String COLUMN_BILL_TITLE = "billTitle";
    private static final String COLUMN_BILL_DESCRIPTION = "billDescription";
    private static final String COLUMN_BILL_AMOUNT = "billAmount";
    private static final String COLUMN_BILL_DATE = "billDate";


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
                COLUMN_DEBT_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT NOT NULL, " +
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

        createTable = "CREATE TABLE " + TABLE_BILLS + " (" +
                COLUMN_BILL_ID + " INTEGER PRIMARY KEY," +
                COLUMN_USER_ID + " TEXT NOT NULL," +
                COLUMN_BILL_TITLE + " TEXT NOT NULL," +
                COLUMN_BILL_DESCRIPTION + " TEXT NOT NULL," +
                COLUMN_BILL_AMOUNT + " TEXT NOT NULL," +
                COLUMN_BILL_DATE + " TEXT NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEBT_PAYOFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
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

    public boolean addBillItem(String user_id, String billTitle, String billDescription,
                               String billAmount, String billDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Pass in a null (e.g. don't pass anything) for the billId for it to autoincrement
        values.put(COLUMN_USER_ID, user_id);
        values.put(COLUMN_BILL_TITLE, billTitle);
        values.put(COLUMN_BILL_DESCRIPTION, billDescription);
        values.put(COLUMN_BILL_AMOUNT, billAmount);
        values.put(COLUMN_BILL_DATE, billDate);
        long result = db.insert(TABLE_BILLS, null, values);

        db.close();

        return result != -1;
    }

    /**
     * Fetch the bills associated with a given user account
     *
     * @param userId the account to fetch bills on
     * @return A List of Bill objects for each bill being tracked for this user.
     * Returns null if no bills are being tracked (no results in database)
     */
    @SuppressLint("Range")
    public List<Bill> getUserBills(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Bill> billList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BILLS +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});

        // Add each debt the person has to the debtList
        boolean moved = cursor.moveToFirst();
        if (!moved) {
            return null;
        }

        while (moved) {
            try {
                String billTitle = cursor.getString(cursor.getColumnIndex(COLUMN_BILL_TITLE));
                String billDescription = cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DESCRIPTION));
                String billAmount = cursor.getString(cursor.getColumnIndex(COLUMN_BILL_AMOUNT));
                String billDate = cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DATE));
                billList.add(new Bill(billTitle, billDescription, billAmount, billDate));
            } catch (NumberFormatException ex) {
                // Catch exceptions for when the data in the database if malformed
            }

            moved = cursor.moveToNext();
        }

        db.close();
        return billList;
    }

    public boolean addDebtItem(String user_id, String name, String description,
                               double monthlyPayment, double interestRate, int loanMaturity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Pass in a null (e.g. don't pass anything) for the debtId for it to autoincrement
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_USER_ID, user_id);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_ORIGINAL_COST, String.format(Locale.US, "%.2f", monthlyPayment));
        values.put(COLUMN_INTEREST_RATE, interestRate);
        values.put(COLUMN_LOAN_MATURITY, "" + loanMaturity);
        long result = db.insert(TABLE_DEBT_PAYOFF, null, values);

        db.close();

        return result != -1;
    }

    /**
     * Fetch the debts associated with a given user account
     *
     * @param userId the account to fetch debts on
     * @return A List of Debt objects for each debt being tracked for this user.
     * Returns null if no debts are being tracked (no results in database)
     */
    @SuppressLint("Range")
    public List<Debt> getUserDebts(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Debt> debtList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEBT_PAYOFF +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});

        // Add each debt the person has to the debtList
        boolean moved = cursor.moveToFirst();
        if (!moved) {
            return null;
        }

        while (moved) {
            try {
                String debtName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String debtDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

                String originalCost = cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_COST));
                String interestRate = cursor.getString(cursor.getColumnIndex(COLUMN_INTEREST_RATE));
                String numberOfMonths = cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_MATURITY));

                String monthlyContribution = getMonthlyContribution(originalCost,
                        interestRate, numberOfMonths);


                debtList.add(new Debt(debtName, debtDescription, monthlyContribution, numberOfMonths));
            } catch (NumberFormatException ex) {
                // Catch exceptions for when the data in the database if malformed
            }

            moved = cursor.moveToNext();
        }

        db.close();
        return debtList;
    }

    /**
     * Calculate the contribution amount per month to pay off the
     * debt by the specified time and with the given interest rate
     *
     * @param originalCost
     * @param interestRate
     * @param numberOfMonths
     * @return the monthly contribution dollar value
     * @throws NumberFormatException
     */
    private String getMonthlyContribution(String originalCost, String interestRate,
                                          String numberOfMonths) throws NumberFormatException {
        double total_cost = Double.parseDouble(originalCost);
        double int_rate = Double.parseDouble(interestRate);
        int months = Integer.parseInt(numberOfMonths);

        double monthly_contribution = Math.ceil((total_cost + total_cost * (int_rate / 100)) / months);

        return "" + monthly_contribution;
    }

    public boolean addExpenses(String userId, ArrayList<HashMap<String, String>> expenses) {
        // For each expense in the expenses list, add that to the expenses table for the given user (by their userId)

        boolean success;
        SQLiteDatabase db = this.getWritableDatabase();

        for (HashMap<String, String> expense : expenses) {
            String type = expense.get("type");
            String cost = expense.get("cost");

            ContentValues values = new ContentValues();
            // Pass in a null (e.g. don't pass anything) for the expenseId for it to autoincrement,
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

    @SuppressLint("Range")
    public List<HashMap<String, String>> getOverviewDataExpenses(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<HashMap<String, String>> expenseList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSES +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});

        // Add each debt the person has to the debtList
        boolean moved = cursor.moveToFirst();
        if (!moved) {
            return null;
        }

        while (moved) {
            try {
                HashMap<String, String> expense = new HashMap<>();
                expense.put("name", cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_ITEM_TYPE)));
                expense.put("cost", cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_PRICE)));
                expenseList.add(expense);
            } catch (NumberFormatException ex) {
                // Catch exceptions for when the data in the database if malformed
            }

            moved = cursor.moveToNext();
        }

        db.close();
        return expenseList;
    }

    @SuppressLint("Range")
    public List<HashMap<String, String>> getOverviewDataDebts(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<HashMap<String, String>> debtList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DEBT_PAYOFF +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});

        // Add each debt the person has to the debtList
        boolean moved = cursor.moveToFirst();
        if (!moved) {
            return null;
        }

        while (moved) {
            try {
                HashMap<String, String> debt = new HashMap<>();
                debt.put("name", cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));

                String originalCost = cursor.getString(cursor.getColumnIndex(COLUMN_ORIGINAL_COST));
                String interestRate = cursor.getString(cursor.getColumnIndex(COLUMN_INTEREST_RATE));
                String numberOfMonths = cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_MATURITY));
                debt.put("cost", getMonthlyContribution(originalCost, interestRate, numberOfMonths));

                debtList.add(debt);
            } catch (NumberFormatException ex) {
                // Catch exceptions for when the data in the database if malformed
            }

            moved = cursor.moveToNext();
        }

        db.close();
        return debtList;
    }

    @SuppressLint("Range")
    public List<HashMap<String, String>> getOverviewDataBills(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<HashMap<String, String>> billList  = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BILLS +
                " WHERE " + COLUMN_USER_ID + " = ?", new String[]{userId});

        // Add each debt the person has to the debtList
        boolean moved = cursor.moveToFirst();
        if (!moved) {
            return null;
        }

        while (moved) {
            try {
                HashMap<String, String> bill = new HashMap<>();
                bill.put("name", cursor.getString(cursor.getColumnIndex(COLUMN_BILL_TITLE)));
                bill.put("cost", cursor.getString(cursor.getColumnIndex(COLUMN_BILL_AMOUNT)));
                billList.add(bill);
            } catch (NumberFormatException ex) {
                // Catch exceptions for when the data in the database if malformed
            }

            moved = cursor.moveToNext();
        }

        db.close();
        return billList;
    }

}
