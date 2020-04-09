package com.tramp.word.module.home.find;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.HomeFindBannerSection;
import com.tramp.word.adapter.section.HomeFindGridViewSection;
import com.tramp.word.adapter.section.HomeFindItemViewSection;
import com.tramp.word.adapter.section.HomeFindViewSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.main.HomeFindInfo;
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

public class HomeFindFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.home_find_recycler)
    RecyclerView HomeFindRecycler;
    private HomeFindInfo.find Find;
    private SectionedRecyclerViewAdapter mSection;
    public static HomeFindFragment newInstance(){
        return new HomeFindFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_find;
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
                .getHomeFindInfo()
                .enqueue(new Callback<HomeFindInfo>() {
                    @Override
                    public void onResponse(Call<HomeFindInfo> call, Response<HomeFindInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Find=response.body().getFinds();
                            finishTask();
                        }else{
                            SwipeRefreshLayout.setRefreshing(false);
                            Utils.ShowToast(getContext(),"数据没有了!");
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeFindInfo> call, Throwable t) {
                        SwipeRefreshLayout.setRefreshing(false);
                        Utils.ShowToast(getContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    @Override
    protected void finishTask() {
        SwipeRefreshLayout.setRefreshing(false);
        mSection.addSection(new HomeFindBannerSection(getActivity(),Find.getBanners(),Find.getTag_title()));
        mSection.addSection(new HomeFindViewSection(getActivity(),Find.getWords()));
        mSection.addSection(new HomeFindItemViewSection(getActivity(),Find.getGoods()));
        mSection.addSection(new HomeFindItemViewSection(getActivity(),Find.getItems()));
        mSection.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mSection=new SectionedRecyclerViewAdapter();
        mSection.addSection(new HomeFindGridViewSection(getActivity()));
        HomeFindRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        HomeFindRecycler.setAdapter(mSection);
    }

    public void clearData(){
        mSection.removeAllSections();
    }
}
