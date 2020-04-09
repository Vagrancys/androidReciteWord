package com.tramp.word.module.pk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.WordModernViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.pk.PkModernInfo;
import com.tramp.word.port.PkKnowInterFace;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/10
 * version:1.0
 */

public class PkNewModernFragment extends RxLazyFragment implements PkKnowInterFace{
    @BindView(R.id.word_news_swipe)
    SwipeRefreshLayout WordModernSwipe;
    @BindView(R.id.word_news_recycler)
    RecyclerView WordModernRecycler;
    @BindView(R.id.view_empty)
    LinearLayout ViewEmpty;
    private WordModernViewAdapter mWordModernViewAdapter;
    private ArrayList<PkModernInfo.Modern> dates=new ArrayList<>();

    public static PkNewModernFragment newInstance(){
        return new PkNewModernFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pk_modern;
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
        WordModernSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        WordModernSwipe.post(()->{
            WordModernSwipe.setRefreshing(true);
            loadData();
        });
        WordModernSwipe.setOnRefreshListener(()->{
            WordModernSwipe.setRefreshing(true);
            clearData();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        Retrofits.getPkAPI().getPkModernInfo(new UserSqlHelper(getContext()).UserId())
                .enqueue(new Callback<PkModernInfo>() {
                    @Override
                    public void onResponse(Call<PkModernInfo> call, Response<PkModernInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            dates.addAll(response.body().getModerns());
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkModernInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void initEmpty(){
        WordModernSwipe.setRefreshing(false);
        ViewEmpty.setVisibility(View.VISIBLE);
        WordModernRecycler.setVisibility(View.GONE);
    }

    @Override
    protected void finishTask() {
        WordModernSwipe.setRefreshing(false);
        mWordModernViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void SwipeLayout() {
        clearData();
        loadData();
    }

    @Override
    protected void initRecyclerView() {
        mWordModernViewAdapter=new WordModernViewAdapter(WordModernRecycler,dates,this);
        WordModernRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        WordModernRecycler.setAdapter(mWordModernViewAdapter);
    }

    public void clearData(){
        dates.clear();
    }
}
