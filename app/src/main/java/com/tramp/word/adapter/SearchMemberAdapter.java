package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
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
import com.tramp.word.module.home.me.FriendDetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/1.
 */

public class SearchMemberAdapter extends AbsRecyclerViewAdapter {
    private Activity mActivity;
    public SearchMemberAdapter(RecyclerView recyclerView,Activity activity){
        super(recyclerView);
        mActivity=activity;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_search_member,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mItemSearchMemberImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, FriendDetailsActivity.class));
            }
        });
        mHolder.mItemSearchMemberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.mItemSearchMemberAdd.setText(mActivity.getResources().getString(R.string.user_friend_exit));
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView mItemSearchMemberImg;
        TextView mItemSearchMemberAdd;
        public ItemViewHolder(View itemView){
            super(itemView);
            mItemSearchMemberImg=$(R.id.item_search_member_img);
            mItemSearchMemberAdd=$(R.id.item_search_member_add);
        }
    }
}






