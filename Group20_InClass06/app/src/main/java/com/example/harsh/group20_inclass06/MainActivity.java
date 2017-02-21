/*
* Group 20
* Harshith Nannapaneni
* Siddhant Jain
* Arstan Korolev
* Lakshmanan Ramu Meenal
* */

package com.example.harsh.group20_inclass06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetData.DataRetreiver,GetImage.imageInterface {

    ArrayList<Feed> feedItems =new ArrayList<Feed>();
    ProgressDialog progress;
    Feed ni;
    String index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progress =new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle("Loading News");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        new GetData(this).execute("http://rss.cnn.com/rss/cnn_tech.rss");


    }

    @Override
    public void setData(ArrayList<Feed> newsList) {

        feedItems =newsList;

        progress.dismiss();

        LinearLayout pll=(LinearLayout) findViewById(R.id.parentLayout);

        for(int i=0;i<newsList.size();i++){
            ni=newsList.get(i);
            LinearLayout linear=new LinearLayout(this);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            linear.setId(1000 + i);
            ImageView imageview =new ImageView(this);
            imageview.setId(i);
            imageview.setMaxHeight(50);
            imageview.setMinimumHeight(50);
            imageview.setMaxWidth(50);
            imageview.setMinimumWidth(50);
            index=""+i;

            new GetImage(this).execute(ni.getThumbnail(),index);
            TextView text=new TextView(this);
            text.setText(ni.getTitle());
            linear.addView(imageview);
            linear.addView(text);

            pll.addView(linear);
            linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String temp = "" + (v.getId() - 1000);

                    Intent i = new Intent(MainActivity.this, FeedData.class);
                    i.putExtra("NEWSFEED", feedItems);
                    i.putExtra("ID", temp);
                    startActivity(i);
                }
            });


        }
        System.out.println(feedItems.toString());
    }

    @Override
    public void setImage(Bitmap bitmap, String viewId) {

        int ivId=Integer.parseInt(viewId);
        ImageView iview=(ImageView)findViewById(ivId);

        iview.setImageBitmap(bitmap);

    }
}
