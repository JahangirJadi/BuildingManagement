package com.marfarijj.buildingmanagement.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marfarijj.buildingmanagement.Models.User;
import com.marfarijj.buildingmanagement.R;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText edtName, edtPass, edtConfirmPass, edtFlatNo, edtPhoneNo;
    Button btnRegister;

    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //init db
        users = FirebaseDatabase.getInstance().getReference("Users");

        btnRegister = findViewById(R.id.btn_register_user);
        edtConfirmPass = findViewById(R.id.edt_confirm_pass);
        edtName = findViewById(R.id.edt_name);
        edtPass = findViewById(R.id.edt_pass);
        edtFlatNo = findViewById(R.id.edt_flat_no);
        edtPhoneNo = findViewById(R.id.edt_phone_no);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        String name, phoneNo, pass, confirmPass, flatNo;

        name = edtName.getText().toString();
        phoneNo = edtPhoneNo.getText().toString();
        pass = edtPass.getText().toString();
        confirmPass = edtConfirmPass.getText().toString();
        flatNo = edtFlatNo.getText().toString();

        if (validate(name, phoneNo, pass, confirmPass, flatNo)) {
            User user = new User(name, pass, confirmPass, flatNo, phoneNo, "false", "false");

            if (Integer.parseInt(user.getFlatNo()) >= 0 && Integer.parseInt(user.getFlatNo()) <= 10) {
                users.child("First Floor").child(phoneNo).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else if (Integer.parseInt(user.getFlatNo()) >= 11 && Integer.parseInt(user.getFlatNo()) <= 20) {
                users.child("Second Floor").child(phoneNo).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (Integer.parseInt(user.getFlatNo()) >= 21 && Integer.parseInt(user.getFlatNo()) <= 30) {
                users.child("Third Floor").child(phoneNo).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (Integer.parseInt(user.getFlatNo()) >= 31 && Integer.parseInt(user.getFlatNo()) <= 40) {
                users.child("Fourth Floor").child(phoneNo).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else if (Integer.parseInt(user.getFlatNo()) >= 31 && Integer.parseInt(user.getFlatNo()) <= 40) {
                users.child("Fifth Floor").child(phoneNo).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "User registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        }


    }

    private Boolean validate(String name, String phoneNo, String pass, String confirmPass, String flatNo) {

        if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(phoneNo) && TextUtils.isEmpty(pass)
                && TextUtils.isEmpty(confirmPass) && TextUtils.isEmpty(flatNo))) {

            if (pass.length() < 6) {
                edtPass.setError("Password length must be greater than 5");
                return false;
            }

            if (!pass.equals(confirmPass)) {
                edtConfirmPass.setError("Password doesn't matched");
                return false;
            } else {
                return true;
            }
        } else
            Toast.makeText(this, "Fill all the fields first", Toast.LENGTH_SHORT).show();
        return false;

    }
}
