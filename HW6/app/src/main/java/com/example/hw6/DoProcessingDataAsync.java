/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class DoProcessingDataAsync extends AsyncTask<ArrayList<ThreeHourlyWeather>, Void, ArrayList<Daily_Weather>> {

    IData activity;
    //StringBuilder sb;
    public DoProcessingDataAsync(IData activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Daily_Weather> doInBackground(ArrayList<ThreeHourlyWeather>... params) {
        ArrayList<ThreeHourlyWeather> ThreeHourlyWeatherList =params[0];
        ArrayList<ThreeHourlyWeather> day1 = null;
        ArrayList<ThreeHourlyWeather> day2 = null;
        ArrayList<ThreeHourlyWeather> day3 = null;
        ArrayList<ThreeHourlyWeather> day4 = null;
        ArrayList<ThreeHourlyWeather> day5 = null;
        Log.d("in backgraound","size :"+ThreeHourlyWeatherList.size()  );
        ThreeHourlyWeather hour0 = ThreeHourlyWeatherList.get(0);
        int date = Integer.parseInt(hour0.getDate());
        ArrayList<ThreeHourlyWeather> currentArrayList = new ArrayList<ThreeHourlyWeather>();
        currentArrayList.add(hour0);
        int count = 1;

        for(ThreeHourlyWeather hour : ThreeHourlyWeatherList)
        {   Log.d("sonal", hour.toString());
            if(date != Integer.parseInt(hour.getDate()))
            {
                switch(count)
                {
                    case 1 :{ day1 = currentArrayList;
                    for(ThreeHourlyWeather d : currentArrayList)
                    {Log.d("1",d.toString());
                    }
                    break;
                    }

                    case 2:{ day2 = currentArrayList;
                        for(ThreeHourlyWeather d : currentArrayList)
                        {Log.d("2",d.toString());
                        }
                        break;}
                    case 3 : {day3 = currentArrayList;
                        for(ThreeHourlyWeather d : currentArrayList)
                        {Log.d("3",d.toString());
                        }
                        break;}
                    case 4 : {day4 = currentArrayList;
                        for(ThreeHourlyWeather d : currentArrayList)
                        {Log.d("4",d.toString());
                        }
                        break;}
                    case 5 : {day5 = currentArrayList;
                        for(ThreeHourlyWeather d : currentArrayList)
                        {Log.d("5",d.toString());
                        }
                        break;}
                }
                count++;
                currentArrayList = new ArrayList<ThreeHourlyWeather>();
                currentArrayList.add(hour);
                date = Integer.parseInt(hour.getDate());
            }
            else
            {
                currentArrayList.add(hour);
            }
        }
        switch(count)
        {
            case 1 : day1 = currentArrayList;
            case 2 : day2 = currentArrayList;
            case 3 : day3 = currentArrayList;
            case 4 : day4 = currentArrayList;
            case 5 : day5 = currentArrayList;
        }
        final ArrayList<Daily_Weather> all_days = new ArrayList<Daily_Weather>();
        Daily_Weather day1_daily = null;
        Daily_Weather day2_daily = null;
        Daily_Weather day3_daily = null;
        Daily_Weather day4_daily = null;
        Daily_Weather day5_daily = null;
        double sum = 0 ,mean_temp = 0;
        int median = 0 ;
        ThreeHourlyWeather medianHour = null;

        if(day1 != null) {
            for (ThreeHourlyWeather weather : day1) {
                sum += Double.parseDouble(weather.getTemperature());
            }
            day1_daily = new Daily_Weather();
            mean_temp = sum / day1.size();
            day1_daily.setTemp(mean_temp + "");
            median = 0;
            if (day1.size() % 2 == 0) {
                median = day1.size() / 2 + 1;
            } else {
                median = (day1.size() + 1) / 2;
            }
            medianHour = day1.get(median - 1);
            day1_daily.setUrl(medianHour.getIcon_url());
            day1_daily.setDate(medianHour.getDatefull());
            all_days.add(day1_daily);
        }
        // second day
        sum = 0 ;
        if(day2 != null) {
            for (ThreeHourlyWeather weather : day2) {
                sum += Double.parseDouble(weather.getTemperature());
            }
            day2_daily = new Daily_Weather();
            mean_temp = sum / day2.size();
            day2_daily.setTemp(mean_temp + "");
            median = 0;
            if (day2.size() % 2 == 0) {
                median = day2.size() / 2 + 1;
            } else {
                median = (day2.size() + 1) / 2;
            }
            medianHour = day2.get(median - 1);
            day2_daily.setUrl(medianHour.getIcon_url());
            day2_daily.setDate(medianHour.getDatefull());
            all_days.add(day2_daily);
        }
// third day
        if(day3!= null)
        {
            sum = 0;
            for (ThreeHourlyWeather weather : day3) {
                sum += Double.parseDouble(weather.getTemperature());
            }
            day3_daily = new Daily_Weather();
            mean_temp = sum / day3.size();
            day3_daily.setTemp(mean_temp + "");
            median = 0;
            if (day3.size() % 2 == 0) {
                median = day3.size() / 2 + 1;
            } else {
                median = (day3.size() + 1) / 2;
            }
            medianHour = day3.get(median - 1);
            day3_daily.setUrl(medianHour.getIcon_url());
            day3_daily.setDate(medianHour.getDatefull());
            all_days.add(day3_daily);
        }
        //forth day
        if(day4!= null) {
            sum = 0;
            for (ThreeHourlyWeather weather : day4) {
                sum += Double.parseDouble(weather.getTemperature());
            }
            day4_daily = new Daily_Weather();
            mean_temp = sum / day4.size();
            day4_daily.setTemp(mean_temp + "");
            median = 0;
            if (day4.size() % 2 == 0) {
                median = day4.size() / 2 + 1;
            } else {
                median = (day4.size() + 1) / 2;
            }
            medianHour = day4.get(median - 1);
            day4_daily.setUrl(medianHour.getIcon_url());
            day4_daily.setDate(medianHour.getDatefull());
            all_days.add(day4_daily);
        }
        //fifth day
        if(day5!=null) {
            sum = 0;
            for (ThreeHourlyWeather weather : day5) {
                sum += Double.parseDouble(weather.getTemperature());
            }
            day5_daily = new Daily_Weather();
            mean_temp = sum / day5.size();
            day5_daily.setTemp(mean_temp + "");
            median = 0;
            if (day5.size() % 2 == 0) {
                median = day5.size() / 2 + 1;
            } else {
                median = (day5.size() + 1) / 2;
            }
            medianHour = day5.get(median - 1);
            day5_daily.setUrl(medianHour.getIcon_url());
            day5_daily.setDate(medianHour.getDatefull());
            all_days.add(day5_daily);
        }

        return all_days;
    }

    @Override
    protected void onPostExecute(ArrayList<Daily_Weather> dailyArray) {
        if(dailyArray != null)
        {
            activity.setDailyWeatherList(dailyArray);
        }
    }

    public  static interface IData {
        public void setDailyWeatherList(ArrayList<Daily_Weather> result);
        //public void setError(String error);
    }
}
