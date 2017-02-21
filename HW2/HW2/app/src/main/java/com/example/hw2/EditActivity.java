/*
Assignment No : Homework 2
File Name : EditActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    final int RESULT_LOAD_IMAGE = 112;
    Uri selectedImage;
    String newcategory;
    int selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        final EditText expenseName = (EditText) findViewById(R.id.editTextExpenseName);
        final EditText Amount = (EditText) findViewById(R.id.editTextAmount);
        final TextView DATE = (TextView) findViewById(R.id.textViewDate);
      //  final Uri[] selectedImage = new Uri[1];
        // Spinner click listener

        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.categories)){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }

        };

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);




        findViewById(R.id.buttonSelectExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras() !=null)
                {
                    final ArrayList<Expense> expenses = getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSES_KEY);
                    CharSequence[] expensesNames = new CharSequence[expenses.size()];
                    int i = 0 ;


                    for (Expense exp : expenses)
                    {
                        Log.d("editexpense" , exp.getExpenseName() +" ... ");
                        expensesNames[i] = exp.getExpenseName();
                        i++;
                    }
                    //Arrays.sort(expensesNames);
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                    builder.setTitle("Pick An Expense");
                    builder.setItems(expensesNames , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        final Expense exp = expenses.get(item);
                        selectedItem = item;
                        expenseName.setText(exp.getExpenseName());
                        Amount.setText(Double.toString(exp.getAmount()));
                        DATE.setText(exp.getDate());
                        int i = 0 ;
                        for(String category : getResources().getStringArray(R.array.categories))
                        {
                            if(category.equals(exp.getCategory()))
                            {
                               break;
                            }
                            i++;
                        }
                        spinner.setSelection(i);
                        DATE.setText(exp.getDate());
                        selectedImage = exp.getReceipt();
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        //ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
                        ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
                        imageReceipt.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                    }
                });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,monthOfYear );
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        ImageButton dateButton = (ImageButton) findViewById(R.id.imageButtonDate);
        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

      //  ImageButton buttonImageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
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
             //   ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
                ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
                if(expenseName.getText().toString().equals("") || Amount.getText().toString().equals("") || DATE.getText().toString().equals("") || newcategory.equals("")  )
                {
                    Toast.makeText(EditActivity.this.getApplicationContext(), "Please Enter all details", Toast.LENGTH_LONG).show();
                }
                else {
                    Expense e = new Expense();
                    e.setExpenseName(expenseName.getText().toString());
                    e.setAmount(Double.parseDouble(Amount.getText().toString()));
                    e.setDate(DATE.getText().toString());
                    e.setReceipt(selectedImage);
                    e.setCategory(newcategory);
                    Intent editResult = new Intent();
                    editResult.putExtra(MainActivity.EDIT_EXPENSE, e);
                    editResult.putExtra(MainActivity.ITEM, selectedItem);
                    setResult(RESULT_OK, editResult);
                    finish();
                }
            }
        });

        findViewById(R.id.buttonCancelExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void updateLabel() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        TextView DATE = (TextView) findViewById(R.id.textViewDate);
        DATE.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        newcategory = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item

        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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

            //ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonRecipt);
            ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
            imageReceipt.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }
}
