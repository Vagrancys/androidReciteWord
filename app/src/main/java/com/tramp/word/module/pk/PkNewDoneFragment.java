package com.tramp.word.module.pk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.WordNewsViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.pk.PkDataInfo;

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

public class PkNewDoneFragment extends RxLazyFragment{
    @BindView(R.id.word_news_swipe)
    SwipeRefreshLayout WordNewsSwipe;
    @BindView(R.id.word_news_recycler)
    RecyclerView WordNewsRecycler;
    @BindView(R.id.view_empty)
    LinearLayout ViewEmpty;
    private WordNewsViewAdapter mWordNewsViewAdapter;
    private int user_id;
    private ArrayList<PkDataInfo.Data> datas=new ArrayList<>();

    public static PkNewDoneFragment newInstance(){
        return new PkNewDoneFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pk_done;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        user_id=new UserSqlHelper(getContext()).UserId();
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
        WordNewsSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        WordNewsSwipe.post(()->{
            WordNewsSwipe.setRefreshing(true);
            loadData();
        });
        WordNewsSwipe.setOnRefreshListener(()->{
            WordNewsSwipe.setRefreshing(true);
            clearData();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        Retrofits.getPkAPI().getPkDataInfo(user_id)
                .enqueue(new Callback<PkDataInfo>() {
                    @Override
                    public void onResponse(Call<PkDataInfo> call, Response<PkDataInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            datas.addAll(response.body().getData());
                            if(datas.size()==0){
                                initEmpty();
                                return;
                            }
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkDataInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void initEmpty(){
        WordNewsSwipe.setRefreshing(false);
        ViewEmpty.setVisibility(View.VISIBLE);
        WordNewsRecycler.setVisibility(View.GONE);
    }

    @Override
    protected void finishTask() {
        WordNewsSwipe.setRefreshing(false);
        mWordNewsViewAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initRecyclerView() {
        mWordNewsViewAdapter=new WordNewsViewAdapter(WordNewsRecycler,datas,getSupportActivity());
        WordNewsRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        WordNewsRecycler.setAdapter(mWordNewsViewAdapter);
        mWordNewsViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                PkDetailsActivity.launch(getSupportActivity(),datas.get(position).getData_id());
            }
        });
    }

    public void clearData(){
        datas.clear();
    }

}
