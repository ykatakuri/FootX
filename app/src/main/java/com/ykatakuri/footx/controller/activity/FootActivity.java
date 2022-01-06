package com.ykatakuri.footx.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.controller.TabFragmentAdapter;

public class FootActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    TabLayout mTabLayout;
    ViewPager2 mPager2;
    TabFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot);

        mBottomNavigationView = findViewById(R.id.bottom_navbar);
        mBottomNavigationView.setSelectedItemId(R.id.navbar_item_foot);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navbar_item_foot:
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
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        mTabLayout = findViewById(R.id.foot_tab_layout);
        mPager2 = findViewById(R.id.foot_view_pager2);

        FragmentManager mFragmentManager = getSupportFragmentManager();
        mAdapter = new TabFragmentAdapter(mFragmentManager, getLifecycle());
        mPager2.setAdapter(mAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText("Evénements"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Suggestions"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    }
}