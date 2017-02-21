package com.example.midterm_siddhant_jain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by sid on 25-10-2016.
 */

public class RecycleAdapter extends
        RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    DatabaseManager dm ;
    private Context mContext;
    private List<App> mThreeHour;
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
    public RecycleAdapter(Context context, List<App> ThreeHour) {

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
        public TextView nameTextView;
        public TextView priceTextView;
        public ImageView iconImageView, trashImageView, priceImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.textViewNameAdapter);
            priceTextView = (TextView) itemView.findViewById(R.id.textViewPriceAdapter);
            iconImageView = (ImageView) itemView.findViewById(R.id.imageViewIconAdapter);
            trashImageView = (ImageView) itemView.findViewById(R.id.imageViewTrashAdapter);
            priceImageView = (ImageView) itemView.findViewById(R.id.imageViewPriceAdapter);
            trashImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.adapter_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position
        final App current = mThreeHour.get(position);

        // Set item views based on your views and data model
        TextView nametextView = viewHolder.nameTextView;
        nametextView.setText(current.getName());
        TextView pricetextView = viewHolder.priceTextView;
        pricetextView.setText(current.getPrice());
        ImageView icon = viewHolder.iconImageView;
        Picasso.with(mContext).load(current.getImage()).into(icon);
        ImageView trash = viewHolder.trashImageView;
        dm = new DatabaseManager(this.getContext());
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm.deleteFavorite(mThreeHour.get(position));
                notifyDataSetChanged();
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mThreeHour.size();
    }
}
