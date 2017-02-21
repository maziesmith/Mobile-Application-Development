/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
//import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_SETTINGS = 1;
    public final static String CITY_KEY = "City";
    public final static String COUNTRY_KEY = "Country";
    ArrayList<Favorite> favorites = new ArrayList<Favorite>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText cityTV = (EditText) findViewById(R.id.editTextCity);
        final EditText countryTV = (EditText) findViewById(R.id.editTextCountry);
        final DatabaseManager dm = new DatabaseManager(this);
        favorites = (ArrayList<Favorite>) dm.getAllFavorite();
        Collections.sort(favorites, new Comparator<Favorite>() {
            //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(Favorite f1, Favorite f2) {
                if(f1.getFavorite() == f2.getFavorite())
                    return 0;
                return f1.getFavorite() < f2.getFavorite() ? 1 : -1;
            }
        });
        Log.d("true", favorites.toString());
        Log.d("main","size of fav"+favorites.size());

        if(favorites.size() != 0) {
            Log.d("main","inside if");
            RecyclerView rvFavorite = (RecyclerView) findViewById(R.id.recyclerViewFav);
            final FavoriteAdapter adapter = new FavoriteAdapter(this, favorites);
            // Attach the adapter to the recyclerview to populate items
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            // Attach layout manager to the RecyclerView
            rvFavorite.setLayoutManager(layoutManager);
            rvFavorite.setAdapter(adapter);
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
            rvFavorite.addItemDecoration(itemDecoration);
            adapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener(){


                @Override
                public void onItemClick(View itemView, int position) {
                    Favorite favclicked = favorites.get(position);
                    favclicked.setFavorite(1);
                    dm.updateFavorite(favclicked);
                    favorites.remove(position);
                    favorites.add(0,favclicked);
                    adapter.notifyDataSetChanged();


                }
            });
            adapter.setOnLongClickListener(new FavoriteAdapter.OnLongClickListener(){

                @Override
                public void onItemLongClick(View itemView, int position) {
                    Favorite favclicked = favorites.get(position);
                    dm.deleteFavorite(favclicked);
                    favorites.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });

        }
        else
        {   Log.d("main","in empty database");
            TextView tv = new TextView(this);
            tv.setText(R.string.nofavcity);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            tv.setGravity(Gravity.CENTER);
            RelativeLayout rel = (RelativeLayout) findViewById(R.id.activity_main);
            rel.addView(tv,params);
        }

        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityWeather.class);
                Log.d("city", cityTV.getText().toString());
                Log.d("state",countryTV.getText().toString());
                intent.putExtra(CITY_KEY, cityTV.getText().toString());
                intent.putExtra(COUNTRY_KEY, countryTV.getText().toString());
                startActivity(intent);
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings: {

                Intent intent = new Intent(this, UserSettingsActivity.class);
                intent.putExtra("call","Main");
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
