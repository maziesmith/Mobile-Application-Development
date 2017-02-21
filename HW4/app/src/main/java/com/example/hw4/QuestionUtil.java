package com.example.hw4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by skaul on 9/17/2016.
 */
public class QuestionUtil {
    static public class QuestionsJSONParser{
        static ArrayList<Question> parseQuestions(String in )
        {ArrayList<Question> questionsList = new ArrayList<Question>();
            try {
                JSONObject root = new JSONObject(in);
                JSONArray questionsJSONArray =  root.getJSONArray("questions");
                for(int i = 0 ; i < questionsJSONArray.length(); i++)
                {
                    JSONObject questionJSONObject = (JSONObject) questionsJSONArray.get(i);
                    Question question = Question.createQuestion(questionJSONObject);
                    questionsList.add(question);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return  questionsList;
        }
    }
}
