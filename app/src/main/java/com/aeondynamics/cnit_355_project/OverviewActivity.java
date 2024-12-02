package com.aeondynamics.cnit_355_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aeondynamics.cnit_355_project.fragment.BillsCalendarFragment;
import com.aeondynamics.cnit_355_project.fragment.DebtTrackerFragment;
import com.aeondynamics.cnit_355_project.fragment.OverviewFragment;
import com.aeondynamics.cnit_355_project.fragment.ReceiptScannerFragment;

import java.util.Arrays;

public class OverviewActivity extends AppCompatActivity {

    // Fragment '0'
    private OverviewFragment overviewFragment;

    // Fragment '1'
    private BillsCalendarFragment billsCalendarFragment;

    // Fragment '2'
    private DebtTrackerFragment debtTrackerFragment;

    // Fragment '3'
    private ReceiptScannerFragment receiptScannerFragment;

    private Button overviewNavButton;
    private Button billCalendarNavButton;
    private Button debtTrackerNavButton;
    private Button receiptScannerNavButton;

    // The userId to identify a user's data in a database query
    private String userId;

    public static DatabaseHelper dbHelper;

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

        // Create a static DatabaseHelper object that can be used by all of the fragments
        dbHelper = new DatabaseHelper(this);

        dbHelper.openDB(); // TODO remove this later so the database doesn't cause a memory leak!
//        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 1);

        userId = getIntent().getStringExtra("userId");

        Bundle args = new Bundle();
        args.putString("userId", userId);

        overviewFragment = new OverviewFragment();
        overviewFragment.setArguments(args);

        billsCalendarFragment = new BillsCalendarFragment();
        billsCalendarFragment.setArguments(args);

        debtTrackerFragment = new DebtTrackerFragment();
        debtTrackerFragment.setArguments(args);

        receiptScannerFragment = new ReceiptScannerFragment();
        receiptScannerFragment.setArguments(args);


        overviewNavButton = findViewById(R.id.navOverview);
        overviewNavButton.setOnClickListener(e -> changeFragment(0));

        billCalendarNavButton = findViewById(R.id.navBillCalendar);
        billCalendarNavButton.setOnClickListener(e -> changeFragment(1));

        debtTrackerNavButton = findViewById(R.id.navDebtTracker);
        debtTrackerNavButton.setOnClickListener(e -> changeFragment(2));

        receiptScannerNavButton = findViewById(R.id.navReceiptScanner);
        receiptScannerNavButton.setOnClickListener(e -> changeFragment(3));

    }

    // Start at 0, since the overview fragment is the default when this activity is launched
    private int currentFragment = 0;

    private void changeFragment(int index) {
        // Exit early if the fragment to change to is the
        // current one, since there is nothing to do
        if (index == currentFragment)
            return;

        String fragmentName;

        switch (index) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, overviewFragment).commit();
                currentFragment = 0;
                fragmentName = "overviewFragment";
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, billsCalendarFragment).commit();
                currentFragment = 1;
                fragmentName = "billsCalendarFragment";
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, debtTrackerFragment).commit();
                currentFragment = 2;
                fragmentName = "debtTrackerFragment";
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, receiptScannerFragment).commit();
                currentFragment = 3;
                fragmentName = "receiptScannerFragment";
                break;
            default:
                Log.d("Fragment Change Error", "The Fragment index ("
                        + index + ") is not 0 to 3");
                fragmentName = "NULL";
        }

        Log.i("Change Screen", "Fragment changed to: " + fragmentName);
    }
}