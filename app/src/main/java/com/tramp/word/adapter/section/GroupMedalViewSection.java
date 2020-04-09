package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupMedalInfo;
import com.tramp.word.module.group.GroupMedalDetailsActivity;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/25.
 */

public class GroupMedalViewSection extends StatelessSection {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<GroupMedalInfo.Medal.Item> Lists;
    private int Number;
    private int Group_id;
    public GroupMedalViewSection(Context context, Activity activity,ArrayList<GroupMedalInfo.Medal.Item> ones,int number,int group_id){
        super(R.layout.item_group_medal_head,R.layout.item_group_medal);
        this.mContext=context;
        this.mActivity=activity;
        Lists=ones;
        Number=number;
        Group_id=group_id;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mItemGroupMedalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMedalDetailsActivity.launch(mActivity,Group_id,Lists.get(position).getMedal_id());
            }
        });
        Glide.with(mContext)
                .load(Lists.get(position).getMedal_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.MedalImg);
        mHolder.MedalText.setText(Lists.get(position).getMedal_name());
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return Lists.size();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        switch (Number){
            case 1:
                mHeader.MedalTitle.setText(R.string.item_group_medal_1);
                break;
            case 2:
                mHeader.MedalTitle.setText(R.string.item_group_medal_2);
                break;
            case 3:
                mHeader.MedalTitle.setText(R.string.item_group_medal_3);
                break;
        }
        mHeader.MedalNumber.setText("共"+Lists.size()+"枚");
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_group_medal_title)
        TextView MedalTitle;
        @BindView(R.id.item_group_medal_number)
        TextView MedalNumber;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_group_medal_linear)
        LinearLayout mItemGroupMedalLinear;
        @BindView(R.id.item_group_medal_img)
        ImageView MedalImg;
        @BindView(R.id.item_group_medal_text)
        TextView MedalText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}