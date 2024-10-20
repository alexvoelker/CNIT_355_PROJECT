package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadingActivity extends AppCompatActivity {
    IUserData data;
    Intent passedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        passedData = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        data = (IUserData) passedData.getSerializableExtra("userData");
        RequestType type = (RequestType) passedData.getSerializableExtra("requestType");
        // TODO Display a loading animation/progress bar while the data is being loaded
        // Probably use multithreading and handlers


        // Attempt to fetch user data
        try {
            boolean returnStatus;

            switch (type) {
                case FETCH_FROM_SERVER:
                    returnStatus = data.fetchUserData();
                    break;
                case UPDATE_SERVER_RECORDS:
                    // TODO make a call to update the server/database

                    // TODO make this reflect the success/failure of the server/database request
                    returnStatus = false;
                    break;
                default:
                    // somehow a different message was sent? So exit
                    throw new NullPointerException();
            }

            if (!returnStatus)
                throw new NullPointerException();
        } catch (NullPointerException ex) {
            // Display to the user that there was an error
            Toast.makeText(this, "Server connection failed, redirecting to login page",
                    Toast.LENGTH_SHORT).show();
            // logging in, and redirect them to the login-page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Then move on to the Overview Activity
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("userData", data);
        startActivity(intent);
    }
}