package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class UserBadgeAdapter extends AbsRecyclerViewAdapter {
    public UserBadgeAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_user_badge,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
        }
    }
}
