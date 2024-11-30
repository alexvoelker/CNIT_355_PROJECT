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

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Attempt to log the user in.
     * If successful, move on to the Loading Activity to load the data
     * If not, display an error message
     */
    public void onClickLogin(View view) {
        boolean loginSuccess = false;
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        // UPDATED 11/17/24 Hash the password before checking against the database
        String hashedPassword = Security.hashPassword(passwordString);

        loginSuccess = dbHelper.checkLogin(usernameString, hashedPassword);

        // If so, set loginSuccess to true
        // Otherwise, tell the user that the username/password is incorrect

        if (loginSuccess) {
            Intent intent = new Intent(this, OverviewActivity.class);
            // fetch the user's id and pass it to the new activity
            //  so that it can be used by the fragments when fetching data
            intent.putExtra("userId", (CharSequence) username);
            // Start the next activity
            startActivity(intent);
        } else {
            Toast.makeText(this, "The Username/Password You Entered is Incorrect!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}