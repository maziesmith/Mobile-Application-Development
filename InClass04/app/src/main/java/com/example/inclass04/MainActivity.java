package com.example.inclass04;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    ProgressDialog diag;
    Executor threadpool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar passwordCount = (SeekBar) findViewById(R.id.seekBarCount);
        passwordCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView per = (TextView) findViewById(R.id.textViewCount);
                Log.d("countprog" , progress+"");
                per.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        final SeekBar passwordLength = (SeekBar) findViewById(R.id.seekBarLength);
        passwordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress + 8;
                TextView per = (TextView) findViewById(R.id.textViewLength);
                Log.d("lengthprog" , progress+"");
                per.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        diag = new ProgressDialog(this);
        diag.setMessage("Generating Passwords...");
        diag.setMax(100);
        diag.setCancelable(false);

        diag.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case CreatePasswords.STATUS_DONE:
                        diag.dismiss();
                        ArrayList<String> passwords = msg.getData().getStringArrayList("PASSWORDS");
                        final CharSequence[] allpasswords = new CharSequence[passwords.size()];
                        int i = 0 ;
                        for(String pass : passwords)
                        {
                            allpasswords[i] = pass;
                            i++;
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Passwords");
                        builder.setItems(allpasswords , new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                TextView showpass = (TextView) findViewById(R.id.textViewPassword);
                                showpass.setText(allpasswords[item]);

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                        break;
                    case CreatePasswords.STATUS_START:
                        ImageView img = (ImageView) findViewById(R.id.imageView);
                        ((ViewGroup)img.getParent()).removeView(img);
                        diag.setContentView(img);

                        diag.show();
                        break;
                    case CreatePasswords.STATUS_STEP:
                        diag.setProgress(msg.getData().getInt("PROGRESS"));
                        //diag.setProgress((Integer) msg.obj);
                        break;
                }



                return false;
            }
        });
        threadpool = Executors.newFixedThreadPool(2);
        findViewById(R.id.buttonThreads).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView countTextView = (TextView) findViewById(R.id.textViewCount);
                int count = Integer.parseInt(countTextView.getText().toString());
                TextView lengthTextView = (TextView) findViewById(R.id.textViewLength);
                int length = Integer.parseInt(lengthTextView.getText().toString());
                threadpool.execute(new CreatePasswords(count,length));
            }
        });

        findViewById(R.id.buttonAsync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView countTextView = (TextView) findViewById(R.id.textViewCount);
                int count = Integer.parseInt(countTextView.getText().toString());
                TextView lengthTextView = (TextView) findViewById(R.id.textViewLength);
                int length = Integer.parseInt(lengthTextView.getText().toString());
                new DoWork(count,length).execute();
            }
        });


    }


    class CreatePasswords implements Runnable {
        int count, length;
        static final int STATUS_START = 0X00;
        static final int STATUS_STEP = 0X01;
        static final int STATUS_DONE = 0X02;

        public CreatePasswords(int count, int length) {
            this.count = count;
            this.length = length;
        }

        @Override
        public void run() {
            ArrayList<String> passwords = new ArrayList<String>(count);
            Message msg = new Message();
            msg.what = STATUS_START;
            handler.sendMessage(msg);
            for (int i = 1; i <= count; i++) {
                Util u = new Util();
                passwords.add(u.getPassword(length));

                msg = new Message();
                msg.what = STATUS_STEP;
                Bundle progress = new Bundle();
                progress.putInt("PROGRESS", (100 * i )/ count) ;
                msg.setData(progress);
                handler.sendMessage(msg);

            }
            msg = new Message();
            msg.what = STATUS_DONE;
            Bundle allpassword = new Bundle();
            allpassword.putStringArrayList("PASSWORDS", passwords);
            msg.setData(allpassword);
            handler.sendMessage(msg);


        }
    }




    private class DoWork extends AsyncTask<Void, Integer, Void> {
        int count,length;

        public DoWork(int count, int length) {
            this.count = count;
            this.length = length;
        }

        ArrayList<String> pass = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0;i<100;i++) {
                for (int j = 0; j < 10000000; j++) {

                }
                publishProgress(i+1);

            }
            Util util = new Util();
            //int passno = 5;

            for(int i = 1; i<=count;i++){
                pass.add(util.getPassword(length));
                Log.d("demo",pass.get(i-1));
            }



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            diag = new ProgressDialog(MainActivity.this);
            diag.setMessage("Computing progress");
            diag.setMax(100);
            diag.setCancelable(false);
            diag.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            diag.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final CharSequence[] allpasswords = new CharSequence[pass.size()];
            int i = 0 ;
            for(String password : pass)
            {
                allpasswords[i] = password;
                i++;
            }
            diag.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Passwords");
            builder.setItems(allpasswords , new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
// Do something with the selection
                    TextView showpass = (TextView) findViewById(R.id.textViewPassword);
                    showpass.setText(allpasswords[item]);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            diag.setProgress(values[0]);

        }
    }

}