/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

/**
 * Created by skaul on 10/18/2016.
 */

public class Daily_Weather {

    String date , temp, url;
 public Daily_Weather(){}
    @Override
    public String toString() {
        return "Daily_Weather{" +
                "date='" + date + '\'' +
                ", temp='" + temp + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Daily_Weather(String date, String temp, String url) {
        this.date = date;
        this.temp = temp;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
