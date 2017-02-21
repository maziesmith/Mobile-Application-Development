/*
* HW6
* Group34
* Sonal Kaulkar, Siddhant Jain
* */
package com.example.hw6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
//import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Created by skaul on 10/14/2016.
 */
public class DatabaseManager  {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FavoriteDAO favoriteDAO;
    public  DatabaseManager(Context context)
    {
        mContext = context;
        dbOpenHelper  = new DatabaseOpenHelper(mContext);
        db= dbOpenHelper.getWritableDatabase();
        favoriteDAO = new FavoriteDAO(db);
    }
    public void close()
    { if(db!=null)
        db.close();
    }

    public FavoriteDAO getFavoriteDAO()
    {
        return this.favoriteDAO;
    }

    public long saveFavorite(Favorite favorite)
    {
        return  this.favoriteDAO.save(favorite);
    }

    public boolean updateFavorite(Favorite favorite)
    {
        return  this.favoriteDAO.update(favorite);
    }

    public boolean deleteFavorite(Favorite favorite)
    {
        return  this.favoriteDAO.delete(favorite);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Favorite getFavorite(String city , String country)
    {
        return this.favoriteDAO.get(city, country);
    }

    public List<Favorite> getAllFavorite()
    {
        return  this.favoriteDAO.getAllFavorites();
    }



}
