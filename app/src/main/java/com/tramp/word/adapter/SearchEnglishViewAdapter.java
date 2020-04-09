package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/12
 * version:1.0
 */

public class SearchEnglishViewAdapter  extends AbsRecyclerViewAdapter{
    public SearchEnglishViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_search_english,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemSearchEnglish;
        TextView ItemSearchEnglishText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemSearchEnglish=$(R.id.item_search_english);
            ItemSearchEnglishText=$(R.id.item_search_english_text);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
