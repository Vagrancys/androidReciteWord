package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class GroupTagAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<String> Lists;
    public GroupTagAdapter(RecyclerView recyclerView, ArrayList<String> tag){
        super(recyclerView);
        Lists=tag;
    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemTag.setText(Lists.get(position));
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_tag,parent,false));
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemTag;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemTag=$(R.id.item_tag);
        }
    }
}
