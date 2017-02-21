package com.example.hw7part1;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class MessageAdapter extends
        RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context mContext;
    private  String muser_id;
    private RecyclerView mMessageRecycler1;
    private DatabaseReference mMessageReference1;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private DatabaseReference mRef;
    List<String>  mMessagesIds = new ArrayList<>();
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
    public MessageAdapter(Context context, DatabaseReference ref) {

        mRef = ref;
        mContext = context;

        ChildEventListener childEventListener = new ChildEventListener()     {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Message message= dataSnapshot.getValue(Message.class);
                Log.d("exp",message.toString());
                Log.d("i2","child");
                // [START_EXCLUDE]
                // Update RecyclerView
                mMessagesIds.add(dataSnapshot.getKey());
                mMessages.add(message);
                notifyItemInserted(mMessagesIds.size() - 1);
                // [END_EXCLUDE]
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
                int expIndex = mMessagesIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mMessagesIds.remove(expIndex);
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

        public TextView senderNameTV, messageTV, timeTV;
        public ImageView attachedImageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            senderNameTV = (TextView) itemView.findViewById(R.id.textViewSenderNameInbox);
            messageTV = (TextView) itemView.findViewById(R.id.textViewMessageTextInbox);
            timeTV = (TextView) itemView.findViewById(R.id.textViewDateInbox);
            attachedImageView = (ImageView) itemView.findViewById(R.id.imageViewInbox);

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
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_message_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Message current = mMessages.get(position);

        ImageView attachedImage = holder.attachedImageView;
        final TextView senderName = holder.senderNameTV;
        TextView message = holder.messageTV;
        TextView time = holder.timeTV;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date createdDate = null;
        try {
            Log.d("date", "date create"+current.getDateSent());
            createdDate = sdf.parse(current.getDateSent());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime p = new PrettyTime();
        Log.d("demo",p.format(createdDate));
        time.setText(p.format(createdDate));
        message.setText(current.getText());
        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().getRoot().child("users");
        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("kkk", "in here ");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(postSnapshot.getValue(User.class).getUser_id().equals(current.getFromuser_id()))
                    {
                       User user = postSnapshot.getValue(User.class);
                        String fname = user.getUserfirstname();
                        String lname = user.getUserlastname();
                        Log.d("insideif",fname);
                        Log.d("insideif",lname);
                        senderName.setText(fname+" "+lname);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("chat11", "failed");
            }
        });
         if(current.getMimageid().equals("attachimage"))
         { Log.d("naaa", current.getMimageid());
             attachedImage.setImageResource(R.drawable.send);
             attachedImage.setVisibility(View.INVISIBLE);
         }
        else
         {   Log.d("sonal", current.getMimageid());
             attachedImage.setImageResource(R.drawable.send);
             attachedImage.setVisibility(View.VISIBLE);
         }
        if(current.getRead_flag().equals("N"))
        {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

    }

    // Involves populating data into the item through holder


    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    // Returns the total count of items in the list

}