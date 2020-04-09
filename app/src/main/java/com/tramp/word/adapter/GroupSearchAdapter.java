package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.SearchListInfo;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/18
 * version:1.0
 */

public class GroupSearchAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<SearchListInfo.List> Lists;
    public GroupSearchAdapter(RecyclerView recyclerView,ArrayList<SearchListInfo.List> lists){
        super(recyclerView);
        Lists=lists;
    }
    @Override
    public int getItemCount() {
        return Lists.size();
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_search,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
        .load(Lists.get(position).getGroup_img())
        .placeholder(R.drawable.user_avater)
        .into(mHolder.ItemImg);
        mHolder.ItemName.setText(Lists.get(position).getGroup_name());
        mHolder.ItemLevel.setImageResource(Utils.getGroupLevelImg(Lists.get(position).getGroup_level()));
        mHolder.ItemStar.setText(Lists.get(position).getGroup_star());
        if(Lists.get(position).getTags().size()>=3){
            mHolder.ItemTagThree.setText(Lists.get(position).getTags().get(3).getTag_name());
            mHolder.ItemTagThree.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemTagThree.setVisibility(View.GONE);
        }
        if(Lists.get(position).getTags().size()>=2){
            mHolder.ItemTagTwo.setText(Lists.get(position).getTags().get(2).getTag_name());
            mHolder.ItemTagTwo.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemTagTwo.setVisibility(View.GONE);
        }
        if(Lists.get(position).getTags().size()>=1){
            mHolder.ItemTagOne.setText(Lists.get(position).getTags().get(1).getTag_name());
            mHolder.ItemTagOne.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemTagOne.setVisibility(View.GONE);
        }
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        TextView ItemName;
        ImageView ItemLevel;
        TextView ItemStar;
        TextView ItemTagOne;
        TextView ItemTagTwo;
        TextView ItemTagThree;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.group_search_avatar);
            ItemName=$(R.id.group_search_name);
            ItemLevel=$(R.id.group_search_level);
            ItemTagOne=$(R.id.group_search_tag_one);
            ItemTagTwo=$(R.id.group_search_tag_two);
            ItemTagThree=$(R.id.group_search_tag_three);
            ItemStar=$(R.id.group_search_star);

        }
    }
}
