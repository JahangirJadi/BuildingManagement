package com.marfarijj.buildingmanagement.Activities;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marfarijj.buildingmanagement.Adapters.ComplainListAdapter;
import com.marfarijj.buildingmanagement.Adapters.NoticeListAdapter;
import com.marfarijj.buildingmanagement.Models.Complain;
import com.marfarijj.buildingmanagement.Models.MaintenanceExpense;
import com.marfarijj.buildingmanagement.Models.Notice;
import com.marfarijj.buildingmanagement.R;
import com.marfarijj.buildingmanagement.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WriteNoticeActivity extends AppCompatActivity {

    DatabaseReference mNotices;

    List<Notice> noticeList;

    RecyclerView rvNoticeList;
    Spinner mSpinner;
    FloatingActionButton fabAddNotice;

    NoticeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notice);

        rvNoticeList = findViewById(R.id.recycler_notice);
        rvNoticeList.setHasFixedSize(true);
        rvNoticeList.setLayoutManager(new LinearLayoutManager(this));

        mSpinner = findViewById(R.id.spinner_notice);
        fabAddNotice = findViewById(R.id.fab_add_notice);
        mNotices = FirebaseDatabase.getInstance().getReference("Notices");
        initSpinner();

        if (SharedPrefManager.getInstance(WriteNoticeActivity.this).getAdminStatus()==false){
            fabAddNotice.setVisibility(View.GONE);
        }

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = parent.getItemAtPosition(position).toString();
                showNotice(month);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
    }

    private void showNotice(String month) {
        noticeList = new ArrayList<>();
        mNotices.child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        Notice notice = child.getValue(Notice.class);

                        noticeList.add(new Notice(notice.getNoticeFrom(), notice.getNoticeSubject(), notice.getNoticeDescription()));

                    }
                }

                else{
                    Toast.makeText(WriteNoticeActivity.this, "No Notice in this month", Toast.LENGTH_SHORT).show();
                }

                adapter = new NoticeListAdapter(noticeList, WriteNoticeActivity.this);
                rvNoticeList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void alertDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.layout_notice, null);
        dialog.setTitle("Write Notice");
        dialog.setView(customLayout);
        final AlertDialog builder = dialog.create();
        builder.show();
        final EditText etNoticeSub = customLayout.findViewById(R.id.et_notice_sub);
        final EditText etNoticeDesc = customLayout.findViewById(R.id.et_notice_desc);
        final Button btnAdd = customLayout.findViewById(R.id.btn_add_notice);
        final Button btnCancel = customLayout.findViewById(R.id.btn_cancel_notice);
        final Spinner mSpinner = customLayout.findViewById(R.id.spinner_month_notice);
        final String[] month = new String[1];
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                month[0] = parent.getItemAtPosition(position).toString();

                System.out.println("selected month:" + month[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);
                System.out.println("month" + month_date.format(c.getTime()));
                Notice notice = new Notice("Admin", etNoticeSub.getText().toString(), etNoticeDesc.getText().toString());

                mNotices.child(month[0]).child(etNoticeSub.getText().toString()).setValue(notice).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(WriteNoticeActivity.this, "Notice Added", Toast.LENGTH_SHORT).show();
                            builder.dismiss();

                        } else {
                            Toast.makeText(WriteNoticeActivity.this, "Failed To Add Notice", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

    }
}
