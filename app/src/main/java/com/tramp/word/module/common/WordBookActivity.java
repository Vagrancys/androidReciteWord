package com.tramp.word.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.util.Log;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.module.search.WordSearchActivity;
import com.tramp.word.module.word.WordMyFragment;
import com.tramp.word.module.word.WordWholeFragment;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.widget.NoScrollViewPager;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/11.
 */

public class WordBookActivity extends RxBaseActivity implements WordBookPopInterFace{
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.word_book_sliding_tab)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.word_book_out)
    ImageView mWordBookOut;
    @BindView(R.id.word_book_search)
    ImageView mWordSearch;
    @BindView(R.id.book_background)
    RelativeLayout mWordBookBackground;
    @BindView(R.id.word_book_pop)
    RelativeLayout mWordBookPop;
    @BindView(R.id.pop_exit)
    ImageView mWordBookPopExit;
    @BindView(R.id.pop_button)
    TextView mWordBookPopButton;

    private WordPagerAdapter mWordAdapter;
    private Animation mWordBookPopInAnim;
    private Animation mWordBookPopOutAnim;
    private Animation mMainTabAnim;
    private Handler mHandler;

    @Override
    public int getLayoutId() {
        return R.layout.activity_word_book;
    }

    @Override
    public void initView(Bundle save) {
        mWordAdapter=new WordPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mWordAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mWordBookPopInAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.word_book_pop_in_anim);
        mWordBookPopOutAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.word_book_pop_out_anim);
        mMainTabAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.main_tab_icon_anim);
        mHandler=new Handler();
        initPopItemView();
    }

    @Override
    protected void initToolBar() {
        mWordBookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("status",1);
                setResult(1,intent);
                finish();
            }
        });

        mWordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WordBookActivity.this, WordSearchActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public void initPopItemView(){
        mWordBookPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordBookPop.startAnimation(mWordBookPopOutAnim);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWordBookBackground.setVisibility(View.GONE);
                        mWordBookPop.setVisibility(View.GONE);
                    }
                },250);

            }
        });

        mWordBookPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordBookPopButton.startAnimation(mMainTabAnim);
                mWordBookPop.startAnimation(mWordBookPopOutAnim);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWordBookBackground.setVisibility(View.GONE);
                        mWordBookPop.setVisibility(View.GONE);
                        Intent intent=new Intent();
                        intent.putExtra("status",2);
                        intent.putExtra("data","100");
                        setResult(1,intent);
                        finish();
                    }
                },400);
            }
        });
    }

    @Override
    public void ShowWordBookItemPop(DefaultBookInfo book, int delete) {

    }

    @Override
    public void ShowWordBookPop(BookItemInfo.Book bool) {
        mViewPager.setFocusable(false);
        mWordBookBackground.setVisibility(View.VISIBLE);
        mWordBookPop.startAnimation(mWordBookPopInAnim);
        mWordBookPop.setVisibility(View.VISIBLE);
    }

    public class WordPagerAdapter extends FragmentPagerAdapter{
        private String[] TITLE={"我的","全部"};
        private Fragment[] fragments;
        public WordPagerAdapter(FragmentManager fm, Context context){
            super(fm);
            fragments=new Fragment[TITLE.length];

        }
        @Override
        public Fragment getItem(int position) {
            if(fragments[position]==null){
                switch (position){
                    case 0:
                        fragments[position]= WordMyFragment.newInstance();
                        break;
                    case 1:
                        fragments[position]= WordWholeFragment.newInstance();
                        break;
                    default:
                        break;
                }
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }
    }

}









