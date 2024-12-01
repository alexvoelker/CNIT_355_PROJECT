package com.aeondynamics.cnit_355_project.fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.aeondynamics.cnit_355_project.Bill;
import com.aeondynamics.cnit_355_project.BillAdapter;
import com.aeondynamics.cnit_355_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillsCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillsCalendarFragment extends Fragment {
    // TODO: Fix to match our code
    private static String ARG_PARAM2 = "param2";



    public BillsCalendarFragment() {
        // Required empty public constructor
    }


    public static BillsCalendarFragment newInstance(String param1, String param2) {
        BillsCalendarFragment fragment = new BillsCalendarFragment();
        Bundle args = new Bundle();

        //TODO: Will need to fix this later
        args.putString(ARG_PARAM2, param1);

        fragment.setArguments(args);
        return fragment;
    }
    Map<String, List<Bill>> billsMap = new HashMap<>();
    List<Bill> displayedBills = new ArrayList<>();
    BillAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//          TODO: Fix later
//           mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    private void addBillToMap(Bill bill) {
        String date = bill.getDueDate(); // This should be the date the user entered
        if (!billsMap.containsKey(date)) {
            billsMap.put(date, new ArrayList<>());
        }
        billsMap.get(date).add(bill); // Add the bill to the correct date in the map
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_calendar, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_bills_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BillAdapter(displayedBills);
        recyclerView.setAdapter(adapter);

        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        // Show all bills initially
        updateDisplayedBills(null);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            updateDisplayedBills(selectedDate); // Show bills for the selected date
        });

        Button addBillButton = view.findViewById(R.id.btn_add_bill);
        addBillButton.setOnClickListener(v -> {
            String date = getSelectedDate(calendarView);
            showAddBillDialog(date);
        });

        return view;
    }

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


    private void showAddBillDialog(String date) {
        // Create a dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Add New Bill");

        // Set up a custom layout with an EditText
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_bill, null);
        builder.setView(dialogView);

        EditText billNameEditText = dialogView.findViewById(R.id.et_bill_name);
        EditText billAmountEditText = dialogView.findViewById(R.id.et_bill_amount);
        EditText billDescriptionEditText = dialogView.findViewById(R.id.et_bill_description); // Add this line for description

        builder.setPositiveButton("Add", (dialog, which) -> {
            String billName = billNameEditText.getText().toString().trim();
            String billAmount = billAmountEditText.getText().toString().trim();
            String billDescription = billDescriptionEditText.getText().toString().trim(); // Get description

            if (!billName.isEmpty() && !billAmount.isEmpty() && !billDescription.isEmpty()) {
                // Create a new Bill object with the user entered date
                Bill newBill = new Bill(billName, billDescription, billAmount, date);
                addBillToMap(newBill);  // Add bill to the map with the entered date
                updateDisplayedBills(date);  // Update the displayed bills for the entered date
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
    private String getSelectedDate(CalendarView calendarView) {
        long dateMillis = calendarView.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-indexed
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day); // Format as yyyy-MM-dd
    }

}
