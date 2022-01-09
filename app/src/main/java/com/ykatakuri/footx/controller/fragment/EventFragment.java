package com.ykatakuri.footx.controller.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.adapter.EventAdapter;
import com.ykatakuri.footx.model.Events;

public class EventFragment extends Fragment {

    Activity mContext;

    Button mCreateButton;

    private RecyclerView recyclerView;
    EventAdapter adapter;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mCreateButton = view.findViewById(R.id.event_button_create);

        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        recyclerView = view.findViewById(R.id.event_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Events> options = new FirebaseRecyclerOptions.Builder<Events>().setQuery(databaseReference, Events.class).build();
        adapter = new EventAdapter(options);
        recyclerView.setAdapter(adapter);

        /*

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    recyclerView = view.findViewById(R.id.event_recyclerview);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    FirebaseRecyclerOptions<Events> options = new FirebaseRecyclerOptions.Builder<Events>().setQuery(databaseReference, Events.class).build();
                    adapter = new EventAdapter(options);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase", error.getMessage());
            }
        };

        databaseReference.addListenerForSingleValueEvent(valueEventListener);
         */

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragment().getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.body_container, new FormFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}