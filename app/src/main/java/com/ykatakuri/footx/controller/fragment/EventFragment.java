package com.ykatakuri.footx.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.adapter.EventRecyclerViewAdapter;
import com.ykatakuri.footx.controller.activity.CreateActivity;
import com.ykatakuri.footx.controller.activity.EditActivity;
import com.ykatakuri.footx.model.Events;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    private FloatingActionButton mCreateEventFloatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mProgressBar;
    private ArrayList<Events> mEventsArrayList;
    private EventRecyclerViewAdapter eventRecyclerViewAdapter;
    private RelativeLayout mEventRelativeLayoutMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mRecyclerView = view.findViewById(R.id.event_recyclerview);
        mEventRelativeLayoutMain = view.findViewById(R.id.event_relative_layout_main);
        mProgressBar = view.findViewById(R.id.event_progressbar);
        mCreateEventFloatingActionButton = view.findViewById(R.id.event_floatingactionbutton_create);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mEventsArrayList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("Events");

        eventRecyclerViewAdapter = new EventRecyclerViewAdapter(mEventsArrayList, getContext(), this::onEventClick);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(eventRecyclerViewAdapter);

        mCreateEventFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

        getEvents();

        return view;
    }

    private void getEvents() {
        mEventsArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mProgressBar.setVisibility(View.GONE);
                mEventsArrayList.add(snapshot.getValue(Events.class));
                eventRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mProgressBar.setVisibility(View.GONE);
                eventRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                eventRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                eventRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onEventClick(int position) {
        displayBottomSheet(mEventsArrayList.get(position));
    }

    private void displayBottomSheet(Events event) {
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this.getContext(), R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(this.getContext()).inflate(R.layout.bottom_sheet_layout, mEventRelativeLayoutMain, false);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();

        TextView field = layout.findViewById(R.id.bottom_sheet_field_name);
        TextView date = layout.findViewById(R.id.bottom_sheet_date);
        TextView time = layout.findViewById(R.id.bottom_sheet_time);
        TextView format = layout.findViewById(R.id.bottom_sheet_format);
        ImageView imageView = layout.findViewById(R.id.bottom_sheet_imageview);

        field.setText(event.getField());
        date.setText(event.getDate());
        time.setText(event.getTime());
        format.setText(event.getFormat());

        Button editButton = layout.findViewById(R.id.bottom_sheet_button_edit);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });

    }
}