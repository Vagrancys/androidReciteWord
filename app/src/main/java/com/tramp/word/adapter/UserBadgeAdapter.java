package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupDetailsInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/2/1.
 */

public class UserBadgeAdapter extends AbsRecyclerViewAdapter {
    private List<GroupDetailsInfo.Details.Medal> Medals;
    public UserBadgeAdapter(RecyclerView recyclerView,List<GroupDetailsInfo.Details.Medal> medals){
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
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Medals.get(position).getMedal_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Medals.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.item_user_badge_img);
        }
    }
}
