package com.example.hw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hw2.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Expense> expenses = new ArrayList<>();
    final static String EXPENSE_KEY = "EXPENSE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.AddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                //intent.putExtra(EXPENSE_KEY, expenses);
                startActivity(intent);
            }
        });
     /*
        findViewById(R.id.DeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivity(intent);
            }
        });
        findViewById(R.id.EditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, FinishActivity.class);
                intent.putExtra(EXPENSE_KEY, expenses);
                startActivity(intent);
            }
        });
        */
    }
}
