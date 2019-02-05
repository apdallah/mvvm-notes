package com.example.camerates.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descriptionEditText;
    NumberPicker periorityNumberPicker;
    public static final String KEY_EXTRA_TITLE="com.example.camerates.myapplication.note_title";
    public static final String KEY_EXTRA_DESCRIPTION="com.example.camerates.myapplication.note_description";
    public static final String KEY_EXTRA_PERIORTY="com.example.camerates.myapplication.note_periorty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        titleEditText = findViewById(R.id.edit_text_title);
        descriptionEditText = findViewById(R.id.edit_text_descripion);
        periorityNumberPicker = findViewById(R.id.number_picker_periorty);

        periorityNumberPicker.setMinValue(1);
        periorityNumberPicker.setMaxValue(10);

    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        int periorty = periorityNumberPicker.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert note title and description", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent data=new Intent();
            data.putExtra(KEY_EXTRA_TITLE,title);
            data.putExtra(KEY_EXTRA_DESCRIPTION,description);
            data.putExtra(KEY_EXTRA_PERIORTY,periorty);
            setResult(RESULT_OK,data);
            finish();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
