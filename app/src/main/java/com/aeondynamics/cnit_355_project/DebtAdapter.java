package com.aeondynamics.cnit_355_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.List;

public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.InnerDebtHolder> {
    private Context context;
    private List<Debt> list;

    public DebtAdapter(Context context, List<Debt> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InnerDebtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_debt, parent, false);
        return new InnerDebtHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerDebtHolder holder, int position) {
        holder.tv_debt_title.setText(list.get(position).getDebtName());
        holder.tv_debt_description.setText(list.get(position).getDebtDescription());
        holder.tv_debt_amount.setText(list.get(position).getDebtMonthlyContribution());
        holder.tv_debt_payoff_time.setText(list.get(position).getDebtPayoffMonths());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InnerDebtHolder extends RecyclerView.ViewHolder {
        TextView tv_debt_title;
        TextView tv_debt_description;
        TextView tv_debt_amount;
        TextView tv_debt_payoff_time;

        public InnerDebtHolder(@NonNull View itemView) {
            super(itemView);
            tv_debt_title = itemView.findViewById(R.id.tv_debt_title);
            tv_debt_description = itemView.findViewById(R.id.tv_debt_description);
            tv_debt_amount = itemView.findViewById(R.id.tv_debt_amount);
            tv_debt_payoff_time = itemView.findViewById(R.id.tv_debt_payoff_time);
        }
    }
}
