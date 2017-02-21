package com.example.harsh.group20_inclass06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetImage extends AsyncTask<String,Void,Bitmap> {
    Bitmap bit;
    imageInterface activity;
    String id;

    public GetImage(imageInterface activity) {
        this.activity=activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url= null;
        try {
            url = new URL(params[0]);
            id =params[1];
            HttpURLConnection con= (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            bit = BitmapFactory.decodeStream(con.getInputStream());
            return bit;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    interface imageInterface{

        void setImage(Bitmap bitmap,String viewId );
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);

        activity.setImage(bitmap, id);
    }
}
