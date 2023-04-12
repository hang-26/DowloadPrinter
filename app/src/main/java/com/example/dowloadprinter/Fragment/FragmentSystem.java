package com.example.dowloadprinter.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dowloadprinter.R;

public class FragmentSystem extends Fragment {
    ImageView btn_ic_p;
    LinearLayout rate_linear;
    LinearLayout share_linear;
    LinearLayout more_linear;
    public FragmentSystem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        addListener();
    }

    private void initView(View view){
        btn_ic_p = view.findViewById(R.id.btn_ic_p);
        rate_linear = view.findViewById(R.id.rate_linear);
        share_linear = view.findViewById(R.id.share_linear);
        more_linear = view.findViewById(R.id.more_linear);
    }

    private void addListener(){
        btn_ic_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lauchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.pinterest");
                if(lauchIntent!=null){
                    startActivity(lauchIntent);
                }
                else{
                    Toast.makeText(getContext(), "App haven't been dowload", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rate_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = "com.pinterest";
                Intent launchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName));
                Toast.makeText(getContext(), " ", Toast.LENGTH_LONG).show();
                startActivity(launchIntent);
            }
        });

        share_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "Your body here";
                String sub = "Your Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                //startActivity(Intent.createChooser(myIntent, "Share Using"));
                share_linear.getContext().startActivity(myIntent);
            }
        });

        more_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com"));
                startActivity(intent);
            }
        });
    }
}