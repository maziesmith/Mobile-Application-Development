/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
//import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.text.GetChars;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;



public class CityWeather extends AppCompatActivity implements GetThreeHourlyWeatherAsync.IHour, DoProcessingDataAsync.IData {
   // private static final int RESULT_SETTINGS = 1;
    ProgressDialog progress;
    Daily_Weather selectedday = null, initialday = null;
    SwipeRefreshLayout swipeRefreshLayout;
   ArrayList<ThreeHourlyWeather> ThreeHourlyWeatherList = null;
   DatabaseManager dm ;
    String city,country = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        dm = new DatabaseManager(this);
        if(getIntent().getExtras()!= null){

            progress =  new ProgressDialog(this);
            progress.setTitle(R.string.loading_hourly_data);
            progress.show();
            city = (String) getIntent().getExtras().get(MainActivity.CITY_KEY);
            country = (String) getIntent().getExtras().get(MainActivity.COUNTRY_KEY);
            //  Log.d("city", city);
            // Log.d("state", state);
            String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city+","+country+"&mode=json&appid=7bd5a066aa6db85107ce65dc76d48c4d";
            Log.d("url : ", url);
            new GetThreeHourlyWeatherAsync(this).execute(url);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings: {

                Intent intent = new Intent(this, UserSettingsActivity.class);
                intent.putExtra("call","CityWeather");
                intent.putExtra(MainActivity.CITY_KEY,city);
                intent.putExtra(MainActivity.COUNTRY_KEY,country);
                startActivity(intent);
                return true;
            }
            case R.id.savecity:{
                String date_to_save = null,temp_to_save = null;
                if(selectedday != null){ date_to_save = selectedday.getDate();
                temp_to_save = selectedday.getTemp();}
                else
                {date_to_save = initialday.getDate();
                temp_to_save = initialday.getTemp();
                Log.d("insavecity", temp_to_save);}
                Favorite fav = new Favorite();
              /*  SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(this);
                String selected = sharedPrefs.getString("prefTempUnit", "NULL");


                if(selected.equals("C"))
                {double curtemp = Double.parseDouble(temp_to_save);
                curtemp = curtemp + 273.15;
                fav.setTemperature(curtemp+"");}
                else
                {double curtemp = Double.parseDouble(temp_to_save);
                    curtemp = (curtemp + 459.67) + (5.0/9.0);
                    fav.setTemperature(curtemp+"");

                }
                */
                fav.setTemperature(temp_to_save);
                fav.setDate(date_to_save);fav.setCity(city);fav.setCountry(country);fav.setFavorite(0);
                Favorite savedfav = dm.getFavorite(city,country);
                if(savedfav== null)
                {dm.saveFavorite(fav);}
                else
                {
                    dm.updateFavorite(fav);
                }
                Toast.makeText(getApplicationContext(), R.string.addedFav, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CityWeather.this,MainActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setHourlyWeatherList(ArrayList<ThreeHourlyWeather> result) {
        progress.cancel();
        ThreeHourlyWeatherList = result;
        Collections.sort(ThreeHourlyWeatherList);
        Log.d("demo",ThreeHourlyWeatherList.toString());
        new DoProcessingDataAsync(this).execute(ThreeHourlyWeatherList);
    }

    @Override
    public void setDailyWeatherList(ArrayList<Daily_Weather> result) {
        ArrayList<Daily_Weather> all_days = new ArrayList<Daily_Weather>();
        all_days = result;
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String selected = sharedPrefs.getString("prefTempUnit", "NULL");
        /*for(Daily_Weather d : all_days)
        {//Log.d("main", d.toString());
            if(selected.equals("C"))
            {
                double curtemp = Double.parseDouble(d.getTemp());
                curtemp = curtemp - 273.15;
                d.setTemp(curtemp+"");
            }
            else
            { double curtemp = Double.parseDouble(d.getTemp());
                Log.d("sonal",curtemp+"");
                double newtemp = (curtemp*(9.0/5.0)) - 459.67;
                d.setTemp(newtemp+"");
                Log.d("changed",newtemp+""+"f set temp"+d.getTemp());
            }


        }
        */
        RecyclerView rvDaily = (RecyclerView) findViewById(R.id.recyclerViewh1);
        DailyAdapter adapter = new DailyAdapter(this, all_days);
        // Attach the adapter to the recyclerview to populate items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        // Attach layout manager to the RecyclerView
        rvDaily.setLayoutManager(layoutManager);
        rvDaily.setAdapter(adapter);
        // rvDaily.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
        rvDaily.addItemDecoration(itemDecoration);
         final ArrayList<Daily_Weather> finalAll_days = all_days;

        initialday = all_days.get(0);
        TextView tvThreeHourly = (TextView) findViewById(R.id.textViewThreeHourly);
        tvThreeHourly.setText("Three Hourly Forecast On "+initialday.getDate());
        final ArrayList<ThreeHourlyWeather> adaph2List = new ArrayList<ThreeHourlyWeather>();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        java.util.Date dateinformat = null;

        try {
            dateinformat = format.parse(initialday.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(ThreeHourlyWeather thw : ThreeHourlyWeatherList)
        {
            if(Integer.parseInt(thw.getDate()) == dateinformat.getDate())
            {
                adaph2List.add(thw);
            }
        }
        Log.d("adaph2", adaph2List.toString());
     /*  for(ThreeHourlyWeather thw : adaph2List) {//Log.d("main", d.toString());
            if (selected.equals("C")) {
                double curtemp = Double.parseDouble(thw.getTemperature());
                curtemp = curtemp - 273.15;
                thw.setTemperature(curtemp + "");
            } else {
                double curtemp = Double.parseDouble(thw.getTemperature());
                Log.d("sonal", curtemp + "");
                double newtemp = (curtemp * (9.0 / 5.0)) - 459.67;
                thw.setTemperature(newtemp + "");
                Log.d("changed", newtemp + "" + "f set temp" + thw.getTemperature());
            }
        }
       */
            final RecyclerView rvHourly = (RecyclerView) findViewById(R.id.recyclerViewh2);
        final ThreeHourlyAdapter thwadapter = new ThreeHourlyAdapter(CityWeather.this, adaph2List);
        // Attach the adapter to the recyclerview to populate items

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(CityWeather.this, LinearLayoutManager.HORIZONTAL, false);
        // Attach layout manager to the RecyclerView
        rvHourly.setLayoutManager(layoutManager2);
        rvHourly.setAdapter(thwadapter);
        // rvDaily.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration2 = new
                DividerItemDecoration(CityWeather.this, DividerItemDecoration.HORIZONTAL_LIST);
        rvHourly.addItemDecoration(itemDecoration2);
        adapter.setOnItemClickListener(new DailyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedday = finalAll_days.get(position);
                ArrayList<ThreeHourlyWeather> adaph2List1 = new ArrayList<ThreeHourlyWeather>();
                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
                java.util.Date dateinformat = null;

                try {
                    dateinformat = format.parse(selectedday.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                for(ThreeHourlyWeather thw : ThreeHourlyWeatherList)
                {
                    if(Integer.parseInt(thw.getDate()) == dateinformat.getDate())
                    {
                        adaph2List1.add(thw);
                    }
                }
                Log.d("adaph2", adaph2List1.toString());
                Log.d("demo","selected day "+position);
               /* SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(CityWeather.this);
                String selected = sharedPrefs.getString("prefTempUnit", "NULL");
                for(ThreeHourlyWeather thw : adaph2List1) {//Log.d("main", d.toString());
                    if (selected.equals("C")) {
                        double curtemp = Double.parseDouble(thw.getTemperature());
                        curtemp = curtemp - 273.15;
                        thw.setTemperature(curtemp + "");
                    } else {
                        double curtemp = Double.parseDouble(thw.getTemperature());
                        Log.d("sonal", curtemp + "");
                        double newtemp = (curtemp * (9.0 / 5.0)) - 459.67;
                        thw.setTemperature(newtemp + "");
                        Log.d("changed", newtemp + "" + "f set temp" + thw.getTemperature());
                    }
                }
                */
                ThreeHourlyAdapter adapterchannged = new ThreeHourlyAdapter(CityWeather.this, adaph2List1);
                rvHourly.setAdapter(adapterchannged);
                adapterchannged.notifyDataSetChanged();
                TextView tvThreeHourly = (TextView) findViewById(R.id.textViewThreeHourly);
                tvThreeHourly.setText("Three Hourly Forecast On "+selectedday.getDate());
            }
        });


    }
}
