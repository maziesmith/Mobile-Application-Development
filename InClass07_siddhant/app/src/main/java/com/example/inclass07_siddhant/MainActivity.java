package com.example.inclass07_siddhant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetData.DataRetreiver {

    ProgressDialog progress;
    ArrayList<Feed> feedItems =new ArrayList<Feed>();
    public String kw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("demo","main");
        progress =new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle("Loading News");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        new GetData(this).execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/xml");
        Button clearButton = (Button) findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = (EditText) findViewById(R.id.editTextSearch);

                searchText.setText("");
            }
       });


    }

    @Override
    public void setData(final ArrayList<Feed> podList) {



        feedItems = podList;
        Button goButton = (Button) findViewById(R.id.buttonGo);
        progress.dismiss();

        ListView listView = (ListView) findViewById(R.id.ListView);

        final ListAdapter adapter =new ListAdapter(this, R.layout.row_item_layout, feedItems);
        listView.setAdapter(adapter);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = (EditText) findViewById(R.id.editTextSearch);
                //Log.d("demo","edit text data"+searchText.getText().toString());

                kw = searchText.getText().toString();
                Log.d("demo","kw is "+ kw);
                Log.d("demo","feeditem conatain kw is "+ feedItems.get(0).getTitle().contains(kw));
                for(int i=0; i<=29;i++){
                    String hi = feedItems.get(i).getTitle();
                    boolean x = hi.contains(kw);
                    Log.d("demo","new is "+hi);
                    Log.d("demo","x is"+ x);

                    if(x){

                        feedItems.get(i).setA(true);
                        Feed temp = feedItems.get(i);
                        feedItems.remove(i);
                        feedItems.add(0,temp);
                        //Log.d("demo","feed is" +feedItems.get(i).toString());
                        //Log.d("demo1","title"+feedItems.get(i).getTitle());
                        Log.d("demo","x in if is "+ x);
                        Log.d("demo","set flag true");
                    }else{
                        podList.get(i).setA(false);
                        Log.d("demo","set flag false");
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
        adapter.setNotifyOnChange(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, ItemDetailsActivity.class);
                i.putExtra("PODFEED",feedItems);
                i.putExtra("ID", position);
                startActivity(i);
            }
        });
    }
}
