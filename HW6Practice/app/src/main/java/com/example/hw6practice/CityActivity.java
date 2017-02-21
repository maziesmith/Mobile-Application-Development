package com.example.hw6practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CityActivity extends AppCompatActivity {
    String city,country = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        if(getIntent().getExtras()!=null){
            city = (String) getIntent().getExtras().get(MainActivity.CITY_KEY);
            country = (String) getIntent().getExtras().get(MainActivity.COUNTRY_KEY);
            String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+","+country+"&mode=json&appid=7bd5a066aa6db85107ce65dc76d48c4d";
            Log.d("url : ", url);
            new GetThreeHourlyWeatherAsync(this).execute(url);

        }

    }
}
