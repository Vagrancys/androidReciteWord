package com.tramp.word.module.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.SearchMemberAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.FriendSearchInfo;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.search_member_delete)
    ImageView SearchMemberDelete;
    @BindView(R.id.search_member_swipe)
    SwipeRefreshLayout SearchMemberSwipe;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.search_member_empty)
    LinearLayout SearchMemberEmpty;

    private SearchMemberAdapter mSearchMemberAdapter;
    private boolean finishing;
    private float originX;
    private int page;
    private int user_id;
    private ArrayList<FriendSearchInfo.search> searches=new ArrayList<>();

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

        mSearchMemberAdapter = new SearchMemberAdapter(mSearchMemberRecycler,this,searches);
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
                    SearchMemberSwipe.setVisibility(View.GONE);
                    SearchMemberDelete.setVisibility(View.GONE);
                }else if(mSearchMemberEdit.getText().toString().length()>0){
                    SearchMemberDelete.setVisibility(View.VISIBLE);
                }
            }
        });

        mSearchMemberImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                clearData();
                loadData();
            }
        });
        SearchMemberDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMemberDelete.setVisibility(View.GONE);
                SearchMemberSwipe.setVisibility(View.GONE);
                mSearchMemberEdit.setText("");
            }
        });
        SearchMemberSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        SearchMemberSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                SearchMemberSwipe.setRefreshing(true);
                clearData();
                loadData();
            }
        });
        UserSqlHelper mUser=new UserSqlHelper(getBaseContext());
        user_id=mUser.UserId();
    }

    public void clearData(){
        SearchMemberEmpty.setVisibility(View.GONE);
        CommonEmpty.setVisibility(View.GONE);
        searches.clear();
    }

    public void loadData(){

        Retrofits.getFriendAPI()
                .getFriendSearchInfo(mSearchMemberEdit.getText().toString(),page,user_id)
        .enqueue(new Callback<FriendSearchInfo>() {
            @Override
            public void onResponse(Call<FriendSearchInfo> call, Response<FriendSearchInfo> response) {
                if(response.body().getCode()==200){
                    searches.addAll(response.body().getSearch());
                    if(searches.size()<=0){
                        initDataEmpty();
                        return;
                    }
                    finishTask();
                }else if(response.body().getCode()==404){
                    initDataEmpty();
                }
            }

            @Override
            public void onFailure(Call<FriendSearchInfo> call, Throwable t) {
                initEmpty();
            }
        });
    }

    public void finishTask(){
        SearchMemberSwipe.setRefreshing(false);
        mSearchMemberRelative.setVisibility(View.VISIBLE);
        mSearchMemberAdapter.notifyDataSetChanged();
    }

    public void initDataEmpty(){
        mSearchMemberRelative.setVisibility(View.GONE);
        SearchMemberEmpty.setVisibility(View.VISIBLE);
        CommonEmpty.setVisibility(View.GONE);
    }

    public void initEmpty(){
        mSearchMemberRelative.setVisibility(View.GONE);
        CommonEmpty.setVisibility(View.VISIBLE);
        SearchMemberEmpty.setVisibility(View.GONE);
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














