package com.tramp.word.module.word;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.BookLeftViewAdapter;
import com.tramp.word.adapter.BookRightViewAdapter;
import com.tramp.word.adapter.WholeLanguageLeftAdapter;
import com.tramp.word.adapter.section.WordWholeItemViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.entity.book.BookListInfo;
import com.tramp.word.entity.task.TaskListInfo;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordWholeFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.view_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.word_whole_left_layout)
    LinearLayout mWordWholeLeftLayout;
    @BindView(R.id.word_whole_right_layout)
    LinearLayout mWordWholeRightLayout;
    @BindView(R.id.word_book_menu_left_text)
    TextView mWordBookMenuLeftText;
    @BindView(R.id.word_book_menu_left_img)
    ImageView mWordBookMenuLeftImg;
    @BindView(R.id.word_language_recycler)
    RecyclerView mWordLanguageRecycler;
    @BindView(R.id.word_book_right_head)
    LinearLayout mWordBookRightHead;
    @BindView(R.id.word_book_right_common)
    LinearLayout mWordBookRightCommon;
    @BindView(R.id.word_book_menu_right_text)
    TextView mWordBookMenuRightText;
    @BindView(R.id.word_book_menu_right_img)
    ImageView mWordBookMenuRightImg;
    @BindView(R.id.book_left_recycler)
    RecyclerView mBookLeftRecycler;
    @BindView(R.id.book_right_recycler)
    RecyclerView mBookRightRecycler;

    private Boolean mIsRefreshing=false;
    private int mWholeLanguage=1;
    private int mWholeRightLayout=1;
    private Animation mWordLanguageTopAnim;
    private Animation mWordLanguageBottomAnim;
    private SectionedRecyclerViewAdapter mSectionViewAdapter;
    private WholeLanguageLeftAdapter mWholeLanguageAdapter;
    private BookLeftViewAdapter mBookLeftViewAdapter;
    private BookRightViewAdapter mBookRightViewAdapter;
    private Animation mWordLanguageLayoutTopAnim;
    private Animation mWordLanguageLayoutBottomAnim;
    private Animation mWordBookRightLayoutTopAnim;
    private Animation mWordBookRightLayoutBottomAnim;
    private List<BookListInfo.One> mOne = new ArrayList<>();
    private List<BookListInfo.Two> mTwo = new ArrayList<>();

    private String[] mTitle = {"汉语","英语","韩语","日语","西班牙语","法语","德语"};
    private List<BookItemInfo.Book> mBooks = new ArrayList<>();
    public static WordWholeFragment newInstance(){
        return new WordWholeFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_whole;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mWordLanguageTopAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.word_language_img_top);
        mWordLanguageBottomAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.word_language_img_bottom);
        mWordLanguageLayoutTopAnim= AnimationUtils.loadAnimation(getActivity(),R.anim.word_language_layout_top);
        mWordLanguageLayoutBottomAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.word_language_layout_bottom);
        mWordBookRightLayoutTopAnim= AnimationUtils.loadAnimation(getActivity(),R.anim.word_book_right_layout_top);
        mWordBookRightLayoutBottomAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.word_book_right_layout_bottom);
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
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing=false;
        mSectionViewAdapter.addSection(new WordWholeItemViewSection(getContext(),mBooks));
        mSectionViewAdapter.notifyDataSetChanged();
    }

    public void clearData(){
        mIsRefreshing=false;
        mSectionViewAdapter.removeAllSections();
    }

    @Override
    protected void initRecyclerView() {
        mSectionViewAdapter=new SectionedRecyclerViewAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mSectionViewAdapter);
        initWordLanguage();
        initWordBookNumber();
    }

    public void initWordLanguage(){
        mWordLanguageRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mWholeLanguageAdapter=new WholeLanguageLeftAdapter(mWordLanguageRecycler,mTitle);
        mWordLanguageRecycler.setAdapter(mWholeLanguageAdapter);

        mWordWholeLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWholeLanguage==1){
                    mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.blue));
                    mWordBookMenuLeftImg.startAnimation(mWordLanguageTopAnim);
                    mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu_select);
                    mSwipeRefreshLayout.setEnabled(false);
                    mWordLanguageRecycler.startAnimation(mWordLanguageLayoutBottomAnim);
                    mWordLanguageRecycler.setVisibility(View.VISIBLE);
                    mWholeLanguage=2;
                }else{
                    mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuLeftImg.startAnimation(mWordLanguageBottomAnim);
                    mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordLanguageRecycler.startAnimation(mWordLanguageLayoutTopAnim);
                    mWordLanguageRecycler.setVisibility(View.GONE);
                    mWholeLanguage=1;
                }
            }
        });
    }

    public void initWordBookNumber(){
        mBookLeftRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBookLeftViewAdapter=new BookLeftViewAdapter(mBookLeftRecycler,mOne);
        mBookLeftRecycler.setAdapter(mBookLeftViewAdapter);

        mBookRightRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBookRightViewAdapter=new BookRightViewAdapter(mBookRightRecycler,mTwo);
        mBookRightRecycler.setAdapter(mBookRightViewAdapter);

        mWordWholeRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWholeLanguage==1){
                    mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.blue));
                    mWordBookMenuRightImg.startAnimation(mWordLanguageTopAnim);
                    mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu_select);
                    mSwipeRefreshLayout.setEnabled(false);
                    mWordBookRightHead.startAnimation(mWordLanguageLayoutBottomAnim);
                    mWordBookRightCommon.startAnimation(mWordBookRightLayoutBottomAnim);
                    mWordBookRightHead.setVisibility(View.VISIBLE);
                    mWordBookRightCommon.setVisibility(View.VISIBLE);
                    mWholeLanguage=2;
                }else{
                    mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuRightImg.startAnimation(mWordLanguageBottomAnim);
                    mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordBookRightHead.startAnimation(mWordLanguageLayoutTopAnim);
                    mWordBookRightCommon.startAnimation(mWordBookRightLayoutTopAnim);
                    mWordBookRightHead.setVisibility(View.GONE);
                    mWordBookRightCommon.setVisibility(View.GONE);
                    mWholeLanguage=1;
                }
            }
        });
    }

}














