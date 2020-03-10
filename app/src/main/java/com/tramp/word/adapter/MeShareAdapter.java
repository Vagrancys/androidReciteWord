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

public class MeShareAdapter extends AbsRecyclerViewAdapter {
    public MeShareAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_me_share,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 31;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
        }
    }
}
