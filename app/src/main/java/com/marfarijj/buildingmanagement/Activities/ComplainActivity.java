package com.marfarijj.buildingmanagement.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marfarijj.buildingmanagement.R;
import com.marfarijj.buildingmanagement.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class ComplainActivity extends AppCompatActivity {

    Button btnSubmit;

    TextInputEditText edtComplainSubject, edtComplainDesc;
    DatabaseReference userComplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        userComplain = FirebaseDatabase.getInstance().getReference("Complains");

        btnSubmit = findViewById(R.id.btn_submit_complain);
        edtComplainDesc = findViewById(R.id.edt_complain_desc);
        edtComplainSubject = findViewById(R.id.edt_complain_subject);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complainSub = edtComplainSubject.getText().toString();
                String complainDesc = edtComplainDesc.getText().toString();

                Map<String, String> complainMap = new HashMap<>();

                complainMap.put("complainSubject", complainSub);
                complainMap.put("complainDescription", complainDesc);
                complainMap.put("complainFrom", SharedPrefManager.getInstance(ComplainActivity.this).getUserName());


                userComplain.push()
                        .setValue(complainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ComplainActivity.this, "Complain Submitted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ComplainActivity.this, DashboardActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ComplainActivity.this, "failed to submit your complain", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}
