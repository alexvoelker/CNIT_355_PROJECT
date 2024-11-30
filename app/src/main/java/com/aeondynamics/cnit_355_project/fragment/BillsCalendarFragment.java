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
import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//          TODO: Fix later
//           mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rv_bills_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Bill> bills = new ArrayList<>();

        BillAdapter adapter = new BillAdapter(bills);
        recyclerView.setAdapter(adapter);

        // Setup CalendarView (Add custom logic later for date selection)
        CalendarView calendarView = findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Handle date selection stuff here
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills_calendar, container, false);
    }
}