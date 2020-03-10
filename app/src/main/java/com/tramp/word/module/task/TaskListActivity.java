package com.tramp.word.module.task;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.section.TaskListAdvertiseSection;
import com.tramp.word.adapter.section.TaskListSelectSection;
import com.tramp.word.adapter.section.TaskListUnSelectSection;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.port.TaskListInterFace;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/17.
 */

public class TaskListActivity extends RxBaseActivity implements TaskListInterFace{
    @BindView(R.id.task_list_out)
    ImageView mTaskListOut;
    @BindView(R.id.task_list_menu)
    ImageView mTaskListMenu;
    @BindView(R.id.task_list_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.task_list_pop)
    RelativeLayout mTaskListPop;
    @BindView(R.id.task_list_background)
    RelativeLayout mTaskListBackground;
    @BindView(R.id.task_list_pop_exit)
    ImageView mTaskListPopExit;
    private ObjectAnimator mTaskTopOneAnim;
    private ObjectAnimator mTaskTopTwoAnim;
    private SectionedRecyclerViewAdapter mSectionAdapter;
    private Animation mTaskListExitAnim;
    private Handler mHandler=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void initToolBar() {
        mTaskListOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTaskListMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListActivity.this,TaskDoneActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initRefreshLayout();
        initRecyclerView();

    }

    public void initAnim(){
        mTaskListExitAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        DisplayMetrics metric=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float MaxHeight=metric.widthPixels;
        float curTranslationY=mTaskListPop.getTranslationY();

        mTaskTopOneAnim= ObjectAnimator.ofFloat(mTaskListPop,"translationY",MaxHeight,curTranslationY-70,curTranslationY);
        mTaskTopOneAnim.setDuration(500);
        mTaskTopTwoAnim= ObjectAnimator.ofFloat(mTaskListPop,"translationY",curTranslationY,curTranslationY-70,MaxHeight+350);
        mTaskTopTwoAnim.setDuration(500);

        mTaskListPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskListPopExit.startAnimation(mTaskListExitAnim);
                mTaskTopTwoAnim.start();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTaskListPop.setVisibility(View.GONE);
                        mTaskListBackground.setVisibility(View.GONE);
                    }
                },550);
            }
        });
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
        mSectionAdapter=new SectionedRecyclerViewAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mRecycler.setAdapter(mSectionAdapter);
    }

    public void loadData(){
        finishTask();
    }

    public void clearData(){
        mSectionAdapter.removeAllSections();
    }

    public void finishTask(){
        mSwipeRefreshLayout.setRefreshing(false);
        mSectionAdapter.addSection(new TaskListAdvertiseSection(getBaseContext()));
        mSectionAdapter.addSection(new TaskListSelectSection(this));
        mSectionAdapter.addSection(new TaskListUnSelectSection(this));
        mSectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowLayout(int value) {
        mTaskListBackground.setVisibility(View.VISIBLE);
        mTaskListPop.setVisibility(View.VISIBLE);
        mTaskTopOneAnim.start();
    }

    @Override
    public void HideLayout(int value) {
        mTaskTopTwoAnim.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTaskListPop.setVisibility(View.GONE);
                mTaskListBackground.setVisibility(View.GONE);
            }
        },550);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}









