package com.example.snippetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ListViewActivity extends AppCompatActivity {

    String name;
    String age;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        if(getIntent().getExtras()!=null){

            name = getIntent().getExtras().getString("NAME");
            age = getIntent().getExtras().getString("AGE");
            gender = getIntent().getExtras().getString("GENDER");
            Toast.makeText(this, name+"  "+age+"  "+gender,Toast.LENGTH_LONG).show();

        }

    }
}
