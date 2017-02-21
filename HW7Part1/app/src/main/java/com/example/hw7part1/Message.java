package com.example.hw7part1;

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
    String fromuser_id, dateSent, mimage_url, mimageid , text, read_flag ;


    String key;

    @Override
    public String toString() {
        return "Message{" +
                "fromuser_id='" + fromuser_id + '\'' +
                ", dateSent='" + dateSent + '\'' +
                ", mimage_url='" + mimage_url + '\'' +
                ", mimageid='" + mimageid + '\'' +
                ", text='" + text + '\'' +
                ", read_flag='" + read_flag + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getFromuser_id() {
        return fromuser_id;
    }

    public void setFromuser_id(String fromuser_id) {
        this.fromuser_id = fromuser_id;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getMimage_url() {
        return mimage_url;
    }

    public void setMimage_url(String mimage_url) {
        this.mimage_url = mimage_url;
    }

    public String getMimageid() {
        return mimageid;
    }

    public void setMimageid(String mimageid) {
        this.mimageid = mimageid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRead_flag() {
        return read_flag;
    }

    public void setRead_flag(String read_flag) {
        this.read_flag = read_flag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fromuser_id",fromuser_id );
        result.put("dateSent", dateSent);
        result.put("mimage_url", mimage_url);
        result.put("mimageid", mimageid);
        result.put("text",text);
        result.put("read_flag",read_flag);
        result.put("key",key);

        return result;
    }

    public Message() {
    }

    public Message(String fromuser_id, String dateSent, String mimage_url, String mimageid, String text, String key, String read_flag) {
        this.fromuser_id = fromuser_id;
        this.dateSent = dateSent;
        this.mimage_url = mimage_url;
        this.mimageid = mimageid;
        this.text = text;
        this.key = key;
        this.read_flag = read_flag;
    }
}
