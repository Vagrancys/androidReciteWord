package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordBookNowViewSection extends StatelessSection {
    private Context context;
    private WordBookPopInterFace mPop;

    public WordBookNowViewSection(Context context){
        super(R.layout.item_word_book_now_head,R.layout.item_word_book_now);
        this.context=context;
        mPop=(WordBookPopInterFace) context;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPop.ShowWordBookPop();
                }
            });
        }
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_layout)
        LinearLayout mItemLayout;
        ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







