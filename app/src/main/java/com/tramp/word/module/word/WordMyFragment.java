package com.tramp.word.module.word;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tramp.word.R;
import com.tramp.word.adapter.section.WordBookKeepViewSection;
import com.tramp.word.adapter.section.WordBookNowViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.task.TaskListInfo;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordMyFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private boolean mIsRefresh=false;
    private SectionedRecyclerViewAdapter mSectionAdapter;
    private DefaultBookInfo mInfo = new DefaultBookInfo();
    private List<DefaultBookInfo> mInfos = new ArrayList<>();

    public static WordMyFragment newInstance(){
        return new WordMyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_my;
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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefresh=true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(()->{
            clearData();
            loadData();
        });
    }

    @Override
    protected void initRecyclerView() {
        mSectionAdapter=new SectionedRecyclerViewAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mSectionAdapter);
    }

    @Override
    protected void loadData() {
        finishTask();
    }

    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefresh=false;
        mSectionAdapter.addSection(new WordBookNowViewSection(getActivity(),mInfo));
        mSectionAdapter.addSection(new WordBookKeepViewSection(getActivity(),mInfos));
        mSectionAdapter.notifyDataSetChanged();
    }
    public void clearData(){
        mIsRefresh=true;
        mSectionAdapter.removeAllSections();
    }
}










