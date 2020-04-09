package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.user.UserFriendInfo;
import com.tramp.word.module.user.FriendSearchActivity;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/24
 * version:1.0
 */
public class UserFriendSection extends StatelessSection {
    private ArrayList<UserFriendInfo.Data.Friend> mFriends;
    private Activity mActivity;
    private Context mContext;
    private int UserStatus;
    public UserFriendSection(Activity activity, Context context,ArrayList<UserFriendInfo.Data.Friend> friends,int user_status){
        super(R.layout.item_friend_header,R.layout.item_friend);
        mFriends=friends;
        mActivity=activity;
        mContext=context;
        UserStatus=user_status;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(mContext)
                .load(mFriends.get(position).getFriend_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.FriendImg);
        mHolder.FriendName.setText(mFriends.get(position).getFriend_name());
        mHolder.FriendTeam.setText(mFriends.get(position).getFriend_sign());
        mHolder.FriendNumber.setText(mFriends.get(position).getFriend_word());
        mHolder.FriendRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,mFriends.get(position).getFriend_id());
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;

        mHeader.FriendTitle.setText(String.valueOf(mFriends.size()));
        if(UserStatus==1){
            mHeader.FriendAddLinear.setVisibility(View.GONE);
        }else{
            mHeader.FriendAddLinear.setVisibility(View.VISIBLE);
        }
        mHeader.FriendAddLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, FriendSearchActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return mFriends.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.friend_title)
        TextView FriendTitle;
        @BindView(R.id.friend_add_linear)
        LinearLayout FriendAddLinear;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.friend_relative)
        RelativeLayout FriendRelative;
        @BindView(R.id.friend_img)
        ImageView FriendImg;
        @BindView(R.id.friend_name)
        TextView FriendName;
        @BindView(R.id.friend_team)
        TextView FriendTeam;
        @BindView(R.id.friend_number)
        TextView FriendNumber;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
