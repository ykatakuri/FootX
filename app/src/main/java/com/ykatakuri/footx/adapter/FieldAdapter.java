package com.ykatakuri.footx.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.model.Field;

public class FieldAdapter extends FirebaseRecyclerAdapter<Field, FieldsViewHolder> {

    public FieldAdapter(@NonNull FirebaseRecyclerOptions<Field> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FieldsViewHolder holder, int position, @NonNull Field model) {
        holder.mNameTextView.setText(model.getName());
        holder.mDescriptionTextView.setText(model.getDescription());
    }

    @NonNull
    @Override
    public FieldsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.field, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(v.getContext(), FormActivity.class);
                //v.getContext().startActivity(intent);
            }
        });

        return new FieldsViewHolder(view);
    }
}
