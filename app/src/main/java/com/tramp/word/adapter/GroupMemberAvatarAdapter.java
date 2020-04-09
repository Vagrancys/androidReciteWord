package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupMemberInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class GroupMemberAvatarAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupMemberInfo.Member.item> Items;
    public GroupMemberAvatarAdapter(RecyclerView recyclerView,ArrayList<GroupMemberInfo.Member.item> items){
        super(recyclerView);
        Items=items;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
        .load(Items.get(position).getUser_avatar())
        .placeholder(R.drawable.user_avater)
        .into(mHolder.ItemImg);
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_member_img,parent,false));
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.item_group_member_img);
        }
    }

}
