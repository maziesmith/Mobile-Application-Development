/*
Assignment No : InClass Assignment 3
File Name : EditActivity.java
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

import org.w3c.dom.Text;

public class EditActivity extends AppCompatActivity {

    final static String NAME_KEY = "NAME" ;
    final static String EMAIL_KEY = "EMAIL" ;
    final static String DEPT_KEY = "DEPT" ;
    final static String MOOD_KEY = "MOOD" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditText nameView = (EditText) findViewById(R.id.editTextName);
        EditText emailView = (EditText) findViewById(R.id.editTextEmail);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        SeekBar moodseekbar = (SeekBar) findViewById(R.id.seekBarMood);
        final Context context = getApplicationContext();
        final CharSequence[] text = new CharSequence[1];
        final int duration = Toast.LENGTH_SHORT;
        final SeekBar moodper = (SeekBar) findViewById(R.id.seekBarMood);

        if(getIntent().getExtras() !=null) {
            Student stud = getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);
            final String imageselected = getIntent().getExtras().getString(DisplayActivity.IMAGE_KEY);
            Log.d("edit", imageselected);
            switch (imageselected) {
                case "name": {
                    nameView.setVisibility(View.VISIBLE);
                    nameView.setText(stud.getName());
                    break;
                }
                case "email": {
                    emailView.setVisibility(View.VISIBLE);
                    emailView.setText(stud.getEmailid());
                    break;
                }
                case "dept": {
                    TextView rglabel = (TextView) findViewById(R.id.textViewDept);
                    rglabel.setVisibility(View.VISIBLE);
                    if (stud.getDepartment().equals("SIS")) rg.check(R.id.radioButtonSIS);
                    if (stud.getDepartment().equals("CS")) rg.check(R.id.radioButtonCS);
                    if (stud.getDepartment().equals("BIO")) rg.check(R.id.radioButtonBIO);
                    if (stud.getDepartment().equals("Others")) rg.check(R.id.radioButtonOthers);
                    rg.setVisibility(View.VISIBLE);

                    break;
                }

                case "mood": {
                    TextView moodlabel = (TextView) findViewById(R.id.textViewMood);
                    moodlabel.setVisibility(View.VISIBLE);
                    moodseekbar.setVisibility(View.VISIBLE);
                    int progress = 0 ;
                    Log.d("edit", stud.getMood());
                    if(stud.getMood().substring(1,2).equals(" "))
                    {
                        progress = Integer.parseInt(stud.getMood().substring(0, 1));
                    }
                    else if (stud.getMood().substring(2,3).equals("0")) {
                        progress = Integer.parseInt(stud.getMood().substring(0, 3));
                    }
                    else
                    {
                        progress = Integer.parseInt(stud.getMood().substring(0, 2));
                    }
                    moodseekbar.setProgress(progress);
                    final TextView per = (TextView) findViewById(R.id.textViewPercent);
                    per.setText(progress+"%");
                    per.setVisibility(View.VISIBLE);
                    moodper.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            per.setText(progress+"%");
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    break;
                }

            }

            final Intent i = new Intent();
            findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 switch(imageselected) {
                     case "name": {
                         EditText editTextName = (EditText) findViewById(R.id.editTextName);
                         if(editTextName.getText().toString().equals(""))
                         {
                             text[0] = "Please enter the name";
                             Toast toast = Toast.makeText(context, text[0], duration);
                             toast.show();

                         }
                         else {
                             i.putExtra(EditActivity.NAME_KEY, editTextName.getText().toString());
                             setResult(RESULT_OK, i);
                             finish();
                         }
                         break;
                     }
                     case "email": {
                         EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
                         if (editTextEmail.getText().toString().equals("")) {
                             text[0] = "Please enter the email id";
                             Toast toast = Toast.makeText(context, text[0], duration);
                             toast.show();
                         } else {
                             boolean email_valid = isValidEmailAddress(editTextEmail.getText().toString());
                             if (email_valid) {
                                 i.putExtra(EditActivity.EMAIL_KEY, editTextEmail.getText().toString());
                                 setResult(RESULT_OK, i);
                                 finish();
                             } else {
                                 text[0] = "Please enter a valid email id";
                                 Toast toast = Toast.makeText(context, text[0], duration);
                                 toast.show();
                             }
                             break;
                         }
                     }
                     case "dept": {
                         RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                         int id = rg.getCheckedRadioButtonId();
                         String department = null;
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
                         i.putExtra(EditActivity.DEPT_KEY, department);
                         setResult(RESULT_OK, i);
                         finish();
                         break;
                     }
                     case "mood": {
                         SeekBar moodseekbar = (SeekBar) findViewById(R.id.seekBarMood);
                         int moodSelected = moodseekbar.getProgress();
                         String mood = moodSelected + " % Positive";
                         i.putExtra(EditActivity.MOOD_KEY, mood);
                         setResult(RESULT_OK, i);
                         finish();
                         break;
                     }

                 }

                }
            });
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
