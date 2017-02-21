package com.example.hw2;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeleteActivity extends AppCompatActivity {
    //final static CharSequence[] items =
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Pick an expense");


        Button selectExpense = (Button) findViewById(R.id.SelectExpenseDelete);
        selectExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
