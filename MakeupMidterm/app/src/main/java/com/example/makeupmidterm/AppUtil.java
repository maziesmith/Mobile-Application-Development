package com.example.makeupmidterm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sid on 24-10-2016.
 */

public class AppUtil {
    static public class AppJSONParser{
        static ArrayList<App> parseApps(String in )
        {ArrayList<App> appsList = new ArrayList<App>();
            try {
                Log.d("demo","inside try of apputil");
                Log.d("demo","in value ="+in);
                JSONObject root1 = new JSONObject(in);

                JSONObject root = root1.getJSONObject("feed");
                JSONArray appsJSONArray =  root.getJSONArray("entry");
                Log.d("demo","jsonarray="+ appsJSONArray.toString());
                for (int i = 0; i < appsJSONArray.length(); i++) {
                    JSONObject appJSONObject = (JSONObject) appsJSONArray.get(i);
                    App appdata = App.createApp(appJSONObject);
                    Log.d("each app", appdata.toString());
                    appsList.add(appdata);
                }

            } catch (JSONException e) {
                appsList = null;
                e.printStackTrace();
            }


            return  appsList;
        }
/*
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
        */
    }
}
