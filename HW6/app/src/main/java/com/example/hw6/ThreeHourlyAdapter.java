/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hw6.Daily_Weather;
import com.example.hw6.R;
import com.example.hw6.ThreeHourlyWeather;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ThreeHourlyAdapter extends
        RecyclerView.Adapter<ThreeHourlyAdapter.ViewHolder> {
    private Context mContext;
    private List<ThreeHourlyWeather> mTHWeather;
    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the contact array into the constructor
    public ThreeHourlyAdapter(Context context, List<ThreeHourlyWeather> THWeather) {

        mTHWeather = THWeather;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }
    // Your holder should contain a member variable
    // for any view that will be set as you render a row

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Store the context for easy access
        public TextView hourTextView;
        public ImageView iconimageView;
        public TextView tempTextView;
        public TextView condTextView;
        public TextView pressureTextView;
        public TextView humidityTextView;
        public TextView windTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            hourTextView = (TextView) itemView.findViewById(R.id.textViewHour);
            tempTextView = (TextView) itemView.findViewById(R.id.textViewTemp);
            condTextView = (TextView) itemView.findViewById(R.id.textViewCondition);
            pressureTextView = (TextView) itemView.findViewById(R.id.textViewPressure);
            humidityTextView = (TextView) itemView.findViewById(R.id.textViewHumidity);
            windTextView = (TextView) itemView.findViewById(R.id.textViewWind);
            iconimageView = (ImageView) itemView.findViewById(R.id.imageViewIconh2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });

        }

    }
    @Override
    public ThreeHourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.h2_row_desc, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ThreeHourlyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        ThreeHourlyWeather current = mTHWeather.get(position);

        // Set item views based on your views and data model
        TextView hourtextView = viewHolder.hourTextView;
        hourtextView.setText(current.getHour());
        TextView temptextView = viewHolder.tempTextView;
        double temperature = Double.parseDouble(current.getTemperature());
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        String selected = sharedPrefs.getString("prefTempUnit", "NULL");
        Log.d("demo", "selected is "+selected);
        double curtemp= 0;
        String displayedTemp= null;
        if(selected.equals("C"))
        {
             curtemp = Double.parseDouble(current.getTemperature());
            curtemp = curtemp - 273.15;
            displayedTemp =numberFormat.format(curtemp)+"°C";
        }
        else
        { curtemp = Double.parseDouble(current.getTemperature());
            Log.d("sonal",curtemp+"");
            curtemp = (curtemp*(9.0/5.0)) - 459.67;
          //  Log.d("changed",curtemp+""+"f set temp"+curren    t.getTemperature());
            displayedTemp =numberFormat.format(curtemp)+"°C";

        }
        temptextView.setText("Temperature :"+displayedTemp);
        ImageView icon = viewHolder.iconimageView;
        Log.d("inthreeadapter","imageurl: "+current.getIcon_url());
        Picasso.with(mContext).load(current.getIcon_url()).into(icon);
        TextView condtextView = viewHolder.condTextView;
        condtextView.setText("Condition : "+current.getCondition());
        TextView humdtextView = viewHolder.humidityTextView;
        humdtextView.setText("Humidity : "+current.getHumidity());
        TextView pressureTextView = viewHolder.pressureTextView;
        pressureTextView.setText("Pressure : "+current.getPressure());
        TextView windtextView = viewHolder.windTextView;
        windtextView.setText("Wind : "+current.getWindspeed()+", "+current.getWinddirection());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mTHWeather.size();
    }
}