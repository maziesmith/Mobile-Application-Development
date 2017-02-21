package com.example.midterm_demo;

import android.app.ProgressDialog;
//import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
//import android.support.v7.widget.LinearSnapHelper;
//import android.support.v7.widget.SnapHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements  GetDataFromJsonAsync.IApp {
    ArrayList<ITuneApp> itunesapp = new ArrayList<ITuneApp>();
    ArrayList<ITuneApp> filteredapps = new ArrayList<ITuneApp>();
    ProgressDialog progress;
DatabaseManager dm = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // progress =  new ProgressDialog(this);
       // progress.setTitle(R.string.loading);
       // progress.show();
        dm = new DatabaseManager(this);
        new GetDataFromJsonAsync(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        findViewById(R.id.imageButtonRefresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetDataFromJsonAsync(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

            }
        });
    }

boolean sortSel = true;
    @Override
    public void setITunesApps(ArrayList<ITuneApp> result) {

    //    progress.cancel();


        itunesapp = result;
        Collections.sort(itunesapp);
        for(int i =  0 ; i < itunesapp.size(); i ++)
        {
            if(itunesapp.get(i).getFiltered().equals("1"))
            {
                itunesapp.remove(i);
            }
        }
        RecyclerView rvUpper = (RecyclerView) findViewById(R.id.rvUpper);
        final ITuneAdapter adapterU = new ITuneAdapter(this, itunesapp);
        // Attach the adapter to the recyclerview to populate items
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // Attach layout manager to the RecyclerView
        rvUpper.setLayoutManager(layoutManager);
        rvUpper.setAdapter(adapterU);
        // rvDaily.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvUpper.addItemDecoration(itemDecoration);



        filteredapps = (ArrayList<ITuneApp>) dm.getAllFilter();
        final FilterAdapter adapterL = new FilterAdapter(this, filteredapps);
        if(filteredapps != null) {
            RecyclerView rvLower = (RecyclerView) findViewById(R.id.rvLower);

            // Attach the adapter to the recyclerview to populate items
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            // Attach layout manager to the RecyclerView
            rvLower.setLayoutManager(layoutManager2);
            rvLower.setAdapter(adapterL);
            // rvDaily.setHasFixedSize(true);
            RecyclerView.ItemDecoration itemDecoration2 = new
                    DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
            rvLower.addItemDecoration(itemDecoration2);
            adapterL.setOnItemClickListener(new FilterAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ITuneApp deleteSel = filteredapps.get(position);
                    dm.deleteFilter(deleteSel);
                    deleteSel.setFiltered("0");
                    filteredapps.remove(position);
                    adapterL.notifyDataSetChanged();
                    itunesapp.add(deleteSel);
                    adapterU.notifyDataSetChanged();
                }
            });



        }
        else
        {
            Log.d("main","in empty database");
            TextView tv = new TextView(this);
            tv.setText(R.string.nofilter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            tv.setGravity(Gravity.CENTER);
            LinearLayout rel = (LinearLayout) findViewById(R.id.linearLower);
            rel.addView(tv,params);
        }

        adapterU.setOnItemLongClickListener(new ITuneAdapter.OnItemLongClickListener() {

            @Override
            public void onItemLongClick(View itemView, int position) {
                ITuneApp selected = itunesapp.get(position);
                selected.setFiltered("1");
                dm.saveFilter(selected);
                itunesapp.remove(position);
                adapterU.notifyDataSetChanged();
                filteredapps.add(selected);
                adapterL.notifyDataSetChanged();



            }
        });
        final Switch genderSwitch = (Switch) findViewById(R.id.switchSort);

        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Switch State", "" + isChecked);
                sortSel = isChecked;


                if (sortSel) {
                    Collections.sort(itunesapp, new Comparator<ITuneApp>() {
                        @Override
                        public int compare(ITuneApp lhs, ITuneApp rhs) {
                            if (Double.parseDouble(lhs.getPrice()) > Double.parseDouble(rhs.getPrice())) {
                                return -1;
                            } else if (Double.parseDouble(lhs.getPrice()) > Double.parseDouble(rhs.getPrice())) {
                                return 1;
                            } else return 0;

                        }
                    });

                    adapterU.notifyDataSetChanged();
                    genderSwitch.setText("Descending");
                } else {
                    genderSwitch.setText("Ascending");
                    Collections.sort(itunesapp);
                    adapterU.notifyDataSetChanged();
                }
            }
        });
    }
}
