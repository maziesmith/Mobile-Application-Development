package com.example.makeupmidterm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 20-11-2016.
 */



        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by sid on 17-10-2016.
 */

public class FragmentAdapter extends ArrayAdapter<Test> {

    List<Test> mData;
    Context mContext;
    int mResource;
    List<String> mExpenseIds = new ArrayList<>();
    List<Test> mExpenses = new ArrayList<>();
    private DatabaseReference mRef;

    public FragmentAdapter(Context context, int resource, List<Test> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("adapter", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, add it to the displayed list
                Test expense = dataSnapshot.getValue(Test.class);
                Log.d("exp", expense.toString());
                Log.d("i2", "child");
                // [START_EXCLUDE]
                // Update RecyclerView
                mExpenseIds.add(dataSnapshot.getKey());
                mExpenses.add(expense);
               // notifyItemInserted(mExpenses.size() - 1);
                //setNotifyOnChange();
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
                int expIndex = mExpenseIds.indexOf(expenseKey);
                if (expIndex > -1) {
                    // Remove data from the list
                    mExpenseIds.remove(expIndex);
                    mExpenses.remove(expIndex);

                    // Update the RecyclerView
                   // notifyItemRemoved(expIndex);
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }
        Test expense = mData.get(position);
        TextView expenseNameTextView = (TextView) convertView.findViewById(R.id.textView4);
        expenseNameTextView.setText(expense.name);
        TextView expenseAmountTextView = (TextView) convertView.findViewById(R.id.textView5);
        String amountString = ""+expense.price;
        expenseAmountTextView.setText(amountString);

        return convertView;
    }
}
