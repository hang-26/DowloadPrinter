package com.example.dowloadprinter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dowloadprinter.Fragment.FragmentFileDowload;
import com.example.dowloadprinter.Fragment.FragmentHome;
import com.example.dowloadprinter.Fragment.FragmentSystem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 vw_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setAdapterPager();
    }

    private void initView() {
        vw_main = findViewById(R.id.vw_main);
    }

    private void setAdapterPager() {
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), getLifecycle());
        List<Fragment> ls = new ArrayList<>();
        ls.add(new FragmentHome());
        ls.add(new FragmentFileDowload());
        ls.add(new FragmentSystem());
        adapter.setListFragment(ls);
        vw_main.setAdapter(adapter);
    }
}