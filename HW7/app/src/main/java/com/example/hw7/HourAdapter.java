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

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by skaul on 10/3/2016.
 */
public class HourAdapter extends ArrayAdapter<HourlyWeather>
        // implements  GetImage.ImageInterface
{
    Context mContext ;
    int mresource;
    List<HourlyWeather> mHours;
    ImageView image;
    public HourAdapter(Context context, int resource, List<HourlyWeather> objects) {

        super(context, resource, objects);
        Log.d("newsadapter", "inside conctructor");
        mContext = context;
        mresource = resource;
        mHours = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  return super.getView(position, convertView, parent);
        if(convertView == null)
        {  Log.d("inflator", "inside null");
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource,parent,false);
        }

        HourlyWeather hour = mHours.get(position);
        Log.d("demo", hour.toString());
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewHour);
        TextView time = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView temperature = (TextView) convertView.findViewById(R.id.textViewTemp);
        TextView condition = (TextView) convertView.findViewById(R.id.textViewWeather);
        Log.d("demo",hour.getHour());
        Log.d("demo",hour.getTemperature());
        Log.d("demo",hour.getCondition());
        Log.d("demo",hour.getIcon_url());
        time.setText(hour.getHour());
        temperature.setText(hour.getTemperature());
        condition.setText(hour.getCondition());
        Picasso.with(mContext).load(hour.getIcon_url()).into(icon);

        return convertView;

    }




}
