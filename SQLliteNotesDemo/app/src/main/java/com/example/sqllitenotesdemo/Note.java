package com.example.sqllitenotesdemo;

/**
 * Created by sid on 16-10-2016.
 */

public class Note {

    private long _id;
    private String subject, text;

    public Note(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public Note(){

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long id) {
        this._id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + _id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
