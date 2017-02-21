package com.example.midterm_siddhant_jain;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.midterm_siddhant_jain.R.drawable.price_high;
import static com.example.midterm_siddhant_jain.R.drawable.price_low;
import static com.example.midterm_siddhant_jain.R.drawable.price_medium;
//import static com.squareup.picasso.Utils.getResources;

/**
 * Created by sid on 24-10-2016.
 */

public class AppAdapter extends ArrayAdapter<App>{
    Context mContext ;
    int mresource;
    List<App> mApps;
    ImageView image;
    public AppAdapter(Context context, int resource, List<App> objects) {

        super(context, resource, objects);
        Log.d("newsadapter", "inside conctructor");
        mContext = context;
        mresource = resource;
        mApps = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //  return super.getView(position, convertView, parent);
        if(convertView == null)
        {  Log.d("inflator", "inside null");
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource,parent,false);
        }

        App app = mApps.get(position);
        Log.d("demo", app.toString());
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageView1);
        TextView name = (TextView) convertView.findViewById(R.id.textViewName);
        TextView price = (TextView) convertView.findViewById(R.id.textViewPrice);
        ImageView priceImage = (ImageView) convertView.findViewById(R.id.imageView2);
        if(Double.parseDouble(app.getPrice().replace("$",""))<2.00){

            int id = convertView.getResources().getIdentifier("com.example.midterm_siddhant_jain:drawable/"+price_low,null,null);
            priceImage.setImageResource(R.drawable.price_low);
        }else if(Double.parseDouble(app.getPrice().replace("$",""))<6.00 && Double.parseDouble(app.getPrice().replace("$",""))>2.00){

            int id = convertView.getResources().getIdentifier("com.example.midterm_siddhant_jain:drawable/"+price_medium,null,null);
            priceImage.setImageResource(R.drawable.price_medium);
        }else{
            int id = convertView.getResources().getIdentifier("com.example.midterm_siddhant_jain:drawable/"+price_high,null,null);
            priceImage.setImageResource(R.drawable.price_high);
        }

        Log.d("demo",app.getName());
        Log.d("demo",app.getPrice());
        Log.d("demo",app.getImage());
        //Log.d("demo",hour.getIcon_url());
        name.setText(app.getName());
        price.setText(app.getPrice());
        //priceImage.setText(hour.getCondition());
        Picasso.with(mContext).load(app.getImage()).into(icon);
        return convertView;

    }




}
