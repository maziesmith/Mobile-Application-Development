package com.example.madhu.in_class_05;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by madhu on 9/19/2016.
 */
public class GetImage extends AsyncTask<String,Void, Bitmap>{

    MainActivity activity;
    ProgressDialog pd;

    public GetImage(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            Bitmap image = BitmapFactory.decodeStream(httpURLConnection.getInputStream());

            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(activity);
        pd.setMessage("Loading Image...");
        pd.setMax(100);
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap!=null)
        {
            activity.setUpImage(bitmap);
        }
        pd.dismiss();
    }

}
