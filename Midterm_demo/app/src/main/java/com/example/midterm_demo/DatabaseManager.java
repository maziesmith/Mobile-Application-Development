package com.example.midterm_demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
//import android.support.annotation.RequiresApi;

import com.example.midterm_demo.FilterDAO;

import java.util.List;

/**
 * Created by skaul on 10/14/2016.
 */
public class DatabaseManager  {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilterDAO filterDAO;
    public  DatabaseManager(Context context)
    {
        mContext = context;
        dbOpenHelper  = new DatabaseOpenHelper(mContext);
        db= dbOpenHelper.getWritableDatabase();
        filterDAO = new FilterDAO(db);
    }
    public void close()
    { if(db!=null)
        db.close();
    }

    public FilterDAO getFilterDAO()
    {
        return this.filterDAO;
    }

    public long saveFilter(ITuneApp Filter)
    {
        return  this.filterDAO.save(Filter);
    }

    public boolean updateFilter(ITuneApp Filter)
    {
        return  this.filterDAO.update(Filter);
    }

    public boolean deleteFilter(ITuneApp Filter)
    {
        return  this.filterDAO.delete(Filter);
    }

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ITuneApp getFilter(String name)
    {
        return this.filterDAO.get(name);
    }

    public List<ITuneApp> getAllFilter()
    {
        return  this.filterDAO.getAllApps();
    }



}
