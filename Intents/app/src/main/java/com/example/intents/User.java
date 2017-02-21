package com.example.intents;

import java.io.Serializable;

/**
 * Created by sid on 06-09-2016.
 */
public class User implements Serializable {
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
