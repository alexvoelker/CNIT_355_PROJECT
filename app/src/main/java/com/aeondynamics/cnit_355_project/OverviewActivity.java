package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OverviewActivity extends AppCompatActivity {
    IUserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_overview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        data = (IUserData) intent.getSerializableExtra("userData");
    }

    /*
    * Change these to switch fragments
    * */
    public void onClickNavOverview(View view) {
    }

    public void onClickNavBillCalendar(View view) {
    }

    public void onClickNavDebtTracker(View view) {
    }

    public void onClickNavReceiptScanner(View view) {
    }
}