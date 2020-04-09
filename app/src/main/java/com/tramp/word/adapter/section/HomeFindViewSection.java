package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.HomeOneItemViewAdapter;
import com.tramp.word.entity.main.HomeFindInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;
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

public class HomeFindViewSection extends StatelessSection{
    private Activity mActivity;
    private HomeOneItemViewAdapter mHomeOneItemViewAdapter;
    private ArrayList<HomeFindInfo.find.word> Words;
    public HomeFindViewSection(Activity activity, ArrayList<HomeFindInfo.find.word> words){
        super(R.layout.item_home_find_head,R.layout.item_home_recycler);
        mActivity=activity;
        Words=words;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.ItemHomeFindOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.startActivity(new Intent(mActivity,WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHomeOneItemViewAdapter=new HomeOneItemViewAdapter(mHolder.ItemHomeRecycler,Words);
        mHolder.ItemHomeRecycler.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        mHolder.ItemHomeRecycler.setAdapter(mHomeOneItemViewAdapter);
        mHomeOneItemViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                mActivity.startActivity(new Intent(mActivity,WedCommonActivity.class));
                Utils.StarActivityInAnim(mActivity);
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_home_find_out)
        TextView ItemHomeFindOut;
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
