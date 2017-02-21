package com.example.hw7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CityWeather extends AppCompatActivity implements GetHourlyWeatherAsync.IHour {
    ListView listview;
    public final static String HOURS_KEY = "HOURSLIST";
    public final static String POSITION_KEY = "POSITION";
    ArrayList<HourlyWeather> hoursList = new ArrayList<HourlyWeather>() ;
    HourAdapter adapter;
    ProgressDialog progress;
    String city,state = null;

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if(getIntent().getExtras()!= null){

            progress =  new ProgressDialog(this);
            progress.setTitle(R.string.loading_hourly_data);
            progress.show();
            city = (String) getIntent().getExtras().get(MainActivity.CITY_KEY);
            state = (String) getIntent().getExtras().get(MainActivity.STATE_KEY);
          //  Log.d("city", city);
           // Log.d("state", state);
            String url = "http://api.wunderground.com/api/99c668be9eecf8c5/hourly/q/"+state+"/"+city+".json";
            Log.d("url : ", url);
            new GetHourlyWeatherAsync(this).execute(url);

        }


    }

    @Override
    public void setHourlyWeatherList(ArrayList<HourlyWeather> result) {
        hoursList = result;
        adapter= new HourAdapter(this, R.layout.row_hourly_item,hoursList);
        listview = (ListView) findViewById(R.id.listViewHourly);
        listview.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        progress.cancel();
        TextView current_locationTV = (TextView) findViewById(R.id.textViewCurrentLocation);
        current_locationTV.setText(city+","+state);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CityWeather.this, WeatherDetails.class);
                intent.putExtra(HOURS_KEY, hoursList);
                intent.putExtra(POSITION_KEY, position);
                intent.putExtra(MainActivity.CITY_KEY,city);
                intent.putExtra(MainActivity.STATE_KEY,state);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setError(String error) {
        progress.cancel();

        final Toast tag = Toast.makeText(getBaseContext(),error,Toast.LENGTH_SHORT);
        tag.show();
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished)
            {tag.show();}
            public void onFinish()
            {tag.show();
             finish();
            }
        }.start();



    }


}
