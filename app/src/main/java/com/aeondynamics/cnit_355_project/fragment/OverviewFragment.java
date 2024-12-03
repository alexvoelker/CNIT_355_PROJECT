package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeondynamics.cnit_355_project.R;

public class OverviewFragment extends Fragment {

    private String userId;

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



        return rootView;
    }
}