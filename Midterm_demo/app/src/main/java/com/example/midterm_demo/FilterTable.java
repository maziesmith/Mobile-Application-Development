package com.example.midterm_demo;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by skaul on 10/14/2016.
 */
public class FilterTable {
    static final String TABLENAME = "Filter";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_DOLLARIMAGE = "dollarimage";
    static final String COLUMN_THUMBURL = "thumburl";
    static final String COLUMN_FILTERED= "filtered";
    static final String COLUMN_CURRENCY= "currency";

    static  public void OnCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+ "(");
        sb.append(COLUMN_NAME+" text  not null,");
        sb.append(COLUMN_PRICE+" text not null,");
        sb.append(COLUMN_DOLLARIMAGE + " text not null,");
        sb.append(COLUMN_THUMBURL + " text not null,");
        sb.append(COLUMN_FILTERED + " text not null,");
        sb.append(COLUMN_CURRENCY + " text not null,");
        sb.append("PRIMARY KEY("+COLUMN_NAME+"));");
        db.execSQL(sb.toString());
    }

    static public  void OnUpGrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS"+TABLENAME);
        FilterTable.OnCreate(db);
    }
}
