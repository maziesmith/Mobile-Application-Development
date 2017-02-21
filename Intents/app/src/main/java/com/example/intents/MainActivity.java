package com.example.intents;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final static String NAME_KEY = "NAME";
    final static String USER_KEY = "USER";
    final static String Parcel_key = "Parcel";
    final static CharSequence[] items = {"red","blue","green"};
    final static String Value_key = "Value";
    public static final int REQ_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------------------------------------Explit intent----------------------------
        findViewById(R.id.GoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(NAME_KEY,"Hi Sid");
                User user = new User("Alive",22.2);





                Person person = new Person("Alice","Gol ganj",32.3);
                intent.putExtra(Parcel_key,person);
                intent.putExtra(USER_KEY,user);
                startActivity(intent);
            }
        });
        //------------------------------------------------------------------------------------
        //---------------------------------Implicit intent(check manifest file)------------------
        findViewById(R.id.GoButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("com.example.intents.intent.action.View");
                intent1.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent1);
            }
        });
        //-------------------------------alert-------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick color")
                //-------------------------multiple choice in alert------------
        .setMultiChoiceItems(items,null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.d("demo",""+items[which]);
            }
        })
                /*-----------------single select by radio buttons----------------
        .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo",""+items[which]);
            }
        })
        ------------------------------------------------------------------*/
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo","ok pressed");
            }
        }).setCancelable(false);

                /*-----------------------select 1 item in form of buttons
        .setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo","selected"+items[which]);
            }
        });
        */
        /* ------------------------ok and cancel buttons for alert---------------
        .setMessage("You sure?")
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo","ok button");
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("demo", "cancel pressed");
            }
        });------------------------------------------------------------------*/
        final AlertDialog alert = builder.create();
        findViewById(R.id.AlertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.show();
            }
        });
        /*-------------------progress bar---------------------
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading..");
        progress.setCancelable(false);
        findViewById(R.id.AlertButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
            }
        });
        -----------------------------------------------------------*/
        findViewById(R.id.SecActForRes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent3, REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQ_CODE){
            if(resultCode==RESULT_OK){
                String value = data.getExtras().getString(Value_key);
                Log.d("demo","Value received is"+value);
            }else if(requestCode==RESULT_CANCELED){
                Log.d("demo","no result");
            }
        }
    }
}
