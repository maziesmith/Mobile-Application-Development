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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class GetThreeHourlyWeatherAsync extends AsyncTask<String, Void, ArrayList<ThreeHourlyWeather>> {

    IHour activity;
    StringBuilder sb;
    public GetThreeHourlyWeatherAsync(IHour activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<ThreeHourlyWeather> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int statusCode = conn.getResponseCode();
                if(statusCode==HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    sb = new StringBuilder();
                    String line = br.readLine();
                    while(line != null)
                    {
                        sb.append(line);
                        line = br.readLine();
                    }
                    //  Log.d("demo" , sb.toString());
                    return ThreeHourlyWeatherUtil.HoursJSONParser.parseHours(sb.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<ThreeHourlyWeather> hourArray) {
        if(hourArray != null)
        {
            activity.setHourlyWeatherList(hourArray);
        }

    }

    public  static interface IHour {
        public void setHourlyWeatherList(ArrayList<ThreeHourlyWeather> result);
        //public void setError(String error);
    }
}
