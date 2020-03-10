package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordWholeLanguageViewSection extends StatelessSection {
    private String[] title;
    private int[] img;
    private Context mContext;

    public WordWholeLanguageViewSection(Context context,String[] title,int[] img){
        super(R.layout.item_word_whole_language);
        this.title=title;
        this.img=img;
        this.mContext=context;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return title.length;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
