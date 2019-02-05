package com.example.camerates.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    public static final int ADD_NOTE_RQUEST_CODE = 1;
    public static final int EDIT_NOTE_RQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton add_note_btn = findViewById(R.id.button_add_note);
        add_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_RQUEST_CODE);
            }
        });
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });
        noteAdapter.setListner(new NoteAdapter.OnItemClickListner() {
            @Override
            public void onItemClickLitner(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.KEY_EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.KEY_EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.KEY_EXTRA_PERIORTY, note.getPriority());
                intent.putExtra(AddEditNoteActivity.KEY_EXTRA_ID, note.getId());
                startActivityForResult(intent, EDIT_NOTE_RQUEST_CODE);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.deleteNote(noteAdapter.getItemAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_All:
                noteViewModel.deleteAlltNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_NOTE_RQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.KEY_EXTRA_TITLE);
            String desrciption = data.getStringExtra(AddEditNoteActivity.KEY_EXTRA_DESCRIPTION);
            int periorty = data.getIntExtra(AddEditNoteActivity.KEY_EXTRA_PERIORTY, 1);
            Note note = new Note(title, desrciption, periorty);

            noteViewModel.insertNote(note);
            Toast.makeText(this, "Note Added Sucessfullty", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_RQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.KEY_EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Error Updating Note", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.KEY_EXTRA_TITLE);
            String desrciption = data.getStringExtra(AddEditNoteActivity.KEY_EXTRA_DESCRIPTION);
            int periorty = data.getIntExtra(AddEditNoteActivity.KEY_EXTRA_PERIORTY, 1);
            Note note = new Note(title, desrciption, periorty);
            note.setId(id);
            noteViewModel.updateNote(note);
            Toast.makeText(this, "Note Updated Sucessfullty", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();

        }
    }
}
