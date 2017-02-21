package com.example.makeupmidterm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sid on 19-11-2016.
 */

public class Test {
    String name;
    String key;
    Double price;

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Test(String name, Double price, String key) {
        this.name = name;
        this.price = price;
        this.key = key;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("key", key);

        return result;
    }
    public Test(){}
}
