package com.example.midterm_demo;

import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class ITuneAppUtil {
    static public class AppsJSONParser{
        static ArrayList<ITuneApp> parseApps(String in )
        {ArrayList<ITuneApp> appsList = new ArrayList<ITuneApp>();
            try {
                JSONObject root = new JSONObject(in);
                JSONObject feed = root.getJSONObject("feed");
                JSONArray appsJSONArray =  feed.getJSONArray("entry");

                for (int i = 0; i < appsJSONArray.length(); i++) {
                    JSONObject appJSONObject = (JSONObject) appsJSONArray.get(i);
                    ITuneApp app = ITuneApp.createITune(appJSONObject);
                    Log.d("each app", app.toString());
                    appsList.add(app);
                }

            } catch (JSONException e) {
                appsList = null;
                e.printStackTrace();
            }


            return  appsList;
        }



       /* static String parseError(String in )
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

        }*/
    }
}
