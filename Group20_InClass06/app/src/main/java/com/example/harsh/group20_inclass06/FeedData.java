package com.example.harsh.group20_inclass06;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FeedData extends AppCompatActivity implements GetImage.imageInterface{
    ArrayList<Feed> news;
    Feed item;
    TextView title, date, desc;
    ImageView img;
    String st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_data);
        title = (TextView) findViewById(R.id.title_tv);
        date = (TextView) findViewById(R.id.pubdate_tv);
        desc = (TextView) findViewById(R.id.story_tv);

        img = (ImageView) findViewById(R.id.imageView);
        if (getIntent().getExtras() != null) {

            news = (ArrayList<Feed>) getIntent().getExtras().get("NEWSFEED");

            String temp = (String) getIntent().getExtras().get("ID");
            int num = Integer.parseInt(temp);

            item = news.get(num);
            title.setText(item.getTitle());

            SimpleDateFormat fromUser = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM a");
            try {

                st = myFormat.format(fromUser.parse(item.getPubdate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            date.setText(st);

            desc.setText(item.getDescription());

            String id = "" + img.getId();

            new GetImage(this).execute(item.getImage(), id);
        }
    }


    @Override
    public void setImage(Bitmap bitmap, String viewId) {
        img.setImageBitmap(bitmap);
    }
}
