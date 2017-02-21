package com.example.midterm_demo;


import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by skaul on 10/14/2016.
 */
public class FilterDAO {
    private SQLiteDatabase db;
    public FilterDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    public long save (ITuneApp app)
    {
        ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_NAME,app.getName());
        values.put(FilterTable.COLUMN_PRICE,app.getPrice());
        values.put(FilterTable.COLUMN_CURRENCY,app.getCurrency());
        values.put(FilterTable.COLUMN_FILTERED,app.getFiltered());
        values.put(FilterTable.COLUMN_THUMBURL,app.getThumb_url());
        values.put(FilterTable.COLUMN_DOLLARIMAGE,app.getDollarimage());
        return  db.insert(FilterTable.TABLENAME, null, values);

    }

    public  boolean update(ITuneApp app)
    {ContentValues values = new ContentValues();
        values.put(FilterTable.COLUMN_FILTERED, app.getFiltered());
        return db.update(FilterTable.TABLENAME,values, FilterTable.COLUMN_NAME+"=?", new String[]{app.getName()}) > 0 ;

    }
    public boolean delete(ITuneApp app)
    {
        return db.delete(FilterTable.TABLENAME, FilterTable.COLUMN_NAME+"=?", new String[]{app.getName()}) > 0 ;

    }
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ITuneApp get(String Name)
    {   ITuneApp note = null;
        Cursor c =  db.query(true, FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_NAME, FilterTable.COLUMN_PRICE, FilterTable.COLUMN_CURRENCY, FilterTable.COLUMN_FILTERED, FilterTable.COLUMN_THUMBURL,FilterTable.COLUMN_DOLLARIMAGE},
                FilterTable.COLUMN_NAME+"=?", new String[]{Name},null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            note = buildFavoriteFromCursor(c);
            if(!c.isClosed())
                c.close();
        }

        return note;
    }

    public List<ITuneApp> getAllApps()
    {
        List<ITuneApp> notes = new ArrayList<ITuneApp>();
        Cursor c = db.query(true, FilterTable.TABLENAME,new String[]{FilterTable.COLUMN_NAME, FilterTable.COLUMN_PRICE, FilterTable.COLUMN_CURRENCY, FilterTable.COLUMN_FILTERED, FilterTable.COLUMN_THUMBURL,FilterTable.COLUMN_DOLLARIMAGE},
                null,null,null,null,null,null);
        if(c!= null && c.moveToFirst())
        {
            do {
                ITuneApp note = buildFavoriteFromCursor(c);
                notes.add(note);

            }
            while(c.moveToNext());
            if(!c.isClosed())
                c.close();


        }
        return notes;
    }

    private ITuneApp buildFavoriteFromCursor(Cursor c)
    {
        ITuneApp fav = null;
        if(c!=null)
        {
            fav = new ITuneApp();
        }
        fav.setName(c.getString(0));
        fav.setPrice(c.getString(1));
        fav.setCurrency(c.getString(2));
        fav.setFiltered(c.getString(3));
        fav.setThumb_url(c.getString(4));
        fav.setDollarimage(c.getString(5));

        return fav;
    }

}
