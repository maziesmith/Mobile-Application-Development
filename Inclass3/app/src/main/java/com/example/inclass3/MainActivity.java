package com.example.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final static String Student_key = "STUDENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int progress = 0;
        TextView per = (TextView) findViewById(R.id.MoodPer);
        per.setText(progress+"%");
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setProgress(progress);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView per = (TextView) findViewById(R.id.MoodPer);
                per.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button submit = (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.editTextName);
                String Student_Name = name.getText().toString();
                if (Student_Name == null || Student_Name.equals("")) {
                    Toast.makeText(MainActivity.this, "Write student name please", Toast.LENGTH_LONG).show();
                } else {
                    EditText email = (EditText) findViewById(R.id.editTextMail);
                    String Student_Mail = email.getText().toString();
                    if (Student_Mail == null || Student_Mail.equals("")) {
                        Toast.makeText(MainActivity.this, "Write student mail please", Toast.LENGTH_LONG).show();
                    }
                    if (isValidEmailAddress(Student_Mail)) {


                        RadioGroup rg = (RadioGroup) findViewById(R.id.RG);
                        int id = rg.getCheckedRadioButtonId();
                        String department = "";
                        if (id == R.id.radioButtonSIS) {
                            department = "SIS";
                        } else if (id == R.id.radioButtonCS) {
                            department = "CS";
                        } else if (id == R.id.radioButtonBIO) {
                            department = "BIO";
                        } else {
                            department = "Others";
                        }
                        TextView per = (TextView) findViewById(R.id.MoodPer);
                        String moodPer = per.getText().toString();
                        moodPer = moodPer.replace("%", "");
                        int moodPercent = Integer.parseInt(moodPer);
                        Student student = new Student(Student_Name, Student_Mail, department, moodPercent);
                        Log.d("demo", student.toString());
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        intent.putExtra(Student_key, student);
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, "Write correct format of email", Toast.LENGTH_LONG).show();
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
