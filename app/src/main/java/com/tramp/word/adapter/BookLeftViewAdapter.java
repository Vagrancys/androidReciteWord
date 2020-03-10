package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/1/14.
 */

public class BookLeftViewAdapter extends AbsRecyclerViewAdapter{
    private String[] title={
            "推荐","大学","高中",
            "初中","小学","兴趣",
            "留学","职场","网校"
    };

    private int TitleSeclet=5;
    public BookLeftViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_left,parent,false));
    }



    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mTextView.setText(title[position]);
            if(TitleSeclet==position){
                mHolder.mLinear.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        LinearLayout mLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            mLinear=$(R.id.word_book_left_layout);
            mTextView=$(R.id.word_whole_left_text);
        }
    }

}
