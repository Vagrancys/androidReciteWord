package com.tramp.word.module.home.me;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.SearchMemberAdapter;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/25.
 */

public class SearchMemberActivity extends RxBaseActivity {
    @BindView(R.id.search_member_out)
    ImageView mSearchMemberOut;
    @BindView(R.id.search_member_edit)
    EditText mSearchMemberEdit;
    @BindView(R.id.search_member_img)
    ImageView mSearchMemberImg;
    @BindView(R.id.search_member_recycler)
    RecyclerView mSearchMemberRecycler;
    @BindView(R.id.search_member_relative)
    RelativeLayout mSearchMemberRelative;
    private SearchMemberAdapter mSearchMemberAdapter;
    private boolean finishing;
    private float originX;
    @Override
    public int getLayoutId() {
        return R.layout.activity_search_member;
    }

    @Override
    public void initView(Bundle save) {
        mSearchMemberEdit.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mSearchMemberEdit.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        performEnterAnimation();
                    }
                });

        mSearchMemberAdapter = new SearchMemberAdapter(mSearchMemberRecycler,this);
        mSearchMemberRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mSearchMemberRecycler.setAdapter(mSearchMemberAdapter);

        mSearchMemberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mSearchMemberEdit.getText().toString().length()==0){
                    mSearchMemberAdapter.notifyDataSetChanged();
                }
            }
        });

        mSearchMemberImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchMemberAdapter.notifyDataSetChanged();
            }
        });
    }

    public void performEnterAnimation(){
        initLocation();
        final float top=getResources().getDisplayMetrics().density*20;
        final ValueAnimator translateVa=translateVa(mSearchMemberEdit.getY(),top);
        final ValueAnimator scaleVa=scaleVa(1,0.0f);
        final ValueAnimator alphaVa=alphaVa(0,1f);
        originX=mSearchMemberEdit.getX();
        final float leftSpace=mSearchMemberRelative.getRight()*2;
        final ValueAnimator translateVaX=translateVaX(originX,leftSpace);
        setDuration(translateVa,scaleVa,translateVaX,alphaVa);
        star(translateVa,scaleVa,translateVaX,alphaVa);
    }

    private void performExitAnimation(){
        final float translateY=getTranslateY();
        final ValueAnimator translateVa=translateVa(mSearchMemberRelative.getY(),mSearchMemberRelative.getY()+translateY);
        final ValueAnimator scaleVa=scaleVa(0.8f,1f);
        final ValueAnimator alphaVa=alphaVa(1f,0);
        exitListener(translateVa);
        final float currentX=mSearchMemberEdit.getX();
        final ValueAnimator translateVaX=translateVaX(currentX,originX);
        setDuration(translateVa,scaleVa,translateVaX,alphaVa);
        star(translateVa,scaleVa,translateVaX,alphaVa);
    }

    private void initLocation(){
        final float translateY=getTranslateY();
        mSearchMemberEdit.setY(mSearchMemberEdit.getY()+translateY);
        mSearchMemberOut.setY(mSearchMemberEdit.getY()+(mSearchMemberEdit.getHeight()-mSearchMemberOut.getHeight())/2);
        mSearchMemberImg.setY(mSearchMemberEdit.getY()+(mSearchMemberEdit.getHeight()-mSearchMemberImg.getHeight())/2);
    }

    private float getTranslateY(){
        float originY=getIntent().getIntExtra("y",0);
        int[] location=new int[2];
        mSearchMemberEdit.getLocationOnScreen(location);
        return originY-(float) location[1];
    }

    private void exitListener(ValueAnimator translateVa){
        translateVa.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0,0);
            }
        });
    }

    private ValueAnimator translateVaX(float from,float to){
        ValueAnimator translateVaX=ValueAnimator.ofFloat(from,to);
        translateVaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearchMemberEdit.setX((Float) animation.getAnimatedValue());
            }
        });
        return translateVaX;
    }

    private ValueAnimator translateVa(float from,float to){
        ValueAnimator translateVa=ValueAnimator.ofFloat(from,to);
        translateVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearchMemberEdit.setY((Float) animation.getAnimatedValue());
                mSearchMemberRelative.setY(mSearchMemberEdit.getY()+(mSearchMemberEdit.getHeight()-mSearchMemberRelative.getHeight())/2);
                mSearchMemberOut.setY(mSearchMemberEdit.getY()+(mSearchMemberEdit.getHeight()-mSearchMemberOut.getHeight())/2);
                mSearchMemberImg.setY(mSearchMemberEdit.getY()+(mSearchMemberEdit.getHeight()-mSearchMemberImg.getHeight())/2);
            }
        });
        return translateVa;
    }

    private ValueAnimator scaleVa(float from,float to){
        ValueAnimator scaleVa=ValueAnimator.ofFloat(from,to);
        scaleVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearchMemberEdit.setScaleX((Float) animation.getAnimatedValue());
            }
        });
        return scaleVa;
    }

    private ValueAnimator alphaVa(float from,float to){
        ValueAnimator alphaVa=ValueAnimator.ofFloat(from,to);
        alphaVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSearchMemberOut.setAlpha((Float) animation.getAnimatedValue());
                mSearchMemberImg.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        return alphaVa;
    }

    private void setDuration(ValueAnimator translateVa,ValueAnimator scaleVa,
                             ValueAnimator translateVaX,ValueAnimator alphaVa){
        alphaVa.setDuration(350);
        translateVa.setDuration(350);
        translateVaX.setDuration(350);
        scaleVa.setDuration(350);
    }

    private void star(ValueAnimator translateVa, ValueAnimator scaleVa,
                      ValueAnimator translateVaX, ValueAnimator alphaVa){
        alphaVa.start();
        translateVa.start();
        translateVaX.start();
        scaleVa.start();
    }

    @Override
    protected void initToolBar() {
        mSearchMemberOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(!finishing){
            finishing=true;
            performExitAnimation();
        }
    }
}














