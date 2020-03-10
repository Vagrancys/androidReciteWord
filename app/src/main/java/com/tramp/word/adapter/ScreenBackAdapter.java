package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ScreenBackAdapter extends AbsRecyclerViewAdapter {
    private int mNumber;
    public ScreenBackAdapter(RecyclerView recyclerView,int number){
        super(recyclerView);
        mNumber=number;
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
        if(mNumber==position){
            mHolder.ItemScreenImg.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemScreenImg.setVisibility(View.GONE);
        }
        mHolder.ItemScreenBack.setOnClickListener(new View.OnClickListener() {
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
        RelativeLayout ItemScreenBack;
        ImageView ItemScreenImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemScreenBack=$(R.id.item_screen_relative);
            ItemScreenImg=$(R.id.item_screen_img);
        }
    }
}
