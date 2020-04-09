package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupItemViewAdapter;
import com.tramp.word.entity.group.GroupContextInfo;
import com.tramp.word.module.group.GroupDetailsActivity;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/19
 * version:1.0
 */

public class GroupItemViewSection extends StatelessSection{
    private GroupItemViewAdapter mGroupItemViewAdapter;
    private Activity mActivity;
    private Context mContext;
    private ArrayList<GroupContextInfo.Item> Lists;
    private int AddStatus;
    private int UserId;
    public GroupItemViewSection(Context context, Activity activity, ArrayList<GroupContextInfo.Item> lists,int add_status,int user_id){
        super(R.layout.item_home_recycler);
        mActivity=activity;
        mContext=context;
        Lists=lists;
        AddStatus=add_status;
        UserId=user_id;
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mGroupItemViewAdapter=new GroupItemViewAdapter(mHolder.ItemHomeRecycler,mActivity,Lists,AddStatus,UserId);
        mHolder.ItemHomeRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mHolder.ItemHomeRecycler.setAdapter(mGroupItemViewAdapter);
        mGroupItemViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupDetailsActivity.launch(mActivity,Lists.get(position).getGroup_id());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
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
