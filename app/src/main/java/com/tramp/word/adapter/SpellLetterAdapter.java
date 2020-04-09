package com.tramp.word.adapter;

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
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/14
 * version:1.0
 */
public class SpellLetterAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<DefaultPinyinInfo> mArray;
    public SpellLetterAdapter(RecyclerView recyclerView, ArrayList<DefaultPinyinInfo> array){
        super(recyclerView);
        mArray=array;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pin_letter,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(mArray.get(position).getWord_select()==""){
            mHolder.mTextView.setVisibility(View.GONE);
            mHolder.mView.setVisibility(View.VISIBLE);
        }else{
            mHolder.mTextView.setText(mArray.get(position).getWord_select());
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
            mTextView=$(R.id.item_pin_letter);
            mView=$(R.id.item_pin_view);
        }
    }
}