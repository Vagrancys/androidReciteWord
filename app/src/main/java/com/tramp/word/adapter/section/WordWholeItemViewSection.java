package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/13.
 */

public class WordWholeItemViewSection extends StatelessSection {
    private Context context;
    public int FooterInt=0;
    private WordBookPopInterFace mPop;

    public WordWholeItemViewSection(Context context){
        super(R.layout.item_empty,R.layout.item_word_whole_item,R.layout.item_word_whole_item_footer);
        this.context=context;
        mPop=(WordBookPopInterFace) context;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            FooterInt=position;
            mHolder.mItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPop.ShowWordBookPop();
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        if(holder instanceof FooterViewHolder){
            FooterViewHolder mFooter=(FooterViewHolder) holder;
            if(FooterInt==getContentItemsTotal()-1){
                mFooter.mLayout.setVisibility(View.VISIBLE);
            }else{
                mFooter.mLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return 12;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_layout)
        LinearLayout mItemLayout;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.whole_item_footer_layout)
        LinearLayout mLayout;
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







