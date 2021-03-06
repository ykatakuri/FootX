package com.ykatakuri.footx.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.fragment.ChatFragment;
import com.ykatakuri.footx.controller.fragment.ExploreFragment;
import com.ykatakuri.footx.controller.fragment.FootFragment;
import com.ykatakuri.footx.controller.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView navigationView;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mFirebaseAuth = FirebaseAuth.getInstance();

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new FootFragment()).commit();
        navigationView.setSelectedItemId(R.id.navbar_item_foot);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){

                    case R.id.navbar_item_foot:
                        fragment = new FootFragment();
                        break;

                    case R.id.navbar_item_chat:
                        fragment = new ChatFragment();
                        break;

                    case R.id.navbar_item_explore:
                        fragment = new ExploreFragment();
                        break;

                    case R.id.navbar_item_profile:
                        fragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}