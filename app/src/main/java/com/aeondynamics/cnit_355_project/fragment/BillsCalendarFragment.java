package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aeondynamics.cnit_355_project.Bill;
import com.aeondynamics.cnit_355_project.BillAdapter;
import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;
import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BillsCalendarFragment extends Fragment {
    private String userId;

    public BillsCalendarFragment() {
        // Required empty public constructor
    }

    Map<String, List<Bill>> billsMap = new HashMap<>();
    List<Bill> displayedBills = new ArrayList<>();
    BillAdapter adapter;

    CalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void addBillToMap(Bill bill) {
        String date = bill.getDueDate(); // This should be the date the user entered
        if (!billsMap.containsKey(date)) {
            billsMap.put(date, new ArrayList<>());
        }

        if (billsMap.get(date) != null)
            billsMap.get(date).add(bill); // Add the bill to the correct date in the map
    }

    /**
     * Add marks to each day in the CalendarView that have a bill due on it
     */
    private void updateCalendarMarks() {
        // Use the billsMap to see the list of data

        // Exit early if there is no work to be done
        if (billsMap == null || billsMap.isEmpty())
            return;

        // Add an icon to each day in the calendar that has a bill due on it
        List<CalendarDay> calendarDays = new ArrayList<>();

        for (String element : billsMap.keySet()) {
            int day = Integer.parseInt(element);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, day);
            CalendarDay cd = new CalendarDay(calendar);
            cd.setImageResource(R.drawable.ic_launcher_background);
            calendarDays.add(cd);
        }

        calendarView.setCalendarDays(calendarDays);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_calendar, container, false);

        try {
            userId = getArguments().getString("userId");
        } catch (NullPointerException ex) {
            Log.e("DATABASE", "Fragment NewDebtPayoffFragment passed null userId");
        }

        // Get the bills from the database
        List<Bill> billsRecords = OverviewActivity.dbHelper.getUserBills(userId);
        if (billsRecords != null) {
            billsMap.clear();
            for (Bill bill : billsRecords)
                addBillToMap(bill);
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_bills_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BillAdapter(displayedBills);
        recyclerView.setAdapter(adapter);



        // Setup Calendar
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setSwipeEnabled(false);
        calendarView.setHeaderColor(R.color.calendar_header);
        updateCalendarMarks();


        // Show all bills initially
        updateDisplayedBills(null);

        calendarView.setOnCalendarDayClickListener((calendarDay) -> {
            Calendar c = calendarDay.getCalendar();
            String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            updateDisplayedBills(day);
        });

        Button addBillButton = view.findViewById(R.id.btn_add_bill);
        addBillButton.setOnClickListener(v -> showAddBillDialog());

        return view;
    }

    private String date = "";

    private void updateDisplayedBills(String date) {
        displayedBills.clear();
        if (date == null) {
            // Show all bills if no date is selected
            for (List<Bill> bills : billsMap.values()) {
                displayedBills.addAll(bills);
            }
        } else {
            // Show only bills for the selected date
            if (billsMap.containsKey(date)) {
                displayedBills.addAll(billsMap.get(date));
            }
        }
        adapter.notifyDataSetChanged();
    }


    private void showAddBillDialog() {
        // Create a dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Add New Bill");

        // Set up a custom layout with an EditText
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_bill, null);
        builder.setView(dialogView);

        EditText billNameEditText = dialogView.findViewById(R.id.et_bill_name);
        EditText billAmountEditText = dialogView.findViewById(R.id.et_bill_amount);
        EditText billDescriptionEditText = dialogView.findViewById(R.id.et_bill_description); // Add this line for description
        EditText billDateEditTExt = dialogView.findViewById(R.id.et_bill_date);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String billName = billNameEditText.getText().toString().trim();
            String billAmount = billAmountEditText.getText().toString().trim();
            String billDescription = billDescriptionEditText.getText().toString().trim(); // Get description
            String billDate = billDateEditTExt.getText().toString().trim();

            if (OverviewActivity.dbHelper.addBillItem(userId,
                    billName, billDescription, billAmount, billDate)) {
                // Create a new Bill object with the user entered date
                Bill newBill = new Bill(billName, billDescription, billAmount, billDate);
                addBillToMap(newBill);  // Add bill to the map with the entered date
                updateDisplayedBills(date);  // Update the displayed bills for the entered date

                Toast.makeText(getContext(), "Bill added successfully",
                        Toast.LENGTH_SHORT).show();

                updateCalendarMarks();
            } else {
                // A field is empty, so tell the user to fill out all fields
                Toast.makeText(getContext(), "All fields must be filled",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
