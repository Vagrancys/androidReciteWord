package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupViewAdapter;
import com.tramp.word.entity.group.GroupContextInfo;
import com.tramp.word.module.group.GroupDetailsActivity;
import com.tramp.word.module.group.GroupFinialActivity;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupActiveViewSection extends StatelessSection{
    private GroupViewAdapter mGroupViewAdapter;
    private Context mContext;
    private Activity mActivity;
    private int mStatus;
    private ArrayList<GroupContextInfo.Item> Actives;
    private int Add_Status;
    private int user_id;
    public GroupActiveViewSection(Context context, Activity activity,int status, ArrayList<GroupContextInfo.Item> actives,int add_status,int user_id){
        super(R.layout.item_group_active_head,R.layout.item_group_active_foot,R.layout.item_home_recycler);
        mContext=context;
        mActivity=activity;
        mStatus=status;
        Actives=actives;
        Add_Status=add_status;
        this.user_id=user_id;
    }
    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.GroupActiveHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupFinialActivity.launch(mActivity,mStatus);
            }
        });
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mGroupViewAdapter=new GroupViewAdapter(mHolder.ItemHomeRecycler,mActivity,mStatus,Actives,Add_Status,user_id);
        mHolder.ItemHomeRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        mHolder.ItemHomeRecycler.setAdapter(mGroupViewAdapter);
        mGroupViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupDetailsActivity.launch(mActivity,Actives.get(position).getGroup_id());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindFooterViewHolder(holder);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_home_recycler)
        RecyclerView ItemHomeRecycler;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.group_active_head)
        RelativeLayout GroupActiveHead;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
