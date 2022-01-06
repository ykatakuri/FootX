package com.ykatakuri.footx.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ykatakuri.footx.controller.fragment.EventFragment;
import com.ykatakuri.footx.controller.fragment.SuggestionFragment;

public class TabFragmentAdapter extends FragmentStateAdapter {

    public TabFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1 :
                return new SuggestionFragment();
        }

        return new EventFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
