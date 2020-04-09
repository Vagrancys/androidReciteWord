package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultTagInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/17
 * version:1.0
 */

public class GroupAddTagAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<DefaultTagInfo> Lists;
    private List<Integer> Integer;
    public GroupAddTagAdapter(RecyclerView recyclerView,ArrayList<DefaultTagInfo> tag,List<Integer> integers){
        super(recyclerView);
        Lists=tag;
        Integer=integers;
    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemTag.setText(Lists.get(position).getTag_title());
        if(Lists.get(position).getTag_status()==1){
            mHolder.ItemTag.setClickable(false);
            mHolder.ItemTag.setTextColor(getContext().getResources().getColor(R.color.white));
            mHolder.ItemTag.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_add_select));
        }else if(Integer.size()==3&&Lists.get(position).getTag_status()==0){
            mHolder.ItemTag.setClickable(true);
            mHolder.ItemTag.setTextColor(getContext().getResources().getColor(R.color.black_3));
            mHolder.ItemTag.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_add_click));
        }else if(Lists.get(position).getTag_status()==0){
            mHolder.ItemTag.setClickable(false);
            mHolder.ItemTag.setTextColor(getContext().getResources().getColor(R.color.black_1));
            mHolder.ItemTag.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_add_nuselect));
        }
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_add_tag,parent,false));
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemTag;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemTag=$(R.id.item_group_tag);
        }
    }
}
