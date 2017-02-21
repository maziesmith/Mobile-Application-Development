package com.example.inclass07_siddhant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sid on 03-10-2016.
 */

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
           // id =params[1];
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

      //  void setImage(Bitmap bitmap,String viewId );
      void setImage(Bitmap bitmap);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);

      //  activity.setImage(bitmap, id);
        activity.setImage(bitmap);
    }
}
