package com.example.makeupmidterm;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity implements TestFragment.OnFragmentInteractionListener, GetAppAsync.IApp{
    EditText ed, ed1;
    String a;
    Double b;
    private TestAdapter mAdapter;
    private RecyclerView mExpenseRecycler;
    private DatabaseReference mExpenseReference;
    private List<Test> ExpenseList = new ArrayList<>();
    private ArrayList<Test> test = new ArrayList<>();
    private Button gotofrag;
    ArrayList<App> personlist = new ArrayList<App>();
    App data = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetAppAsync(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        ed = (EditText) findViewById(R.id.editText);
        ed1 = (EditText) findViewById(R.id.editText2);
        Button button = (Button) findViewById(R.id.button2);
        App data;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                mExpenseReference = FirebaseDatabase.getInstance().getReference().child("test").child("test1");
                a = ed.getText().toString();
                // ref.child("test").child("test1").setValue(ed.getText().toString());
                String key = ref.child("test").push().getKey();
                String c = ed1.getText().toString();
                b = Double.parseDouble(c);
                Test t = new Test(a, b, key);
                Map<String, Object> postValues = t.toMap();
                Map<String, Object> childUpdates = new HashMap<String, Object>();
                childUpdates.put("/test/test1/" + key, postValues);
                t.setKey(key);
                ref.updateChildren(childUpdates);
                mExpenseRecycler = (RecyclerView) findViewById(R.id.rvtest);

                mExpenseRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                mAdapter = new TestAdapter(MainActivity.this, mExpenseReference);
                mExpenseRecycler.setAdapter(mAdapter);
                gotofrag = (Button) findViewById(R.id.button3);
                gotofrag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getFragmentManager().beginTransaction()
                                .add(R.id.activity_main, new TestFragment(), "first")
                                .commit();
                    }
                });
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public ArrayList<Test> getExpense() {
        return test;
    }


    @Override
    public void setAppList(ArrayList<App> result) {
        personlist = result;
        data = personlist.get(0);
        Log.d("demo", personlist.toString());
        TextView tv1  = (TextView) findViewById(R.id.textView6);
        TextView tv2  = (TextView) findViewById(R.id.textView7);
        TextView tv3  = (TextView) findViewById(R.id.textView8);
        tv1.setText(data.getName());
        tv2.setText(data.getPrice());
       // tv3.setText(data.getImage());
    }
}
