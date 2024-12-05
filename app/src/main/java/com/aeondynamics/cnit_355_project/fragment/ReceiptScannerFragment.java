package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ReceiptScannerFragment extends Fragment {

    private String userId;

    public ReceiptScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private EditText expenseTrackerInputField;
    private Button buttonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_receipt_scanner, container, false);
        try {
            userId = getArguments().getString("userId");
        } catch (NullPointerException ex) {
            Log.e("DATABASE", "Fragment ReceiptScannerFragment passed null userId");
        }

        expenseTrackerInputField = rootView.findViewById(R.id.expenseTrackerInputField);
        buttonAdd = rootView.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(v -> {
            String[] expenseItems = expenseTrackerInputField.getText().toString().split("\n");
            boolean succeeded = parseAndUpdateExpenses(expenseItems);

            if (succeeded) {
                Toast.makeText(getContext(),
                        "Expenses added successfully!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),
                        "Operation Failure, could not insert expenses into database\n" +
                                "Check your input format. It should be 'Type; $00.00'\n" +
                                "and have one per line",
                        Toast.LENGTH_LONG);
            }
        });

        return rootView;
    }

    public boolean parseAndUpdateExpenses(String[] expenseItems) {
        Log.i("EXPENSE TRACKER", "Items: " + Arrays.toString(expenseItems));
        ArrayList<HashMap<String, String>> expenses = new ArrayList<>();

        for (String line : expenseItems) {
            HashMap<String, String> item = new HashMap<>();

            // Skip blank rows
            if (line.isEmpty())
                continue;

            String[] lineItems = line.split(";");
            try {
                if (lineItems.length != 2)
                    // There shouldn't be more than two items (the type and cost) per expense item
                    throw new Exception();

                lineItems[1] = lineItems[1].replaceAll("\\s+", "");

                // Parse the cost
                if (lineItems[1].matches("[$].+"))
                    lineItems[1] = lineItems[1].substring(1);

                // Remove whitespace from the input
                lineItems[0] = lineItems[0].replaceAll("\\s+", "");
                lineItems[1] = lineItems[1].replaceAll("\\s+", "");
                String type = lineItems[0];
                double cost = Double.parseDouble(lineItems[1]);

                item.put("type", type);
                item.put("cost", String.format("%.2f", cost));
                expenses.add(item);
            } catch (Exception ex) {
                // There was an invalid input, so fail the operation by returning false
                return false;
            }
        }

        return OverviewActivity.dbHelper.addExpenses(userId, expenses);
    }
}