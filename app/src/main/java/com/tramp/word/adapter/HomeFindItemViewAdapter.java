package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.main.HomeFindInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/11
 * version:1.0
 */

public class HomeFindItemViewAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    private ArrayList<HomeFindInfo.find.item> Items;
    public HomeFindItemViewAdapter(RecyclerView recyclerView, Activity activity, ArrayList<HomeFindInfo.find.item> items){
        super(recyclerView);
        mActivity=activity;
        Items=items;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_find_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemHomeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(mActivity, WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
        Glide.with(getContext())
                .load(Items.get(position).getGood_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        mHolder.ItemName.setText(Items.get(position).getGood_name());
        mHolder.ItemTitle.setText(Items.get(position).getGood_title());
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout ItemHomeRelative;
        ImageView ItemImg;
        TextView ItemName;
        TextView ItemTitle;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemHomeRelative=$(R.id.item_home_relative);
            ItemImg=$(R.id.item_home_pic);
            ItemName=$(R.id.item_home_text);
            ItemTitle=$(R.id.item_home_summary);
        }
    }
}
