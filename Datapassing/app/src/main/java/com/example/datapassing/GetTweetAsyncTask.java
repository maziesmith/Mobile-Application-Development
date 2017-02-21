package com.example.datapassing;

import android.content.Context;
import android.os.AsyncTask;

import java.util.LinkedList;

/**
 * Created by sid on 19-09-2016.
 */
public class GetTweetAsyncTask extends AsyncTask<String, Void, LinkedList<String>> {
    //MainActivity activity;
    IData activity;
    /*public GetTweetAsyncTask(MainActivity activity) {
        this.activity = activity;
    }*/
    public GetTweetAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected LinkedList<String> doInBackground(String... params) {
        LinkedList<String> tweets = new LinkedList<String>();
        tweets.add("Tweet 0");
        tweets.add("Tweet 1");
        tweets.add("Tweet 2");
        tweets.add("Tweet 3");
        return tweets;
    }

    @Override
    protected void onPostExecute(LinkedList<String> result) {
        activity.setupData(result);
        super.onPostExecute(result);
    }

    static public interface IData{
        public void setupData(LinkedList<String> result);
        public Context getContext();
    }
}
