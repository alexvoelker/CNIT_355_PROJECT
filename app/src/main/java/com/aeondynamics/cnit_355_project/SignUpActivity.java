package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
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
    IUserData data;
    String accountCreationFailureMessage;

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
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        EditText username = findViewById(R.id.editTextNewUsername);
        String usernameString = username.getText().toString();
        EditText password = findViewById(R.id.editTextNewPassword);
        String passwordString = password.getText().toString();

        //can change later if group decides different approach
        String hashedPassword = Security.hashPassword(passwordString);

        // dob -> user's data of birth
        EditText dob = findViewById(R.id.editTextNewDOB);
        String dobString = dob.getText().toString();
        // TODO add more fields as necessary to create a proper account

        if (!checkValidAccount(usernameString, hashedPassword)) {
            Toast.makeText(this, accountCreationFailureMessage, Toast.LENGTH_SHORT).show();
        } else {
            //data = new UserData(usernameString, passwordString, dobString);
            data = new UserData(usernameString, hashedPassword, dobString);
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtra("userData", (Serializable) data);
            intent.putExtra("requestType", RequestType.UPDATE_SERVER_RECORDS);
            startActivity(intent);
        } //UPDATED 11/17/24 with hashed password stuff

    }

    private boolean checkValidAccount(String username, String password) {
        // TODO make sure that the username hasn't been taken by another
        //  account, and that the password meets our requirements
        //DONE with verifying username, need to discuss pw requirements still
        // TODO set the value of accountCreationFailureMessage corresponding to what the error is
        //DONE
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (username.isEmpty() || password.isEmpty()) {
            accountCreationFailureMessage = "Username and Password cannot be empty";
            return false;
        }
        if (dbHelper.checkLogin(username, password)) {
            accountCreationFailureMessage = "Username already exists";
            return false;
        }
        // More validation here (e.g., password strength checks)
        return true;
    } //UPDATED 11/17/24
}
