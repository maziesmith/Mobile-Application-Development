/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
//import android.support.annotation.RequiresApi;


import com.example.hw6.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skaul on 10/14/2016.
 */
public class FavoriteDAO {
    private SQLiteDatabase db;
    public FavoriteDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    public long save (Favorite fav)
    {
        ContentValues values = new ContentValues();
        values.put(FavoriteTable.COLUMN_TEMP,fav.getTemperature());
        values.put(FavoriteTable.COLUMN_CITY,fav.getCity());
        values.put(FavoriteTable.COLUMN_COUNTRY,fav.getCountry());
        values.put(FavoriteTable.COLUMN_DATE,fav.getDate());
        values.put(FavoriteTable.COLUMN_FAVORITE,fav.getFavorite());
        return  db.insert(FavoriteTable.TABLENAME, null, values);

    }

    public  boolean update(Favorite fav)
    {ContentValues values = new ContentValues();
        values.put(FavoriteTable.COLUMN_TEMP, fav.getTemperature());
        values.put(FavoriteTable.COLUMN_DATE,fav.getDate());
        values.put(FavoriteTable.COLUMN_FAVORITE,fav.getFavorite());

        return db.update(FavoriteTable.TABLENAME,values, FavoriteTable.COLUMN_CITY+"=? and "+FavoriteTable.COLUMN_COUNTRY+"=?", new String[]{fav.getCity(),fav.getCountry()}) > 0 ;

    }
    public boolean delete(Favorite fav)
    {
        return db.delete(FavoriteTable.TABLENAME, FavoriteTable.COLUMN_CITY+"=? and "+FavoriteTable.COLUMN_COUNTRY+"=?", new String[]{fav.getCity(),fav.getCountry()}) > 0 ;

    }
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Favorite get(String City , String Country)
    {   Favorite note = null;
         Cursor c =  db.query(true, FavoriteTable.TABLENAME,new String[]{FavoriteTable.COLUMN_CITY,FavoriteTable.COLUMN_COUNTRY,FavoriteTable.COLUMN_TEMP,FavoriteTable.COLUMN_DATE,FavoriteTable.COLUMN_FAVORITE},
                FavoriteTable.COLUMN_CITY+"=? and "+FavoriteTable.COLUMN_COUNTRY+"=?", new String[]{City, Country},null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            note = buildFavoriteFromCursor(c);
            if(!c.isClosed())
                c.close();
        }

        return note;
    }

    public List<Favorite> getAllFavorites()
    {
        List<Favorite> notes = new ArrayList<Favorite>();
        Cursor c = db.query(true, FavoriteTable.TABLENAME,new String[]{FavoriteTable.COLUMN_CITY,FavoriteTable.COLUMN_COUNTRY,FavoriteTable.COLUMN_TEMP,FavoriteTable.COLUMN_DATE,FavoriteTable.COLUMN_FAVORITE},
                null,null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            do {
                Favorite note = buildFavoriteFromCursor(c);
                notes.add(note);

            }
            while(c.moveToNext());
            if(!c.isClosed())
                c.close();


        }
        return notes;
    }

    private Favorite buildFavoriteFromCursor(Cursor c)
    {
        Favorite fav = null;
        if(c!=null)
        {
            fav = new Favorite();
        }
        fav.setCity(c.getString(0));
        fav.setCountry(c.getString(1));
        fav.setTemperature(c.getString(2));
        fav.setDate(c.getString(3));
        fav.setFavorite(Integer.parseInt(c.getString(4)));

        return fav;
    }

}
