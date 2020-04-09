package com.tramp.word.module.book;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.section.WordBookKeepViewSection;
import com.tramp.word.adapter.section.WordBookNowViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/12.
 */

public class BookMyFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.view_empty)
    LinearLayout ViewEmpty;
    private SectionedRecyclerViewAdapter mSectionAdapter;
    private UserSqlHelper mUserSqlHelper;
    private DefaultBookInfo NewBook;
    private ArrayList<DefaultBookInfo> BookList=new ArrayList<>();

    public static BookMyFragment newInstance(){
        return new BookMyFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_my;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        mUserSqlHelper=new UserSqlHelper(getContext());
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
        if(mUserSqlHelper.isExistBook()){
            initData();
        }else{
            initEmpty();
        }
    }

    public void initEmpty(){
        mSwipeRefreshLayout.setRefreshing(false);
        ViewEmpty.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    public void initData(){
        if(mUserSqlHelper.isBookEmpty()){
            initSql();
        }else{
            initEmpty();
        }
    }

    public void initSql(){
        Cursor cursor1=mUserSqlHelper.BookList();
        while (cursor1.moveToNext()){
            NewBook=new DefaultBookInfo();
            NewBook.setBook_id(cursor1.getInt(cursor1.getColumnIndex("book_id")));
            NewBook.setBook_img(cursor1.getString(cursor1.getColumnIndex("book_img")));
            NewBook.setGood(cursor1.getInt(cursor1.getColumnIndex("good")));
            NewBook.setBook_name(cursor1.getString(cursor1.getColumnIndex("book_name")));
            NewBook.setNew_num(cursor1.getInt(cursor1.getColumnIndex("new_num")));
            NewBook.setTotal_num(cursor1.getInt(cursor1.getColumnIndex("total_num")));
            BookList.add(NewBook);
        }
        Cursor cursor=mUserSqlHelper.NewBook();
        NewBook=new DefaultBookInfo();
        NewBook.setBook_id(cursor.getInt(cursor.getColumnIndex("book_id")));
        NewBook.setBook_img(cursor.getString(cursor.getColumnIndex("book_img")));
        NewBook.setGood(cursor.getInt(cursor.getColumnIndex("good")));
        NewBook.setBook_name(cursor.getString(cursor.getColumnIndex("book_name")));
        NewBook.setNew_num(cursor.getInt(cursor.getColumnIndex("new_num")));
        NewBook.setTotal_num(cursor.getInt(cursor.getColumnIndex("total_num")));
        cursor.close();
        cursor1.close();
        finishTask();
    }

    @Override
    protected void finishTask() {
        if(NewBook==null&&BookList.size()<=0){
            initEmpty();
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        mSectionAdapter.addSection(new WordBookNowViewSection(getActivity(),NewBook));
        mSectionAdapter.addSection(new WordBookKeepViewSection(getActivity(),BookList));
        mSectionAdapter.notifyDataSetChanged();
    }
    public void clearData(){
        mSectionAdapter.removeAllSections();
    }
}










