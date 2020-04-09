package com.tramp.word.widget.dataview;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.tramp.word.entity.user.UserDataInfo;
import com.tramp.word.module.user.UserRecordingFragment;
import com.tramp.word.utils.DateUtils;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/21
 * version:1.0
 */
public class MonthViewPagerListener implements ViewPager.OnPageChangeListener {
    private MonthDateView[] mShowViews;
    private int mCurrIndex=498;
    private ViewPager mPager;
    private ScrollDirection mDirection =ScrollDirection.NO_SCROLL;
    private List<UserDataInfo.Day> Days;
    private CustomDate mShowDate=new CustomDate(DateUtils.getYear(), DateUtils.getMonth()+1,DateUtils.getCurrentMonthDay());
    public MonthViewPagerListener(ViewPager viewPager, UserRecordingFragment.MonthViewPager<MonthDateView> viewAdapter, List<UserDataInfo.Day> days){
        this.mShowViews=viewAdapter.getAllItems();
        this.mPager=viewPager;
        this.Days=days;
    }
    @Override
    public void onPageSelected(int position) {
        Log.e("listener","year"+position);
        measureDirection(position);
        updateMonthView(position);
    }

    private void updateMonthView(int position){
        if(mDirection==ScrollDirection.RIGHT){
            mShowViews[position%mShowViews.length].rightScroll(Days,mShowDate);
        }else if(mDirection==ScrollDirection.LEFT){
            mShowViews[position%mShowViews.length].leftScroll(Days,mShowDate);
        }
        mDirection=ScrollDirection.NO_SCROLL;
    }

    private void measureDirection(int position){
        if(position>mCurrIndex){
            if(mShowDate.month==12){
                mShowDate.month=1;
                mShowDate.year+=1;
            }else{
                mShowDate.month+=1;
            }
            mDirection=ScrollDirection.RIGHT;
        }else if(position<mCurrIndex){
            if(mShowDate.month==1){
                mShowDate.month=12;
                mShowDate.year-=1;
            }else{
                mShowDate.month-=1;
            }
            mDirection=ScrollDirection.LEFT;
        }
        mCurrIndex=position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    enum ScrollDirection{
        RIGHT,LEFT,NO_SCROLL
    }

    public void setDays(List<UserDataInfo.Day> mDays){
        Days.clear();
        Days.addAll(mDays);
    }
}
