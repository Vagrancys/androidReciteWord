package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupDayInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupPraiseViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupDayInfo.Day.Praise> Praises;
    public GroupPraiseViewAdapter(RecyclerView recyclerView, ArrayList<GroupDayInfo.Day.Praise> praises){
        super(recyclerView);
        Praises=praises;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Praises.get(position).getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_day_praise,parent,false));
    }

    @Override
    public int getItemCount() {
        return Praises.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.item_day_list_img);
        }
    }
}
