/*
Assignment No : Homework 2
File Name : MainActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    static int currentItem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        if(getIntent().getExtras() !=null)
        {
            final ArrayList<Expense> expenses = getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_KEY);
            Log.d("showactivity" , "expenses  : "+expenses.size());
            if (expenses.size() > 0 ) {
                Expense expInitial = expenses.get(0);
                displayExpense(expInitial);
                findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentItem++;
                        if (currentItem < expenses.size()) {
                            Expense expense = expenses.get(currentItem);
                            displayExpense(expense);
                        } else {
                            Toast.makeText(ShowActivity.this.getApplicationContext(), "No more expenses ", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                findViewById(R.id.imageButtonPrevious).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentItem--;
                        if (currentItem >= 0) {
                            Expense expense = expenses.get(currentItem);
                            displayExpense(expense);
                        } else {
                            Toast.makeText(ShowActivity.this.getApplicationContext(), "No more expenses ", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                findViewById(R.id.imageButtonFirst).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Expense expense = expenses.get(0);
                        currentItem = 0 ;
                        displayExpense(expense);

                    }
                });

                findViewById(R.id.imageButtonLast).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Expense expense = expenses.get(expenses.size()-1);
                        currentItem = expenses.size()-1;
                        displayExpense(expense);

                    }
                });

                findViewById(R.id.buttonFinishShow).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            else
            {
                Toast.makeText(ShowActivity.this.getApplicationContext(), "No Expenses Added  ", Toast.LENGTH_LONG).show();
                finish();
            }
        }



    }

    void displayExpense(Expense expense)
    {
        TextView tvName = (TextView) findViewById(R.id.textViewShowName);
        TextView tvCategory = (TextView) findViewById(R.id.textViewShowCategory);
        TextView tvAmt = (TextView) findViewById(R.id.textViewShowAmount);
        TextView tvDate = (TextView) findViewById(R.id.textViewShowDate);
        double amt = expense.getAmount();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        //System.out.println(formatter.format(amt));
        tvName.setText(expense.getExpenseName());
        tvCategory.setText(expense.getCategory());
        tvAmt.setText(formatter.format(amt));
        tvDate.setText(expense.getDate());
        Uri selectedImage = expense.getReceipt();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
       // ImageButton imageReceipt = (ImageButton) findViewById(R.id.imageButtonShowActivity);
        ImageView imageReceipt = (ImageView) findViewById(R.id.imageViewReceipt);
        imageReceipt.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    }
}
