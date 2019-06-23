package com.marfarijj.buildingmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marfarijj.buildingmanagement.Models.MaintenanceExpense;
import com.marfarijj.buildingmanagement.R;

import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.MyViewHolder> {

    List<MaintenanceExpense> expenseList;
    Context context;

    public ExpenseListAdapter(List<MaintenanceExpense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_monthly_expense, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        MaintenanceExpense expense = expenseList.get(i);
        holder.tvCost.setText(expense.getMaintenanceItemCost());
        holder.tvMonth.setText(expense.getMaintenanceItemMonth());
        holder.tvName.setText(expense.getMaintenanceItemName());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCost, tvMonth;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCost = itemView.findViewById(R.id.tv_monthly_expense_item_cost);
            tvName = itemView.findViewById(R.id.tv_monthly_expense_item_name);
            tvMonth = itemView.findViewById(R.id.tv_monthly_expense_month);
        }
    }
}
