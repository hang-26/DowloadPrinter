package com.example.dowloadprinter.recycleview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dowloadprinter.Fragment.molde.MyMedia;
import com.example.dowloadprinter.R;

import org.apache.commons.text.StringEscapeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterDowloadPrinterest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyMedia> listDownload = new ArrayList<>();
    public AdapterDowloadPrinterest (){}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View linkDownload = inflater.inflate(R.layout.listdowload_file,parent, false);
        ViewHolderDown viewHolderDown = new ViewHolderDown(linkDownload);
        return viewHolderDown;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderDown) {
            ViewHolderDown myHolder = (ViewHolderDown) holder;
            MyMedia name = listDownload.get(position);

           myHolder.nameP.setText(name.title);
           if(name.link.endsWith("jpg")){
               myHolder.ic_play.setVisibility(View.GONE);
           }
           if(name.link.endsWith("gif")){
               myHolder.ic_play.setVisibility(View.GONE);
           }
          // if(name.link.endsWith("mp4"))
            String unEscapeLink= StringEscapeUtils.unescapeJava(name.link);
            Glide.with(myHolder.imv_recycleview.getContext()).load(unEscapeLink).into(myHolder.imv_recycleview);
            DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd"); // Format date
            String date = df1.format(Calendar.getInstance().getTime());
            myHolder.timeP.setText(date);

            myHolder.btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String body = "Your body here";
                    String sub = "Your Subject";
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                    myIntent.putExtra(Intent.EXTRA_TEXT,body);
                    //startActivity(Intent.createChooser(myIntent, "Share Using"));
                    myHolder.btn_share.getContext().startActivity(myIntent);
                }
            });

        }
        }

    @Override
    public int getItemCount() {
        return listDownload.size();
    }

    public static class ViewHolderDown extends RecyclerView.ViewHolder{
        public TextView nameP;
        public TextView  timeP;
        public  TextView typePP;
        public ImageView imv_recycleview;
        public ImageView ic_play;
        public Button btn_share;
        public ViewHolderDown(@NonNull View itemView){
            super(itemView);
            nameP = itemView.findViewById(R.id.txt_nameP);
            timeP = itemView.findViewById(R.id.txt_timeP);
            typePP = itemView.findViewById(R.id.tv_type);
            imv_recycleview = itemView.findViewById(R.id.imv_recycleview);
            ic_play = itemView.findViewById(R.id.ic_play);
            btn_share = itemView.findViewById(R.id.btn_share);
        }
    }

    public void setListDownload(List<MyMedia> list){
        listDownload.addAll(list);
        notifyDataSetChanged();
    }
}
