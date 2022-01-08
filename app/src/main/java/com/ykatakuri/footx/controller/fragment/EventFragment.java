package com.ykatakuri.footx.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.activity.FieldActivity;

public class EventFragment extends Fragment {

    Activity mContext;

    Button mCreateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        mCreateButton = view.findViewById(R.id.event_button_create);

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
}