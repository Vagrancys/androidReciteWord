package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;

import java.util.TimerTask;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteAnimLetterViewAdapter extends AbsRecyclerViewAdapter {
    private String Title;
    public ReciteAnimLetterViewAdapter(RecyclerView recyclerView,String title){
        super(recyclerView);
        Title=title;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_recite_anim_letter,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(position==getItemCount()-1){
            mHolder.mTextView.setText("确定");
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mTextView=$(R.id.item_word_recite_anim_letter);
        }
    }
}
