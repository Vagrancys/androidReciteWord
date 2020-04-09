package com.tramp.word.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.user.FriendSearchInfo;
import com.tramp.word.module.user.FriendDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class SearchMemberAdapter extends AbsRecyclerViewAdapter {
    private Activity mActivity;
    private ArrayList<FriendSearchInfo.search> Searches;
    public SearchMemberAdapter(RecyclerView recyclerView,Activity activity,ArrayList<FriendSearchInfo.search> search){
        super(recyclerView);
        Searches=search;
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
        mHolder.ItemSearchMemberName.setText(Searches.get(position).getUser_name());
        Glide.with(mActivity)
                .load(Searches.get(position).getUser_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemSearchMemberImg);
        switch (Searches.get(position).getUser_status()){
            case 1:
                mHolder.ItemSearchMemberAdd.setVisibility(View.VISIBLE);
                mHolder.ItemSearchMemberTime.setVisibility(View.GONE);
                break;
            case 2:
                mHolder.ItemSearchMemberAdd.setVisibility(View.GONE);
                mHolder.ItemSearchMemberTime.setVisibility(View.VISIBLE);
                mHolder.ItemSearchMemberTime.setText("已添加");
                break;
        }
        mHolder.ItemSearchMemberImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,Searches.get(position).getUser_id());
            }
        });
        mHolder.ItemSearchMemberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemSearchMemberAdd.setVisibility(View.GONE);
                mHolder.ItemSearchMemberTime.setVisibility(View.VISIBLE);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Searches.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemSearchMemberImg;
        TextView ItemSearchMemberName;
        TextView ItemSearchMemberAdd;
        TextView ItemSearchMemberTime;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemSearchMemberName=$(R.id.item_search_member_text);
            ItemSearchMemberTime=$(R.id.item_search_member_time);
            ItemSearchMemberImg=$(R.id.item_search_member_img);
            ItemSearchMemberAdd=$(R.id.item_search_member_add);
        }
    }
}






