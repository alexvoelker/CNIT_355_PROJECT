package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeondynamics.cnit_355_project.R;

public class DebtTrackerFragment extends Fragment {

    private String userId;

    public DebtTrackerFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_debt_tracker, container, false);
        try {
            userId = getArguments().getString("userId");
        } catch (NullPointerException ex) {
            Log.e("DATABASE", "Fragment DebtTrackerFragment passed null userId");
        }

        TextView tv = rootView.findViewById(R.id.textView5);
        tv.setText("Java edited text; debt tracker activity\n\nWelcome: " + userId);

        return rootView;

    }
}