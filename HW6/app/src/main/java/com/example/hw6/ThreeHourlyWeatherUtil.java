/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class ThreeHourlyWeatherUtil {
    static public class HoursJSONParser {
        static ArrayList<ThreeHourlyWeather> parseHours(String in) {
            ArrayList<ThreeHourlyWeather> hoursList = new ArrayList<ThreeHourlyWeather>();
            try {
                JSONObject root = new JSONObject(in);
                JSONArray hoursJSONArray = root.getJSONArray("list");

                for (int i = 0; i < hoursJSONArray.length(); i++) {
                    JSONObject hourJSONObject = (JSONObject) hoursJSONArray.get(i);
                    ThreeHourlyWeather hour = ThreeHourlyWeather.createHour(hourJSONObject);
                    Log.d("each hour", hour.toString());
                    hoursList.add(hour);
                }

            } catch (JSONException e) {
                hoursList = null;
                e.printStackTrace();
            }


            return hoursList;
        }

    }
}
