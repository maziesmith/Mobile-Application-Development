package com.example.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    final static String SNAME_KEY = "SNAME";
    final static String SMAIL_KEY = "SMAIL";
    final static String SDEP_KEY = "SDEP";
    final static String SMOOD_KEY = "SMOOD";

    final static String NewName_key = "NewName";
    final static String NewMail_key = "NewMail";
    final static String NewDep_key = "NewDep";
    final static String NewMood_key = "NewMood";
    public static final int REQ_CODE = 100;
    public static final int REQ_CODE2 = 101;
    public static final int REQ_CODE3 = 102;
    public static final int REQ_CODE4 = 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final Intent intent = getIntent();
        Student student = (Student) getIntent().getExtras().getSerializable(MainActivity.Student_key);
        TextView name = (TextView) findViewById(R.id.textView6);
        name.setText(student.name);
        TextView mail = (TextView) findViewById(R.id.textView7);
        mail.setText(student.email);
        TextView department = (TextView) findViewById(R.id.textView8);
        department.setText(student.department);
        TextView mood = (TextView) findViewById(R.id.textView9);
        mood.setText(String.valueOf(student.mood)+"%");
        Log.d("demo",""+student.mood);
        Log.d("demo",String.valueOf(student.mood));
        ImageView imageName = (ImageView)findViewById(R.id.imageView1);
        imageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView name = (TextView) findViewById(R.id.textView6);
                String sname = name.getText().toString();
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.putExtra(SNAME_KEY,sname);
                startActivityForResult(intent2, REQ_CODE);

            }
        });
        ImageView imageMail = (ImageView)findViewById(R.id.imageView2);
        imageMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mail = (TextView) findViewById(R.id.textView7);
                String smail = mail.getText().toString();
                Intent intent3 = new Intent();
                intent3.setAction(Intent.ACTION_VIEW);
                intent3.putExtra(SMAIL_KEY,smail);
                startActivityForResult(intent3, REQ_CODE2);

            }
        });
        ImageView imageDepartment = (ImageView)findViewById(R.id.imageView3);
        imageDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dep = (TextView) findViewById(R.id.textView8);
                String department = dep.getText().toString();
                Log.d("demo","department is "+department);
                Intent intent4 = new Intent();
                intent4.setAction(Intent.ACTION_VIEW);
                intent4.putExtra(SDEP_KEY,department);
                startActivityForResult(intent4, REQ_CODE3);

            }
        });
        ImageView imageMood = (ImageView)findViewById(R.id.imageView4);
        imageMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mood = (TextView) findViewById(R.id.textView9);
                String moodper = mood.getText().toString();
                Log.d("demo","moodper is "+moodper);
                Intent intent5 = new Intent();
                intent5.setAction(Intent.ACTION_VIEW);
                intent5.putExtra(SMOOD_KEY,moodper);
                startActivityForResult(intent5, REQ_CODE4);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQ_CODE){
            if(resultCode==RESULT_OK){
                String newName = data.getExtras().getString(NewName_key);
                Log.d("demo","name Value received is"+newName);
                TextView name = (TextView) findViewById(R.id.textView6);
                name.setText(newName);
            }else if(requestCode==RESULT_CANCELED){
                Log.d("demo","no result");
            }
        }else if(requestCode==REQ_CODE2){
            if(resultCode==RESULT_OK){
                String newMail = data.getExtras().getString(NewMail_key);
                Log.d("demo","mail Value received is"+newMail);
                TextView mail = (TextView) findViewById(R.id.textView7);
                mail.setText(newMail);
            }else if(requestCode==RESULT_CANCELED){
                Log.d("demo","no result");
            }
        }else if(requestCode==REQ_CODE3){
            if(resultCode==RESULT_OK){
                String department = data.getExtras().getString(NewDep_key);
                Log.d("demo"," Department Value received is"+department);
                TextView dep = (TextView) findViewById(R.id.textView8);
                dep.setText(department);
            }else if(requestCode==RESULT_CANCELED){
                Log.d("demo","no result");
            }
        }else if(requestCode==REQ_CODE4){
            if(resultCode==RESULT_OK){
                String moodPer = data.getExtras().getString(NewMood_key);
                Log.d("demo","Mood Value received is "+moodPer);
                TextView mood = (TextView) findViewById(R.id.textView9);
                mood.setText(moodPer);
            }else if(requestCode==RESULT_CANCELED){
                Log.d("demo","no result");
            }
        }
    }

}
