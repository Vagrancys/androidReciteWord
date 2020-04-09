package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteAnimSpellViewAdapter extends AbsRecyclerViewAdapter {
    private String Title;
    public ReciteAnimSpellViewAdapter(RecyclerView recyclerView,String title){
        super(recyclerView);
        Title=title;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_recite_anim_spell,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
        }
    }
}
