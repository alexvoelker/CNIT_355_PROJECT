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
        String date = bill.getDueDate();
        if (!billsMap.containsKey(date)) {
            billsMap.put(date, new ArrayList<>());
        }
        billsMap.get(date).add(bill);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_calendar, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_bills_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BillAdapter(displayedBills);
        recyclerView.setAdapter(adapter);

        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
            updateDisplayedBills(selectedDate);
        });

        Button addBillButton = view.findViewById(R.id.btn_add_bill);
        addBillButton.setOnClickListener(v -> {
            // Add a new bill to the selected date
            String currentDate = getCurrentCalendarDate(calendarView);
            Bill newBill = new Bill("New Bill", currentDate);
            addBillToMap(newBill);
            updateDisplayedBills(currentDate);
        });

        return view;
    }


    private void updateDisplayedBills(String date) {
        displayedBills.clear();
        if (billsMap.containsKey(date)) {
            displayedBills.addAll(billsMap.get(date));
        }
        adapter.notifyDataSetChanged();
    }

    private String getCurrentCalendarDate(CalendarView calendarView) {
        long dateMillis = calendarView.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}