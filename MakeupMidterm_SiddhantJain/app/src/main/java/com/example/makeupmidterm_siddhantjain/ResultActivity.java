package com.example.makeupmidterm_siddhantjain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity implements GetVenueAsync.IVenue {
    ProgressDialog progress;
    private DatabaseReference mDatabase;
    //String city = null;
    public static final String VENUE_ID = "VENUE";
    public static final String TR_ID = "TR";
    ArrayList<Venue> transfer = new ArrayList<Venue>();
    Venue transfer1 = new Venue();
    Venue selectedday = null, initialday = null;
    private DatabaseReference mFavCityReference;
    private static final String EXTRA_USER_ID = "USER_ID";
    private static final String CITY_KEY = "CITY";
    private String user_id;
    String cityname;
    int counter = 2;
    String key;
    ImageView addfavcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (getIntent().getExtras() != null) {
            user_id = getIntent().getExtras().getString(EXTRA_USER_ID);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            mFavCityReference = FirebaseDatabase.getInstance().getReference().child("Favcity").child(user_id);
            progress = new ProgressDialog(this);
            progress.setTitle(R.string.loading_hourly_data);
            progress.show();
            final String city = (String) getIntent().getExtras().get(SearchActivity.CITY_KEY);
            TextView cn = (TextView) findViewById(R.id.textViewCity_Result);
            cn.setText(city);
            cityname = city;
            addfavcity = (ImageView) findViewById(R.id.imageViewAddCityResult);

            key = mDatabase.child("Favcity").child(user_id).push().getKey();
            Log.d("demogetkey",key);
            mFavCityReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren()){
                        child.getValue();
                      //  Log.d("demotostring",child.toString());
                      //  Log.d("demovalue",child.getValue().toString());
                       // Log.d("demovalue",child.getKey().toString());
                        if(child.getValue().toString().contains(city)){
                            addfavcity.setImageResource(R.drawable.favorite);
                            key = child.getKey().toString();
                            counter++;
                        }
                        if(child.getValue().toString().contains(key)){
                            //addfavcity.setImageResource(R.drawable.favorite);
                            //counter++;
                          //  Log.d("demokey", "key matches");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

                addfavcity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (counter % 2 == 0) {
                            City e = new City(cityname, key);
                            Map<String, Object> postValues = e.toMap();

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/Favcity/" + user_id + "/" + key, postValues);
                            e.setKey(key);
                            // childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                            Log.d("mystery", "here");
                            mDatabase.updateChildren(childUpdates);
                            addfavcity.setImageResource(R.drawable.favorite);
                            counter++;
                        } else {
                            DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().getRoot().child("Favcity").child(user_id).child(key);
                            db_node.setValue(null);
                            counter++;
                            addfavcity.setImageResource(R.drawable.not_favorite);
                        }
                    }
                });


           // Log.d("demo", "aa"+ city);
            String url = "https://api.foursquare.com/v2/venues/search?client_id=FXNFM0DEJAH5DHPMU0OD1YMCLX3C2V5BOD1VPCAXFLRU2ZFD&client_secret=BWIDB5PZ2QTWL0CDOVEBSQJNOM1RO4EROP3IZ0D5NGUTAII1&v=20161121&near="+city;

            new GetVenueAsync(this).execute(url);
        }
    }


    @Override
    public void setVenueList(ArrayList<Venue> result) {
        progress.cancel();
        ArrayList<Venue> all_days = new ArrayList<Venue>();
        all_days = result;
        transfer = all_days;
       // Log.d("demo", all_days.get(0).getIcon_url());
        RecyclerView rvDaily = (RecyclerView) findViewById(R.id.rv_result);
        final RVAdapter adapter = new RVAdapter(this, user_id, all_days);
        // Attach the adapter to the recyclerview to populate items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // Attach layout manager to the RecyclerView
        rvDaily.setLayoutManager(new LinearLayoutManager(this));
        rvDaily.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(ResultActivity.this, DetailsActivity.class);
               // transfer1 = transfer.get(position);
                 i.putExtra(VENUE_ID, transfer);
                i.putExtra(TR_ID, position);
                //String selectedkey = adapter.mThreeHour.get(position).getKey();
               // i.putExtra(EXTRA_KEY_ID, selectedkey);
                startActivity(i);
            }
        });
    }
}
/**
 * fav city>
 *          user id>
 *                   city name
 *
 *
 *
 *
 *
 *
 *
 * */