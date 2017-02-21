package com.example.madhu.in_class_05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    final static CharSequence[] klist={"UNCC","ANDROID","WINTER","AURORA","WONDERS"};
    String[] urlList;
    String searchKeyword = "";
    ImageView nextButton;
    ImageView previousButton;
    int imageUrlIndex =1;
    ImageView imgV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new GetImage(this).execute(urlList[1]);


        nextButton = (ImageView)findViewById(R.id.next_imageview);
        previousButton = (ImageView)findViewById(R.id.prev_imageView);
        nextButton.setEnabled(false);
        previousButton.setEnabled(false);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageUrlIndex++;
                if(imageUrlIndex>urlList.length-1)
                {
                    imageUrlIndex = 1;
                }
                new GetImage(MainActivity.this).execute(urlList[imageUrlIndex]);


            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageUrlIndex--;
                if(imageUrlIndex <1)
                {
                    imageUrlIndex = urlList.length-1;

                }
                new GetImage(MainActivity.this).execute(urlList[imageUrlIndex]);


            }
        });

        final TextView searchtv= (TextView)findViewById(R.id.searchkeyword_textview);
        findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose a keyword").setItems(klist, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            searchtv.setText(klist[which].toString());
                                searchKeyword = klist[which].toString();
                                Log.d("demo1","searchKeyword= "+searchKeyword);

                                isConnected();
                            }
                        });
//                ArrayAdapter<String> arrayAdapter =new ArrayAdapter<String>(MainActivity.this,android.R.layout.select_dialog_item);
//                arrayAdapter.add(
              AlertDialog alert= builder.create();
                alert.show();
            }

        });


    }
    private Boolean isConnected(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if(nf!=null && nf.isConnected()){
            new GetData().execute(searchKeyword);
        }
        else{
            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_LONG).show();
        }
        return  false;
    }
    public class GetData extends AsyncTask<String,Void,String[]>{
             BufferedReader bf = null;
            ProgressDialog pd;
        @Override
        protected String[] doInBackground(String... params) {
            try {
                URL url =new URL("http://dev.theappsdr.com/apis/photos/index.php?keyword="+params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)(url.openConnection());
                httpURLConnection.setRequestMethod("GET");
                bf=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line= "";

                while ((line=bf.readLine())!=null){
                    sb.append(line+"\n");
                }
                urlList=sb.toString().split(";");
                return urlList;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(bf!=null){
                    try {
                        bf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("Loading Dictionary...");
            pd.setMax(100);
            pd.setCancelable(false);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings.length == 1)
            {
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
                Toast.makeText(getApplicationContext(),"No Image Found",Toast.LENGTH_SHORT).show();
            }
            else if(strings.length ==2)
            {
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
                new GetImage(MainActivity.this).execute(urlList[1]);
            }
            else
            {
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
                new GetImage(MainActivity.this).execute(urlList[imageUrlIndex]);
            }
            pd.dismiss();

        }
    }


    public void setUpImage(Bitmap b) {
        ImageView imgV= (ImageView)findViewById(R.id.displayImageView);
        imgV.setImageBitmap(b);
    }
}

