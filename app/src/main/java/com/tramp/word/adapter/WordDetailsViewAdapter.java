package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.section.WordWholeLanguageViewSection;

/**
 * Created by Administrator on 2019/1/30.
 */

public class WordDetailsViewAdapter extends AbsRecyclerViewAdapter {

    public WordDetailsViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }
    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_details,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 13;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        LinearLayout mLinear;
        View mErrorLeft;
        ImageView mError;
        ImageView mSelect;
        public ItemViewHolder(View itemView){
            super(itemView);
            mLinear=$(R.id.item_word_details_linear);
            mErrorLeft=$(R.id.item_details_error_left);
            mError=$(R.id.item_word_details_error);
            mSelect=$(R.id.item_word_details_select);
        }
    }
}



