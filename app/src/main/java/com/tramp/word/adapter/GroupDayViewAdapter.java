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
import com.tramp.word.entity.group.GroupDayInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupDayViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupDayInfo.Day.List> Lists;
    public GroupDayViewAdapter(RecyclerView recyclerView, ArrayList<GroupDayInfo.Day.List> lists){
        super(recyclerView);
        Lists=lists;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemName.setText(Lists.get(position).getUser_name());
        Glide.with(getContext())
                .load(Lists.get(position).getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        if(position==0){
            mHolder.ItemTop.setImageResource(R.drawable.crown_1);
        }else if(position==1){
            mHolder.ItemTop.setImageResource(R.drawable.crown_2);
        }else if(position==2){
            mHolder.ItemTop.setImageResource(R.drawable.crown_3);
        }
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_day_list,parent,false));
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemName;
        ImageView ItemImg;
        ImageView ItemTop;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemName=$(R.id.item_day_list_name);
            ItemImg=$(R.id.item_day_list_img);
            ItemTop=$(R.id.item_day_list_top);
        }
    }
}
