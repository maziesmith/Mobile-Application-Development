package com.example.midterm_siddhant_jain;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sid on 25-10-2016.
 */

public class FilterTable {

    static final String TABLENAME = "Filter";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_THUMB_URL = "thumb_url";

    static  public void OnCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+ "(");
        sb.append(COLUMN_NAME+" text  not null,");
        sb.append(COLUMN_PRICE+" text not null,");
        sb.append(COLUMN_THUMB_URL + " text not null,");
        //sb.append(COLUMN_TEMP + " text not null,");
        //sb.append(COLUMN_FAVORITE + " text not null,");
        sb.append("PRIMARY KEY("+COLUMN_NAME+");");
        db.execSQL(sb.toString());
    }

    static public  void OnUpGrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS"+TABLENAME);
        FilterTable.OnCreate(db);
    }

}
