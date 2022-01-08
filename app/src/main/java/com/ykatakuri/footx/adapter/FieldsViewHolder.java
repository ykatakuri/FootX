package com.ykatakuri.footx.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykatakuri.footx.R;

public class FieldsViewHolder extends RecyclerView.ViewHolder {
    TextView mNameTextView, mDescriptionTextView, mImageTextView, mAddressTextView;

    public FieldsViewHolder(@NonNull View itemView) {
        super(itemView);

        mNameTextView = itemView.findViewById(R.id.field_textview_name);
        mDescriptionTextView = itemView.findViewById(R.id.field_textview_description);

    }
}
