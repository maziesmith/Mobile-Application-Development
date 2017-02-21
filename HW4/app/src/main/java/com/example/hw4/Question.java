package com.example.hw4;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skaul on 9/23/2016.
 */
public class Question implements Parcelable {
    int id,answer;
    String text ;
    String image ;
    ArrayList<String> choices = null;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    static public Question createQuestion(JSONObject js)
    {
         Question question = new Question();
        try {
            question.setId(js.getInt("id"));
            if(js.has("image")) {
                question.setImage(js.getString("image"));
            }
            else
            {
                question.setImage("noimage");
            }

            question.setText(js.getString("text"));

            JSONObject choices = js.getJSONObject("choices");
            JSONArray choicesArray = choices.getJSONArray("choice");
            ArrayList<String> options = new ArrayList<String>();
            for(int i = 0 ; i <  choicesArray.length(); i++)
            {
                options.add((String) choicesArray.get(i));

            }
            question.setAnswer(choices.getInt("answer"));
            question.setChoices(options);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(id);
        dest.writeInt(answer);
        dest.writeString(text);
        dest.writeString(image);
        dest.writeStringList(choices);

    }

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) { //order matters of unmarshalling
        this.id = in.readInt();
        this.answer = in.readInt();
        this.text = in.readString();
        this.image = in.readString();
        this.choices =  in.createStringArrayList();

    }
}
