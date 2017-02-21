/*
Assignment No : InClass Assignment 3
File Name : DisplayActivity.java
Full Name : Sonal Kaulkar
            Siddhant Jain
Group No 34
 */
package com.example.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    final static String IMAGE_KEY = "IMAGE" ;
    public static final int REQ_CODE_NAME = 111;
    public static final int REQ_CODE_EMAIL = 112;
    public static final int REQ_CODE_DEPT = 113;
    public static final int REQ_CODE_MOOD = 114;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        if(getIntent().getExtras() !=null) {
            final Student student = getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);
            final TextView name = (TextView) findViewById(R.id.textViewName);
            Log.d("display", student.getName());
            name.setText(student.getName());
            final TextView email = (TextView) findViewById(R.id.textViewEmail);
            Log.d("display", student.getEmailid());
            email.setText(student.getEmailid());
            final TextView dept = (TextView) findViewById(R.id.textViewDept);
            Log.d("display", student.getDepartment());
            dept.setText(student.getDepartment());
            final TextView mood = (TextView) findViewById(R.id.textViewmood);
            Log.d("display", student.getMood());
            mood.setText(student.getMood());


            final Intent displayIntent = new Intent("com.example.InClass03.intent.action.EDIT");
            displayIntent.addCategory(Intent.CATEGORY_DEFAULT);
            ImageView nameImage = (ImageView) findViewById(R.id.imageViewName);
            nameImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    student.setName(name.getText().toString());
                    displayIntent.putExtra(MainActivity.STUDENT_KEY, student);
                    displayIntent.putExtra(DisplayActivity.IMAGE_KEY, "name");
                    startActivityForResult(displayIntent,REQ_CODE_NAME);
                }
            });
            ImageView emailImage = (ImageView) findViewById(R.id.imageViewEmail);
            emailImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    student.setEmailid(email.getText().toString());
                    displayIntent.putExtra(MainActivity.STUDENT_KEY, student);
                    displayIntent.putExtra(DisplayActivity.IMAGE_KEY, "email");
                    startActivityForResult(displayIntent,REQ_CODE_EMAIL);
                }
            });
            ImageView depImage = (ImageView) findViewById(R.id.imageViewDept);
            depImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    student.setDepartment(dept.getText().toString());
                    displayIntent.putExtra(MainActivity.STUDENT_KEY, student);
                    displayIntent.putExtra(DisplayActivity.IMAGE_KEY, "dept");
                    startActivityForResult(displayIntent,REQ_CODE_DEPT);
                }
            });
            ImageView moodImage = (ImageView) findViewById(R.id.imageViewMood);
            moodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("display", "before set mood "+student.getMood());
                    student.setMood(mood.getText().toString());
                    Log.d("display", "after set mood "+student.getMood());
                    displayIntent.putExtra(MainActivity.STUDENT_KEY, student);
                    displayIntent.putExtra(DisplayActivity.IMAGE_KEY, "mood");
                    startActivityForResult(displayIntent,REQ_CODE_MOOD);
                }
            });


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_CODE_NAME)
        {
            if(resultCode == RESULT_OK)
            {
                String value = data.getExtras().getString(EditActivity.NAME_KEY);
                TextView nametext = (TextView)findViewById(R.id.textViewName);
                nametext.setText(value);
            }

        }
        if(requestCode == REQ_CODE_EMAIL)
        {
            if(resultCode == RESULT_OK)
            {
                String value = data.getExtras().getString(EditActivity.EMAIL_KEY);
                TextView emailtext = (TextView)findViewById(R.id.textViewEmail);
                emailtext.setText(value);
            }

        }
        if(requestCode == REQ_CODE_DEPT)
        {
            if(resultCode == RESULT_OK)
            {
                String value = data.getExtras().getString(EditActivity.DEPT_KEY);
                TextView depttext = (TextView)findViewById(R.id.textViewDept);
                depttext.setText(value);
            }

        }

        if(requestCode == REQ_CODE_MOOD)
        {
            if(resultCode == RESULT_OK)
            {
                String value = data.getExtras().getString(EditActivity.MOOD_KEY);
                TextView moodtext = (TextView)findViewById(R.id.textViewmood);
                moodtext.setText(value);
            }

        }
    }
}
