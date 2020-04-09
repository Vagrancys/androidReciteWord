package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.friend.FriendMainInfo;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/22
 * version:1.0
 */
public class FriendBadgeAdapter extends AbsRecyclerViewAdapter{
    private List<FriendMainInfo.Friend.Group.Medal> Medals;
    public FriendBadgeAdapter(RecyclerView recyclerView, List<FriendMainInfo.Friend.Group.Medal> medals){
        super(recyclerView);
        Medals=medals;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_user_badge,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        UserBadgeAdapter.ItemViewHolder mHolder=(UserBadgeAdapter.ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Medals.get(position).getMedal_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if(Medals==null){
            return 0;
        }else{
            return Medals.size();
        }
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.item_user_badge_img);
        }
    }
}
