package com.tramp.word.adapter.section;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.HomeCheckItemViewAdapter;
import com.tramp.word.entity.main.HomeCheckInfo;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/10
 * version:1.0
 */

public class HomeItemViewBottomSection extends StatelessSection {
    private Activity mActivity;
    private HomeCheckItemViewAdapter mHomeItemViewAdapter;
    private ArrayList<HomeCheckInfo.item> Items;
    public HomeItemViewBottomSection(Activity activity,ArrayList<HomeCheckInfo.item> items){
        super(R.layout.item_home_check_head,R.layout.item_home_check_foot,R.layout.item_home_recycler);
        mActivity=activity;
        Items=items;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.ItemHomeCheckImg.setVisibility(View.GONE);
        mHeader.ItemHomeCheckOut.setVisibility(View.GONE);
     }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHomeItemViewAdapter=new HomeCheckItemViewAdapter(mHolder.ItemHomeRecycler,mActivity,Items);
        mHolder.ItemHomeRecycler.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        mHolder.ItemHomeRecycler.setAdapter(mHomeItemViewAdapter);
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_home_check_img)
        ImageView ItemHomeCheckImg;
        @BindView(R.id.item_home_check_out)
        TextView ItemHomeCheckOut;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_home_recycler)
        RecyclerView ItemHomeRecycler;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}










