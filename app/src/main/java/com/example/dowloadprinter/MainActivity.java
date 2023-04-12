package com.example.dowloadprinter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dowloadprinter.Fragment.AdappterViewPager;
import com.example.dowloadprinter.Fragment.FragmentFileDowload;
import com.example.dowloadprinter.Fragment.FragmentHome;
import com.example.dowloadprinter.Fragment.FragmentSystem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager2 vw_main;
    LinearLayout home_tab;
    LinearLayout download_tab;
    LinearLayout system_tab;
    TextView tv_home;
    TextView tv_download;
    TextView tv_system;
    ImageView imv_home;
    ImageView imv_download;
    ImageView imv_system;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListentner();
        FragmentHome fragmentHome = new FragmentHome();
        FragmentFileDowload fragmentFileDowload = new FragmentFileDowload();
        FragmentSystem fragmentSystem = new FragmentSystem();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentFileDowload);
        fragmentList.add(fragmentSystem);
        //set view pager
        AdappterViewPager adappterViewPager = new AdappterViewPager(getSupportFragmentManager(),getLifecycle(),fragmentList);
        vw_main.setAdapter(adappterViewPager);
        checkPermissionStorage();


    }
    private void initView(){
        vw_main = findViewById(R.id.vw_main);
        home_tab = findViewById(R.id.home_tab);
        download_tab = findViewById(R.id.download_tab);
        system_tab = findViewById(R.id.system_tab);
        tv_home = findViewById(R.id.tv_home);
        tv_download = findViewById(R.id.tv_download);
        tv_system = findViewById(R.id.tv_system);
        imv_home = findViewById(R.id.imv_home);
        imv_download = findViewById(R.id.imv_download);
        imv_system = findViewById(R.id.imv_system);
    }


    private void addListentner(){
        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(0);
                home_tab.setBackgroundColor(getColor(R.color.gray));
                download_tab.setBackgroundColor(getColor(R.color.white));
                system_tab.setBackgroundColor(getColor(R.color.white));
            }
        });

        download_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(1);
                download_tab.setBackgroundColor(getColor(R.color.gray));
                home_tab.setBackgroundColor(getColor(R.color.white));
                system_tab.setBackgroundColor(getColor(R.color.white));
            }
        });

        system_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vw_main.setCurrentItem(2);
                system_tab.setBackgroundColor(getColor(R.color.gray));
                home_tab.setBackgroundColor(getColor(R.color.white));
                download_tab.setBackgroundColor(getColor(R.color.white));
            }
        });


    }

    private void checkPermissionStorage(){
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
        }else {
            Log.v(TAG,"Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

}