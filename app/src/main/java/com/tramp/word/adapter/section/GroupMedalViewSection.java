package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.module.group.GroupMedalDetailsActivity;
import com.tramp.word.module.home.recite.ReciteWordContextActivity;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/25.
 */

public class GroupMedalViewSection extends StatelessSection {
    private Context mContext;
    private Activity mActivity;

    public GroupMedalViewSection(Context context, Activity activity){
        super(R.layout.item_group_medal_head,R.layout.item_group_medal);
        this.mContext=context;
        this.mActivity=activity;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mItemGroupMedalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, GroupMedalDetailsActivity.class));
                mActivity.overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return 3;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_group_medal_linear)
        LinearLayout mItemGroupMedalLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}