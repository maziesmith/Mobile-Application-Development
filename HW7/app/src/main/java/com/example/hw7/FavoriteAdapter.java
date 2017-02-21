package com.example.hw7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by skaul on 10/3/2016.
 */
public class FavoriteAdapter extends ArrayAdapter<Favorite>
        // implements  GetImage.ImageInterface
{
    Context mContext ;
    int mresource;
    List<Favorite> mFavorites;
    ImageView image;
    public FavoriteAdapter(Context context, int resource, List<Favorite> objects) {

        super(context, resource, objects);
        Log.d("newsadapter", "inside conctructor");
        mContext = context;
        mresource = resource;
        mFavorites = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  return super.getView(position, convertView, parent);
        if(convertView == null)
        {  Log.d("inflator", "inside null");
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource,parent,false);
        }

        Favorite fav = mFavorites.get(position);
     //   Log.d("demo + position ", fav.toString() +"  "+position);
     //   ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewHour);
        TextView citystateTV = (TextView) convertView.findViewById(R.id.textViewS);
        TextView tempTV = (TextView) convertView.findViewById(R.id.textViewTemp);
        TextView dateTV = (TextView) convertView.findViewById(R.id.textViewDate);
        //Log.d("demo",hour.getHour());
        //Log.d("demo",hour.getTemperature());
        // Log.d("demo",hour.getCondition());
        //Log.d("demo",hour.getIcon_url());
        String citystate = fav.getCity()+", "+fav.getState();
        citystateTV.setText(citystate);
        tempTV.setText(fav.getTemperature());
        String uDate = fav.getDate();
        SimpleDateFormat olddateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = olddateFormat.parse(uDate);
            olddateFormat.applyPattern("MM/dd/yy");
            dateTV.setText(olddateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

       // Picasso.with(mContext).load(hour.getIcon_url()).into(icon);

        return convertView;

    }




}
