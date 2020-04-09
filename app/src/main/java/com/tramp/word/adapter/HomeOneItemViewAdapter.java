package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.main.HomeFindInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/11
 * version:1.0
 */

public class HomeOneItemViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<HomeFindInfo.find.word> Words;
    public HomeOneItemViewAdapter(RecyclerView recyclerView,ArrayList<HomeFindInfo.find.word> words){
        super(recyclerView);
        Words=words;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_find,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Words.get(position).getWord_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        mHolder.ItemName.setText(Words.get(position).getWord_name());
        mHolder.ItemTitle.setText(Words.get(position).getWord_number()+"人背");
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Words.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout ItemHomeFindRelative;
        ImageView ItemImg;
        TextView ItemName;
        TextView ItemTitle;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemHomeFindRelative=$(R.id.item_home_find_relative);
            ItemImg=$(R.id.item_home_find_pic);
            ItemName=$(R.id.item_home_find_text);
            ItemTitle=$(R.id.item_home_find_number);
        }
    }
}
