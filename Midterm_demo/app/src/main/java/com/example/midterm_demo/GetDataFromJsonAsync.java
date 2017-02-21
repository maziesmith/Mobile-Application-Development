package com.example.midterm_demo;

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
public class GetDataFromJsonAsync extends AsyncTask<String, Void, ArrayList<ITuneApp>> {

    IApp activity;
    StringBuilder sb;
    public GetDataFromJsonAsync(IApp activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<ITuneApp> doInBackground(String... params) {
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
                    return ITuneAppUtil.AppsJSONParser.parseApps(sb.toString());
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
    protected void onPostExecute(ArrayList<ITuneApp> appArray) {
        if(appArray != null)
        {
            activity.setITunesApps(appArray);
        }

    }

    public  static interface IApp {
        public void setITunesApps(ArrayList<ITuneApp> result);
        //public void setError(String error);
    }
}
