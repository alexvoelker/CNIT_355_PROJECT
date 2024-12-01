package com.aeondynamics.cnit_355_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private List<Bill> billList;

    public BillAdapter(List<Bill> billList) {
        this.billList = billList;
    }

    public BillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.title.setText(bill.getTitle());
        holder.description.setText(bill.getDescription());
        holder.amount.setText(bill.getAmount());
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, amount, date;

        public BillViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_bill_title);
            description = itemView.findViewById(R.id.tv_bill_description);
            amount = itemView.findViewById(R.id.tv_bill_amount);
            date = itemView.findViewById(R.id.tv_bill_date);
        }
    }
}
