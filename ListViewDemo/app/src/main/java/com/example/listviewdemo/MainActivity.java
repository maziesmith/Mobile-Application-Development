package com.example.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //String[] Colors = {"Red", "Blue", "Green", "White", "Black", "Orange", "Yellow"};

    ArrayList<Color> colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colors = new ArrayList<Color>();
        colors.add(new Color("RED","#000000"));
        colors.add(new Color("BLUE","#0000FF"));
        colors.add(new Color("GREEN","#654321"));
        colors.add(new Color("WHITE","#006600"));
        colors.add(new Color("BLACK","#FF6600"));
        colors.add(new Color("ORANGE","#FF0000"));
        colors.add(new Color("YELLOW","#BBBBBB"));

        ListView listview = (ListView) findViewById(R.id.listView);
        //ArrayAdapter<Color> adapter = new ArrayAdapter<Color>(this, android.R.layout.simple_list_item_1, colors);
        ColorAdapter adapter = new ColorAdapter(this, R.layout.row_item_layout, colors);
        listview.setAdapter(adapter);
        adapter.setNotifyOnChange(true);//a method to notify changes
        /* this is code for arrayadapter
        adapter.add(new Color("PINK"));
        adapter.remove(colors.get(2));
        adapter.insert(new Color("BROWN"),2);
*/
        //adapter.notifyDataSetChanged();---------------another approach

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo",position+" position and value "+colors.get(position).toString());
            }
        });

    }
}
