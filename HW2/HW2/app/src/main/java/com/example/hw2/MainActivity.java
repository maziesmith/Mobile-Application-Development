/*
Assignment No : Homework 2
File Name : MainActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */

package com.example.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hw2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<Expense> expenses = new ArrayList<>();
    final static String EXPENSE_KEY = "EXPENSE";
    final static String EXPENSES_KEY = "EXPENSES";
    final static String Delete_KEY = "DELETE";
    final static int ADD_REQ_CODE = 111;
    final static int EDIT_REQ_CODE = 112;
    final static int DELETE_REQ_CODE = 113;
    final static String ITEM = "ITEMSELECTED";
    public static final String ADD_EXPENSE = "addexpense";
    public static final String EDIT_EXPENSE = "editexpense";
    public static final String DELETE_EXPENSE = "deleteexpense";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.AddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                //intent.putExtra(EXPENSE_KEY, expenses);
                startActivityForResult(intent,ADD_REQ_CODE );
            }
        });

        findViewById(R.id.DeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                Collections.sort(expenses);
                intent.putExtra(Delete_KEY, expenses);
                if(expenses.size() == 0 )
                {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "No More Expenses to Delete", Toast.LENGTH_LONG).show();
                }
                else {
                    startActivityForResult(intent, DELETE_REQ_CODE);
                }
            }
        });
        findViewById(R.id.EditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Collections.sort(expenses);
                intent.putExtra(EXPENSES_KEY, expenses);
                if(expenses.size() == 0 )
                {
                    Toast.makeText(MainActivity.this.getApplicationContext(), "No More Expenses to Edit", Toast.LENGTH_LONG).show();
                }
                else
                {
                startActivityForResult(intent,EDIT_REQ_CODE);}
            }
        });
        findViewById(R.id.ShowExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivity(intent);
            }
        });

        findViewById(R.id.FinishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Expense expense =(Expense) data.getExtras().getParcelable(ADD_EXPENSE);
                expenses.add(expense);
                for(Expense exp : expenses)
                {
                    Log.d("main",exp.toString());
                }
            }
        }
        if(requestCode == EDIT_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Expense expense =(Expense) data.getExtras().getParcelable(EDIT_EXPENSE);
                Log.d("mainafteredit",expense.toString());
                int itemselected = data.getExtras().getInt(MainActivity.ITEM);
                expenses.get(itemselected).setCategory(expense.getCategory());
                expenses.get(itemselected).setExpenseName(expense.getExpenseName());
                expenses.get(itemselected).setReceipt(expense.getReceipt());
                expenses.get(itemselected).setDate(expense.getDate());
                expenses.get(itemselected).setAmount(expense.getAmount());
                Log.d("aftersaving",expenses.get(itemselected).toString());
            }
        }

        if(requestCode == DELETE_REQ_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Expense expense =(Expense) data.getExtras().getParcelable(DELETE_EXPENSE);
                Log.d("mainafteredit",expense.toString());
                int itemselected = data.getExtras().getInt(MainActivity.ITEM);
                Log.d("main","itemselected= "+itemselected);
                expenses.remove(itemselected);
            }
        }


    }

}
