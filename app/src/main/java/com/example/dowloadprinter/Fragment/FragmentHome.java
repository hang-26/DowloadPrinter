package com.example.dowloadprinter.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dowloadprinter.R;
import com.example.dowloadprinter.retrofit.ApiHttpUrl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentHome extends Fragment {
    ImageView imv_p;
    Button btn_down;
    EditText edt_copy_link;
    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        addListentner();
    }
    private void initView(View view){
        imv_p = view.findViewById(R.id.imv_p);
        btn_down = view.findViewById(R.id.btn_down);
        edt_copy_link = view.findViewById(R.id.edt_copy_link);
    }

    private  void addListentner(){
        imv_p.setOnClickListener(new View.OnClickListener() {
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

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlLink = edt_copy_link.getText().toString();
                getLink(urlLink);
            }
        });
    }

    private void getLink(String url){
        getRealLink(url);
    }

    private  void getRealLink(String url){
        ApiHttpUrl.getRealUrlAffterRender(url, new ApiHttpUrl.ItfApiHttpUrl() {
            @Override
            public void onComplete(String realUrl, Throwable throwable) {
                renderLinkData(realUrl);
                String linkData = renderLinkData(realUrl);
                if(linkData == null){
                    // Thong bao error
                }else {
                    urlMedia(linkData);
                }

            }
        });
    }

    private  void urlMedia(String linkMedia) {

        ApiHttpUrl.getApi(linkMedia, new ApiHttpUrl.ItfApiBody() {
            @Override
            public void onComplete(String body, Throwable throwable) {

            }
        });
    }

    private  String renderLinkData(String realLink){
        String pinId = getRegexBetween(realLink,"pin/","/");
        if(pinId ==null){
            return null;
        }
        else {
            return "https://www.pinterest.com/resource/PinResource/get/?data={\"options\":{\"id\":\"" + pinId + "\",\"field_set_key\":\"unauth_react_main_pin\"}}";
        }

    }

    private static  String getRegexBetween(String strFull, String keyStart, String keyEnd){
        String REGEX_SPLIT_IMG_URL = keyStart + ".*?"+keyEnd;
        Pattern pattern2 = Pattern.compile(REGEX_SPLIT_IMG_URL);
        if(strFull == null){return null;}
        Matcher m2 = pattern2.matcher(strFull);
        if( m2.find()){
            return m2.group().replace(keyStart,"").replace(keyEnd,"");
        }
        else {
            return null;
        }
    }


}