package com.example.jsondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetPersonAsyncTask().execute("http://liisp.uncc.edu/~mshehab/api/json/persons-v1.json");
    }
}
