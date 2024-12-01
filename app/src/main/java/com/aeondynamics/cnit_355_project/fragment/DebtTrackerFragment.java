package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

            NewDebtPayoffFragment newFragment = new NewDebtPayoffFragment();
            Bundle args = new Bundle();
            args.putString("userId", userId);
            newFragment.setArguments(args);

            transaction.replace(R.id.fragmentContainerView, newFragment);

            // Add the back stack name so this transaction
            // can be reversed when the form is filled out
            transaction.addToBackStack("newDebtForm");
            transaction.commit();
        });

        return rootView;

    }
}