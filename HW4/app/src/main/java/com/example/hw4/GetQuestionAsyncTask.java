package com.example.hw4;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by skaul on 9/23/2016.
 */
public class GetQuestionAsyncTask extends AsyncTask<String, Void, ArrayList<Question>> {

    IQuestionAysnc activity ;

    public GetQuestionAsyncTask(IQuestionAysnc activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                int statusCode = conn.getResponseCode();
                if(statusCode==HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while(line != null)
                    {
                        sb.append(line);
                        line = br.readLine();
                    }
                    //  Log.d("demo" , sb.toString());
                    return QuestionUtil.QuestionsJSONParser.parseQuestions(sb.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questionArray) {
        if(questionArray != null)
        {
            Log.d("demo", questionArray.toString());
            activity.getQuestionsFromAsync(questionArray);
        }
    }

    public static interface IQuestionAysnc {
        public  void getQuestionsFromAsync(ArrayList<Question> questions);

    }
}
