package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverviewFragment extends Fragment {

    private String userId;

    private PieChart pieChart;

    public OverviewFragment() {
        // Required empty public constructor
    }

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
            Log.e("DATABASE", "Fragment NewDebtPayoffFragment passed null userId");
        }

        List<HashMap<String, String>> debts = new ArrayList<>();
        List<HashMap<String, String>> bills = new ArrayList<>();
        List<HashMap<String, String>> expenses = new ArrayList<>();

        debts = OverviewActivity.dbHelper.getOverviewDataDebts(userId);
        bills = OverviewActivity.dbHelper.getOverviewDataBills(userId);
        expenses = OverviewActivity.dbHelper.getOverviewDataExpenses(userId);

        PieModel pieModel = new PieModel();

        return rootView;
    }
}