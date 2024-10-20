package com.aeondynamics.cnit_355_project;

// TODO actually implement Serializable for this class
import java.io.Serializable;

/**
 * NOTE: FOR THE SAKE OF SIMPLICITY, ONLY USE THIS CLASS IF WE KNOW
 * THAT THE USER ACCOUNT ACTUALLY EXISTS (E.G. VALID USERNAME/PASSWORD)
 */
public class UserData implements IUserData, Serializable {
    String username;
    String password;

    /**
     * Constructor to create a UserData object for a pre-existing account
     * @param username the account's username
     * @param password the account's password
     */
    UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor to create a UserData object for a newly signed-up account
     * @param username the account's username
     * @param password the account's password
     * @param dateOfBirth the account holder's date of birth
     */
    UserData(String username, String password, String dateOfBirth) {
        // TODO add more parameters as necessary for account creation (as listed in the SignUpActivity)
    }

    /**
     * Fetch the data associated with a user's account,
     * and fill the fields in this class correspondingly
     * @return boolean the success of this operation
     */
    @Override
    public boolean fetchUserData() {
        return false;
    }

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

