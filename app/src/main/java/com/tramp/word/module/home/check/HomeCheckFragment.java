package com.tramp.word.module.home.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.HomeCheckItemViewSection;
import com.tramp.word.adapter.section.HomeItemViewBottomSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.main.HomeCheckInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/01/09
 * version:1.0
 */

public class HomeCheckFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.home_check)
    TextView HomeCheck;
    @BindView(R.id.home_check_recycler)
    RecyclerView HomeCheckRecycler;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.net_button)
    TextView NetButton;
    private Animation mScaleAnim;
    private HomeCheckInfo Lists;
    private SectionedRecyclerViewAdapter mSection;
    public static HomeCheckFragment newInstance(){
        return new HomeCheckFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_check;
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
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue));
        SwipeRefreshLayout.post(()->{
            SwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        SwipeRefreshLayout.setOnRefreshListener(()->{
            SwipeRefreshLayout.setRefreshing(true);
            clearData();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        Retrofits.getMainAPI()
                .getHomeCheckInfo()
                .enqueue(new Callback<HomeCheckInfo>() {
                    @Override
                    public void onResponse(Call<HomeCheckInfo> call, Response<HomeCheckInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Lists=response.body();
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeCheckInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    @Override
    protected void finishTask() {
        SwipeRefreshLayout.setRefreshing(false);
        mSection.addSection(new HomeCheckItemViewSection(getActivity(),Lists.getBooks()));
        mSection.addSection(new HomeItemViewBottomSection(getActivity(),Lists.getGoods()));
        mSection.addSection(new HomeCheckItemViewSection(getActivity(),Lists.getWords()));
        mSection.notifyDataSetChanged();
    }

    private void initEmpty(){
        SwipeRefreshLayout.setRefreshing(false);
        CommonEmpty.setVisibility(View.VISIBLE);
        SwipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    protected void initRecyclerView() {
        mScaleAnim= AnimationUtils.loadAnimation(getContext(),R.anim.default_button_scale_anim);
        HomeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), WedCommonActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        mSection=new SectionedRecyclerViewAdapter();
        HomeCheckRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        HomeCheckRecycler.setAdapter(mSection);

        NetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NetButton.startAnimation(mScaleAnim);
                clearData();
                loadData();
            }
        });
    }

    public void clearData(){
        mSection.removeAllSections();
    }
}





