package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.revise.DefaultPinyinInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class PinContentAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<DefaultPinyinInfo> mArray;
    private int mStatus;
    private int mSuccess;
    public PinContentAdapter(RecyclerView recyclerView, ArrayList<DefaultPinyinInfo> array, int status,int success){
        super(recyclerView);
        mArray=array;
        mStatus=status;
        mSuccess=success;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pin_content,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mItemPinContent.setText(mArray.get(position).getContent_letter());
        if(mArray.get(position).getSelect_status()==1){
            mHolder.mItemPinContent.setTextColor(Color.BLUE);
            mHolder.mItemPinContent.setBackground(getContext().getResources().getDrawable(R.drawable.btn_pin_back));
        }else if(mArray.get(position).getSelect_status()==0&&mStatus==0){
            mHolder.mItemPinContent.setTextColor(Color.WHITE);
            mHolder.mItemPinContent.setBackground(getContext().getResources().getDrawable(R.drawable.btn_pin_back_nu));
        }else if(mArray.get(position).getSelect_status()==0&&mStatus==1){
            mHolder.mItemPinContent.setTextColor(getContext().getResources().getColor(R.color.white_1));
            mHolder.mItemPinContent.setBackground(getContext().getResources().getDrawable(R.drawable.btn_pin_back_end));
        }else if(!(mArray.get(position).getContent_letter().equals(mArray.get(position).getContent_select()))&&mSuccess==1){
            mHolder.mItemPinContent.setTextColor(Color.WHITE);
            mHolder.mItemPinContent.setBackground(getContext().getResources().getDrawable(R.drawable.btn_pin_back_error));
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mItemPinContent;
        public ItemViewHolder(View itemView){
            super(itemView);
            mItemPinContent=$(R.id.item_pin_content);
        }
    }
}
