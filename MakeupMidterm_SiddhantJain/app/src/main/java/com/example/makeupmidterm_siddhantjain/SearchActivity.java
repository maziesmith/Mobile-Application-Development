package com.example.makeupmidterm_siddhantjain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {
    public static final String CITY_KEY = "CITY";
    Button buttonGo, buttonCity, buttonVenue, buttonLogout;
    private String user_id;
   //
    private static final String EXTRA_KEY_ID = "KEY_ID";
   // private ExpenseAdapter mAdapter;
    private RecyclerView mExpenseRecycler;
    private DatabaseReference mExpenseReference;
    private static final String EXTRA_USER_ID = "USER_ID";

    EditText city;
    String cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getStringExtra(EXTRA_USER_ID);
            mExpenseReference = FirebaseDatabase.getInstance().getReference()
                    .child("fav city").child(user_id);
            mExpenseReference = FirebaseDatabase.getInstance().getReference()
                    .child("fav place").child(user_id);

            buttonGo = (Button) findViewById(R.id.buttonGo_Search);

            city = (EditText) findViewById(R.id.editTextSearch_Search);
           // cityname = city.getText().toString();

            buttonGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SearchActivity.this, ResultActivity.class);
                    i.putExtra(EXTRA_USER_ID, user_id);
                    cityname = city.getText().toString();
                  //  Log.d("demo", cityname);
                    i.putExtra(CITY_KEY, cityname);
                    i.putExtra(EXTRA_USER_ID,user_id);
                    startActivity(i);
                }
            });
            buttonCity = (Button) findViewById(R.id.buttonFavCities_Search);

            buttonCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SearchActivity.this, FavoriteCitiesActivity.class);
                    i.putExtra(EXTRA_USER_ID, user_id);

                    startActivity(i);
                }
            });

        }
    }
}
