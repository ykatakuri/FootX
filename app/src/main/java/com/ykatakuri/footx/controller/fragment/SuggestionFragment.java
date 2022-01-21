package com.ykatakuri.footx.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.adapter.SuggestionRecyclerViewAdapter;
import com.ykatakuri.footx.controller.activity.EditActivity;
import com.ykatakuri.footx.controller.activity.MainActivity;
import com.ykatakuri.footx.model.Events;

import java.util.ArrayList;

public class SuggestionFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView mRecyclerView;
    private FirebaseAuth firebaseAuth;
    private ProgressBar mProgressBar;
    private ArrayList<Events> mEventsArrayList;
    private SuggestionRecyclerViewAdapter suggestionRecyclerViewAdapter;
    private RelativeLayout mSuggestionRelativeLayoutMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);

        mRecyclerView = view.findViewById(R.id.suggestion_recyclerview);
        mSuggestionRelativeLayoutMain = view.findViewById(R.id.suggestion_relative_layout_main);
        mProgressBar = view.findViewById(R.id.suggestion_progressbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mEventsArrayList = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("Events");

        suggestionRecyclerViewAdapter = new SuggestionRecyclerViewAdapter(mEventsArrayList, getContext(), this::onEventClick);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(suggestionRecyclerViewAdapter);

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
                suggestionRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mProgressBar.setVisibility(View.GONE);
                suggestionRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                suggestionRecyclerViewAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                suggestionRecyclerViewAdapter.notifyDataSetChanged();
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
        View layout = LayoutInflater.from(this.getContext()).inflate(R.layout.bottom_sheet_layout_suggestion, mSuggestionRelativeLayoutMain, false);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.show();

        TextView field = layout.findViewById(R.id.bottom_sheet_suggestion_field_name);
        TextView date = layout.findViewById(R.id.bottom_sheet_suggestion_date);
        TextView time = layout.findViewById(R.id.bottom_sheet_suggestion_time);
        TextView format = layout.findViewById(R.id.bottom_sheet_suggestion_format);
        ImageView imageView = layout.findViewById(R.id.bottom_sheet_suggestion_imageview);

        field.setText(event.getField());
        date.setText(event.getDate());
        time.setText(event.getTime());
        format.setText(event.getFormat());

        Button participateButton = layout.findViewById(R.id.bottom_sheet_suggestion_button_participate);

        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Participation acceptée...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}