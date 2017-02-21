package com.example.makeupmidterm_siddhantjain;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sid on 21-11-2016.
 */



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
        import com.squareup.picasso.Picasso;

        import java.text.DecimalFormat;
        import java.util.List;
import java.util.Map;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RVAdapter extends
        RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private Context mContext;
    boolean flag, flag1;
    private DatabaseReference mDatabase;
    private DatabaseReference mFavVenueReference;
    List<Venue> mThreeHour;
    String mUserId;
    String key;
    int counter = 2;
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
    public RVAdapter(Context context, String userId, List<Venue> ThreeHour) {

        mThreeHour = ThreeHour;
        mContext = context;
        mUserId = userId;
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
        public TextView categoryTextView;
        public ImageView iconImageView;
        public ImageView addImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.textViewVenueNameRV);
            categoryTextView = (TextView) itemView.findViewById(R.id.textViewVenueCategoryRV);
            iconImageView = (ImageView) itemView.findViewById(R.id.imageViewVenueRV);
            addImageView = (ImageView) itemView.findViewById(R.id.imageViewAddRV);
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
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_item_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Venue current = mThreeHour.get(position);

        // Set item views based on your views and data model
        TextView nametextView = viewHolder.nameTextView;
        nametextView.setText(current.getName());
        TextView categoryTextView = viewHolder.categoryTextView;


        // double temperature = Double.parseDouble(current.getTemp());
        // DecimalFormat numberFormat = new DecimalFormat("#.00");
        //String selected = sharedPrefs.getString("prefTempUnit", "NULL");
        //Log.d("demo", "selected is "+selected);
        //double curtemp = 0 ;
        // String displayedTemp = null;
        //Log.d("demo", current.getIcon_url());
        categoryTextView.setText(current.getCategory());
        ImageView iconImageView = viewHolder.iconImageView;
        Picasso.with(mContext).load(current.getIcon_url()).into(iconImageView);
        final ImageView addimg = viewHolder.addImageView;
        final String vid = current.getVenue_id();
        Log.d("venues", vid);
        mFavVenueReference = FirebaseDatabase.getInstance().getReference().child("FavVenue").child(mUserId);
         mDatabase = FirebaseDatabase.getInstance().getReference();
        //key = mDatabase.child("FavVenue").child(mUserId).push().getKey();


        flag = false;
        mFavVenueReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    child.getValue();
                    Log.d("demotostring", child.toString());
                    Log.d("demovalue", child.getValue().toString());
                    //Log.d("demovalue",child.getKey().toString());
                    //Log.d("demoname", vkey);
                    if (child.getValue().toString().contains(vid)) {
                        Log.d("demoifvalue", child.getValue().toString());
                        flag = true;
                        break;
                        //addimg.setImageResource(R.drawable.favorite);
                        //key = child.getKey().toString();
                        //counter++;
                    }
                    //if(child.getValue().toString().contains(key)){
                    //addfavcity.setImageResource(R.drawable.favorite);
                    //counter++;
                    //  Log.d("demokey", "key matches");
                    //}
                }
                Log.d("flag", "is :" + flag);
                if (flag) {
                    Log.d("intrue", "here");
                    flag = false;
                    addimg.setImageResource(R.drawable.favorite);
                } else {
                    addimg.setImageResource(R.drawable.plus);
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final String mapid = current.getVenue_id();
        final String mapname = current.getName();
        final String mapcat = current.getCategory();
        final String mapaddress = current.getAddress();
        final String mapicon = current.getIcon_url();
        final int mapcheckin = current.getCheckin_count();
        final int mapusercount = current.getUsers_count();
        final int maphere = current.getHere_now();
        final String mapkey = current.getKey();
        flag1 = false;
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFavVenueReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if (child.getValue().toString().contains(vid)) {
                                flag1 = true;
                                key = child.getKey().toString();
                                break;
                            }
                        }
                        if (!flag1) {
                            Log.d("intrue", "here");
                            key = mDatabase.child("FavVenue").child(mUserId).push().getKey();
                            Venue e = new Venue(mapid, mapname, mapicon, mapcat, mapaddress, mapcheckin, mapusercount, maphere, key);
                            Map<String, Object> postValues = e.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/FavVenue/" + mUserId + "/" + key, postValues);
                            e.setKey(key);
                            Log.d("mystery", "here");
                            String elsekey = key;
                            mDatabase.updateChildren(childUpdates);
                            addimg.setImageResource(R.drawable.favorite);

                        } else {

                            flag1 = false;
                            DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("FavVenue").child(mUserId).child(key);
                            db_node.setValue(null);
                            Log.d("demokey1", "key in else " + key);
                            addimg.setImageResource(R.drawable.not_favorite);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mThreeHour.size();
    }
}