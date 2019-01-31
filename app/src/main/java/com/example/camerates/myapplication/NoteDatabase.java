package com.example.camerates.myapplication;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

//anotiate this class as db with version number and list of entities
@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //singletone instance of db
    private static NoteDatabase instance;

    public abstract NoteDao getNoteDao();

    //synchronized so that one thread can access the db object at a time incase of multithreading
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance==null){
            //use of database bulider cause our class is abstract and can't be instantiated
            instance= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class
                    ,"notes_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitNotesDb(instance).execute();

        }
    };

    private static class InitNotesDb extends AsyncTask<Void,Void,Void> {

        private NoteDao noteDao;

        public InitNotesDb(NoteDatabase noteDatabase){
            this.noteDao=noteDatabase.getNoteDao();
        }
        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.insertNote(new Note("Title 1","Descriotn1",1));
            noteDao.insertNote(new Note("Title 2","Descriotn2",2));
            noteDao.insertNote(new Note("Title 3","Descriotn3",3));
            return null;
        }
    }
}
