package com.example.midterm_siddhant_jain;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sid on 24-10-2016.
 */

public class GetAppAsync extends AsyncTask<String, Void, ArrayList<App>>{

    IApp activity;
    StringBuilder sb;
    public GetAppAsync(IApp activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
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
                     Log.d("demo" , "sbtostring "+sb.toString());
                    return AppUtil.AppJSONParser.parseApps(sb.toString());
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
    protected void onPostExecute(ArrayList<App> appArray) {
        if(appArray != null)
        {
            Collections.sort(appArray);
            // Log.d("demo", hourArray.toString());
            activity.setAppList(appArray);
        }
        else
        { Log.d("inside", "else");
            //String error =  AppUtil.AppJSONParser.parseError(sb.toString());
            //Log.d("inside2", "error : "+error);
            //activity.setError(error);
        }
    }
    public  static interface IApp {
        public void setAppList(ArrayList<App> result);
        //public void setError(String error);
    }
}
