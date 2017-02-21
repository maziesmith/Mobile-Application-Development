package com.example.inclass5_siddhant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {
    final static CharSequence[] items = {"UNCC","ANDROID","WINTER","AURORA","WONDERS"};
    String getItem;
    ImageView nextButton;
    ImageView previousButton;
    Button goButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = (ImageView)findViewById(R.id.next_imageview);
        previousButton = (ImageView)findViewById(R.id.prev_imageView);
        nextButton.setEnabled(false);
        previousButton.setEnabled(false);
        TextView searchText = (TextView) findViewById(R.id.searchkeyword_textview);
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose a keyword")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getItem = items[which].toString();
                            }
                        });
            }
        });

        goButton = (Button) findViewById(R.id.go_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams("POST","http://dev.theappsdr.com/apis/photos/index.php?keyword=");
            }
        });

    }
    private class GetDataWithParams extends AsyncTask<RequestParams, Void, String> {

        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader br = null;

            try {
                HttpURLConnection con = params[0].setupConnection();
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = br.readLine())!= null){
                    sb.append(line +"/n");
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    if(br!=null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result!=null){
                Log.d("demo",result);

            }else{
                Log.d("demo","null result");
            }
        }
    }
}
