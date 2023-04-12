package com.example.dowloadprinter.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dowloadprinter.Fragment.molde.MyGift;
import com.example.dowloadprinter.Fragment.molde.MyMedia;
import com.example.dowloadprinter.Fragment.molde.MyPhoto;
import com.example.dowloadprinter.Fragment.molde.MyVideo;
import com.example.dowloadprinter.R;
import com.example.dowloadprinter.homelistdown.AdapterHomeList;
import com.example.dowloadprinter.retrofit.ApiHttpUrl;
import com.example.dowloadprinter.util.UtilsJson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentHome extends Fragment {
    ImageView imv_p;
    TextView tv_intro;
    Button btn_down;
    EditText edt_copy_link;
    ProgressBar prs_down;
    RecyclerView rcv_homeList;
    AdapterHomeList adapterHomeList = new AdapterHomeList();
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
        setUpAdapter();
        addListentner();
    }

    private void setUpAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_homeList.setAdapter(adapterHomeList);
        rcv_homeList.setLayoutManager(linearLayoutManager);

    }

    private void initView(View view){
        imv_p = view.findViewById(R.id.imv_p);
        btn_down = view.findViewById(R.id.btn_down);
        edt_copy_link = view.findViewById(R.id.edt_copy_link);
        prs_down = view.findViewById(R.id.prs_down);
        rcv_homeList = view.findViewById(R.id.rcv_homeList);
        tv_intro = view.findViewById(R.id.tv_intro);

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
                tv_intro.setVisibility(View.GONE);
                prs_down.setVisibility(View.VISIBLE);
                btn_down.setVisibility(View.GONE);
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
                prs_down.setVisibility(View.GONE);
                btn_down.setVisibility(View.VISIBLE);
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
            public void onComplete(String res, Throwable throwable) {
                if(res!= null && res.startsWith("{") == true && res.endsWith("}") ){
                   List<MyMedia> listMedia =  parseJsonToMedia(res);
                   if(listMedia.isEmpty()){
                       Toast.makeText(getContext(),"Error! Try again",Toast.LENGTH_SHORT).show();
                   }else {
                       showData(listMedia);
                   }

                }
            }
        });
    }

    public void showData(List<MyMedia> list){
        adapterHomeList.setMyHomeLists(list);
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

    public List<MyMedia> parseJsonToMedia(String strJson){
        String data = UtilsJson.getJsonObjectByKey(strJson,"data");
        String title_one = UtilsJson.getStringInJsonByKey(data, "grid_title");
        if(title_one== null){
            title_one =" ";
        }
        List<MyMedia> arrayList = new ArrayList<>();
        if(data !=null && data.contains("video_list")){
            //Video
            String videoList =  UtilsJson.getJsonObjectByKey(data, "video_list");
            String finalTitle_one = title_one;
            UtilsJson.getListJsonObjectChild(videoList).forEach((jsonVideo)->{
                if(UtilsJson.getStringInJsonByKey(jsonVideo,"url").contains(".mp4")){
                    MyVideo myVideo = new MyVideo();
                    myVideo.link = UtilsJson.getStringInJsonByKey(jsonVideo,"url");
                    myVideo.title = finalTitle_one + UtilsJson.getStringInJsonByKey(jsonVideo, UtilsJson.KEY_OF_OBJECT);
                    myVideo.duration = UtilsJson.getIntInJsonByKey(jsonVideo,"duration");
                    arrayList.add(myVideo);

                }

            });
        }else {
            //PHOTO
            String images = UtilsJson.getJsonObjectByKey(data,"images");
            String finalTitle_one1 = title_one;
            UtilsJson.getListJsonObjectChild(images).forEach(json->{
                String url = UtilsJson.getStringInJsonByKey(json,"url");
                if(url.endsWith(".photo")){
                    MyPhoto myPhoto = new MyPhoto();
                    myPhoto.link =url;
                    myPhoto.title = finalTitle_one1 + UtilsJson.getStringInJsonByKey(json,UtilsJson.KEY_OF_OBJECT);
                    arrayList.add(myPhoto);
                }
                else {
                    MyGift myGift = new MyGift();
                    myGift.link = url;
                    myGift.title = finalTitle_one1 + UtilsJson.getStringInJsonByKey(json,UtilsJson.KEY_OF_OBJECT);
                    arrayList.add(myGift);
                }
            });
        }

        return arrayList;
    }



}