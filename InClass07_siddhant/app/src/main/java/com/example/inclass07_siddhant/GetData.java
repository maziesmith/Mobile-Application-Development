package com.example.inclass07_siddhant;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by sid on 03-10-2016.
 */

public class GetData extends AsyncTask<String,Void,ArrayList<Feed>> {
    DataRetreiver activity;

    @Override
    protected void onPostExecute(ArrayList<Feed> feeds) {
        super.onPostExecute(feeds);
        Collections.sort(feeds, new Comparator<Feed>() {
            public int compare(Feed f1, Feed f2) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
                Date d1=null;
                Date d2=null;
                Log.d("demo","date string "+f1.getDate());
                Log.d("demo","date summary "+f1.getSummary());
                String str11 = f1.getDate().substring(0,10);
                String str12 = f1.getDate().substring(11,19);
                String str1 = str11 +", "+ str12;
                String str21 = f2.getDate().substring(0,10);
                String str22 = f2.getDate().substring(11,19);
                String str2 = str21 +", "+ str22;
                Log.d("demo","date new string "+ str1);
                try {
                    d1 = simpleDateFormat.parse(str1);
                    d2 = simpleDateFormat.parse(str2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("demo"," d1 "+d1);
                return d2.compareTo(d1);
            }
        });
        activity.setData(feeds);
    }
    public GetData(DataRetreiver activity) {

        this.activity=activity;

    }
    @Override
    protected ArrayList<Feed> doInBackground(String... params) {
        try{
            URL url=new URL(params[0]);

            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode=con.getResponseCode();
            if(statuscode==HttpURLConnection.HTTP_OK)
            {
                Log.d("demo1"," @getdata3");
                InputStream inputStream= con.getInputStream();
                return FeedParser.ParsePod(inputStream);
            }






        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }




    interface DataRetreiver{
        void setData(ArrayList<Feed> newsList);
    }
}
