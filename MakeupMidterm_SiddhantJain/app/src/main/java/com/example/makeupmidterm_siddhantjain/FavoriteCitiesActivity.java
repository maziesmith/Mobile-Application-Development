package com.example.makeupmidterm_siddhantjain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavoriteCitiesActivity extends AppCompatActivity {

    private CityAdapter mAdapter;
    private RecyclerView mCityRecycler;
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final String EXTRA_KEY_ID = "KEY_ID";
    private DatabaseReference mCityReference;
    private String user_id;
    private List<City> CityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cities);
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getStringExtra(EXTRA_USER_ID);
            mCityReference = FirebaseDatabase.getInstance().getReference()
                    .child("Favcity").child(user_id);
            mCityRecycler = (RecyclerView) findViewById(R.id.rvcity);

            mCityRecycler.setLayoutManager(new LinearLayoutManager(this));

            mAdapter = new CityAdapter(this, user_id, mCityReference);
            mCityRecycler.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }
    }
}
