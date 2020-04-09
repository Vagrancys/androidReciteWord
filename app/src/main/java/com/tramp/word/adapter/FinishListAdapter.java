package com.tramp.word.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.word.WordContextActivity;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/08
 * version:1.0
 */
public class FinishListAdapter extends AbsRecyclerViewAdapter{
    private Animation mRotate;
    private ArrayList<DefaultWordInfo> words;
    private Activity mActivity;
    private int Status;
    public FinishListAdapter(RecyclerView recyclerView, ArrayList<DefaultWordInfo> words, Activity activity,int status){
        super(recyclerView);
        this.words=words;
        mActivity=activity;
        Status=status;
        mRotate= AnimationUtils.loadAnimation(mActivity,R.anim.rotate_y_anim);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        bindContext(viewGroup.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_finish_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.FinishTitle.setText(words.get(position).getWord_name());
        mHolder.FinishTime.setText(String.valueOf(words.get(position).getWord_time()));
        mHolder.FinishMeaning.setText(words.get(position).getWord_meaning());
        mHolder.FinishRelative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.FinishRelative1.startAnimation(mRotate);
                mHolder.FinishRelative1.setVisibility(View.GONE);
                mHolder.FinishRelative2.setVisibility(View.VISIBLE);
            }
        });
        mHolder.FinishRelative2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.FinishRelative2.startAnimation(mRotate);
                mHolder.FinishRelative2.setVisibility(View.GONE);
                mHolder.FinishRelative1.setVisibility(View.VISIBLE);
            }
        });
        mHolder.FinishSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordContextActivity.launch(mActivity,position,Status,0);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView FinishTitle;
        TextView FinishMeaning;
        TextView FinishTime;
        ImageView FinishSearch;
        RelativeLayout FinishRelative1;
        RelativeLayout FinishRelative2;
        public ItemViewHolder(View itemView){
            super(itemView);
            FinishTitle=$(R.id.finish_title);
            FinishTime=$(R.id.finish_time);
            FinishMeaning=$(R.id.finish_meaning);
            FinishSearch=$(R.id.finish_search);
            FinishRelative1=$(R.id.finish_relative_one);
            FinishRelative2=$(R.id.finish_relative_two);
        }
    }
}
