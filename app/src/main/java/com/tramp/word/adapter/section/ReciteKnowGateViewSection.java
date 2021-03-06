package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.module.home.recite.ReciteWordContextActivity;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/25.
 */

public class ReciteKnowGateViewSection extends StatelessSection {
    private Context mContext;
    private Animation mRotate;
    private Activity mActivity;
    public ReciteKnowGateViewSection(Context context, Activity activity){
        super(R.layout.item_recite_know_gate);
        this.mContext=context;
        this.mActivity=activity;
        mRotate= AnimationUtils.loadAnimation(mActivity,R.anim.recite_know_anim);
    }
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.mLinear.startAnimation(mRotate);
                mHolder.mLinear.setVisibility(View.GONE);
                mHolder.mRelative.setVisibility(View.VISIBLE);
            }
        });
        mHolder.mRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.mRelative.startAnimation(mRotate);
                mHolder.mRelative.setVisibility(View.GONE);
                mHolder.mLinear.setVisibility(View.VISIBLE);
            }
        });
        mHolder.mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,ReciteWordContextActivity.class));
                mActivity.overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return 10;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recite_know_gate_relative)
        RelativeLayout mRelative;
        @BindView(R.id.recite_know_search)
        ImageView mSearch;
        @BindView(R.id.recite_know_gate_linear)
        LinearLayout mLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
