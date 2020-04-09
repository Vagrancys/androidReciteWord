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
 * time  : 2019/03/19
 * version:1.0
 */

public class GroupMessageViewAdapter extends AbsRecyclerViewAdapter{
    public GroupMessageViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_message,parent,false));
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemGroupMessage;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemGroupMessage=$(R.id.group_message_text);
        }
    }
}
