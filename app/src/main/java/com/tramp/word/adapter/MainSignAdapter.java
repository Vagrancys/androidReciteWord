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

public class MainSignAdapter extends AbsRecyclerViewAdapter {
    private int mDay;
    private int[] mNum={
            R.drawable.checkin_img_x_3,R.drawable.checkin_img_x_5,
            R.drawable.checkin_img_x_6,R.drawable.checkin_img_x_10,
            R.drawable.checkin_img_x_8,R.drawable.checkin_img_x_7
    };
    private int[] mNumUn={
            R.drawable.checkin_img_x_3_inactive,R.drawable.checkin_img_x_5_inactive,
            R.drawable.checkin_img_x_6_inactive,R.drawable.checkin_img_x_10_inactive,
            R.drawable.checkin_img_x_8_inactive,R.drawable.checkin_img_x_7_inactive
    };
    private String[] title={
            "第一天","第2天","第三天","第十天+","第5-9天","第4天"
    };
    public MainSignAdapter(RecyclerView recyclerView,int day){
        super(recyclerView);
        mDay=day;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_main_sign,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        //position=4 mDay=5;
        mHolder.mItemSignNum.setImageResource(mNumUn[position]);
        mHolder.mItemSignImg.setImageResource(R.drawable.icon_coin_gray);
        mHolder.mItemSignTitle.setTextColor(getContext().getResources().getColor(R.color.black_1));
        if(position<=mDay&&mDay<=3){
            mHolder.mItemSignNum.setImageResource(mNum[position]);
            mHolder.mItemSignImg.setImageResource(R.drawable.checkin_icon_coin);
            mHolder.mItemSignTitle.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        if(position==4&&mDay>5){
            mHolder.mItemSignNum.setImageResource(mNum[position]);
            mHolder.mItemSignImg.setImageResource(R.drawable.checkin_icon_coin);
            mHolder.mItemSignTitle.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        if(position==5&&mDay>4){
            mHolder.mItemSignNum.setImageResource(mNum[position]);
            mHolder.mItemSignImg.setImageResource(R.drawable.checkin_icon_coin);
            mHolder.mItemSignTitle.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        if(position==6&&mDay>3){
            mHolder.mItemSignNum.setImageResource(mNum[position]);
            mHolder.mItemSignImg.setImageResource(R.drawable.checkin_icon_coin);
            mHolder.mItemSignTitle.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView mItemSignImg;
        ImageView mItemSignNum;
        TextView mItemSignTitle;
        public ItemViewHolder(View itemView){
            super(itemView);
            mItemSignImg=$(R.id.item_sign_img);
            mItemSignNum=$(R.id.item_sign_num);
            mItemSignTitle=$(R.id.item_sign_title);
        }
    }
}





