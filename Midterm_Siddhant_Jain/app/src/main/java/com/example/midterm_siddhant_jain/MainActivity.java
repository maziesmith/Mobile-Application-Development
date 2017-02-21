package com.example.midterm_siddhant_jain;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetAppAsync.IApp {

    private Switch aSwitch;
    ListView listview;
    ArrayList<App> applist = new ArrayList<App>();
    ArrayList<App> favorites = new ArrayList<App>();
    ProgressDialog progress;
    AppAdapter adapter;
    DatabaseManager dm ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseManager(this);
        progress =  new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.show();
        Collections.sort(applist);
        String url = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
        new GetAppAsync(this).execute(url);



    }

    @Override
    public void setAppList(ArrayList<App> result){
        aSwitch = (Switch) findViewById(R.id.switch1);
        applist = result;

        if(aSwitch.isChecked()){
            Collections.sort(applist);
        }

        List<App> ll = dm.getAllFavorite();
        Log.d("listapp",ll.toString());

        for(int i=0; i<applist.size();i++){
            App savedFilter = dm.getFavorite(applist.get(i).getName());

            if(savedFilter!=null){
                Log.d("if","inside if of loop"+ applist.get(i).getName());

                applist.remove(i);
            }
        }

        adapter= new AppAdapter(this, R.layout.row_item_list,applist);
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        progress.cancel();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Collections.sort(applist);
                    buttonView.setText("Ascending");
                    adapter.notifyDataSetChanged();
                }else{
                    Collections.sort(applist, Collections.reverseOrder());
                    buttonView.setText("Descending");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App a1 = applist.get(position);
                dm.saveFavorite(a1);
                applist.remove(position);
                adapter.notifyDataSetChanged();
            }

        });
        favorites = (ArrayList<App>) dm.getAllFavorite();
        if(favorites.size() != 0) {
            Log.d("main", "inside if");
            RecyclerView rvFavorite = (RecyclerView) findViewById(R.id.recyclerView1);
            final RecycleAdapter adapter = new RecycleAdapter(this, favorites);
            // Attach the adapter to the recyclerview to populate items
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            // Attach layout manager to the RecyclerView
            rvFavorite.setLayoutManager(layoutManager);
            rvFavorite.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
