package com.tramp.word.module.home.recite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.HomeReciteViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.module.common.WordBookActivity;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/8.
 */

public class HomeReciteFragment extends RxLazyFragment implements View.OnClickListener{
    private static String TAG = "HomeReciteFragment";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.recite_word_book)
    ImageView mReciteWordBook;

    private SectionedRecyclerViewAdapter mSectionAdapter;
    private MainAnimInterFace mInterFace;
    private boolean mIsRefreshing = false;

    public static HomeReciteFragment newInstance(){
        return new HomeReciteFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_recite;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        mInterFace=(MainAnimInterFace) getActivity();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        mInterFace.ShowAnimLayout();
        mInterFace.StartAnim();
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing=true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(()->{
            clearData();
            loadData();
        });

    }

    @Override
    protected void loadData() {
        finishTask();
    }

    @Override
    protected void finishTask() {
        mInterFace.HideAnimLayout();
        Log.e(TAG,"星星动画!");
        mInterFace.StopAnim();
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing=false;
        mSectionAdapter.addSection(new HomeReciteViewSection(getActivity()));
        mSectionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mSectionAdapter=new SectionedRecyclerViewAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mSectionAdapter);
    }

    @OnClick({R.id.recite_word_book})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recite_word_book:
                Intent intent=new Intent(getActivity(), WordBookActivity.class);
                getSupportActivity().startActivityForResult(intent,0);
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
                break;
        }
    }

    public void clearData(){
        mIsRefreshing = true;
        mSectionAdapter.removeAllSections();
    }

}
















