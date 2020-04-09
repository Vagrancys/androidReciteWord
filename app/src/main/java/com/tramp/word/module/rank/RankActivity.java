package com.tramp.word.module.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/11
 * version:1.0
 */

public class RankActivity extends RxBaseActivity {
    @BindView(R.id.rank_out)
    ImageView RankOut;
    @BindView(R.id.rank_sliding)
    SlidingTabLayout RankSliding;
    @BindView(R.id.rank_view_pager)
    ViewPager RankPager;
    private RankViewPager mRankViewPager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_rank;
    }

    @Override
    public void initView(Bundle save) {
        mRankViewPager=new RankViewPager(getSupportFragmentManager());
        RankPager.setOffscreenPageLimit(2);
        RankPager.setAdapter(mRankViewPager);
        RankPager.setCurrentItem(0);
        RankSliding.setViewPager(RankPager);
    }

    @Override
    protected void initToolBar() {
        RankOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public class RankViewPager extends FragmentPagerAdapter{
        private Fragment[] mFragment;
        private String[] mTitle={"总排行","周排行"};
        public RankViewPager(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[mTitle.length];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=RankTotalFragment.newInstance();
                    break;
                case 1:
                    mFragment[position]=RankWeekFragment.newInstance();
                    break;
            }
            return mFragment[position];
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }
}
