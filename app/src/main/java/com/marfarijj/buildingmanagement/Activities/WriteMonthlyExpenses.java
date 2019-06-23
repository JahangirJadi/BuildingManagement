package com.marfarijj.buildingmanagement.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
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
import com.marfarijj.buildingmanagement.Adapters.ExpenseListAdapter;
import com.marfarijj.buildingmanagement.Models.MaintenanceExpense;
import com.marfarijj.buildingmanagement.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class WriteMonthlyExpenses extends AppCompatActivity {

    Toolbar toolbar;
    Spinner mSpinner;
    DatabaseReference maintenanceExpense;

    RecyclerView rvExpenseList;
    ExpenseListAdapter adapter;
    List<MaintenanceExpense> expenseList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_field) {
            alertDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_monthly_expenses);

        toolbar = findViewById(R.id.toolbar_expense);

        setSupportActionBar(toolbar);


        toolbar.setTitle("Write Monthly Expense");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        mSpinner = findViewById(R.id.spinner_expense_by_month);
        rvExpenseList = findViewById(R.id.recycler_monthly_expense);
        rvExpenseList.setHasFixedSize(true);
        rvExpenseList.setLayoutManager(new LinearLayoutManager(this));

        maintenanceExpense = FirebaseDatabase.getInstance().getReference("Maintenance Expense");

        initSpinner();


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String month = parent.getItemAtPosition(position).toString();
                loadMonthlyExpense(month);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);
    }


    private void loadMonthlyExpense(final String month) {

        expenseList = new ArrayList<>();

        maintenanceExpense.child(month).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    // System.out.println("month aaya:" +month);

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        MaintenanceExpense expense = child.getValue(MaintenanceExpense.class);

                        expenseList.add(new MaintenanceExpense(
                                expense.getMaintenanceItemName(),
                                expense.getMaintenanceItemMonth(),
                                expense.getMaintenanceItemCost()));
                    }


                }

                adapter = new ExpenseListAdapter(expenseList, WriteMonthlyExpenses.this);
                rvExpenseList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WriteMonthlyExpenses.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void alertDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.layout_monthly_expense, null);
        dialog.setTitle("ADD MONTHLY EXPENSE");
        dialog.setView(customLayout);
        final AlertDialog builder = dialog.create();
        builder.show();
        final EditText etCost = customLayout.findViewById(R.id.et_expense_item_cost);
        final EditText etName = customLayout.findViewById(R.id.et_expense_item_name);
        final Button btnAdd = customLayout.findViewById(R.id.btn_add);
        final Button btnCancel = customLayout.findViewById(R.id.btn_cancel);
        final Spinner mSpinner = customLayout.findViewById(R.id.monthly_expense_spinner);
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
                MaintenanceExpense expense = new MaintenanceExpense(etName.getText().toString(), month[0], etCost.getText().toString());

                maintenanceExpense.child(month[0]).child(etName.getText().toString()).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(WriteMonthlyExpenses.this, "Expense added to database", Toast.LENGTH_SHORT).show();
                            builder.dismiss();
                        } else {
                            Toast.makeText(WriteMonthlyExpenses.this, "Failed To Add", Toast.LENGTH_SHORT).show();
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
