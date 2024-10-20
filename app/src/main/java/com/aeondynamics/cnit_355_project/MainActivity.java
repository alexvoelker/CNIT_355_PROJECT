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
    IUserData data;

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
        // Do login things here
        EditText username = findViewById(R.id.editTextUsername);
        String usernameString = username.getText().toString();
        EditText password = findViewById(R.id.editTextPassword);
        String passwordString = password.getText().toString();

        loginSuccess = checkLogin(usernameString, passwordString);


        // If so, set loginSuccess to true
        // Otherwise, tell the user that the username/password is incorrect

        if (loginSuccess) {
            Intent intent = new Intent(this, LoadingActivity.class);
            // Pass the user data to be loaded in to the next activity
            data = new UserData(usernameString, passwordString);
            intent.putExtra("userData", (Serializable) data);
            intent.putExtra("requestType", RequestType.FETCH_FROM_SERVER);
            // Start the next activity
            startActivity(intent);
        } else {
            Toast.makeText(this, "The Username/Password You Entered is Incorrect!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkLogin(String username, String password) {
        // Call the server or internal database to check if the input information is correct
        // TODO return true if the information was valid
        return false;
    }
}