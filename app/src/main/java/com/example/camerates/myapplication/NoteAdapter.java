package com.example.camerates.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewitem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);

        return new NoteViewHolder(viewitem);
    }

    public Note getItemAt(int position) {
        return noteList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        Note currentItem = noteList.get(i);
        noteViewHolder.textViewTitle.setText(currentItem.getTitle());
        noteViewHolder.textViewPeriorty.setText(String.valueOf(currentItem.getPriority()));
        noteViewHolder.textViewDescription.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewPeriorty;
        TextView textViewDescription;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewPeriorty = itemView.findViewById(R.id.textview_periorty);
            textViewDescription = itemView.findViewById(R.id.textview_description);
        }
    }
}
