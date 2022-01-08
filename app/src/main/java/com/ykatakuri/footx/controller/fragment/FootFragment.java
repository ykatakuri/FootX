package com.ykatakuri.footx.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ykatakuri.footx.R;
import com.ykatakuri.footx.adapter.TabFragmentAdapter;

public class FootFragment extends Fragment {

    TabLayout mTabLayout;
    ViewPager2 mViewPager;
    TabFragmentAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot, container, false);

        mTabLayout = view.findViewById(R.id.foot_tab_layout);
        mViewPager = view.findViewById(R.id.foot_view_pager2);

        FragmentManager fragmentManager = getChildFragmentManager();
        mAdapter = new TabFragmentAdapter(fragmentManager, getLifecycle());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText("Evénements"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Suggestions"));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });

        return view;
    }
}