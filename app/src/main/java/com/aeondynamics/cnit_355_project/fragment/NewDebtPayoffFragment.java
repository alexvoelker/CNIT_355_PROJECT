package com.aeondynamics.cnit_355_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aeondynamics.cnit_355_project.OverviewActivity;
import com.aeondynamics.cnit_355_project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewDebtPayoffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewDebtPayoffFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewDebtPayoffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewDebtPayoffFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewDebtPayoffFragment newInstance(String param1, String param2) {
        NewDebtPayoffFragment fragment = new NewDebtPayoffFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText billName;
    EditText description;
    EditText monthlyPayment;
    EditText interest;
    EditText loanMaturity;
    Button debtButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_debt_payoff, container, false);

        billName = rootView.findViewById(R.id.billName);
        description = rootView.findViewById(R.id.Description);
        monthlyPayment = rootView.findViewById(R.id.monthlyPayment);
        interest = rootView.findViewById(R.id.interest);
        loanMaturity = rootView.findViewById(R.id.loanMonths);
        debtButton = rootView.findViewById(R.id.trackDebt);

        debtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OverviewActivity.dbHelper.addDebtItem();

            }
        });


        return rootView;
    }
}