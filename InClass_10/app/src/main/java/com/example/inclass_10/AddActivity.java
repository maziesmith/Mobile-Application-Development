package com.example.inclass_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "USER_ID";
    private String user_id;
    String category ,expensename;
    double amount;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        if (getIntent().getExtras() != null) {
            user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            Spinner spinner = (Spinner)findViewById(R.id.spinnerCategory);

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories)){
                @Override
                public int getCount() {
                    return super.getCount()-1;
                }

            };

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(dataAdapter.getCount());

            AdapterView.OnItemSelectedListener categorySelectedListener = new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> spinner, View container,
                                           int position, long id) {
                    category = spinner.getItemAtPosition(position).toString();
                    Log.d("tag", "selected"+category);
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            };
            mDatabase = FirebaseDatabase.getInstance().getReference();
            spinner.setOnItemSelectedListener(categorySelectedListener);
            findViewById(R.id.buttonCancelExpense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(AddActivity.this, ListActivity.class);
                    i.putExtra(EXTRA_USER_ID,user_id);
                    finish();

                    startActivity(i);
                }
            });
            findViewById(R.id.buttonSaveExpense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText expenseName = (EditText)findViewById(R.id.editTextExpenseName);
                    EditText Amount = (EditText) findViewById(R.id.editTextAmount);
                    // TextView DATE = (TextView) findViewById(R.id.textViewDate);
                    // ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
                    //ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
                    Log.d("add", expenseName.getText().toString());
                    Log.d("add", Amount.getText().toString());
                    Log.d("add", category);
                    if (expenseName.getText().toString().equals("") || Amount.getText().toString().equals("") || category.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Enter all details", Toast.LENGTH_LONG).show();
                    } else {
                         amount = Double.parseDouble(Amount.getText().toString());
                        expensename = expenseName.getText().toString();
                        Calendar today = Calendar.getInstance();
                        today.clear(Calendar.HOUR);
                        today.clear(Calendar.MINUTE);
                        today.clear(Calendar.SECOND);
                        Date todayDate = today.getTime();

                        String key = mDatabase.child("users").child(user_id).child("expenses").push().getKey();
                        //-----^-----------------wrong but still works-----(mDatabase.child("expenses").child(user_id).-------
                        Expense e = new Expense(expensename, category, amount, todayDate.toString(),key);
                        Map<String, Object> postValues = e.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/expenses/" +user_id+"/"+key, postValues);
                        e.setKey(key);
                       // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                        Log.d("mystery","here");
                        mDatabase.updateChildren(childUpdates);
                        Intent i = new Intent(AddActivity.this, ListActivity.class);
                        i.putExtra(EXTRA_USER_ID,user_id);
                        finish();
                        startActivity(i);
                    }
                }
            });

        }
    }
}
