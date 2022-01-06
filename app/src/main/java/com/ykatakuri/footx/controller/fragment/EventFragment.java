package com.ykatakuri.footx.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.activity.FieldActivity;

public class EventFragment extends Fragment {

    Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        return view;
    }

    public void onStart() {
        super.onStart();

        Button mCreateButton = mContext.findViewById(R.id.event_button_create);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FieldActivity.class);
                startActivity(intent);
            }
        });
    }
}