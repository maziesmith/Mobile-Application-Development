package com.example.hw4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
private ArrayList<Question> questionslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        if(getIntent().getExtras()!=null)
        {
            double score = getIntent().getExtras().getDouble(TriviaActivity.SCORE_KEY);
            ProgressBar percentage = (ProgressBar)findViewById(R.id.progressBarPer);
            percentage.setProgress((int) score);
            TextView percentLabel = (TextView)findViewById(R.id.textViewPercent);
            int s = (int) score;
            percentLabel.setText(s+"%");
            questionslist = getIntent().getExtras().getParcelableArrayList(MainActivity.QUESTIONS_KEY);

        }

        findViewById(R.id.buttonQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StatsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);

            }
        });


        findViewById(R.id.buttonTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StatsActivity.this, TriviaActivity.class);
                intent.putExtra(MainActivity.QUESTIONS_KEY,questionslist);
                finish();
                startActivity(intent);

            }
        });
    }
}
