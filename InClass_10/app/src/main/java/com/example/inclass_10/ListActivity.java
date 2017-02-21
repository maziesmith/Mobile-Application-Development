package com.example.inclass_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final String EXTRA_KEY_ID = "KEY_ID";
    private ExpenseAdapter mAdapter;
    private RecyclerView mExpenseRecycler;
    private DatabaseReference mExpenseReference;
    private String user_id;
    private List<Expense> ExpenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getStringExtra(EXTRA_USER_ID);
            mExpenseReference = FirebaseDatabase.getInstance().getReference()
                    .child("expenses").child(user_id);

            mExpenseRecycler = (RecyclerView) findViewById(R.id.expense_list_recycler);

            mExpenseRecycler.setLayoutManager(new LinearLayoutManager(this));

            mAdapter = new ExpenseAdapter(this, mExpenseReference);
            mExpenseRecycler.setAdapter(mAdapter);

            Log.d("demo"," "+ mAdapter.mExpenses.toString());
            Log.d("demo"," .child(userid)"+ mExpenseReference.getKey().toString());
            Log.d("demo"," mexpenseref.tostring "+ mExpenseReference);
            mExpenseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        TextView tv = (TextView) findViewById(R.id.textView11);
                        tv.setText("");
                    }else{
                        TextView tv = (TextView) findViewById(R.id.textView11);
                        tv.setText(" There is no expense to show. Please add your expenses from the menu");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            findViewById(R.id.imageButtonAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("listadd", "here");
                    Intent i = new Intent(ListActivity.this, AddActivity.class);
                    i.putExtra(EXTRA_USER_ID, user_id);
                    startActivity(i);
                }
            });


            mAdapter.setOnItemClickListener(new ExpenseAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(View itemView, int position) {
                    Intent i = new Intent(ListActivity.this, ShowExpense.class);
                    i.putExtra(EXTRA_USER_ID, user_id);
                    String selectedkey = mAdapter.mExpenses.get(position).getKey();
                    i.putExtra(EXTRA_KEY_ID, selectedkey);
                    startActivity(i);
                }
            });

            mAdapter.setOnLongClickListener(new ExpenseAdapter.OnLongItemClickListener() {
                @Override
                public void onItemLongClick(View itemView, int position) {
                    String selectedKey = mAdapter.mExpenses.get(position).getKey();
                    Log.d("list", "Selected Key: " + selectedKey);
                    Log.d("list", "User id: " + user_id);
                    DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("expenses").child(user_id).child(selectedKey);
                    db_node.setValue(null);
                    Toast.makeText(ListActivity.this, "Expense deleted", Toast.LENGTH_LONG).show();
                    mExpenseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                TextView tv = (TextView) findViewById(R.id.textView11);

                                tv.setText("");
                            }else{
                                TextView tv = (TextView) findViewById(R.id.textView11);
                                tv.setText(" There is no expense to show. Please add your expenses from the menu");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            });

        }

    }
}
