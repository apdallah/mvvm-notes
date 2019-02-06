package com.example.camerates.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {

    private OnItemClickListner listner;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getTitle().equals(t1.getTitle())
                    && note.getDescription().equals(t1.getDescription())
                    && note.getPriority() == t1.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewitem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.note_item, viewGroup, false);

        return new NoteViewHolder(viewitem);
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        Note currentItem = getItem(i);
        noteViewHolder.textViewTitle.setText(currentItem.getTitle());
        noteViewHolder.textViewPeriorty.setText(String.valueOf(currentItem.getPriority()));
        noteViewHolder.textViewDescription.setText(currentItem.getDescription());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClickLitner(getItem(position));
                    }
                }
            });
        }
    }

    public void setListner(OnItemClickListner listner) {
        this.listner = listner;
    }

    public interface OnItemClickListner {
        void onItemClickLitner(Note note);

    }
}
