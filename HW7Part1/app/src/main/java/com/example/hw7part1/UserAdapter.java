package com.example.hw7part1;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class UserAdapter extends
        RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private  String muser_id;
    private RecyclerView mMessageRecycler1;
    private DatabaseReference mMessageReference1;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mRef;
    List<String>  mUsersIds = new ArrayList<>();
    List<User> mUsers = new ArrayList<>();
    // Define listener member variable
    private static OnItemClickListener listener;
    private static OnLongItemClickListener longlistener;
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
    public UserAdapter(Context context, DatabaseReference ref, String user_id) {

        mRef = ref;
        mContext = context;
        muser_id = user_id;
        ChildEventListener childEventListener = new ChildEventListener()     {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                User user= dataSnapshot.getValue(User.class);
                Log.d("exp",user.toString());
                Log.d("i2","child");
                // [START_EXCLUDE]
                // Update RecyclerView
                if(!user.getUser_id().equals(muser_id)) {
                    mUsersIds.add(dataSnapshot.getKey());
                    mUsers.add(user);
                    notifyItemInserted(mUsersIds.size() - 1);
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("adapter", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String expenseKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int expIndex = mUsersIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mUsersIds.remove(expIndex);
                    mUsers.remove(expIndex);

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

        public TextView nameTV;
        public ImageView profilePicimageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.nameTextView);
            profilePicimageView = (ImageView) itemView.findViewById(R.id.imageViewProfilePic);

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
                        int   position = getAdapterPosition();
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
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_user, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder viewHolder, final int position) {
        // Get the data model based on position

        User current = mUsers.get(position);
Log.d("adapter", muser_id);
Log.d("adapter", current.getUser_id());
Log.d("adapter", current.getUserfirstname());

        // Set item views based on your views and data model
 if(!current.getUser_id().equals(muser_id)) {
    final ImageView profileView = viewHolder.profilePicimageView;
    TextView name = viewHolder.nameTV;


    name.setText(current.getUserfirstname() + " " + current.getUserlastname());


    String image_id = current.getImage_id();
    String path = current.getImage_url();
     StorageReference storageReference = storage.getReferenceFromUrl("gs://fir-test-471fa.appspot.com");
     StorageReference eachImage = storageReference.child(path);
    eachImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
            Picasso.with(mContext).load(uri).into(profileView);
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d("adapater", e.toString());
        }
    });

   }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    // Returns the total count of items in the list

}