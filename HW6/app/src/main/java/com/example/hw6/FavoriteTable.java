/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by skaul on 10/14/2016.
 */
public class FavoriteTable {
    static final String TABLENAME = "Favorites";
    static final String COLUMN_CITY = "city";
    static final String COLUMN_COUNTRY = "country";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TEMP = "temp";
    static final String COLUMN_FAVORITE = "favorite";

    static  public void OnCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+ "(");
        sb.append(COLUMN_CITY+" text  not null,");
        sb.append(COLUMN_COUNTRY+" text not null,");
        sb.append(COLUMN_DATE + " text not null,");
        sb.append(COLUMN_TEMP + " text not null,");
        sb.append(COLUMN_FAVORITE + " text not null,");
        sb.append("PRIMARY KEY("+COLUMN_CITY+","+COLUMN_COUNTRY+"));");
        db.execSQL(sb.toString());
    }

    static public  void OnUpGrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS"+TABLENAME);
        FavoriteTable.OnCreate(db);
    }
}
