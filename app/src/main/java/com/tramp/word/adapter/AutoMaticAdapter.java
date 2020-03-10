package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class AutoMaticAdapter extends AbsRecyclerViewAdapter {
    private String[] mTitle;
    private int mStatus;
    public AutoMaticAdapter(RecyclerView recyclerView,String[] title,int status){
        super(recyclerView);
        mTitle=title;
        mStatus=status;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_auto_matic,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;

        mHolder.mItemAutoMasticText.setText(mTitle[position]);

        if(position==mStatus){
            mHolder.mItemAutoMasticImg.setVisibility(View.VISIBLE);
        }else{
            mHolder.mItemAutoMasticImg.setVisibility(View.GONE);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mItemAutoMasticText;
        ImageView mItemAutoMasticImg;
        RelativeLayout mItemAutoMasticRelative;
        public ItemViewHolder(View itemView){
            super(itemView);
            mItemAutoMasticText=$(R.id.auto_mastic_text);
            mItemAutoMasticImg=$(R.id.auto_mastic_img);
            mItemAutoMasticRelative=$(R.id.auto_mastic_relative);
        }
    }
}






