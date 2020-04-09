package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.user.UserShareInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class MeShareAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<UserShareInfo.Share.Day> Days;
    public MeShareAdapter(RecyclerView recyclerView,ArrayList<UserShareInfo.Share.Day> days){
        super(recyclerView);
        Days=days;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_me_share,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        switch (Days.get(position).getDay_status()){
            case 0:
                mHolder.ItemShare.setVisibility(View.GONE);
                mHolder.ItemShareCircle.setVisibility(View.GONE);
                break;
            case 1:
                mHolder.ItemShare.setVisibility(View.VISIBLE);
                mHolder.ItemShareCircle.setVisibility(View.GONE);
                mHolder.ItemShare.setText(Days.get(position).getDay_number());
                break;
            case 2:
                mHolder.ItemShareCircle.setVisibility(View.VISIBLE);
                mHolder.ItemShare.setVisibility(View.GONE);
                mHolder.ItemShareCircle.setText(Days.get(position).getDay_number());
                break;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Days.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemShare;
        TextView ItemShareCircle;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemShare=$(R.id.item_share);
            ItemShareCircle=$(R.id.item_share_circle);
        }
    }
}
