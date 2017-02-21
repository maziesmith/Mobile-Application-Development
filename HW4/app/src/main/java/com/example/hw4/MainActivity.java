package com.example.hw4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetQuestionAsyncTask.IQuestionAysnc {
    private ProgressDialog progress;
    private Button buttonStart,buttonExit;
    private ArrayList<Question> questionsList;
    public static final String QUESTIONS_KEY = "QUESTIONS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAllViews();
        progress = new ProgressDialog(MainActivity.this);
        progress.setCancelable(false);
        progress.setTitle(getString(R.string.Loading_trivia));
        progress.show();
        new GetQuestionAsyncTask(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
              intent.putExtra(QUESTIONS_KEY,questionsList);
              startActivity(intent);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void getQuestionsFromAsync(ArrayList<Question> questions) {
        questionsList = questions;
        progress.cancel();
        Log.d("main", "in main back");
        buttonStart.setEnabled(true);
    }

    void setAllViews()
    {

        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonExit = (Button)findViewById(R.id.buttonExit);
    }
}
