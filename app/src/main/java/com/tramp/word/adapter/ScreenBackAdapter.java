package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.user.UserPhotoInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ScreenBackAdapter extends AbsRecyclerViewAdapter {
    private int mNumber;
    private List<UserPhotoInfo.Photo> Photo;
    public ScreenBackAdapter(RecyclerView recyclerView,int number,List<UserPhotoInfo.Photo> photo){
        super(recyclerView);
        mNumber=number;
        Photo=photo;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_screen_back,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Photo.get(position).getPhoto_url())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemScreenBack);
        if(mNumber==position){
            mHolder.ItemScreenImg.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemScreenImg.setVisibility(View.GONE);
        }
        mHolder.ItemBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemScreenImg.setVisibility(View.VISIBLE);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout ItemBack;
        ImageView ItemScreenImg;
        ImageView ItemScreenBack;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemBack=$(R.id.item_screen_relative);
            ItemScreenImg=$(R.id.item_screen_img);
            ItemScreenBack=$(R.id.item_screen_back);
        }
    }
}
