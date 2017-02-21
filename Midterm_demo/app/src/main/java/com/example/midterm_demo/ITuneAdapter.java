package com.example.midterm_demo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.midterm_demo.ITuneApp;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ITuneAdapter extends
        RecyclerView.Adapter<ITuneAdapter.ViewHolder> {
    private Context mContext;
    private List<ITuneApp> mApps;
    // Define listener member variable
    private static OnItemLongClickListener listener;
    // Define the listener interface
    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    // Pass in the contact array into the constructor
    public ITuneAdapter(Context context, List<ITuneApp> MAPPS) {

        mApps = MAPPS;
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

        public ImageView iconThumb;
        public ImageView icondollar;
        public TextView nameTV;
        public TextView priceTV;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            iconThumb = (ImageView) itemView.findViewById(R.id.imageViewThumb);
            icondollar = (ImageView) itemView.findViewById(R.id.imageViewDollar);
            nameTV = (TextView) itemView.findViewById(R.id.textViewName);
             priceTV = (TextView) itemView.findViewById(R.id.textViewPrice);
            itemView.setOnLongClickListener (new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemLongClick(v,position);
                            return false;
                        }

                }
                    return false;
            }});

        }

    }
    @Override
    public ITuneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ITuneAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        ITuneApp current = mApps.get(position);

        ImageView iconThumb = viewHolder.iconThumb;
        ImageView icondollar = viewHolder.icondollar;
        TextView nameTV = viewHolder.nameTV;
        TextView priceTV = viewHolder.priceTV;
        nameTV.setText(current.getName());
        priceTV.setText("Price : "+current.getCurrency()+" "+current.getPrice() );
        Picasso.with(mContext).load(current.getThumb_url()).into(iconThumb);
        if(current.getDollarimage().equals("1"))
        {
          icondollar.setImageResource(R.drawable.price_low);
        }else if(current.getDollarimage().equals("2"))
        {
            icondollar.setImageResource(R.drawable.price_medium);
        }
        else if(current.getDollarimage().equals("3"))
        {
            icondollar.setImageResource(R.drawable.price_high);
        }


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mApps.size();
    }
}
