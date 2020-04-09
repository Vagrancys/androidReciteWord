package com.tramp.word.adapter.section;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.word.WordContextActivity;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/01/25
 * version:1.0
 */

public class ReciteItemViewSection extends StatelessSection {
    private Animation mRotate;
    private Activity mActivity;
    private ArrayList<DefaultWordInfo> Words;
    private int Gate;
    private int Status;
    public ReciteItemViewSection(Activity activity, ArrayList<DefaultWordInfo> words, int gate,int status){
        super(R.layout.item_recite_item_head,R.layout.item_recite_item);
        this.mActivity=activity;
        Words=words;
        Gate=gate;
        Status=status;
        mRotate= AnimationUtils.loadAnimation(mActivity,R.anim.rotate_y_anim);
    }
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemText.setText(Words.get(position).getWord_name());
        mHolder.ItemNextText.setText(Words.get(position).getWord_meaning());
        mHolder.ItemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemLinear.startAnimation(mRotate);
                mHolder.ItemLinear.setVisibility(View.GONE);
                mHolder.ItemRelative.setVisibility(View.VISIBLE);
            }
        });
        mHolder.ItemRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemRelative.startAnimation(mRotate);
                mHolder.ItemRelative.setVisibility(View.GONE);
                mHolder.ItemLinear.setVisibility(View.VISIBLE);
            }
        });
        mHolder.ItemSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordContextActivity.launch(mActivity,position,Status,0);
            }
        });
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHolder=(HeaderViewHolder) holder;
        mHolder.ItemHead.setText("第"+Gate+"关");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_head)
        TextView ItemHead;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getContentItemsTotal() {
        return Words.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_relative)
        RelativeLayout ItemRelative;
        @BindView(R.id.item_search)
        ImageView ItemSearch;
        @BindView(R.id.item_linear)
        LinearLayout ItemLinear;
        @BindView(R.id.item_text)
        TextView ItemText;
        @BindView(R.id.item_next_text)
        TextView ItemNextText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
