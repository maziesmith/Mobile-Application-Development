/*
Assignment No : Homework 2
File Name : DeleteActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class DeleteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    final int RESULT_LOAD_IMAGE = 112;
    Uri selectedImage;
    String newcategory;
    int selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerDelete);
        spinner.setEnabled(false);
        spinner.setClickable(false);
        final EditText expenseName = (EditText) findViewById(R.id.ExpenseNameEditTextDelete);
        expenseName.setEnabled(false);
        expenseName.setFocusable(false);
        final EditText Amount = (EditText) findViewById(R.id.editTextAmountDelete);
        Amount.setEnabled(false);
        Amount.setFocusable(false);
        final TextView DATE = (TextView) findViewById(R.id.textViewDateDelete);
        spinner.setOnItemSelectedListener(this);

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
        Button selectExpense = (Button) findViewById(R.id.SelectExpenseDelete);
        selectExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getExtras() !=null)
                {
                    final ArrayList<Expense> expenses = getIntent().getExtras().getParcelableArrayList(MainActivity.Delete_KEY);
                    CharSequence[] expensesNames = new CharSequence[expenses.size()];
                    int i = 0 ;
                    for (Expense exp : expenses)
                    {
                        expensesNames[i] = exp.getExpenseName();
                        i++;
                    }
                    Arrays.sort(expensesNames);
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
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
                            Log.d("demo",filePathColumn+"");
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            Log.d("demo","passed cursor");
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            Log.d("demo","passed picturepath"+ picturePath);
                           // ImageButton imageReceipt = (ImageButton) findViewById(R.id.ReceiptImageDelete);
                            ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
                            imageReceipt.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                            Log.d("demo","passed setImage");
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }
        });
        findViewById(R.id.DeleteButtonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expenseName.getText().toString().equals("") || Amount.getText().toString().equals("") || DATE.getText().toString().equals("") || newcategory.equals("")  )
                {
                    Toast.makeText(DeleteActivity.this.getApplicationContext(), "Please Select an Expense", Toast.LENGTH_LONG).show();
                }
                else {
                    Expense e = new Expense();
                    e.setExpenseName(expenseName.getText().toString());
                    e.setAmount(Double.parseDouble(Amount.getText().toString()));
                    e.setDate(DATE.getText().toString());
                    e.setReceipt(selectedImage);
                    e.setCategory(newcategory);
                    Intent delResult = new Intent();
                    delResult.putExtra(MainActivity.DELETE_EXPENSE, e);
                    delResult.putExtra(MainActivity.ITEM, selectedItem);
                    setResult(RESULT_OK, delResult);
                    finish();
                }
            }
        });
        findViewById(R.id.CancelButtonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newcategory = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
