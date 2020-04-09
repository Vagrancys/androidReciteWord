package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupRankInfo;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/18
 * version:1.0
 */

public class GroupRankViewAdapter extends AbsRecyclerViewAdapter{
    private int ItemStatus;
    private ArrayList<GroupRankInfo.Rank> Ranks;
    public GroupRankViewAdapter(RecyclerView recyclerView,int itemStatus,ArrayList<GroupRankInfo.Rank> ranks){
        super(recyclerView);
        ItemStatus=itemStatus;
        Ranks=ranks;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_rank,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemNum.setText(""+position+1);
        Glide.with(getContext())
                .load(Ranks.get(position).getGroup_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemAvatar);
        mHolder.ItemLevel.setImageResource(Utils.getGroupLevelImg(Ranks.get(position).getGroup_level()));
        mHolder.ItemName.setText(Ranks.get(position).getGroup_name());
        switch (ItemStatus){
            case 1:
                mHolder.ItemNewLinear.setVisibility(View.VISIBLE);
                mHolder.ItemOnLinear.setVisibility(View.GONE);
                mHolder.ItemTotalLinear.setVisibility(View.GONE);
                mHolder.ItemNewStar.setText(Ranks.get(position).getGroup_star());
                mHolder.ItemNewDone.setText(Ranks.get(position).getGroup_number()+"%");
                break;
            case 2:
                mHolder.ItemNewLinear.setVisibility(View.GONE);
                mHolder.ItemOnLinear.setVisibility(View.VISIBLE);
                mHolder.ItemTotalLinear.setVisibility(View.GONE);
                mHolder.ItemOnStar.setText(Ranks.get(position).getGroup_hot());
                mHolder.ItemOnDone.setText(Ranks.get(position).getGroup_day()+"天");
                break;
            case 3:
                mHolder.ItemNewLinear.setVisibility(View.GONE);
                mHolder.ItemOnLinear.setVisibility(View.GONE);
                mHolder.ItemTotalLinear.setVisibility(View.VISIBLE);
                mHolder.ItemTotalStar.setText(Ranks.get(position).getGroup_all_star());
                break;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Ranks.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemNum;
        ImageView ItemAvatar;
        ImageView ItemLevel;
        TextView ItemName;
        LinearLayout ItemNewLinear;
        TextView ItemNewStar;
        TextView ItemNewDone;
        LinearLayout ItemOnLinear;
        TextView ItemOnStar;
        TextView ItemOnDone;
        LinearLayout ItemTotalLinear;
        TextView ItemTotalStar;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemNum=$(R.id.group_rank_num);
            ItemAvatar=$(R.id.group_rank_avatar);
            ItemLevel=$(R.id.group_rank_level);
            ItemName=$(R.id.group_rank_name);
            ItemNewLinear=$(R.id.group_linear_new);
            ItemNewStar=$(R.id.group_new_star);
            ItemNewDone=$(R.id.group_new_done);
            ItemOnLinear=$(R.id.group_linear_on);
            ItemOnStar=$(R.id.group_on_star);
            ItemOnDone=$(R.id.group_on_done);
            ItemTotalLinear=$(R.id.group_linear_total);
            ItemTotalStar=$(R.id.group_total_star);
        }
    }
}
