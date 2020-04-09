package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/15
 * version:1.0
 */

public class NewGroupViewAdapter extends AbsRecyclerViewAdapter{
    public NewGroupViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_new_group,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemNewGroupImg;
        TextView ItemNewGroupText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemNewGroupImg=$(R.id.item_new_group_img);
            ItemNewGroupText=$(R.id.item_new_group_text);
        }
    }
}
