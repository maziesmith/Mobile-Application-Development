package com.example.inclass07_siddhant;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemDetailsActivity extends AppCompatActivity implements GetImage.imageInterface {
    TextView title, date, desc;
    ImageView img;
    String st, lol;
    ArrayList<Feed> pods;
    Feed item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        title = (TextView) findViewById(R.id.textViewTitle);
        date = (TextView) findViewById(R.id.textViewUpdateDate);
        desc = (TextView) findViewById(R.id.textViewSummary1);

        img = (ImageView) findViewById(R.id.imageViewFinal);
        if (getIntent().getExtras() != null) {
            pods = (ArrayList<Feed>) getIntent().getExtras().get("PODFEED");
            int temp = (Integer) getIntent().getExtras().get("ID");
            //int num = Integer.parseInt(temp);
            Date d1=null;
            Date d2=null;
            item = pods.get(temp);
            title.setText(item.getTitle());
            SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM a");
            String str11 = item.getUpdate().substring(0,10);
            String str12 = item.getUpdate().substring(11,19);
            String str1 = str11 +", "+ str12;
            try {
                d1 = fromUser.parse(str1);
                 lol = myFormat.format(d1);
                st = myFormat.format(fromUser.parse(str1));
        } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setText(lol);

            desc.setText(item.getSummary());
            String id = "" + img.getId();

            new GetImage(this).execute(item.getImage(), id);
        }
}

    @Override
    public void setImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }
}