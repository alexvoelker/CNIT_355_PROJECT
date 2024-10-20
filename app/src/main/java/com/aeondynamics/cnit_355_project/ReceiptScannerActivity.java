package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReceiptScannerActivity extends AppCompatActivity {
    IUserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receipt_scanner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        data = (IUserData) intent.getSerializableExtra("userData");
    }
    public void onClickNavOverview(View view) {
        // Go to the overview activity
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("userData", data);
        startActivity(intent);
    }

    public void onClickNavBillCalendar(View view) {
        // Go to the overview activity
        Intent intent = new Intent(this, BillCalendarActivity.class);
        intent.putExtra("userData", data);
        startActivity(intent);
    }

    public void onClickNavDebtTracker(View view) {
        // Go to the overview activity
        Intent intent = new Intent(this, DebtTrackerActivity.class);
        intent.putExtra("userData", data);
        startActivity(intent);
    }

    public void onClickNavReceiptScanner(View view) {
        // Go to the overview activity
        Intent intent = new Intent(this, ReceiptScannerActivity.class);
        intent.putExtra("userData", data);
        startActivity(intent);
    }
}