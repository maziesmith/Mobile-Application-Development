package com.example.hw6practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText city;
    EditText country;
    public final static String CITY_KEY = "City";
    public final static String COUNTRY_KEY = "Country";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (EditText) findViewById(R.id.editTextCity);
        country = (EditText) findViewById(R.id.editTextCountry);

        Button button = (Button) findViewById(R.id.buttonSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = city.getText().toString();
                String b = country.getText().toString();
                Intent i = new Intent(MainActivity.this, CityActivity.class);
                i.putExtra(CITY_KEY,a);
                i.putExtra(COUNTRY_KEY,b);
                startActivity(i);
            }
        });

    }
}
