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
        EditText username = findViewById(R.id.editTextNewUsername);
        String usernameString = username.getText().toString();
        EditText password = findViewById(R.id.editTextNewPassword);
        String passwordString = password.getText().toString();

        // dob -> user's data of birth
        EditText dob = findViewById(R.id.editTextNewDOB);
        String dobString = dob.getText().toString();
        // TODO add more fields as necessary to create a proper account

        if (!checkValidAccount(usernameString, passwordString)) {
            Toast.makeText(this, accountCreationFailureMessage, Toast.LENGTH_SHORT).show();
        } else {
            data = new UserData(usernameString, passwordString, dobString);
            Intent intent = new Intent(this, LoadingActivity.class);
            intent.putExtra("userData", (Serializable) data);
            intent.putExtra("requestType", RequestType.UPDATE_SERVER_RECORDS);
            startActivity(intent);
        }

    }

    private boolean checkValidAccount(String username, String password) {
        // TODO make sure that the username hasn't been taken by another
        //  account, and that the password meets our requirements

        // TODO set the value of accountCreationFailureMessage corresponding to what the error is

        return false;
    }
}