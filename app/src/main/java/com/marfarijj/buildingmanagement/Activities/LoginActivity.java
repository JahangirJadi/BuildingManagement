package com.marfarijj.buildingmanagement.Activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Models.User;
import com.marfarijj.buildingmanagement.R;
import com.marfarijj.buildingmanagement.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    TextInputEditText edtPhoneNo, edtPass;

    DatabaseReference users, admin;
    Boolean loggedInAsAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login_user);
        edtPass = findViewById(R.id.edt_login_pass);
        edtPhoneNo = findViewById(R.id.edt_login_phone_no);
        users = FirebaseDatabase.getInstance().getReference("Users");
        loggedInAsAdmin = getIntent().getBooleanExtra("loggedInAsAdmin", false);

        System.out.println("admin login:" + loggedInAsAdmin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });


    }

    private void userLogin() {
        final String phoneNo = edtPhoneNo.getText().toString();
        final String pass = edtPass.getText().toString();

        if (validateInputs(phoneNo, pass)) {

            users.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        for (DataSnapshot snapshot : child.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            System.out.println("userssssssss:" + user.getName());

                            String key = snapshot.getKey();
                            System.out.println("keyyyyyyyy:" + key);


                            if (key.equals(phoneNo) && user.getPassword().equals(pass)) {
                                String collector = user.getIsCollector();
                                if (collector.equals("true")) {
                                    System.out.println("login as collector");
                                    SharedPrefManager.getInstance(LoginActivity.this).userLogin(key, user.getName(), false, true, user.getFlatNo());
                                    Intent loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    loginIntent.putExtra("username", user.getName());

                                    startActivity(loginIntent);
                                    finish();


                                } else {

                                    System.out.println("login as user");
                                    SharedPrefManager.getInstance(LoginActivity.this).userLogin(key, user.getName(), false, false, user.getFlatNo());
                                    Intent loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    loginIntent.putExtra("username", user.getName());

                                    startActivity(loginIntent);
                                    finish();
                                }


                            } else if (loggedInAsAdmin) {
                                System.out.println("login as admin");
                                admin = FirebaseDatabase.getInstance().getReference("Admin");

                                final String phoneNo = edtPhoneNo.getText().toString();
                                final String pass = edtPass.getText().toString();

                                if (validateInputs(phoneNo, pass)) {
                                    admin.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                                User user = child.getValue(User.class);
                                                String key = child.getKey();
                                                System.out.println("Adminkeys" + key);

                                                if (key.equals(phoneNo) && user.getPassword().equals(pass)) {
                                                    SharedPrefManager.getInstance(LoginActivity.this).userLogin(key, user.getName(), true, false, user.getFlatNo());
                                                    Intent loginIntent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                                    loginIntent.putExtra("username", user.getName());

                                                    startActivity(loginIntent);
                                                    finish();

                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Invalid id or password", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid id or password", Toast.LENGTH_SHORT).show();
                            }


                        }


//                        User user = child.getValue(User.class);
//                        String key = child.getKey();
//                        System.out.println("keys" + key);
//


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private Boolean validateInputs(String phoneNo, String pass) {

        if (!(TextUtils.isEmpty(phoneNo) && TextUtils.isEmpty(pass))) {

            return true;
        } else
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
        return false;
    }
}
