package com.tramp.word.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.SearchEnglishHistoryAdapter;
import com.tramp.word.adapter.SearchEnglishViewAdapter;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/12
 * version:1.0
 */

public class SearchEnglishFragment extends RxLazyFragment{
    @BindView(R.id.search_english_recycler)
    RecyclerView SearchEnglishRecycler;
    @BindView(R.id.search_english_history_recycler)
    RecyclerView SearchEnglishHistoryRecycler;
    private SearchEnglishViewAdapter mSearch;
    private SearchEnglishHistoryAdapter mSearchHistory;
    public static SearchEnglishFragment newInstance(){
        return new SearchEnglishFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_english;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        loadData();
    }

    @Override
    protected void loadData() {
        finishTask();
    }

    @Override
    protected void initRecyclerView() {
        mSearch=new SearchEnglishViewAdapter(SearchEnglishRecycler);
        SearchEnglishRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        SearchEnglishRecycler.setAdapter(mSearch);
        mSearch.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                getSupportActivity().startActivity(new Intent(getSupportActivity(),WordSearchDetailsActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });
        mSearchHistory=new SearchEnglishHistoryAdapter(SearchEnglishHistoryRecycler);
        SearchEnglishHistoryRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        SearchEnglishHistoryRecycler.setAdapter(mSearchHistory);
        mSearchHistory.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(position==3){
                    SearchEnglishHistoryRecycler.setVisibility(View.GONE);
                    mSearchHistory.notifyDataSetChanged();
                }
                getSupportActivity().startActivity(new Intent(getSupportActivity(),WordSearchDetailsActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });
    }

    public void DataChange(String text) {
        loadData();
    }
}









