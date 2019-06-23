package com.marfarijj.buildingmanagement.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Activities.MaintenanceCollectionActivity;
import com.marfarijj.buildingmanagement.Models.Maintenance;
import com.marfarijj.buildingmanagement.Models.User;
import com.marfarijj.buildingmanagement.R;
import com.marfarijj.buildingmanagement.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MaintenanceAdapter extends RecyclerView.Adapter<MaintenanceAdapter.MyViewHolder> {

    List<User> userList;
    Context context;

    public MaintenanceAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_maintenance_collection, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {


        final String flatNo = SharedPrefManager.getInstance(context).getUserFlatNo();
        String Floor = "";

        if (Integer.parseInt(flatNo) >= 0 && Integer.parseInt(flatNo) <= 10) {
            Floor = "First Floor";

        } else if (Integer.parseInt(flatNo) >= 11 && Integer.parseInt(flatNo) <= 20) {
            Floor = "Second Floor";

        } else if (Integer.parseInt(flatNo) >= 21 && Integer.parseInt(flatNo) <= 30) {
            Floor = "Third Floor";

        } else if (Integer.parseInt(flatNo) >= 31 && Integer.parseInt(flatNo) <= 40) {
            Floor = "Fourth Floor";


        } else if (Integer.parseInt(flatNo) >= 41 && Integer.parseInt(flatNo) <= 50) {

            Floor = "Fifth Floor";

        }

        Calendar c = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.US);

        final String month = simpleDateFormat.format(c.getTime());

        final DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users").child(Floor);
        final DatabaseReference maintenace = FirebaseDatabase.getInstance().getReference("Maintenance Collection");

        final User user = userList.get(i);

        myViewHolder.tvName.setText(user.getName());
        myViewHolder.tvFlatNo.setText(user.getFlatNo());


        System.out.println("maintenance:" + user.getMaintenanceFeesStatus());


        if (user.getMaintenanceFeesStatus().equals("true")) {
            myViewHolder.cardView.setBackgroundResource(R.color.statusPaidColor);
            users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("true");

        } else {
            myViewHolder.cardView.setBackgroundResource(R.color.statusNotPaidColor);
            users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("false");

        }


        if (SharedPrefManager.getInstance(context).getCollectorStatus()) {
            myViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CharSequence options[] = new CharSequence[]{"Maintenace Paid", "Maintenance Not Paid"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Options");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            if (position == 0) {
                                myViewHolder.cardView.setBackgroundResource(R.color.statusPaidColor);
                                users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("true");
                                Map<String, String> map = new HashMap<>();
                                map.put("memberName", user.getName());
                                map.put("Status", "paid");

                                maintenace.child(month).child(user.getName()).setValue(map);

                            }

                            if (position == 1) {
                                myViewHolder.cardView.setBackgroundResource(R.color.statusNotPaidColor);
                                users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("false");
                                Map<String, String> map = new HashMap<>();
                                map.put("memberName", user.getName());
                                map.put("Status", "not Paid");

                                maintenace.child(month).child(user.getName()).setValue(map);
                            }

                        }
                    });
                    builder.show();


                    return true;
                }
            });
        }


//        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CharSequence options[] = new CharSequence[]{"Maintenace Paid", "Maintenance Not Paid"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Select Options");
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int position) {
//
//                        if (position == 0) {
//                            myViewHolder.cardView.setBackgroundResource(R.color.statusPaidColor);
//                            users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("true");
//
//                        }
//
//                        if (position == 1) {
//                            myViewHolder.cardView.setBackgroundResource(R.color.statusNotPaidColor);
//                            users.child(user.getPhoneNo()).child("maintenanceFeesStatus").setValue("false");
//                        }
//
//                    }
//                });
//                builder.show();
//            }
//
//        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFlatNo;
        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFlatNo = itemView.findViewById(R.id.tv_maintenance_user_flat_no);
            tvName = itemView.findViewById(R.id.tv_maintenance_user_name);
            cardView = itemView.findViewById(R.id.card_maintenance);

        }
    }
}
