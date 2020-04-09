package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.pk.PkDataInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/09
 * version:1.0
 */

public class WordNewsViewAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<PkDataInfo.Data> datas;
    private Activity mActivity;
    private int user;
    public WordNewsViewAdapter(RecyclerView recyclerView,ArrayList<PkDataInfo.Data> data,Activity activity){
        super(recyclerView);
        datas=data;
        user=new UserSqlHelper(activity).UserId();
        mActivity=activity;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_news,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(mActivity)
                .load(datas.get(position).getData_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemAvatar);
        mHolder.ItemAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,datas.get(position).getData_id());
                mActivity.startActivity(new Intent(mActivity, FriendDetailsActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
        mHolder.ItemTitle.setText(datas.get(position).getData_name());
        mHolder.ItemNumber.setText(datas.get(position).getData_number());
        mHolder.ItemNum.setText(datas.get(position).getData_num());
        mHolder.ItemWord.setText(datas.get(position).getData_word());
        mHolder.ItemTime.setText(datas.get(position).getData_time());
        switch (datas.get(position).getData_status()){
            case 0:
                mHolder.ItemStatus.setImageResource(R.drawable.bg_card_fail);
                break;
            case 1:
                mHolder.ItemStatus.setImageResource(R.drawable.bg_card_win);
                break;
            case 2:
                mHolder.ItemStatus.setImageResource(R.drawable.bg_card_draw);
                break;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemAvatar;
        TextView ItemTitle;
        TextView ItemNumber;
        TextView ItemNum;
        TextView ItemWord;
        TextView ItemTime;
        ImageView ItemStatus;
        RelativeLayout ItemRelative;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemAvatar=$(R.id.item_word_news_img);
            ItemTitle=$(R.id.item_word_news_name);
            ItemNumber=$(R.id.item_word_news_num_left);
            ItemNum=$(R.id.item_word_news_num_right);
            ItemWord=$(R.id.item_word_news_text);
            ItemTime=$(R.id.item_word_news_time);
            ItemStatus=$(R.id.item_word_news_status);
            ItemRelative=$(R.id.item_word_news_relative);
        }
    }
}





