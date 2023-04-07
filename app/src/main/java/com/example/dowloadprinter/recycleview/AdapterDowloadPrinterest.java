package com.example.dowloadprinter.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dowloadprinter.Fragment.AdappterViewPager;
import com.example.dowloadprinter.R;

import java.util.List;

public class AdapterDowloadPrinterest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MyModel> listDownload;
    public AdapterDowloadPrinterest (List<MyModel> lsDownload){
        this.listDownload = lsDownload;
    }

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
            MyModel name = listDownload.get(position);

            myHolder.nameP.setText(name.getNameP());
            myHolder.sizeP.setText(String.valueOf(name.getSizeP()));
            myHolder.timeP.setText(name.getTimeP());
        }
        }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolderDown extends RecyclerView.ViewHolder{
        public TextView nameP;
        public TextView sizeP, timeP;
        public  TextView typePP;
        public ImageView imv_recycleview;
        public ViewHolderDown(@NonNull View itemView){
            super(itemView);
            nameP = itemView.findViewById(R.id.txt_nameP);
            sizeP = itemView.findViewById(R.id.txt_sizeP);
            timeP = itemView.findViewById(R.id.txt_timeP);
            typePP = itemView.findViewById(R.id.tv_type);
            imv_recycleview = itemView.findViewById(R.id.imv_recycleview);
        }
    }

}
