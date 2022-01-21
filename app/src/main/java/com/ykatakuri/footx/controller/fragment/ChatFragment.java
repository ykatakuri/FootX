package com.ykatakuri.footx.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.activity.ChatActivity;
import com.ykatakuri.footx.controller.activity.EditActivity;
import com.ykatakuri.footx.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    private FloatingActionButton mStartChatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        mStartChatButton = view.findViewById(R.id.chat_floatingactionbutton_chat);

        mStartChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}