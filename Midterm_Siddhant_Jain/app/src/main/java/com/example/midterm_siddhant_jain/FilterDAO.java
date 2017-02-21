package com.example.midterm_siddhant_jain;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sid on 25-10-2016.
 */

public class FilterDAO {
    private SQLiteDatabase db;
    public FilterDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    public long save (App fav)
    {
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,fav.getName());
        values.put(FilterTable.COLUMN_PRICE,fav.getPrice());
        values.put(FilterTable.COLUMN_THUMB_URL,fav.getImage());
        //values.put(FavoriteTable.COLUMN_DATE,fav.getDate());
        //values.put(FavoriteTable.COLUMN_FAVORITE,fav.getFavorite());
        return  db.insert(FilterTable.TABLENAME, null, values);

    }
/*
    public  boolean update(App fav)
    {ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_TEMP, fav.getTemperature());
        values.put(FilterTable.COLUMN_DATE,fav.getDate());
        values.put(FavoriteTable.COLUMN_FAVORITE,fav.getFavorite());

        return db.update(FavoriteTable.TABLENAME,values, FavoriteTable.COLUMN_CITY+"=? and "+FavoriteTable.COLUMN_COUNTRY+"=?", new String[]{fav.getCity(),fav.getCountry()}) > 0 ;

    }*/
    public boolean delete(App fav)
    {
        return db.delete(FilterTable.TABLENAME, FilterTable.COLUMN_NAME+"=? and "+FilterTable.COLUMN_PRICE+"=?", new String[]{fav.getName(),fav.getPrice()}) > 0 ;

    }
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public App get(String Name)
    {   App note = null;
        Cursor c =  db.query(true, FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_NAME,FilterTable.COLUMN_PRICE,FilterTable.COLUMN_THUMB_URL},
                FilterTable.COLUMN_NAME+"=?", new String[]{Name+""},null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            note = buildFavoriteFromCursor(c);
            if(!c.isClosed())
                c.close();
        }

        return note;
    }

    public List<App> getAllFavorites()
    {
        List<App> notes = new ArrayList<App>();
        Cursor c = db.query(true, FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_NAME,FilterTable.COLUMN_PRICE,FilterTable.COLUMN_THUMB_URL},
                null,null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            do {
                App note = buildFavoriteFromCursor(c);
                notes.add(note);

            }
            while(c.moveToNext());
            if(!c.isClosed())
                c.close();


        }
        return notes;
    }

    private App buildFavoriteFromCursor(Cursor c)
    {
        App fav = null;
        if(c!=null)
        {
            fav = new App();
        }
        fav.setName(c.getString(0));
        fav.setPrice(c.getString(1));
        fav.setImage(c.getString(2));
        //fav.setDate(c.getString(3));
        //fav.setFavorite(Integer.parseInt(c.getString(4)));

        return fav;
    }

}

