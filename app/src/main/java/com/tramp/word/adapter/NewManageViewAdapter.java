package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/14
 * version:1.0
 */

public class NewManageViewAdapter extends AbsRecyclerViewAdapter{
    public NewManageViewAdapter(RecyclerView recyclerView){
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
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_new_manage_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemManageRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHolder.ItemManageRelative1.getVisibility()==View.VISIBLE){
                    mHolder.ItemManageRelative1.setVisibility(View.GONE);
                }else{
                    mHolder.ItemManageRelative1.setVisibility(View.VISIBLE);
                }
            }
        });
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout ItemManageRelative;
        RelativeLayout ItemManageRelative1;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemManageRelative=$(R.id.item_manage_relative);
            ItemManageRelative1=$(R.id.item_manage_relative1);
        }
    }
}
