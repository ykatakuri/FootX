package com.ykatakuri.footx.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.activity.EditActivity;
import com.ykatakuri.footx.controller.activity.LoginActivity;
import com.ykatakuri.footx.controller.activity.MainActivity;

public class ProfileFragment extends Fragment {

    private Button mLogoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLogoutButton = view.findViewById(R.id.fragment_profile_button_logout);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Vous êtes déconnecté...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }
}