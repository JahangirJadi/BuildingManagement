package com.marfarijj.buildingmanagement.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.marfarijj.buildingmanagement.R;

public class DashboardActivity extends AppCompatActivity {



    CardView cardMaintenance, cardMonthlyExpense, cardNotice, cardComplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        cardComplain = findViewById(R.id.card_complain);
        cardMaintenance = findViewById(R.id.card_maintenance_collection);
        cardMonthlyExpense = findViewById(R.id.card_monthly_expenses);
        cardNotice = findViewById(R.id.card_notice);


        cardMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MaintenanceCollectionActivity.class));
            }
        });

        cardComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ComplainActivity.class));
            }
        });

        cardMonthlyExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, WriteMonthlyExpenses.class));
            }
        });

        cardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, WriteNoticeActivity.class));
            }
        });
    }
}
