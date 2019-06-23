package com.marfarijj.buildingmanagement.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Adapters.MaintenanceAdapter;
import com.marfarijj.buildingmanagement.Models.Maintenance;
import com.marfarijj.buildingmanagement.Models.User;
import com.marfarijj.buildingmanagement.R;
import com.marfarijj.buildingmanagement.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceCollectionActivity extends AppCompatActivity {


    RecyclerView recyclerMaintenance;
    List<User> userList;
    MaintenanceAdapter adapter;

    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_collection);

        recyclerMaintenance = findViewById(R.id.recycler_collection);
        recyclerMaintenance.setHasFixedSize(true);
        recyclerMaintenance.setLayoutManager(new LinearLayoutManager(this));


        users = FirebaseDatabase.getInstance().getReference("Users");

        populateCard();


    }

    private void populateCard() {
        userList = new ArrayList<>();

        final String flatNo = SharedPrefManager.getInstance(MaintenanceCollectionActivity.this).getUserFlatNo();
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


        users.child(Floor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {


                    User user = child.getValue(User.class);


                    if (SharedPrefManager.getInstance(MaintenanceCollectionActivity.this).getCollectorStatus()) {

                        if (Integer.parseInt(user.getFlatNo()) > 0 && Integer.parseInt(user.getFlatNo()) <= 10) {

                            userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));

                        } else if (Integer.parseInt(user.getFlatNo()) >= 11 && Integer.parseInt(user.getFlatNo()) <= 20) {

                            userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));

                        } else if (Integer.parseInt(user.getFlatNo()) >= 21 && Integer.parseInt(user.getFlatNo()) <= 30) {

                            userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));

                        } else if (Integer.parseInt(user.getFlatNo()) >= 31 && Integer.parseInt(user.getFlatNo()) <= 40) {

                            userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));

                        } else if (Integer.parseInt(user.getFlatNo()) >= 41 && Integer.parseInt(user.getFlatNo()) <= 50) {

                            userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));

                        }

                    } else if (user.getFlatNo().equals(flatNo)){

                        System.out.println("user info:" + user.getName() + "\n" + user.getMaintenanceFeesStatus() + "\n" + user.getFlatNo() + "\n" + user.getPhoneNo());
                        userList.add(new User(user.getName(), user.getMaintenanceFeesStatus(), user.getFlatNo(), user.getPhoneNo()));
                    }


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new MaintenanceAdapter(userList, MaintenanceCollectionActivity.this);
        recyclerMaintenance.setAdapter(adapter);

    }
}
