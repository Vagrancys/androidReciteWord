package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/2/1.
 */

public class PinLetterAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<DefaultLetterEntity> mArray;
    public PinLetterAdapter(RecyclerView recyclerView,ArrayList<DefaultLetterEntity> array){
        super(recyclerView);
        mArray=array;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pin_anim_letter,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(mArray.get(position).getSelect()==null){
            mHolder.mTextView.setVisibility(View.GONE);
            mHolder.mView.setVisibility(View.VISIBLE);
        }else{
            mHolder.mTextView.setText(mArray.get(position).getSelect());
            mHolder.mTextView.setVisibility(View.VISIBLE);
            mHolder.mView.setVisibility(View.GONE);
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        View mView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mTextView=$(R.id.item_pin_anim_letter);
            mView=$(R.id.item_pin_anim_view);
        }
    }
}
