package com.tramp.word.module.pk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxLazyFragment;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class WordNewsFragment extends RxLazyFragment{
    @BindView(R.id.word_news_new)
    TextView WordNewsNew;
    @BindView(R.id.word_news_finish)
    TextView WordNewsFinish;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private ViewPagerAdapter mView;
    public static WordNewsFragment newInstance(){
        return new WordNewsFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_news;
    }

    @Override
    public void finishCreateView(Bundle state) {
        WordNewsNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordNewsNew.setTextColor(getResources().getColor(R.color.white));
                WordNewsNew.setBackground(getResources().getDrawable(R.drawable.word_text_left_bg));
                WordNewsFinish.setTextColor(getResources().getColor(R.color.red));
                WordNewsFinish.setBackground(getResources().getDrawable(R.drawable.btn_background_radius));
                mViewPager.setCurrentItem(0);
            }
        });

        WordNewsFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordNewsNew.setTextColor(getResources().getColor(R.color.red));
                WordNewsNew.setBackground(getResources().getDrawable(R.drawable.btn_background_radius));
                WordNewsFinish.setTextColor(getResources().getColor(R.color.white));
                WordNewsFinish.setBackground(getResources().getDrawable(R.drawable.word_text_right_bg));
                mViewPager.setCurrentItem(1);
            }
        });
        mView=new ViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mView);
        mViewPager.setCurrentItem(0);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter{
        private Fragment[] mFragment;
        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[2];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=PkNewModernFragment.newInstance();
                    break;
                case 1:
                    mFragment[position]=PkNewDoneFragment.newInstance();
                    break;
            }
            return mFragment[position];
        }

        @Override
        public int getCount() {
            return mFragment.length;
        }
    }
}




