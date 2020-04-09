package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.module.word.WordContextActivity;
import com.tramp.word.utils.Utils;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/14
 * version:1.0
 */

public class NewWordViewAdapter extends AbsRecyclerViewAdapter{
    private Animation mRotateAnim;
    private Activity mActivity;
    public NewWordViewAdapter(RecyclerView recyclerView,Activity activity){
        super(recyclerView);
        mActivity=activity;
        mRotateAnim= AnimationUtils.loadAnimation(mActivity,R.anim.rotate_y_anim);
    }
    @Override
    public int getItemCount() {
        return 20;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_new_word_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder =(ItemViewHolder) holder;
        mHolder.ItemPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHolder.ItemRelative.getVisibility()==View.VISIBLE){
                    mHolder.ItemRelative.setVisibility(View.GONE);
                }else{
                    mHolder.ItemRelative.setVisibility(View.VISIBLE);
                }
            }
        });
        mHolder.ItemRelative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemRelative2.startAnimation(mRotateAnim);
                mHolder.ItemRelative3.setVisibility(View.VISIBLE);
                mHolder.ItemRelative3.startAnimation(mRotateAnim);
                mHolder.ItemRelative2.setVisibility(View.GONE);
            }
        });
        mHolder.ItemRelative3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemRelative3.startAnimation(mRotateAnim);
                mHolder.ItemRelative3.setVisibility(View.GONE);
                mHolder.ItemRelative2.startAnimation(mRotateAnim);
                mHolder.ItemRelative2.setVisibility(View.VISIBLE);
            }
        });

        mHolder.ItemSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity, WordContextActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemPoint;
        RelativeLayout ItemRelative;
        RelativeLayout ItemRelative2;
        RelativeLayout ItemRelative3;
        ImageView ItemSearch;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemPoint=$(R.id.item_point);
            ItemRelative=$(R.id.item_relative);
            ItemRelative2=$(R.id.item_relative2);
            ItemRelative3=$(R.id.item_relative3);
            ItemSearch=$(R.id.item_search);
        }
    }
}
