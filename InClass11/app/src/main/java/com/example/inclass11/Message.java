package com.example.inclass11;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaul on 10/31/2016.
 */
@IgnoreExtraProperties
public class Message {
    String firstname,lname, dateCreate, image_url, imageid , text;
    ArrayList<Message> comments;
    public String getImageid() {
        return imageid;
    }
@Exclude
 String key;

    @Override
    public String toString() {
        return "Message{" +
                "firstname='" + firstname + '\'' +
                ", lname='" + lname + '\'' +
                ", CreatedAt='" + dateCreate + '\'' +
                ", image_url='" + image_url + '\'' +
                ", imageid='" + imageid + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                '}';
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public ArrayList<Message> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Message> comments) {
        this.comments = comments;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname",firstname );
        result.put("lname", lname);
        result.put("dateCreate", dateCreate);
        result.put("imageid", imageid);
        result.put("image_url",image_url);
        result.put("text",text);

        return result;
    }

    public Message() {
    }

    public Message(String firstname, String lname, String createdAt, String image_url, String text, String imageid, ArrayList<Message> comments) {
        this.firstname = firstname;
        this.lname = lname;
        this.dateCreate = createdAt;
        this.image_url = image_url;
        this.text = text;
        this.imageid = imageid;
        this.comments = comments;
    }
}
