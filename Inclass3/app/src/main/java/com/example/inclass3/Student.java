package com.example.inclass3;

import java.io.Serializable;

/**
 * Created by sid on 07-09-2016.
 */
public class Student implements Serializable {
    String name, email, department;
    int mood;

    public Student(String name, String email, String department, int mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood=" + mood +
                '}';
    }
}
