package com.tramp.word.adapter.section;

import android.app.Activity;
import android.os.Handler;
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
 * Created by Administrator on 2019/1/25.
 */

public class ReciteListViewSection extends StatelessSection {
    private Animation mRotate;
    private Activity mActivity;
    private ArrayList<DefaultWordInfo> Words;
    private int Status;
    private Handler mHandler;
    public ReciteListViewSection(Activity activity, ArrayList<DefaultWordInfo> words, int status, Handler handler){
        super(R.layout.item_recite_list);
        this.mActivity=activity;
        Words=words;
        Status=status;
        mRotate= AnimationUtils.loadAnimation(mActivity,R.anim.rotate_y_anim);
        mHandler=handler;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ListText.setText(Words.get(position).getWord_name());
        mHolder.ListNextText.setText(Words.get(position).getWord_meaning());
        mHolder.ListLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ListLinear.startAnimation(mRotate);
                mHolder.ListLinear.setVisibility(View.GONE);
                mHolder.ListRelative.setVisibility(View.VISIBLE);
                mHandler.postDelayed(()->{
                    mHolder.ListRelative.startAnimation(mRotate);
                    mHolder.ListRelative.setVisibility(View.GONE);
                    mHolder.ListLinear.setVisibility(View.VISIBLE);
                },1500);
            }
        });
        mHolder.ListRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ListRelative.startAnimation(mRotate);
                mHolder.ListRelative.setVisibility(View.GONE);
                mHolder.ListLinear.setVisibility(View.VISIBLE);
            }
        });
        mHolder.ListSearch.setOnClickListener(new View.OnClickListener() {
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
    public int getContentItemsTotal() {
        return Words.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_relative)
        RelativeLayout ListRelative;
        @BindView(R.id.list_search)
        ImageView ListSearch;
        @BindView(R.id.list_linear)
        LinearLayout ListLinear;
        @BindView(R.id.list_text)
        TextView ListText;
        @BindView(R.id.list_next_text)
        TextView ListNextText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}