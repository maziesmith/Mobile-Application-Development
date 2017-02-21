package com.example.makeupmidterm_siddhantjain;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sid on 21-11-2016.
 */

public class GetVenueAsync  extends AsyncTask<String, Void, ArrayList<Venue>> {

    IVenue activity;
    StringBuilder sb;

    public GetVenueAsync(IVenue activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Venue> doInBackground(String... params) {
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
                    return VenueUtil.VenueJSONParser.parseVenues(sb.toString());
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
    protected void onPostExecute(ArrayList<Venue> venueArray) {
        if(venueArray != null)
        {
            activity.setVenueList(venueArray);
        }

    }

    public  static interface IVenue {
        public void setVenueList(ArrayList<Venue> result);
        //public void setError(String error);
    }
}
