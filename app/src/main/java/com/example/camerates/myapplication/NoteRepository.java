package com.example.camerates.myapplication;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){

        //Create database and automaticlly room will instinate note Dao
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.getNoteDao();
        //get all notes cuase we use liveData it'll do database opreation in another thread
        allNotes=noteDao.getAllNotes();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public  void insert(Note note){
            new InsertNote(noteDao).execute(note);
    }
    public  void update(Note note){

        new UpdateNote(noteDao).execute(note);
    }
    public  void delete(Note note){

        new DeleteNote(noteDao).execute(note);
    }
    public  void deleteAllNotes(){

        new DeleteAllNotes(noteDao).execute();
    }

    private static class InsertNote extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public InsertNote(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNote extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public UpdateNote(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }


    private static class DeleteNote extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public DeleteNote(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotes extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        public DeleteAllNotes(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
