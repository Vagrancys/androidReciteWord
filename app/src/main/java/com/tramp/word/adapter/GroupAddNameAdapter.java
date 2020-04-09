package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupAvatarInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/16
 * version:1.0
 */

public class GroupAddNameAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupAvatarInfo.Item> Items;
    private int avatar_id;
    public GroupAddNameAdapter(RecyclerView recyclerView,ArrayList<GroupAvatarInfo.Item> items){
        super(recyclerView);
        Items=items;
        avatar_id= PreferencesUtils.getInt(ConstantUtils.AVATAR_ID,0);
    }
    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_avatar,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Items.get(position).getAvatar_url())
                .placeholder(R.drawable.pic_lang_cn)
                .into(mHolder.ItemImg);
        if(avatar_id==position){
            mHolder.ItemSelect.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemSelect.setVisibility(View.GONE);
        }
        mHolder.ItemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemSelect.setVisibility(View.VISIBLE);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        ImageView ItemSelect;
        FrameLayout ItemFrame;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.group_avatar_img);
            ItemSelect=$(R.id.group_avatar_img_select);
            ItemFrame=$(R.id.item_avatar);
        }
    }
}
