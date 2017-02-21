package com.example.midterm_siddhant_jain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by sid on 25-10-2016.
 */

public class DatabaseManager  {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilterDAO favoriteDAO;
    public  DatabaseManager(Context context)
    {
        mContext = context;
        dbOpenHelper  = new DatabaseOpenHelper(mContext);
        db= dbOpenHelper.getWritableDatabase();
        favoriteDAO = new FilterDAO(db);
    }
    public void close()
    { if(db!=null)
        db.close();
    }

    public FilterDAO getFavoriteDAO()
    {
        return this.favoriteDAO;
    }

    public long saveFavorite(App favorite)
    {
        return  this.favoriteDAO.save(favorite);
    }
/*
    public boolean updateFavorite(App favorite)
    {
        return  this.favoriteDAO.update(favorite);
    }
*/
    public boolean deleteFavorite(App favorite)
    {
        return  this.favoriteDAO.delete(favorite);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public App getFavorite(String name)
    {
        return this.favoriteDAO.get(name);
    }

    public List<App> getAllFavorite()
    {
        return  this.favoriteDAO.getAllFavorites();
    }



}

