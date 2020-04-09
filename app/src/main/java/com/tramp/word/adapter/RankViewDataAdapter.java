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

import com.tramp.word.R;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/12
 * version:1.0
 */

public class RankViewDataAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    public RankViewDataAdapter(RecyclerView recyclerView,Activity activity){
        super(recyclerView);
        mActivity=activity;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_rank_data,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(getItemCount()-1==position){
            mHolder.ItemRankDataRelative.setVisibility(View.GONE);
            mHolder.ItemRankDataText.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemRankDataRelative.setVisibility(View.VISIBLE);
            mHolder.ItemRankDataText.setVisibility(View.GONE);
        }
        mHolder.ItemRankDataAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, FriendDetailsActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 10+1;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        @BindView(R.id.item_rank_data_relative)
        RelativeLayout ItemRankDataRelative;
        @BindView(R.id.item_rank_data_text)
        TextView ItemRankDataText;
        @BindView(R.id.item_ranK_data_avatar)
        ImageView ItemRankDataAvatar;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
