package com.example.inclass_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowExpense extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final String EXTRA_KEY_ID = "KEY_ID";
    private String user_id;
    private String key_id;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(getIntent().getExtras()!= null)
        {
            user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            key_id = getIntent().getExtras().getString(EXTRA_KEY_ID);
            DatabaseReference mRef = mDatabase.child("expenses").child(user_id).child(key_id);
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Expense e = dataSnapshot.getValue(Expense.class);
                    TextView tvName = (TextView)findViewById(R.id.textViewName);
                    TextView tvCategory = (TextView) findViewById(R.id.textViewCategory);
                    TextView tvAmt = (TextView) findViewById(R.id.textViewAmount);
                    TextView tvDate = (TextView) findViewById(R.id.textViewDate);
                    tvName.setText(e.getName());
                    tvCategory.setText(e.getCategory());
                    double amt = e.getAmount();
                    NumberFormat formatter = NumberFormat.getCurrencyInstance();
                    tvAmt.setText(formatter.format(amt));
                    String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
                    tvDate.setText(date);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent i = new Intent(ShowExpense.this, ListActivity.class);
                    i.putExtra(EXTRA_USER_ID, user_id);
                    startActivity(i);
                }
            });

        }

    }
}
