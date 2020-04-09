package com.tramp.word.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2019/2/7.
 */

public class PullAnimContentView extends ViewGroup {
    private int mlastMoveY;
    private int mLayoutContentHeight;
    public interface OnRefreshListener{
        void refreshFinished();
    }

    private OnRefreshListener mRefreshListener;

    public void setRefreshListener(OnRefreshListener mRefreshListener){
        this.mRefreshListener=mRefreshListener;
    }

    public PullAnimContentView(Context context) {
        super(context);
    }

    public PullAnimContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullAnimContentView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight=0;
        for (int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
            if (i<getChildCount()){
                if(child instanceof ScrollView){
                    mLayoutContentHeight+=getMeasuredHeight();
                    continue;
                }
                mLayoutContentHeight+=child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y=(int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mlastMoveY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy=mlastMoveY-y;
                scrollBy(0,dy);
                Log.d("Pull",""+dy+"scrollY"+getScrollY());
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY()>=mLayoutContentHeight){
                    if(mRefreshListener !=null){
                        mRefreshListener.refreshFinished();
                    }
                }else{
                    releaseWidthStatusTryRefresh();
                    releaseWidthStatusTryLoadMore();
                }
                break;
        }
        mlastMoveY=y;
        return super.onTouchEvent(event);
    }

    private void releaseWidthStatusTryRefresh(){
        scrollBy(0,-getScrollY());
    }

    private void releaseWidthStatusTryLoadMore(){
        scrollBy(0,-getScrollY());
    }

}







