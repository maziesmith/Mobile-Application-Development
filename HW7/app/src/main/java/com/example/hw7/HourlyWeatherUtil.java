package com.example.hw7;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class HourlyWeatherUtil {
    static public class HoursJSONParser{
        static ArrayList<HourlyWeather> parseHours(String in )
        {ArrayList<HourlyWeather> hoursList = new ArrayList<HourlyWeather>();
            try {
                JSONObject root = new JSONObject(in);
                JSONArray hoursJSONArray =  root.getJSONArray("hourly_forecast");

                    for (int i = 0; i < hoursJSONArray.length(); i++) {
                        JSONObject hourJSONObject = (JSONObject) hoursJSONArray.get(i);
                        HourlyWeather hour = HourlyWeather.createHour(hourJSONObject);
                        Log.d("each hour", hour.toString());
                        hoursList.add(hour);
                    }

            } catch (JSONException e) {
                hoursList = null;
                e.printStackTrace();
            }


            return  hoursList;
        }

        static String parseError(String in )
        {
            JSONObject root = null;
            String errorText = null;
            try {
                root = new JSONObject(in);
                JSONObject response = root.getJSONObject("response");
                JSONObject error = response.getJSONObject("error");

                if(error != null)
                {
                   errorText = error.getString("description");
                }

            } catch (JSONException e) {
                errorText = "Incorrect City /State combination";
                e.printStackTrace();
            }
           return errorText;

        }
    }
}
