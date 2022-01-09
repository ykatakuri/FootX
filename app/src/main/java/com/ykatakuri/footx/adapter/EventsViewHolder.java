package com.ykatakuri.footx.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykatakuri.footx.R;

public class EventsViewHolder extends RecyclerView.ViewHolder {
    TextView mFieldNameTextView, mAuthorTextView, mDateTextView, mTimeTextView, mFormatTextView;

    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);

        mFieldNameTextView = itemView.findViewById(R.id.event_cv_textview_field_name);
        mAuthorTextView = itemView.findViewById(R.id.event_cv_textview_author);
        mDateTextView = itemView.findViewById(R.id.event_cv_textview_date);
        mTimeTextView = itemView.findViewById(R.id.event_cv_textview_time);
        mFormatTextView = itemView.findViewById(R.id.event_cv_textview_format);

    }
}
