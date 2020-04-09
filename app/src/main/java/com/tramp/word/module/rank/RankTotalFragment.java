package com.tramp.word.module.rank;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.RankViewDataAdapter;
import com.tramp.word.base.RxLazyFragment;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/11
 * version:1.0
 */

public class RankTotalFragment extends RxLazyFragment{
    @BindView(R.id.rank_total_friend)
    TextView RankTotalFriend;
    @BindView(R.id.rank_total_total)
    TextView RankTotalTotal;
    @BindView(R.id.rank_total_swipe)
    SwipeRefreshLayout RankTotalSwipe;
    @BindView(R.id.rank_total_friend_relative)
    RelativeLayout RankTotalRelative;
    @BindView(R.id.rank_total_recycler)
    RecyclerView RankTotalRecycler;
    @BindView(R.id.rank_total_up_relative)
    RelativeLayout RankTotalUpRelative;
    @BindView(R.id.rank_total_relative)
    RelativeLayout RankTotalOneRelative;
    private RankViewDataAdapter mRankViewDataAdapter;
    public static RankTotalFragment newInstance(){
        return new RankTotalFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_rank_total;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }



    @Override
    protected void initRefreshLayout() {
        RankTotalSwipe.setColorSchemeColors(getResources().getColor(R.color.red));
        RankTotalSwipe.post(()->{
            loadData();
        });
        RankTotalSwipe.setOnRefreshListener(()->{
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RankTotalSwipe.setRefreshing(true);
        finishTask();
    }

    @Override
    protected void finishTask() {
        RankTotalSwipe.setRefreshing(false);
        RankTotalRelative.setVisibility(View.VISIBLE);
        mRankViewDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        RankTotalFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankTotalFriend.setBackground(getResources().getDrawable(R.drawable.rank_left_bg));
                RankTotalFriend.setTextColor(getResources().getColor(R.color.white));
                RankTotalTotal.setBackgroundColor(getResources().getColor(R.color.white));
                RankTotalTotal.setTextColor(getResources().getColor(R.color.orange));
                RankTotalRelative.setVisibility(View.GONE);
                RankTotalOneRelative.setVisibility(View.VISIBLE);
                RankTotalUpRelative.setVisibility(View.GONE);
                loadData();
            }
        });

        RankTotalTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankTotalTotal.setBackground(getResources().getDrawable(R.drawable.rank_right_bg));
                RankTotalTotal.setTextColor(getResources().getColor(R.color.white));
                RankTotalFriend.setBackgroundColor(getResources().getColor(R.color.white));
                RankTotalFriend.setTextColor(getResources().getColor(R.color.orange));
                RankTotalRelative.setVisibility(View.GONE);
                RankTotalOneRelative.setVisibility(View.GONE);
                RankTotalUpRelative.setVisibility(View.VISIBLE);
                loadData();
            }
        });
        mRankViewDataAdapter=new RankViewDataAdapter(RankTotalRecycler,getSupportActivity());
        RankTotalRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RankTotalRecycler.setAdapter(mRankViewDataAdapter);
    }
}
