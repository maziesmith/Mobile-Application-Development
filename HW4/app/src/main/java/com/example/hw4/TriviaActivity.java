package com.example.hw4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity  implements  GetImage.ImageInterface{
private ArrayList<Question> questionsList;
    private ProgressDialog progress;
    private int index = 0;
    private int questionum = 1;
    private TextView questionno ;
    private TextView seconds ;
    private TextView questiontext ;
    private RadioGroup optionsGroup;
    private  Button quitButton;
    private Button nextButton;
    private ArrayList<String> choices;
    private ImageView questionimage;
    private double score ;
    private final long startTime = 120 * 1000;
    private final long interval = 1 * 1000;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public static final String SCORE_KEY = "SCORE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        setAllViews();
        if(getIntent().getExtras() != null)
        {
            questionsList = getIntent().getExtras().getParcelableArrayList(MainActivity.QUESTIONS_KEY);
            Question first = questionsList.get(0);
            questionno.setText("Q"+questionum);
            //seconds.setText(120+"");
            questiontext.setText(first.getText());
            choices = first.getChoices();
            int id = 0;
            for (int i=0; i< optionsGroup.getChildCount(); i++){
                Log.d("trivia first", i+"");
                optionsGroup.removeViewAt(i);
            }
            for(int i =0 ; i < choices.size(); i++)
            {
                RadioButton btn = new RadioButton(this);
                id = i+1;
                btn.setId(id);
                btn.setText(choices.get(i));
                optionsGroup.addView(btn);
            }
            if(!first.getImage().equals("noimage")){
                progress = new ProgressDialog(questionimage.getContext());
                progress.setCancelable(false);
                progress.setTitle(getString(R.string.Loading_image));
                progress.show();
            new GetImage(this).execute(first.getImage());}
            else {
                questionimage.setImageDrawable(null);
            }
            countDownTimer = new MyCountDownTimer(startTime, interval);
            countDownTimer.start();
            seconds.setText(String.valueOf(startTime / 1000));
            timerHasStarted = true;

        }

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                timerHasStarted = false;
                Intent intent = new Intent(TriviaActivity.this,MainActivity.class);
                finish();
                startActivity(intent);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("trivia" ,optionsGroup.getCheckedRadioButtonId()+" ");
                if(optionsGroup.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(TriviaActivity.this,"Please select your answer ", Toast.LENGTH_LONG).show();
                }
                else {
                    int answer = questionsList.get(index).getAnswer();
                    int selected = optionsGroup.getCheckedRadioButtonId();
                    Log.d("trivia quest" , index+"");
                    Log.d("trivia selected" , selected+"score"+score);


                    if (answer == selected) {
                        score += 6.25;
                    }
                    index++;
                    if (index == questionsList.size()) {
                        countDownTimer.cancel();
                        timerHasStarted = false;
                        Intent intent = new Intent(TriviaActivity.this,StatsActivity.class);
                        intent.putExtra(SCORE_KEY,score);
                        intent.putExtra(MainActivity.QUESTIONS_KEY,questionsList);
                        finish();
                        startActivity(intent);

                    } else {
                        questionum++;
                        Question nextQuestion = questionsList.get(index);
                        questionno.setText("Q" + questionum);
                      //  seconds.setText(120 + "");
                        questiontext.setText(nextQuestion.getText());
                        choices = nextQuestion.getChoices();
                        int id = 0;
                        optionsGroup.clearCheck();
                        optionsGroup.removeAllViews();
                        for (int i = 0; i < choices.size(); i++) {

                            RadioButton btn = new RadioButton(TriviaActivity.this);
                            id = i + 1;
                            btn.setId(id);
                            btn.setText(choices.get(i));
                            optionsGroup.addView(btn);
                        }
                        if (!nextQuestion.getImage().equals("noimage")) {
                            new GetImage(TriviaActivity.this).execute(nextQuestion.getImage());
                            progress = new ProgressDialog(questionimage.getContext());
                            progress.setCancelable(false);
                            progress.setTitle(getString(R.string.Loading_image));
                            progress.show();

                        }
                        else {
                            questionimage.setImageDrawable(null);
                        }

                    }
                }
            }
        });
    }

    void setAllViews()
    {
        questionno = (TextView) findViewById(R.id.textViewQ1) ;
        seconds = (TextView) findViewById(R.id.textViewSeconds);
        questiontext = (TextView) findViewById(R.id.textViewQuestionText) ;
        optionsGroup = (RadioGroup) findViewById(R.id.RadioGroupChoices);
        nextButton = (Button)findViewById(R.id.buttonNext);
        quitButton = (Button)findViewById(R.id.buttonQuit);
        questionimage = (ImageView) findViewById(R.id.imageViewImage);
    }

    @Override
    public void setImage(Bitmap bitmap) {
        this.questionimage.setImageBitmap(bitmap);
        progress.cancel();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            countDownTimer.cancel();
            timerHasStarted = false;
            Intent intent = new Intent(TriviaActivity.this,StatsActivity.class);
            intent.putExtra(SCORE_KEY,score);
            intent.putExtra(MainActivity.QUESTIONS_KEY,questionsList);
            finish();
            startActivity(intent);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            seconds.setText("" + millisUntilFinished / 1000);
        }
    }
}
