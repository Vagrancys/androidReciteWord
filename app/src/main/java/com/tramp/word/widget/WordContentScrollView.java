package com.tramp.word.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2019/1/28.
 */

public class WordContentScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener=null;

    public WordContentScrollView(Context context){
        super(context);
    }
    public WordContentScrollView(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public WordContentScrollView(Context context, AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener=scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollViewListener !=null){
            scrollViewListener.onScrollItem(t,oldt);
        }
    }

    public interface ScrollViewListener{
        void onScrollItem(int y,int oldy);
    }
}




















