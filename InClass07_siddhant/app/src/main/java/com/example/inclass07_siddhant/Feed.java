package com.example.inclass07_siddhant;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by sid on 03-10-2016.
 */

public class Feed  implements Serializable,Comparator<Feed> {

    //For each item retrieve the title, summary, release date, the smallest image url as
    //thumbnail, and largest image url.
    String title, summary, date, thumbnail, image, update;
    boolean a;

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int compare(Feed o1, Feed o2) {
        return 0;
    }
}
