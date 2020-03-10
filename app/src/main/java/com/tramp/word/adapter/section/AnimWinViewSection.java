package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/2/1.
 */

public class AnimWinViewSection extends StatelessSection {
    private Context mContext;
    private int Win;
    public AnimWinViewSection(Context context, int win){
        super(R.layout.item_anim_win_head,R.layout.item_anim_win_item);
        mContext=context;
        Win=win;
    }
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
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
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public int getContentItemsTotal() {
        return 10;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
