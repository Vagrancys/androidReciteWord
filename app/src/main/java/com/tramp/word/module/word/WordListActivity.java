package com.tramp.word.module.word;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tramp.word.R;
import com.tramp.word.adapter.WordListAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.MainTabEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/23.
 *
 */

public class WordListActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    private WordListAdapter mWordListAdapter;
    private String[] mTitles={"已认识","需复习","未学习"};
    private int[] mIconUnSelectIds={
            R.drawable.icon_word_list_known,R.drawable.icon_word_list_review,
            R.drawable.icon_word_list_unstudy,
    };
    private ArrayList<CustomTabEntity> mTabEntities=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word_list;
    }

    @Override
    public void initView(Bundle save) {
        mWordListAdapter=new WordListAdapter(getSupportFragmentManager(),getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mWordListAdapter);
        mViewPager.setCurrentItem(0);
        initFragment();
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setCurrentTab(0);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initFragment(){
        for (int j=0;j<mTitles.length;j++){
            mTabEntities.add(new MainTabEntity(mTitles[j],mIconUnSelectIds[j],mIconUnSelectIds[j]));
        }
    }

    @Override
    protected void initToolBar() {

        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.recite_word_list_text));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
