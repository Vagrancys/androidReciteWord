package com.tramp.word.widget;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2019/1/8.
 */

public class NoScrollViewPager extends ViewPager{
    public NoScrollViewPager(Context context){
        super(context);
    }
    public NoScrollViewPager(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
}











