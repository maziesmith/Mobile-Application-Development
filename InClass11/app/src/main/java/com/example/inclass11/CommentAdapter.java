package com.example.inclass11;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
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
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class CommentAdapter extends
        RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context mContext;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mRef;
    List<String>  mMessageIds = new ArrayList<>();
    List<Message> mMessages = new ArrayList<>();
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
    public CommentAdapter(Context context, DatabaseReference ref) {

        mRef = ref;
        mContext = context;
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
        ChildEventListener childEventListener = new ChildEventListener()     {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Message message = dataSnapshot.getValue(Message.class);
                Log.d("exp",message.toString());
                Log.d("i2","child");
                // [START_EXCLUDE]
                // Update RecyclerView
                mMessageIds.add(dataSnapshot.getKey());
                mMessages.add(message);
                notifyItemInserted(mMessages.size() - 1);
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
                int expIndex = mMessageIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mMessageIds.remove(expIndex);
                    mMessages.remove(expIndex);

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
        public TextView date;
        public ImageView imageView;
        public TextView textTV;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTV = (TextView) itemView.findViewById(R.id.textViewName);
            date = (TextView) itemView.findViewById(R.id.textViewTime);
            textTV = (TextView) itemView.findViewById(R.id.textViewMessageText);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewPic);


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
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_comment_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final CommentAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Message current = mMessages.get(position);

        // Set item views based on your views and data model
        TextView message =  viewHolder.textTV;
        final ImageView imageMsg =  viewHolder.imageView;
        TextView name = viewHolder.nameTV;
        TextView time = viewHolder.date;

        name.setText(current.getFirstname()+" "+current.getLname());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date createdDate = null;
        try {
            createdDate = sdf.parse(current.getDateCreate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime p = new PrettyTime();
        Log.d("demo",p.format(createdDate));
        time.setText(p.format(createdDate));
        if(current.getImageid().equals("noimage"))
        {
            message.setVisibility(View.VISIBLE);
            message.setText(current.getText());
            imageMsg.setVisibility(View.INVISIBLE);
        }
        else
        {

            imageMsg.setVisibility(View.VISIBLE);
            message.setVisibility(View.INVISIBLE);
            String image_id = current.getImageid();
            String path = current.getImage_url();
            StorageReference storageReference = storage.getReferenceFromUrl("gs://firecast-app-c24ee.appspot.com");
            StorageReference eachImage = storageReference.child(path);
            eachImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(mContext).load(uri).into(imageMsg);
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
        return mMessages.size();
    }

    // Returns the total count of items in the list

}