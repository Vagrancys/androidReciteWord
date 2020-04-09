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
 * time  : 2019/03/14
 * version:1.0
 */

public class NewWordBookViewAdapter extends AbsRecyclerViewAdapter {

    public NewWordBookViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }
    @Override
    public int getItemCount() {
        return 1+4;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_new_word_book,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(position==getItemCount()-3){
            mHolder.ItemNewWordTitle.setText("默认日语生词本");
        }else if(position==getItemCount()-2){
            mHolder.ItemNewWordTitle.setText("默认英语生词本");
        }else if(position==getItemCount()-1){
            mHolder.ItemNewWordTitle.setText("默认生存本");
        }else if(position==getItemCount()){
            mHolder.ItemNewWordTitle.setText("已记住的单词");
        }
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemNewWordTitle;
        TextView ItemNewWordNumber;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemNewWordTitle=$(R.id.item_new_word_title);
            ItemNewWordNumber=$(R.id.item_new_word_number);
        }
    }
}
