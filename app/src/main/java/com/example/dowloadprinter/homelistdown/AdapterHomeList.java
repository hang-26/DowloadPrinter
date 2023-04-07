package com.example.dowloadprinter.homelistdown;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dowloadprinter.Fragment.FragmentHome;
import com.example.dowloadprinter.Fragment.molde.MyGift;
import com.example.dowloadprinter.Fragment.molde.MyMedia;
import com.example.dowloadprinter.Fragment.molde.MyPhoto;
import com.example.dowloadprinter.Fragment.molde.MyVideo;
import com.example.dowloadprinter.R;
import com.example.dowloadprinter.recycleview.AdapterDowloadPrinterest;
import com.example.dowloadprinter.recycleview.MyModel;

import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class AdapterHomeList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    private List<MyMedia> myHomeLists = new ArrayList<>();
    public AdapterHomeList(){
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View pdownload = inflater.inflate(R.layout.home_list_fragment,parent, false);
        ViewHolderHome viewHolderHome = new ViewHolderHome(pdownload);
        return viewHolderHome;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderHome){
            ViewHolderHome viewP = (ViewHolderHome) holder;
            MyMedia myList = myHomeLists.get(position);
            viewP.tv_size.setText(myList.title);
            if(myList instanceof MyVideo){
                viewP.tv_type.setText("VIDEO");
            }
            if(myList instanceof MyPhoto){
                viewP.tv_type.setText("PHOTO");
            }
            if (myList instanceof MyGift){
                viewP.tv_type.setText("GIFT");
            }
           String unEscapeLink= StringEscapeUtils.unescapeJava(myList.link);
            Glide.with(viewP.imv_homeList.getContext()).load(unEscapeLink).into(viewP.imv_homeList);

            viewP.imv_btndown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(viewP.imv_btndown.getContext(),myList.title,Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return myHomeLists.size();
    }

    public static class ViewHolderHome extends RecyclerView.ViewHolder{
        public TextView tv_size;
        public TextView tv_type;
        public ImageView imv_homeList;
        public ImageView imv_btndown;
        public ViewHolderHome(@NonNull View itemView) {
            super(itemView);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_type = itemView.findViewById(R.id.tv_type);
            imv_homeList = itemView.findViewById(R.id.imv_homeList);
            imv_btndown = itemView.findViewById(R.id.imv_btndown);
        }
    }

    public void setMyHomeLists(List<MyMedia> myLists) {
        myHomeLists.clear();
        myHomeLists.addAll(myLists);
        notifyDataSetChanged();
    }

}


