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
public class DailyAdapter extends
        RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private Context mContext;
    private List<Daily_Weather> mThreeHour;
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
    public DailyAdapter(Context context, List<Daily_Weather> ThreeHour) {

        mThreeHour = ThreeHour;
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
        public TextView dateTextView;
        public TextView tempTextView;
        public ImageView medianimageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.textViewDateRecycle1);
            tempTextView = (TextView) itemView.findViewById(R.id.textViewTempRecycle1);
            medianimageView = (ImageView) itemView.findViewById(R.id.imageViewRecycle1);
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
    public DailyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.h1_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DailyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Daily_Weather current = mThreeHour.get(position);

        // Set item views based on your views and data model
        TextView datetextView = viewHolder.dateTextView;
        datetextView.setText(current.getDate());
        TextView temptextView = viewHolder.tempTextView;
        double temperature = Double.parseDouble(current.getTemp());
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        String selected = sharedPrefs.getString("prefTempUnit", "NULL");
        Log.d("demo", "selected is "+selected);
        double curtemp = 0 ;
        String displayedTemp = null;
        if(selected.equals("C"))
        {
             curtemp = Double.parseDouble(current.getTemp());
            curtemp = curtemp - 273.15;
            displayedTemp =numberFormat.format(curtemp)+"°C";

        }
        else
        {  curtemp = Double.parseDouble(current.getTemp());
            Log.d("sonal",curtemp+"");
            curtemp= (curtemp*(9.0/5.0)) - 459.67;
           // Log.d("changed",curtemp+""+"f set temp"+current.getTemp());
            displayedTemp =numberFormat.format(curtemp)+"°F";
        }
        temptextView.setText(displayedTemp);
        ImageView icon = viewHolder.medianimageView;
        Picasso.with(mContext).load(current.getUrl()).into(icon);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mThreeHour.size();
    }
}