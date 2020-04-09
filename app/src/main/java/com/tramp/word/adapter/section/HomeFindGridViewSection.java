package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.HomeFindItemAdapter;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.module.life.NewWordActivity;
import com.tramp.word.module.rank.RankActivity;
import com.tramp.word.module.search.WordSearchActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/11
 * version:1.0
 */

public class HomeFindGridViewSection extends StatelessSection{
    private Activity mActivity;
    private HomeFindItemAdapter mHomeItemViewAdapter;
    public HomeFindGridViewSection(Activity activity){
        super(R.layout.item_home_recycler);
        mActivity=activity;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHomeItemViewAdapter=new HomeFindItemAdapter(mHolder.ItemHomeRecycler);
        mHolder.ItemHomeRecycler.setLayoutManager(new GridLayoutManager(mActivity,4));
        mHolder.ItemHomeRecycler.setAdapter(mHomeItemViewAdapter);
        mHomeItemViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                switch (position){
                    case 0:
                        mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                        break;
                    case 1:
                        mActivity.startActivity(new Intent(mActivity,RankActivity.class));
                        break;
                    case 2:
                        mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                        break;
                    case 3:
                        mActivity.startActivity(new Intent(mActivity, WordSearchActivity.class));
                        break;
                    case 4:
                        mActivity.startActivity(new Intent(mActivity,NewWordActivity.class));
                        break;
                    case 5:
                        mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                        break;
                    case 6:
                        mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                        break;
                    case 7:
                        mActivity.startActivity(new Intent(mActivity, WedCommonActivity.class));
                        break;
                }
                Utils.StarActivityInAnim(mActivity);
            }
        });
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
