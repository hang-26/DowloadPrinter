package com.example.dowloadprinter.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class AdappterViewPager extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

        public AdappterViewPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> ls) {
            super(fragmentManager, lifecycle);
            this.fragmentList = ls;
        }
    public AdappterViewPager(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public List<Fragment> getFragmentList(){
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> list){
        this.fragmentList = list;
    }


}
