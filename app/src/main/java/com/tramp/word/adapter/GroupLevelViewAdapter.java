package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.group.GroupLevelInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupLevelViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupLevelInfo.Level.Item> Items;
    private String[] mStar={"0","100","300",
            "1400","3000","15000",
            "30000","100000","150000",
            "440000","730000","1200000",
            "7500000","20000000","30000000",
            "60000000"};
    private String[] mNumber={"10","10","20",
            "20","50","50",
            "100","100","200",
            "200","200","200",
            "200","200","250",
            "350"};
    public GroupLevelViewAdapter(RecyclerView recyclerView,ArrayList<GroupLevelInfo.Level.Item> items){
        super(recyclerView);
        Items=items;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemGroupLevel.setText("Lv"+(position+1));
        mHolder.ItemGroupText.setText(mStar[position]);
        Log.e("groupLevel","position="+position+"level="+(Items.size()-1));
        if(position<=Items.size()-1){
            mHolder.ItemGroupLevel.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_blue_bg));
            mHolder.ItemGroupText.setTextColor(getContext().getResources().getColor(R.color.black_1));
            mHolder.ItemGroupTime.setVisibility(View.VISIBLE);
            mHolder.ItemGroupTime.setText(String.valueOf(Items.get(position).getItem_time()));
            mHolder.ItemGroupBarTop.setVisibility(View.VISIBLE);
            mHolder.ItemGroupText.setTextColor(getContext().getResources().getColor(R.color.black));
            mHolder.ItemGroupNumber.setTextColor(getContext().getResources().getColor(R.color.black_1));
        }else{
            mHolder.ItemGroupBarTop.setVisibility(View.GONE);
            mHolder.ItemGroupTime.setVisibility(View.GONE);
            mHolder.ItemGroupText.setTextColor(getContext().getResources().getColor(R.color.black_3));
            mHolder.ItemGroupNumber.setTextColor(getContext().getResources().getColor(R.color.black_3));
            mHolder.ItemGroupLevel.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_black_bg));
        }
        mHolder.ItemGroupNumber.setText(mNumber[position]);
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_level,parent,false));
    }

    @Override
    public int getItemCount() {
        return mStar.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemGroupLevel;
        View ItemGroupBarBottom;
        View ItemGroupBarTop;
        TextView ItemGroupText;
        TextView ItemGroupNumber;
        TextView ItemGroupTime;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemGroupLevel=$(R.id.item_group_level);
            ItemGroupBarBottom=$(R.id.item_group_bar_bottom);
            ItemGroupBarTop=$(R.id.item_group_bar_top);
            ItemGroupText=$(R.id.item_group_texts);
            ItemGroupNumber=$(R.id.item_group_numbers);
            ItemGroupTime=$(R.id.item_group_time);
        }
    }
}
