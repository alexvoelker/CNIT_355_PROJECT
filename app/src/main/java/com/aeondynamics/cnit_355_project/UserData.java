package com.aeondynamics.cnit_355_project;

// TODO actually implement Serializable for this class
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * NOTE: FOR THE SAKE OF SIMPLICITY, ONLY USE THIS CLASS IF WE KNOW
 * THAT THE USER ACCOUNT ACTUALLY EXISTS (E.G. VALID USERNAME/PASSWORD)
 */
public class UserData implements IUserData, Serializable {
    String username;
    String password;
    String dateOfBirth;
    private transient Context context; // Use 'transient' to prevent serialization
//UPDATED 11/17/24

    /**
     * Constructor to create a UserData object for a pre-existing account
     * @param username the account's username
     * @param hashedPassword the account's password
     */
    UserData(String username, String hashedPassword) {
        this.username = username;
        this.password = hashedPassword;
    } //UPDATED 11/17/24

    /**
     * Constructor to create a UserData object for a newly signed-up account
     *
     * @param username       the account's username
     * @param hashedPassword the account's password
     * @param dateOfBirth    the account holder's date of birth
     */

    public UserData(String username, String hashedPassword, String dateOfBirth) {
        this.username = username;
        this.password = hashedPassword;
        this.dateOfBirth = dateOfBirth;
    } //UPDATED 11/17/24

    /**
     * Constructor to create a UserData object to work with the fetchUserData below
     *
     * @param username       the account's username
     * @param hashedPassword the account's password
     * @param dateOfBirth    the account holder's date of birth
     */

    UserData(String username, String hashedPassword, String dateOfBirth, Context context) {
        this.username = username;
        this.password = hashedPassword;
        this.dateOfBirth = dateOfBirth;
        this.context = context;
    }  //UPDATED 11/17/24

    /**
     * Fetch the data associated with a user's account,
     * and fill the fields in this class correspondingly
     * @return boolean the success of this operation
     */
    @Override
    public boolean fetchUserData() {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            // Query the database for the user's data
            String query = "SELECT * FROM users WHERE username = ?";
            cursor = db.rawQuery(query, new String[]{this.username});

            // Check if the user exists
            if (cursor.moveToFirst()) {
                // Populate the fields in this UserData object
                this.password = cursor.getString(cursor.getColumnIndexOrThrow("password")); // hashed password
                this.dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow("dob")); // other fields
                return true; // Successfully fetched data
            } else {
                return false; // User not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Handle any exceptions
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    } //UPDATED 11/17/24

    /**
     * Modify the database/server's records on this user's data
     * @return boolean the success of this operation
     */
    @Override
    public boolean updateUserData() {
        // Should we update the database/server based on what exists in
        // this class instance, or by the inputs to this function?
        return false;
    }
}

