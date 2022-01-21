package com.ykatakuri.footx.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykatakuri.footx.R;
import com.ykatakuri.footx.model.Events;

import java.util.ArrayList;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Events> eventsArrayList;
    private Context context;
    private EventClickInterface eventClickInterface;
    int lastPos = -1;

    public EventRecyclerViewAdapter(ArrayList<Events> eventsArrayList, Context context, EventClickInterface eventClickInterface) {
        this.eventsArrayList = eventsArrayList;
        this.context = context;
        this.eventClickInterface = eventClickInterface;
    }

    @NonNull
    @Override
    public EventRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        Events event = eventsArrayList.get(position);
        holder.mFieldNameTextView.setText(event.getField());
        holder.mDateTextView.setText(event.getDate() +" à "+event.getTime());

        setAnimation(holder.itemView, position);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventClickInterface.onEventClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mFieldNameTextView, mAuthorTextView, mDateTextView, mTimeTextView, mFormatTextView;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.event_recyclerview_imageview);
            mFieldNameTextView = itemView.findViewById(R.id.event_recyclerview_fieldname);
            mDateTextView = itemView.findViewById(R.id.event_recyclerview_date_time);
        }
    }

    public interface EventClickInterface {
        void onEventClick(int position);
    }
}
