/*
Assignment No : InClass Assignment 3
File Name : MainActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */
package com.example.inclass03;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final static String STUDENT_KEY = "STUDENT" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = getApplicationContext();
        final CharSequence[] text = new CharSequence[1];
        final int duration = Toast.LENGTH_SHORT;

        final SeekBar moodper = (SeekBar) findViewById(R.id.seekBarMood);
        moodper.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView per = (TextView) findViewById(R.id.textViewPercent);
                per.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameView = (EditText) findViewById(R.id.editTextName);
                EditText emailView = (EditText) findViewById(R.id.editTextEmail);
                if (nameView.getText().toString().equals("")) {
                    text[0] = "Please enter the name";
                    Toast toast = Toast.makeText(context, text[0], duration);
                    toast.show();
                } else {
                    if (emailView.getText().toString().equals("")) {
                        text[0] = "Please enter the emailid";
                        Toast toast = Toast.makeText(context, text[0], duration);
                        toast.show();
                    } else {
                        String name = nameView.getText().toString();
                        String emailid = emailView.getText().toString();
                        boolean email_valid = isValidEmailAddress(emailid);
                        if (email_valid) {
                            RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                            int id = rg.getCheckedRadioButtonId();
                            SeekBar moodseekbar = (SeekBar) findViewById(R.id.seekBarMood);
                            int moodSelected = moodseekbar.getProgress();
                            String department = null, mood = null;
                            switch (id) {
                                case R.id.radioButtonSIS: {
                                    department = "SIS";
                                    break;
                                }
                                case R.id.radioButtonCS: {
                                    department = "CS";
                                    break;
                                }
                                case R.id.radioButtonBIO: {
                                    department = "BIO";
                                    break;
                                }
                                case R.id.radioButtonOthers: {
                                    department = "Others";
                                    break;
                                }
                            }
                            mood = moodSelected + "% Positive";
                            Log.d("main", name);
                            Log.d("main", emailid);
                            Log.d("main", department);
                            Log.d("main", mood);
                            Student s = new Student(name, emailid, department, mood);
                            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                            intent.putExtra(STUDENT_KEY, s);
                            startActivity(intent);
                        } else{
                            text[0] = "Please enter a valid email address";
                            Toast toast = Toast.makeText(context, text[0], duration);
                            toast.show();
                        }
                    }
                }

            }
        });

    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
