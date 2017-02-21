package com.example.listviewdemo;

/**
 * Created by sid on 30-09-2016.
 */
public class Color {
    String colorName, colorHex;

    public Color(String colorName, String colorHex) {
        this.colorName = colorName;
        this.colorHex = colorHex;
    }
//this toString is very important because it shows the value printed on listView BUT ONLY WITH ARRAYADAPTER AND NOT WITH COLORADAPTER
    @Override
    public String toString() {
        return this.colorName+"!!!";
    }
}
