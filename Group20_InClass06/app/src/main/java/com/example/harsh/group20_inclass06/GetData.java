package com.example.harsh.group20_inclass06;

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


public class GetData extends AsyncTask<String,Void,ArrayList<Feed>> {
    DataRetreiver activity;

    @Override
    protected void onPostExecute(ArrayList<Feed> newsItems) {

        super.onPostExecute(newsItems);

        Collections.sort(newsItems, new Comparator<Feed>() {
            public int compare(Feed f1, Feed f2) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                Date d1=null,d2=null;

                try {
                    d1 = simpleDateFormat.parse(f1.getPubdate());
                    d2 = simpleDateFormat.parse(f2.getPubdate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return d2.compareTo(d1);
            }
        });

        activity.setData(newsItems);
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
                return FeedParser.ParseNews(inputStream);
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
