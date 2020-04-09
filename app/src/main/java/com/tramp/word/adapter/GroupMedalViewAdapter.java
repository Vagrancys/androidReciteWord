package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupMainInfo;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/19
 * version:1.0
 */

public class GroupMedalViewAdapter extends AbsRecyclerViewAdapter{
    private List<GroupMainInfo.medal> Medals;
    public GroupMedalViewAdapter(RecyclerView recyclerView,List<GroupMainInfo.medal> medals){
        super(recyclerView);
        Medals=medals;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder =(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Medals.get(position).getMedal_img()).placeholder(R.drawable.user_avater)
                .into(mHolder.ItemGroupMedal);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Medals.size();
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_medal_view,parent,false));
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemGroupMedal;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemGroupMedal=$(R.id.item_group_medal_img);
        }
    }
}
