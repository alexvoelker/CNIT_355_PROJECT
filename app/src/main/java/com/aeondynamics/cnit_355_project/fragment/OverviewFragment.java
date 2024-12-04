package com.aeondynamics.cnit_355_project.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OverviewFragment extends Fragment {

    private String userId;

    private PieChart pieChart;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public String DEBT_SLICE_COLOR = "#640D5F";
    public String BILL_SLICE_COLOR = "#D91656";
    public String EXPENSE_SLICE_COLOR = "#EB5B00";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        try {
            userId = getArguments().getString("userId");
        } catch (NullPointerException ex) {
            Log.e("DATABASE", "Fragment OverviewFragment passed null userId");
            return rootView;
        }

        // Get lists of items in the form: item: {name: name, cost: cost}
        List<HashMap<String, String>> debts = OverviewActivity.dbHelper.getOverviewDataDebts(userId);
        List<HashMap<String, String>> bills = OverviewActivity.dbHelper.getOverviewDataBills(userId);
        List<HashMap<String, String>> expenses = OverviewActivity.dbHelper.getOverviewDataExpenses(userId);

        float costDebts = getTotalCost(debts);
        float costBills = getTotalCost(bills);
        float costExpenses = getTotalCost(expenses);

        // Fill in data on the pie chart
        pieChart = rootView.findViewById(R.id.piechart);

        pieChart.addPieSlice(new PieModel(
                "Debts",
                costDebts,
                Color.parseColor(DEBT_SLICE_COLOR)
        ));

        pieChart.addPieSlice(new PieModel(
                "Bills",
                costBills,
                Color.parseColor(BILL_SLICE_COLOR)
        ));

        pieChart.addPieSlice(new PieModel(
                "Expenses",
                costExpenses,
                Color.parseColor(EXPENSE_SLICE_COLOR)
        ));

        pieChart.startAnimation();


        // Fill in the list of expenses for each section: Expenses, Bills, Debts
        TextView billTitle = rootView.findViewById(R.id.textViewBills);
        billTitle.setText(String.format("Bills     (%s)", formatPrice(costBills)));
        billTitle.setTextColor(Color.parseColor(BILL_SLICE_COLOR));
        TextView billData = rootView.findViewById(R.id.billsdata);
        billData.setText(formatDataList(bills));

        TextView debtTitle = rootView.findViewById(R.id.textViewDebts);
        debtTitle.setText(String.format("Debts     (%s)", formatPrice(costDebts)));
        debtTitle.setTextColor(Color.parseColor(DEBT_SLICE_COLOR));
        TextView debtData = rootView.findViewById(R.id.debtsdata);
        debtData.setText(formatDataList(debts));


        TextView expenseTitle = rootView.findViewById(R.id.textViewExpenses);
        expenseTitle.setText(String.format("Expenses  (%s)", formatPrice(costExpenses)));
        expenseTitle.setTextColor(Color.parseColor(EXPENSE_SLICE_COLOR));
        TextView expenseData = rootView.findViewById(R.id.expensesdata);
        expenseData.setText(formatDataList(expenses));

        return rootView;
    }

    private String formatDataList(List<HashMap<String, String>> data) {
        StringBuilder sb = new StringBuilder();
        if (data == null)
            return "";

        for (HashMap<String, String> item : data) {
            sb.append(item.get("name"))
                    .append("   ")
                    .append(formatPrice(Double.parseDouble(item.get("cost"))))
                    .append('\n');
        }

        return sb.toString();
    }

    private String formatPrice(double price) {
        return String.format(Locale.US, "$%,.2f", price);
    }

    private float getTotalCost(List<HashMap<String, String>> list) {
        float total = 0;

        if (list == null)
            return 0;

        for (HashMap<String, String> item : list) {
            total += Double.parseDouble(item.get("cost"));
        }

        return total;
    }
}