package com.tramp.word.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/26.
 */

public class PullWordRefreshLayout extends ViewGroup {
    private View mHeader;
    private View mFooter;
    private ImageView mHeaderImg;
    private TextView mHeaderText;
    private ImageView mFooterImg;
    private TextView mFooterText;
    private int lastChildIndex;
    private int mLayoutContentHeight;
    private int mEffectiveHeaderHeight;
    private int mEffectiveFooterHeight;
    private int mlastMoveY;
    private int mLastYIntercept;
    private List<DefaultWordInfo> words=new ArrayList<>();
    private int list_number=1;
    private Status mStatus=Status.NORMAL;
    private enum  Status{
        NORMAL,TRY_REFRESH,REFRESHING,TRY_LOADMORE,LOADING
    }

    public void setWords(List<DefaultWordInfo> words){
        this.words=words;
    }
    private void updateStatus(Status status) {
        mStatus = status;
    }

    public interface OnRefreshListener{
        void refreshFinished();
        void loadMoreFinished();
        void HeaderLayout(int y);
        void FooterLayout(int y);
    }

    private OnRefreshListener mRefreshListener;

    public void setRefreshListener(OnRefreshListener mRefreshListener){
        this.mRefreshListener=mRefreshListener;
    }

    public PullWordRefreshLayout(Context context) {
        super(context);
    }

    public PullWordRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullWordRefreshLayout(Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        lastChildIndex=getChildCount()-1;
        addHeader();
        addFooter();
    }

    private void addHeader(){
        mHeader= LayoutInflater.from(getContext()).inflate(R.layout.pull_item,null,false);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(mHeader,params);

        mHeaderImg=(ImageView) mHeader.findViewById(R.id.pull_word_img);
        mHeaderText=(TextView) mHeader.findViewById(R.id.pull_word_text);
    }

    private void addFooter(){
        mFooter=LayoutInflater.from(getContext()).inflate(R.layout.pull_item,null,false);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        addView(mFooter,params);

        mFooterImg=(ImageView) mFooter.findViewById(R.id.pull_word_img);
        mFooterText=(TextView) mFooter.findViewById(R.id.pull_word_text);
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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight=0;
        for (int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            if(child==mHeader){
                child.layout(0,0-child.getMeasuredHeight(),child.getMeasuredWidth(),0);
                mEffectiveHeaderHeight=child.getHeight();
            }else if(child==mFooter){
                child.layout(0,mLayoutContentHeight,child.getMeasuredWidth(),mLayoutContentHeight+child.getMeasuredHeight());
                mEffectiveFooterHeight=child.getHeight();
            }else{
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
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept=false;
        int y=(int) event.getY();
        if(mStatus==Status.REFRESHING||mStatus==Status.LOADING){
            return false;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                mlastMoveY=y;
                intercept=false;
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if(y>mLastYIntercept){
                    View child=getChildAt(0);
                    intercept=getRefreshIntercept(child);
                    if(intercept){
                        updateStatus(mStatus.TRY_REFRESH);
                    }
                }else if(y<mLastYIntercept){
                    View child=getChildAt(lastChildIndex);
                    intercept=getLoadMoreIntercept(child);
                    if(intercept){
                        updateStatus(mStatus.TRY_LOADMORE);
                    }
                }else {
                    intercept=false;
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                intercept=false;
                break;
            }
        }
        mLastYIntercept=y;
        return intercept;
    }

    private boolean getRefreshIntercept(View child){
        boolean intercept=false;
        if(child instanceof AdapterView){
            intercept=adapterViewRefreshIntercept(child);
        }else if(child instanceof ScrollView){
            intercept=scrollViewRefreshIntercept(child);
        }else if(child instanceof RecyclerView){
            intercept=recyclerViewRefreshIntercept(child);
        }
        return intercept;
    }

    private boolean getLoadMoreIntercept(View child){
        boolean intercept=false;
        if(child instanceof AdapterView){
            intercept=adapterViewLoadMoreIntercept(child);
        }else if(child instanceof ScrollView){
            intercept=scrollViewLoadMoreIntercept(child);
        }else if(child instanceof RecyclerView){
            intercept=recyclerViewLoadMoreIntercept(child);
        }
        return intercept;
    }


    private boolean adapterViewRefreshIntercept(View child){
        boolean intercept=false;
        AdapterView adapterChild=(AdapterView) child;
        if(adapterChild.getFirstVisiblePosition()!=0||adapterChild.getChildAt(0).getTop()!=0){
            intercept=false;
        }
        return intercept;
    }

    private boolean adapterViewLoadMoreIntercept(View child){
        boolean intercept=false;
        AdapterView adapterChild=(AdapterView) child;
        if(adapterChild.getLastVisiblePosition()==adapterChild.getChildCount()-1
                &&(adapterChild.getChildAt(adapterChild.getChildCount()-1).getBottom()>=getMeasuredHeight())){
            intercept=true;
        }
        return intercept;
    }

    private boolean scrollViewRefreshIntercept(View child){
        boolean intercept=false;
        if(child.getScrollY()<=0){
            intercept=true;
        }
        return intercept;
    }

    private boolean scrollViewLoadMoreIntercept(View child){
        boolean intercept=false;
        ScrollView scrollView=(ScrollView) child;
        View scrollChild=scrollView.getChildAt(0);
        if(scrollView.getScrollY()>=(scrollChild.getHeight()-scrollView.getHeight())){
            intercept=true;
        }
        return intercept;
    }

    private boolean recyclerViewRefreshIntercept(View child){
        boolean intercept=false;
        RecyclerView recyclerView=(RecyclerView) child;
        if(recyclerView.computeHorizontalScrollOffset()<=0){
            intercept=true;
        }
        return intercept;
    }

    private boolean recyclerViewLoadMoreIntercept(View child){
        boolean intercept=false;
        RecyclerView recyclerView=(RecyclerView) child;
        if(recyclerView.computeHorizontalScrollExtent()+recyclerView.computeHorizontalScrollOffset()>=recyclerView.computeHorizontalScrollRange()){
            intercept=true;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y=(int) event.getY();
        if(mStatus==Status.REFRESHING||mStatus==Status.LOADING){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mlastMoveY=y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy=mlastMoveY-y;
                if(getScrollY()<=0&&dy<=0){
                    if(mStatus==Status.TRY_LOADMORE){
                        scrollBy(0,dy/100);
                    }else{
                        scrollBy(0,dy/3);
                    }
                    if(mRefreshListener !=null){
                        mRefreshListener.HeaderLayout(getScrollY());
                    }
                }else if(getScrollY()>=0&&dy>=0){
                    if(mStatus==Status.TRY_REFRESH){
                        scrollBy(0,dy/100);
                    }else{
                        scrollBy(0,dy/3);
                    }
                    if(mRefreshListener !=null){
                        mRefreshListener.FooterLayout(getScrollY());
                    }
                }else{
                    scrollBy(0,dy/3);
                }
                beforeRefreshing();
                beforeLoadMore();
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY()<=-mEffectiveHeaderHeight){
                    releaseWidthStatusRefresh();
                    if(mRefreshListener !=null){
                        mRefreshListener.refreshFinished();
                    }
                }else if(getScrollY()>=mEffectiveFooterHeight){
                    releaseWidthStatusLoadMore();
                    if(mRefreshListener !=null){
                        mRefreshListener.loadMoreFinished();
                    }
                }
                releaseWidthStatusTryRefresh();
                releaseWidthStatusTryLoadMore();

                break;
        }
        mlastMoveY=y;
        return super.onTouchEvent(event);
    }

    public void beforeRefreshing(){
        int scrollY=(int) Math.abs(getScaleY());
        scrollY=scrollY>mEffectiveHeaderHeight?mEffectiveHeaderHeight:scrollY;
        float angle=(float) (scrollY*1.0/mEffectiveHeaderHeight*180);
        mHeaderImg.setRotation(angle);
        if(list_number==0){
            mHeaderImg.setImageResource(R.drawable.icon_smile);
            mHeaderText.setText(getResources().getString(R.string.word_pull_hint));
        }else {
            if(getScrollY()<=-mEffectiveHeaderHeight){
                mHeaderImg.setImageResource(R.drawable.icon_pullup);
            }else{
                mHeaderImg.setImageResource(R.drawable.icon_pulldown);
            }
            mHeaderText.setText(words.get(list_number).getWord_name());
        }

    }

    public void beforeLoadMore(){
        int scrollY=(int) Math.abs(getScaleY());
        scrollY=scrollY>mEffectiveFooterHeight?mEffectiveFooterHeight:scrollY;
        float angle=(float) (scrollY*1.0/mEffectiveFooterHeight*180);
        mFooterImg.setRotation(angle);
        if(list_number==words.size()-1){
           mFooterImg.setImageResource(R.drawable.icon_smile);
            mFooterText.setText(getResources().getString(R.string.word_pull_hint));
        }else {
            if(getScrollY()>=mEffectiveHeaderHeight){
                mFooterImg.setImageResource(R.drawable.icon_pullup);
            }else{
                mFooterImg.setImageResource(R.drawable.icon_pulldown);
            }
            mFooterText.setText(words.get(list_number).getWord_name());
        }

    }

    private void releaseWidthStatusTryRefresh(){
        scrollBy(0,-getScrollY());
        mHeaderText.setText("geo");
        updateStatus(Status.NORMAL);
        list_number--;
    }

    private void releaseWidthStatusTryLoadMore(){
        scrollBy(0,-getScrollY());
        mFooterText.setText("write");
        updateStatus(Status.NORMAL);
        list_number++;
    }

    private void releaseWidthStatusLoadMore(){
        updateStatus(Status.LOADING);
    }

    private void releaseWidthStatusRefresh() {
        updateStatus(Status.REFRESHING);
    }

    public void refreshFinished(){
        scrollTo(0,0);
        mHeaderText.setText("especially");
        mHeaderImg.setImageResource(R.drawable.icon_pulldown);
        updateStatus(Status.NORMAL);
    }

    public void loadMoreFinished(){
        mFooterText.setText("geography");
        mFooterImg.setImageResource(R.drawable.icon_pullup);
        scrollTo(0,0);
        updateStatus(Status.NORMAL);
    }
}





















