package com.example.midterm_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skaul on 10/14/2016.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "myFavorites.db";
    static final int DB_VERSION = 1;
    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FilterTable.OnCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilterTable.OnUpGrade(db, oldVersion, newVersion);
    }
}
