package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/1/14.
 */

public class BookRightViewAdapter extends AbsRecyclerViewAdapter{
    private String[] title={
            "小升初","人教版","上海牛津版",
            "译林牛津版","北师大版","上海新世纪版",
            "翼教版","课外兴趣","重大版",
            "其他"
    };
    private int TitleSelect=5;

    public BookRightViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_right,parent,false));
    }



    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mTextView.setText(title[position]);
            if(TitleSelect==position){
                mHolder.mTextView.setTextColor(getContext().getResources().getColor(R.color.blue));
                mHolder.mImageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        ImageView mImageView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mTextView=$(R.id.word_whole_right_text);
            mImageView=$(R.id.word_whole_right_img);
        }
    }

}



