package com.example.jsondemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sid on 19-09-2016.
 */
public class Person {
    String price, name, url;

    public static Person createPerson(JSONObject js) throws JSONException {
        Person person = new Person();
        person.setName(js.getString("im:name"));
        //JSONObject root = new JSONObject(in);
        JSONArray img = js.getJSONArray("im:image");
        JSONObject img0 = (JSONObject) img.get(0);
        person.setUrl(js.getString("im:image"));
        person.setPrice(js.getString("department"));
        return person;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
