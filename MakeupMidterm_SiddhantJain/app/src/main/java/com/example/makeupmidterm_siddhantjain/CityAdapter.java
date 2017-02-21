package com.example.makeupmidterm_siddhantjain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 03-12-2016.
 */

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.provider.ContactsContract;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CityAdapter extends
        RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private Context mContext;
    private DatabaseReference mRef;
    private String mUserId;
    List<String> mCityIds = new ArrayList<>();
    List<City> mCities = new ArrayList<>();
    // Define listener member variable
    private static OnItemClickListener listener;
    private static OnLongItemClickListener longlistener;
    String key;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnLongClickListener(OnLongItemClickListener listener) {
        this.longlistener = listener;
    }

    // Pass in the contact array into the constructor
    public CityAdapter(Context context, String userId, DatabaseReference ref) {

        mRef = ref;
        mContext = context;
        mUserId = userId;
       /* mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                mExpenses.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Expense exp = postSnapshot.getValue(Expense.class);
                    mExpenses.add(exp);
                    Log.d("i1","listener");
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        });
*/
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, add it to the displayed list
                City city = dataSnapshot.getValue(City.class);
                Log.d("exp", city.toString());
                Log.d("i2", "child");
                // [START_EXCLUDE]
                // Update RecyclerView
                mCityIds.add(dataSnapshot.getKey());
                mCities.add(city);
                notifyItemInserted(mCities.size() - 1);
                // [END_EXCLUDE]
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
/*
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int commentIndex = mCommentIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Replace with the new data
                    mComments.set(commentIndex, newComment);

                    // Update the RecyclerView
                    notifyItemChanged(commentIndex);
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + commentKey);
                }
                // [END_EXCLUDE]
            }*/

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("adapter", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String expenseKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int expIndex = mCityIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mCityIds.remove(expIndex);
                    mCities.remove(expIndex);

                    // Update the RecyclerView
                    notifyItemRemoved(expIndex);
                } else {
                    Log.w("adapter", "onChildRemoved:unknown_child:" + expenseKey);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

           /* @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }*/

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("adapter", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(mContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRef.addChildEventListener(childEventListener);
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

        public TextView citynameTV;
        //public TextView expenseAmt;
        ImageView img;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            citynameTV = (TextView) itemView.findViewById(R.id.textViewFavCityRow);
            //expenseAmt = (TextView) itemView.findViewById(R.id.textViewAmount);
            img = (ImageView) itemView.findViewById(R.id.imageViewTrashCity);
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
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longlistener != null) {
                        int position = getAdapterPosition();
                        if (longlistener != null && position != RecyclerView.NO_POSITION) {
                            longlistener.onItemLongClick(itemView, position);
                        }
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_item_favcity, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        City current = mCities.get(position);
        final String cname = current.getCityname();
        // Set item views based on your views and data model
        TextView expName = viewHolder.citynameTV;
        expName.setText(current.getCityname());
        //TextView expAmt = viewHolder.expenseAmt;
        //expAmt.setText("$" + current.getAmount());
        ImageView trashbin = viewHolder.img;
        trashbin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("Favcity").child(mUserId);
                Userref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("kkk", "in here ");
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            child.getValue();
                            Log.d("demotostring", child.toString());
                            Log.d("demovalue", child.getValue().toString());
                            Log.d("demovalue", child.getKey().toString());
                            if (child.getValue().toString().contains(cname)) {
                                //addfavcity.setImageResource(R.drawable.favorite);
                                key = child.getKey().toString();
                                DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("Favcity").child(mUserId).child(key);
                                db_node.setValue(null);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("chat11", "failed");
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    // Returns the total count of items in the list

}