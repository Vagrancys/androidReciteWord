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

public class RankWeekFragment extends RxLazyFragment{
    @BindView(R.id.rank_week_friend)
    TextView RankWeekFriend;
    @BindView(R.id.rank_week_total)
    TextView RankWeekTotal;
    @BindView(R.id.rank_week_swipe)
    SwipeRefreshLayout RankWeekSwipe;
    @BindView(R.id.rank_week_friend_relative)
    RelativeLayout RankWeekRelative;
    @BindView(R.id.rank_week_recycler)
    RecyclerView RankWeekRecycler;
    @BindView(R.id.rank_week_up_relative)
    RelativeLayout RankWeekUpRelative;
    @BindView(R.id.rank_week_relative)
    RelativeLayout RankWeekOneRelative;
    private RankViewDataAdapter mRankViewDataAdapter;
    public static RankWeekFragment newInstance(){
        return new RankWeekFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_rank_week;
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
        RankWeekSwipe.setColorSchemeColors(getResources().getColor(R.color.red));
        RankWeekSwipe.post(()->{
            loadData();
        });
        RankWeekSwipe.setOnRefreshListener(()->{
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RankWeekSwipe.setRefreshing(true);
        finishTask();
    }

    @Override
    protected void finishTask() {
        RankWeekSwipe.setRefreshing(false);
        RankWeekRelative.setVisibility(View.VISIBLE);
        mRankViewDataAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        RankWeekFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankWeekFriend.setBackground(getResources().getDrawable(R.drawable.rank_left_bg));
                RankWeekFriend.setTextColor(getResources().getColor(R.color.white));
                RankWeekTotal.setBackgroundColor(getResources().getColor(R.color.white));
                RankWeekTotal.setTextColor(getResources().getColor(R.color.orange));
                RankWeekRelative.setVisibility(View.GONE);
                RankWeekOneRelative.setVisibility(View.VISIBLE);
                RankWeekUpRelative.setVisibility(View.GONE);
                loadData();
            }
        });

        RankWeekTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankWeekTotal.setBackground(getResources().getDrawable(R.drawable.rank_right_bg));
                RankWeekTotal.setTextColor(getResources().getColor(R.color.white));
                RankWeekFriend.setBackgroundColor(getResources().getColor(R.color.white));
                RankWeekFriend.setTextColor(getResources().getColor(R.color.orange));
                RankWeekRelative.setVisibility(View.GONE);
                RankWeekOneRelative.setVisibility(View.GONE);
                RankWeekUpRelative.setVisibility(View.VISIBLE);
                loadData();
            }
        });
        mRankViewDataAdapter=new RankViewDataAdapter(RankWeekRecycler,getSupportActivity());
        RankWeekRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RankWeekRecycler.setAdapter(mRankViewDataAdapter);
    }
}
