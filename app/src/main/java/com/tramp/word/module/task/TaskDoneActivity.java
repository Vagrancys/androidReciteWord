package com.tramp.word.module.task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.TaskDoneViewAdapter;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/19.
 */

public class TaskDoneActivity extends RxBaseActivity {
    @BindView(R.id.task_done_out)
    ImageView mTaskDoneOut;
    @BindView(R.id.task_done_recycler)
    RecyclerView mTaskDoneRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private TaskDoneViewAdapter mTaskDoneViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_task_done;
    }

    @Override
    protected void initToolBar() {
        mTaskDoneOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        initRefreshLayout();
        initRecyclerView();
    }

    public void initRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                loadData();
            }
        });
    }

    public void initRecyclerView(){
        mTaskDoneViewAdapter=new TaskDoneViewAdapter(mTaskDoneRecycler);
        mTaskDoneRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mTaskDoneRecycler.setAdapter(mTaskDoneViewAdapter);
    }

    public void loadData(){
        finishTask();
    }

    public void clearData(){
        mTaskDoneViewAdapter.notifyDataSetChanged();
    }

    public void finishTask(){
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}






