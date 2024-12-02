package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity {
    private String accountCreationFailureMessage;

    private EditText username;
    private EditText password;
    private EditText passwordDoubleCheck;

    // dob: user's data of birth
    private EditText dob;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        username = findViewById(R.id.editTextNewUsername);
        password = findViewById(R.id.editTextNewPassword);
        passwordDoubleCheck = findViewById(R.id.editTextNewPasswordDoubleCheck);
        dob = findViewById(R.id.editTextNewDOB);
    }

    public void onClickBack(View view) {
        // Return to the login page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Attempt to create a new user account
     * If successful, move on to the Loading Activity to update the server/database
     * If not, display an error message
     */
    public void onClickSignUp(View view) {
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String passwordDoubleCheckString = passwordDoubleCheck.getText().toString();

        //can change later if group decides different approach
        String hashedPassword = Security.hashPassword(passwordString);
        String hashedPasswordDoubleCheck = Security.hashPassword(passwordDoubleCheckString);

        String dobString = dob.getText().toString();

        // using the hashed password in checkValid Account for security
        if (!checkValidAccount(usernameString, hashedPassword, hashedPasswordDoubleCheck)) {
            Toast.makeText(this, accountCreationFailureMessage, Toast.LENGTH_LONG).show();
        } else {
            // Add the user to the database
            boolean success = dbHelper.addUser(usernameString, hashedPassword, dobString);
            if (!success) {
                Toast.makeText(this,
                        "Failed to create new user", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(this, OverviewActivity.class);
            // fetch the newly created user's id and pass it to the new activity
            //  so that it can be used by the fragments when fetching data
            intent.putExtra("userId", usernameString);
            startActivity(intent);
        }

    }

    private boolean checkValidAccount(String username, String password, String passwordDoubleCheck) {
        //DONE make sure that the username hasn't been taken by another
        //  account, and that the password meets our requirements
        //DONE with verifying username, need to discuss pw requirements still
        //DONE set the value of accountCreationFailureMessage corresponding to what the error is
        //DONE

        if (username.isEmpty() || password.isEmpty() || passwordDoubleCheck.isEmpty()) {
            // Make sure a required field isn't empty so the new user won't have
            accountCreationFailureMessage = "Username and Password fields cannot be empty";
            return false;
        } else if (!password.equals(passwordDoubleCheck)) {
            // Make sure both password fields match
            accountCreationFailureMessage = "Both password fields must match";
            return false;
        } else if (dbHelper.checkExistingUser(username)) {
            // Check if the user already exists
            accountCreationFailureMessage = "Username already exists";
            return false;
        }

        // More validation here (e.g., password strength checks)
        return true;
    }
}
