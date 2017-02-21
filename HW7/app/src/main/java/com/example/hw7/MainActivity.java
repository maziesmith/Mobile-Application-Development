package com.example.hw7;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String CITY_KEY = "City";
    public final static String STATE_KEY = "State";
    public ArrayList<Favorite> Favorites = null;
    ListView listViewFav = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);//Menu Resource, Menu
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        listViewFav = (ListView) findViewById(R.id.listViewFav);
        final EditText cityTV = (EditText) findViewById(R.id.editTextCity);
        final EditText stateTV = (EditText) findViewById(R.id.editTextState);
        final SharedPreference sp = new SharedPreference();
        Favorites = sp.loadFavorites(this);
        Log.d("HI","I M HERE ");
        if (Favorites == null) {
            RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);
            TextView mTextView = new TextView(this);
            mTextView.setText(R.string.no_fav);
            mTextView.setId(Integer.parseInt("1"));
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            rel.addView(mTextView, params);
        } else
        {
            for(Favorite f : Favorites)
            {
                Log.d("main on load", f.toString());
            }
            final FavoriteAdapter adapter = new FavoriteAdapter(this, R.layout.row_favorite_item, Favorites);
            listViewFav.setAdapter(adapter);
           TextView textView = new TextView(MainActivity.this);
            textView.setText(R.string.fav);
            listViewFav.addHeaderView(textView);
            adapter.setNotifyOnChange(true);
            listViewFav.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    Log.v("long clicked", "pos: " + pos);
                    Favorite fav = Favorites.get(pos-1);
                    Log.d("main", fav.toString());
                    adapter.remove(fav);
                    sp.removeFavorite(MainActivity.this, fav, pos-1);
                    Favorites = sp.loadFavorites(MainActivity.this);
                    Toast.makeText(MainActivity.this, "City Deleted", Toast.LENGTH_LONG).show();
                    if (Favorites.size() == 0 )
                    {   Log.d("inside null of listener","HI");
                        RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);
                        TextView mTextView = new TextView(MainActivity.this);
                        mTextView.setText(R.string.no_fav);
                        mTextView.setId(Integer.parseInt("1"));
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        //params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                        rel.addView(mTextView, params);

                    }
                    //FavoriteAdapter adapter = new FavoriteAdapter(MainActivity.this, R.layout.row_favorite_item, Favorites);
                    //Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    //MainActivity.this.startActivity(intent);
                    return true;
                }
            });

        }

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityWeather.class);
                Log.d("city", cityTV.getText().toString());
                Log.d("state", stateTV.getText().toString());
                intent.putExtra(CITY_KEY, cityTV.getText().toString());
                intent.putExtra(STATE_KEY, stateTV.getText().toString());
                startActivity(intent);

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hw7/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.hw7/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
