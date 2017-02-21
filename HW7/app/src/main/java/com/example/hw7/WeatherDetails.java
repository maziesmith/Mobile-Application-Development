package com.example.hw7;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.hw7.CityWeather.HOURS_KEY;

public class WeatherDetails extends AppCompatActivity {
    String city, state = null;
    ArrayList<HourlyWeather> hoursList = null;
    HourlyWeather currentWeather = null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addfav: {
                Favorite fav = new Favorite();
                fav.setCity(city);
                fav.setState(state);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                fav.setDate(dateFormat.format(date)); //2014/08/06 15:59:48
                fav.setTemperature(currentWeather.getTemperature());
                SharedPreference sp = new SharedPreference();
                boolean flag = sp.addFavorite(this,fav);
                if(flag) {
                    Toast.makeText(getApplicationContext(), R.string.added, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(WeatherDetails.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if(getIntent().getExtras() != null)
        {
            hoursList = getIntent().getExtras().getParcelableArrayList(HOURS_KEY);
            int position = getIntent().getExtras().getInt(CityWeather.POSITION_KEY);
            city = getIntent().getExtras().getString(MainActivity.CITY_KEY);
            state = getIntent().getExtras().getString(MainActivity.STATE_KEY);
            int minTemp = 99999  , maxTemp = 0;
            for (HourlyWeather weather : hoursList)
            {
                if(Integer.parseInt(weather.getTemperature().substring(0,2)) < minTemp)
                {
                    minTemp = Integer.parseInt(weather.getTemperature().substring(0,2));
                }
                if(Integer.parseInt(weather.getTemperature().substring(0,2)) >= maxTemp)
                {
                    maxTemp = Integer.parseInt(weather.getTemperature().substring(0,2));
                }

            }
            currentWeather = hoursList.get(position);
            Log.d("weather", currentWeather.toString());
            TextView currentTV = (TextView) findViewById(R.id.textViewCurrentHour);
            currentTV.setText(city+", "+state+" (" +currentWeather.getHour()+")");
            TextView currentTemp = (TextView) findViewById(R.id.textViewTemp );
            currentTemp.setText(currentWeather.getTemperature());
            TextView currentCondition = (TextView) findViewById(R.id.textViewWeather );
            currentCondition.setText(currentWeather.getCondition());
            TextView currentMax = (TextView) findViewById(R.id.textViewMax );
            currentMax.setText(maxTemp+" Fahrenheit");
            TextView currentMin = (TextView) findViewById(R.id.textViewMin );
            currentMin.setText(minTemp+" Fahrenheit");
            TextView currentFeelsLike = (TextView) findViewById(R.id.textViewFeelsLike );
            currentFeelsLike.setText(currentWeather.getFeelsLike());
            TextView currentHumidity = (TextView) findViewById(R.id.Humidity );
            currentHumidity.setText(currentWeather.getHumidity());
            TextView currentDew = (TextView) findViewById(R.id.textViewDewpoint );
            currentDew.setText(currentWeather.getDewpoint());
            TextView currentPressure = (TextView) findViewById(R.id.textViewPressure );
            currentPressure.setText(currentWeather.getPressure());
            TextView currentClouds = (TextView) findViewById(R.id.textViewClouds );
            currentClouds.setText(currentWeather.getClimate());
            TextView currentWinds = (TextView) findViewById(R.id.textViewWinds );
            currentWinds.setText(currentWeather.getWindspeed()+","+currentWeather.getWinddirection());
            ImageView icon = (ImageView) findViewById(R.id.imageViewIcon);
            Picasso.with(this).load(currentWeather.getIcon_url()).into(icon);



        }
    }
}
