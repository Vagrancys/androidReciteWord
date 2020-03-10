package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tramp.word.R;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordBookKeepViewSection extends StatelessSection {
    private Context context;
    private WordBookPopInterFace mPop;

    public WordBookKeepViewSection(Context context){
        super(R.layout.item_word_book_keep_head,R.layout.item_word_book_keep);
        this.context=context;
        mPop=(WordBookPopInterFace) context;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;
            itemViewHolder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPop.ShowWordBookPop();
                }
            });
    }

    @Override
    public int getContentItemsTotal() {
        return 5;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_layout)
        RelativeLayout mItemLayout;
        @BindView(R.id.item_word_book_img)
        ImageView mImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







