package com.ykatakuri.footx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.model.Events;

public class SuggestionAdapter extends FirebaseRecyclerAdapter<Events, SuggestionsViewHolder> {
    public SuggestionAdapter(@NonNull FirebaseRecyclerOptions<Events> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SuggestionsViewHolder holder, int position, @NonNull Events model) {
        holder.mFieldNameTextView.setText(model.getField());
        holder.mAuthorTextView.setText(model.getAuthor());
        holder.mDateTextView.setText(model.getDate());
        holder.mTimeTextView.setText(model.getTime());
        holder.mFormatTextView.setText(model.getFormat());
    }

    @NonNull
    @Override
    public SuggestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_suggestion, parent, false);
        view.findViewById(R.id.suggestion_cv_button_participate).setOnClickListener(v -> Toast.makeText(v.getContext(), "Button participate is clicked", Toast.LENGTH_SHORT).show());

        return new SuggestionsViewHolder(view);
    }
}
