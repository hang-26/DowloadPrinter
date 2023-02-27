package com.example.dowloadprinter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dowloadprinter.Fragment.FragmentFileDowload;
import com.example.dowloadprinter.Fragment.FragmentHome;
import com.example.dowloadprinter.Fragment.FragmentSystem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout home_tab;
    LinearLayout download_tab;
    LinearLayout system_tab;
    ViewPager2 vw_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupViewPgae();
        addListener();
    }
    private  void initView(){
        home_tab = findViewById(R.id.home_tab);
        download_tab = findViewById(R.id.download_tab);
        system_tab = findViewById(R.id.system_tab);
        vw_main = findViewById(R.id.vw_main);
    }

    private void setupViewPgae(){
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), getLifecycle());
        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentHome());
        list.add(new FragmentFileDowload());
        list.add(new FragmentSystem());
        mainPagerAdapter.setListFragment(list);
        vw_main.setAdapter(mainPagerAdapter);

    }

    private  void addListener(){
        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(0);

            }
        });
        download_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(1);
            }
        });
        system_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(2);
            }
        });
    }

}