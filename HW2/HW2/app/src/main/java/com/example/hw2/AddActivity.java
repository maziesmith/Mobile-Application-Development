/*
Assignment No : Homework 2
File Name : AddActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    final int RESULT_LOAD_IMAGE = 112;
    String category ;
    String expensename;
    double amount;
    Date date;
    Uri selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

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



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        ImageButton dateButton = (ImageButton) findViewById(R.id.imageButtonDate);
        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //ImageButton buttonImageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
        ImageView buttonImageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
        buttonImageReceipt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

       findViewById(R.id.buttonSaveExpense).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText expenseName = (EditText) findViewById(R.id.editTextExpenseName);
               EditText Amount = (EditText) findViewById(R.id.editTextAmount);
               TextView DATE = (TextView) findViewById(R.id.textViewDate);
              // ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
               ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
               if(expenseName.getText().toString().equals("") || Amount.getText().toString().equals("") || DATE.getText().toString().equals("") || category.equals("Select a category") || "receipt".equals(imageReceipt.getTag()) )
               {
                   Toast.makeText(AddActivity.this.getApplicationContext(), "Please Enter all details", Toast.LENGTH_LONG).show();
               }
               else {
                   amount = Double.parseDouble(Amount.getText().toString());
                   expensename = expenseName.getText().toString();
                   Expense e = new Expense(expensename, category, amount, selectedImage, DATE.getText().toString());
                   Intent addResult = new Intent();
                   addResult.putExtra(MainActivity.ADD_EXPENSE, e);
                   setResult(RESULT_OK, addResult);
                   finish();
               }

           }
       });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

          //  ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
            ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
            imageReceipt.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageReceipt.setTag("imageReceived");
        }

    }

    private void updateLabel() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TextView viewDate = (TextView)findViewById(R.id.textViewDate);
        viewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        category = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item

        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }




}

