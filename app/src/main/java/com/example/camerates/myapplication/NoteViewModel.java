package com.example.camerates.myapplication;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> noteList;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository=new NoteRepository(application);
        noteList=noteRepository.getAllNotes();
    }
    public void insertNote(Note note){
        noteRepository.insert(note);
    }

    public void updateNote(Note note){
        noteRepository.update(note);
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }

    public void deleteAlltNotes( ){
        noteRepository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes(){
        return noteList;
    }

}
