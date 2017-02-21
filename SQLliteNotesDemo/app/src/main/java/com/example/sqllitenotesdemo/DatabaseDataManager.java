package com.example.sqllitenotesdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by sid on 16-10-2016.
 */

public class DatabaseDataManager {

    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NotesDAO notesDAO;

    public DatabaseDataManager(Context mContext){
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        notesDAO = new NotesDAO(db);
    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public NotesDAO getNotesDAO(){
        return this.notesDAO;
    }

    public long saveNote(Note note){
        return this.notesDAO.save(note);
    }

    public boolean updateNote(Note note){
        return this.notesDAO.update(note);
    }

    public boolean deleteNote(Note note){
        return this.notesDAO.delete(note);
    }

    public Note getNote(long id){
        return this.notesDAO.get(id);
    }
    public List<Note> getAllNotes(){
        return this.notesDAO.getAll();
    }


}
