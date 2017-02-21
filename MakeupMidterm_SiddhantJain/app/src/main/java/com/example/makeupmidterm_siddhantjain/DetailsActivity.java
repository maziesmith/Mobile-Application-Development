package com.example.makeupmidterm_siddhantjain;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ProgressDialog pd;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getIntent().getExtras() != null) {
            final ArrayList<Venue> venues = getIntent().getExtras().getParcelableArrayList(ResultActivity.VENUE_ID);
            final int position = getIntent().getExtras().getInt(ResultActivity.TR_ID);
            Log.d("details", venues.toString());
            TextView tvname = (TextView) findViewById(R.id.textViewNameDetails);
            Venue venue = venues.get(position);
            tvname.setText(venue.getName());
            TextView tvcat = (TextView) findViewById(R.id.textViewCategoryDetails);
            tvcat.setText(venue.getCategory());
            TextView tvadd = (TextView) findViewById(R.id.textViewAddressDetails);
            tvadd.setText(venue.getAddress());
            TextView tvcheck = (TextView) findViewById(R.id.textViewCheckinsDetails);
            tvcheck.setText(String.valueOf(venue.getCheckin_count()));
            TextView tvuser = (TextView) findViewById(R.id.textViewUsersDetails);
            tvuser.setText(String.valueOf(venue.getUsers_count()));
            TextView tvhere = (TextView) findViewById(R.id.textViewHereNowDetails);
            tvhere.setText(String.valueOf(venue.getHere_now()));
            pd = new ProgressDialog(this);
            pd.setMessage("Downloading Image");
            img = (ImageView) findViewById(R.id.imageViewDetails);
            new DownloadImageTask(img).execute(venue.getIcon_url());
        }
    }


    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            pd.dismiss();
            bmImage.setImageBitmap(result);
        }
    }
}

