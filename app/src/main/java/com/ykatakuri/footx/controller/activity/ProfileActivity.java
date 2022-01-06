package com.ykatakuri.footx.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ykatakuri.footx.R;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mBottomNavigationView = findViewById(R.id.bottom_navbar);
        mBottomNavigationView.setSelectedItemId(R.id.navbar_item_profile);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navbar_item_foot:
                        startActivity(new Intent(getApplicationContext(), FootActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navbar_item_chat:
                        startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navbar_item_explore:
                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.navbar_item_profile:
                        return true;
                }

                return false;
            }
        });
    }
}