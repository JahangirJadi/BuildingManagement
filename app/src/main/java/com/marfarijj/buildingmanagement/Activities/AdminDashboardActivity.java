package com.marfarijj.buildingmanagement.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Models.Complain;
import com.marfarijj.buildingmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AdminDashboardActivity extends AppCompatActivity {

    CardView cardWriteMonthlyExpense, cardWriteNotice, cardShowMonthlyMaintenanceCollection, cardShowComplains;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardShowComplains = findViewById(R.id.card_view_complain_admin);
        cardWriteMonthlyExpense = findViewById(R.id.card_monthly_expenses_admin);
        cardWriteNotice = findViewById(R.id.card_write_notice_admin);
        cardShowMonthlyMaintenanceCollection = findViewById(R.id.card_maintenance_collection_admin);

        cardShowComplains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, ShowComplainsActivity.class));
            }
        });

        cardWriteMonthlyExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, WriteMonthlyExpenses.class));
            }
        });

        cardShowMonthlyMaintenanceCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, MaintenanceCollectionActivity.class));
            }
        });

        cardWriteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, WriteNoticeActivity.class));
            }
        });
    }


}
