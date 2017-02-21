package com.example.finalprepmaps;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sid on 11-12-2016.
 */

public class Favloc {

    String cityname;
    String key;
    public Favloc(String cityname, String key){

        this.cityname = cityname;
        this.key = key;
    }
    public Favloc(){}

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityname='" + cityname + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cityname", cityname);
        result.put("key", key);

        return result;
    }
}
