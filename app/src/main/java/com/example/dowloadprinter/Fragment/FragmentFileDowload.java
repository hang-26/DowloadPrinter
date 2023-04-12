package com.example.dowloadprinter.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dowloadprinter.Fragment.molde.MyMedia;
import com.example.dowloadprinter.R;
import com.example.dowloadprinter.recycleview.AdapterDowloadPrinterest;
import com.example.dowloadprinter.util.DownloadUtil;
import com.example.dowloadprinter.util.FileFolderStoreHelper;

import java.util.ArrayList;
import java.util.List;


public class FragmentFileDowload extends Fragment {

    RecyclerView imv_recycleviewDown;
    AdapterDowloadPrinterest adapterDowloadPrinterest = new AdapterDowloadPrinterest();
   // List<MyMedia> listDown= new ArrayList<>();
    public FragmentFileDowload() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_dowload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        List<MyMedia> listDownloadVideo= FileFolderStoreHelper.getVideoFolder(getContext(), DownloadUtil.getFolderSaveStorage().getPath());
        List<MyMedia> listDownloadImage = FileFolderStoreHelper.getImageFolder(getContext(), DownloadUtil.getFolderSaveStorage().getPath());
        setupAdapter();
        List<MyMedia> lisDown = new ArrayList<>();
        lisDown.addAll(listDownloadVideo);
        lisDown.addAll(listDownloadImage);
        showListDownload(lisDown);

    }



    private void initView(View view){
        imv_recycleviewDown = view.findViewById(R.id.imv_recycleviewDown);
    }

    private void setupAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        imv_recycleviewDown.setAdapter(adapterDowloadPrinterest);
        imv_recycleviewDown.setLayoutManager(linearLayoutManager);

    }

    public void showListDownload(List<MyMedia> list){
        adapterDowloadPrinterest.setListDownload(list);
    }
}