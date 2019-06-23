package com.marfarijj.buildingmanagement.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Adapters.ComplainListAdapter;
import com.marfarijj.buildingmanagement.Models.Complain;
import com.marfarijj.buildingmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ShowComplainsActivity extends AppCompatActivity {

    DatabaseReference mComplains;

    List<Complain> complainList;

    RecyclerView rvComplainList;

    ComplainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_complains);

        rvComplainList = findViewById(R.id.recycler_complains);

        rvComplainList.setHasFixedSize(true);
        rvComplainList.setLayoutManager(new LinearLayoutManager(this));

        showComplains();
    }

    private void showComplains() {
        mComplains = FirebaseDatabase.getInstance().getReference("Complains");

        complainList = new ArrayList<>();

        mComplains.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot child : dataSnapshot.getChildren()){

                        Complain complain = child.getValue(Complain.class);

                        complainList.add(new Complain(complain.getComplainFrom(), complain.getComplainSubject(), complain.getComplainDescription()));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ComplainListAdapter(complainList, ShowComplainsActivity.this);
        rvComplainList.setAdapter(adapter);

    }
}
