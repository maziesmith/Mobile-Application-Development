package com.example.hw7;

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
public class GetHourlyWeatherAsync extends AsyncTask<String, Void, ArrayList<HourlyWeather>> {

    IHour activity;
    StringBuilder sb;
    public GetHourlyWeatherAsync(IHour activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<HourlyWeather> doInBackground(String... params) {
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
                    return HourlyWeatherUtil.HoursJSONParser.parseHours(sb.toString());
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
    protected void onPostExecute(ArrayList<HourlyWeather> hourArray) {
        if(hourArray != null)
        {
           // Log.d("demo", hourArray.toString());
            activity.setHourlyWeatherList(hourArray);
        }
        else
        { Log.d("inside", "else");
           String error =  HourlyWeatherUtil.HoursJSONParser.parseError(sb.toString());
            Log.d("inside2", "error : "+error);
           activity.setError(error);
        }
    }

    public  static interface IHour {
        public void setHourlyWeatherList(ArrayList<HourlyWeather> result);
        public void setError(String error);
    }
}
