package com.tramp.word.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.MemberStatusInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public class MemberStatusViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<MemberStatusInfo.member> Member;
    private Context mContext;
    public MemberStatusViewAdapter(RecyclerView recyclerView,ArrayList<MemberStatusInfo.member> members,Context context){
        super(recyclerView);
        Member=members;
        mContext=context;
    }
    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_member_status,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemName.setText(Member.get(position).getMember_name());
        Glide.with(mContext)
                .load(Member.get(position).getMember_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemAvatar);
        mHolder.ItemTime.setText(Member.get(position).getMember_time());
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Member.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemAvatar;
        TextView ItemName;
        TextView ItemTime;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemAvatar=$(R.id.item_status_img);
            ItemName=$(R.id.item_status_name);
            ItemTime=$(R.id.item_status_time);
        }
    }
}
