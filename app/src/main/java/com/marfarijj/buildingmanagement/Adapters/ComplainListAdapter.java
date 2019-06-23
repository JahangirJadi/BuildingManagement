package com.marfarijj.buildingmanagement.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marfarijj.buildingmanagement.Models.Complain;
import com.marfarijj.buildingmanagement.R;

import java.util.List;

public class ComplainListAdapter extends RecyclerView.Adapter<ComplainListAdapter.MyViewHolder> {

    List<Complain> complainList;
    Context context;

    public ComplainListAdapter(List<Complain> complainList, Context context) {
        this.complainList = complainList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_complain, viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Complain complain = complainList.get(i);

        holder.tvComplainDesc.setText(complain.getComplainDescription());
        holder.tvComplainSub.setText(complain.getComplainSubject());
        holder.tvComplainFrom.setText(complain.getComplainFrom());



    }

    @Override
    public int getItemCount() {
        return complainList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvComplainFrom, tvComplainSub, tvComplainDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComplainDesc = itemView.findViewById(R.id.tv_complain_desc);
            tvComplainFrom = itemView.findViewById(R.id.tv_complain_from);
            tvComplainSub = itemView.findViewById(R.id.tv_complain_sub);
        }
    }
}
