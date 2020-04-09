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
import com.tramp.word.entity.group.GroupListInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupListViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupListInfo.List> Lists;
    public GroupListViewAdapter(RecyclerView recyclerView, ArrayList<GroupListInfo.List> lists){
        super(recyclerView);
        Lists=lists;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_board_list,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Lists.get(position).getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.BoardImg);
        mHolder.BoardName.setText(Lists.get(position).getUser_name());
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView BoardImg;
        TextView BoardName;
        public ItemViewHolder(View itemView){
            super(itemView);
            BoardImg=$(R.id.board_list_img);
            BoardName=$(R.id.board_List_name);
        }
    }
}
