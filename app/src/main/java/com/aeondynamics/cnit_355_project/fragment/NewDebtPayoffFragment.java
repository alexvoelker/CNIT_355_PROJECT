package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;

public class NewDebtPayoffFragment extends Fragment {

    public NewDebtPayoffFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText billName;
    EditText description;
    EditText monthlyPayment;
    EditText interest;
    EditText loanMaturity;
    Button debtButton;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_debt_payoff, container, false);
        try {
            userId = getArguments().getString("userId");
        } catch (NullPointerException ex) {
            Log.e("DATABASE", "Fragment NewDebtPayoffFragment passed null userId");
        }


        billName = rootView.findViewById(R.id.name);
        description = rootView.findViewById(R.id.editTextText2);
        monthlyPayment = rootView.findViewById(R.id.monthlyPayment);
        interest = rootView.findViewById(R.id.interest);
        loanMaturity = rootView.findViewById(R.id.loanMonths);

        debtButton = rootView.findViewById(R.id.trackDebt);
        debtButton.setOnClickListener(view -> {
            // Update the database to have a new debt item for the given user
            try {
                String name = billName.getText().toString();
                String descString = description.getText().toString();
                double monthlyPaymentDouble =
                        Double.parseDouble(monthlyPayment.getText().toString());
                double interestDouble =
                        Double.parseDouble(interest.getText().toString());
                int loadMaturityInteger = Integer.parseInt(loanMaturity.getText().toString());

                boolean succeeded = OverviewActivity.dbHelper.addDebtItem(userId, name, descString,
                        monthlyPaymentDouble, interestDouble, loadMaturityInteger);
                if (!succeeded)
                    throw new Exception();

                Toast.makeText(getContext(), "Records updated successfully!",
                        Toast.LENGTH_LONG).show();
                // At this point, the insert succeeded, so return the user back to the DebtTrackerFragment
                getParentFragmentManager().popBackStack();

            } catch (Exception ex) {
                Toast.makeText(getContext(), "Failed to add new debt record to database.",
                        Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}