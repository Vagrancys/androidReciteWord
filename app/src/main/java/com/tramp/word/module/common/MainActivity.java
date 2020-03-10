package com.tramp.word.module.common;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tramp.word.R;
import com.tramp.word.adapter.HomePagerAdapter;
import com.tramp.word.adapter.MainSignAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.MainTabEntity;
import com.tramp.word.port.HomeMeSignInterFace;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.MainProgressDialog;
import com.tramp.word.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements MainAnimInterFace{
    @BindView(R.id.common_tab)
    CommonTabLayout mCommon;
    @BindView(R.id.view_pager)
    NoScrollViewPager mPager;
    @BindView(R.id.view_anim)
    LinearLayout mAnim;
    @BindView(R.id.img_anim)
    ImageView mImgAnim;
    @BindView(R.id.main_common_background)
    RelativeLayout mMainCommonBackground;
    @BindView(R.id.main_task_pop_exit)
    ImageView mMainTaskPopExit;
    @BindView(R.id.main_task_pop)
    RelativeLayout mMainTaskPop;
    @BindView(R.id.main_music_pop)
    RelativeLayout mMusicPop;
    @BindView(R.id.main_music_down)
    TextView mMainMusicDown;
    @BindView(R.id.main_music_progress)
    ProgressBar mMainMusicProgress;
    @BindView(R.id.main_music_num)
    TextView mMainMusicNum;

    @BindView(R.id.main_sign_pop_recycler)
    RecyclerView mMainSignPopRecycler;
    @BindView(R.id.main_sign_pop_exit)
    ImageView mMainSignPopExit;
    @BindView(R.id.main_sign_pop)
    RelativeLayout mMainSignPop;
    @BindView(R.id.main_sign_pop_start)
    TextView mMainSignPopStart;

    private AnimationDrawable mAnimation;
    private Animation mAnimIcon;
    private ObjectAnimator mTaskTopOneAnim;
    private ObjectAnimator mTaskTopTwoAnim;
    private ObjectAnimator mSignPopUpAnim;
    private ObjectAnimator mSignPopDownAnim;
    private int MusicProgress;
    private int MusicTwoProgress;
    private int status;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x1120:
                    mMainTaskPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                    break;
                case 0x1121:
                    mMainMusicProgress.setProgress(MusicProgress);
                    mMainMusicNum.setText(MusicProgress+"KB/100KB");
                    break;
                case 0x1122:
                    mMainMusicProgress.setSecondaryProgress(MusicTwoProgress);
                    break;
                case 0x1123:
                    mMainMusicDown.setText("暂停");
                    mMainMusicDown.setTextColor(getResources().getColor(R.color.blue));
                    mMainMusicDown.setBackground(getResources().getDrawable(R.drawable.btn_main_music_down));
                    mMainMusicDown.setEnabled(true);
                    break;
                case 0x1124:
                    mMainMusicNum.setText("413KB");
                    mMainMusicDown.setBackgroundColor(getResources().getColor(R.color.black_2));
                    mMainMusicDown.setTextColor(getResources().getColor(R.color.black_1));
                    mMainMusicDown.setText("待解压");
                    MusicProgress=0;
                    mMainMusicProgress.setProgressDrawable(getResources().getDrawable(R.drawable.main_anim_progress_one_back));
                    mMainMusicDown.setEnabled(false);
                    status=2;
                    MainProgressStart();
                    break;
                case 0x1125:
                    mMainMusicDown.setEnabled(false);
                    mMainMusicDown.setText("解压中");
                    break;
                case 0x1126:
                    mMainMusicDown.setText("下载");
                    mMainMusicDown.setBackgroundColor(getResources().getColor(R.color.white));
                    mMainMusicDown.setTextColor(getResources().getColor(R.color.black_1));
                    mMainMusicProgress.setVisibility(View.GONE);
                    isClose=false;
                    break;
                case 0x1127:
                    mMainProgress.show();
                    if(mMainProgress.isShowing()){
                        MainDialogProgressStart();
                    }else{
                        isDialogStop=true;
                    }
                    break;
                case 0x1128:
                    mMainProgress.setDrawable(getResources().getDrawable(R.drawable.main_anim_progress_one_back));
                    mMainProgress.setDown("解压中");
                    mMainProgress.setNumber("413KB");
                    break;
                case 0x1129:
                    mMainProgress.setDrawable(getResources().getDrawable(R.drawable.main_anim_progress_back));
                    mMainProgress.setDown("数据写入");
                    break;
                case 0x1130:
                    mMainSignPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    });
    private Animation mMusicAnim;
    private HomePagerAdapter mHomeAdapter;
    private String[] mTitles={"背词","备考","发现","我的"};
    private int[] mIconUnSelectIds={
            R.drawable.icon_tab_home,R.drawable.icon_word_list_review,
            R.drawable.icon_tab_discover_oval,R.drawable.icon_tab_me
    };
    private int[] mIconSelectIds={
            R.drawable.icon_tab_home_selected,R.drawable.icon_word_review,
            R.drawable.icon_tab_discover_oval_selected,R.drawable.icon_tab_me_selected
    };

    private ArrayList<CustomTabEntity> mTabEntities=new ArrayList<>();
    private Thread mProgressThread;
    private boolean MusicStatus=true;
    private boolean isStop=false;
    private boolean isDialogStop=false;
    private boolean isDialogClose=true;
    private boolean isClose=true;
    private int Level=1;
    private MainProgressDialog mMainProgress;
    private MainSignAdapter mMainSignAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initProgress();
        mHomeAdapter=new HomePagerAdapter(getSupportFragmentManager(),getApplicationContext());
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mHomeAdapter);
        mPager.setCurrentItem(0);
        initFragment();
        mCommon.setTabData(mTabEntities);
        mCommon.setCurrentTab(0);
        mCommon.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPager.setCurrentItem(position);
                mCommon.getIconView(position).startAnimation(mAnimIcon);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommon.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMainTaskPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskTopTwoAnim.start();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMainTaskPop.setVisibility(View.GONE);
                        mMainCommonBackground.setVisibility(View.GONE);
                    }
                },550);
            }
        });

        mMainCommonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMusicPop.getVisibility()==View.VISIBLE){
                    MusicStatus=false;
                    mMusicPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                }
            }
        });

        initSign();
    }

    private void initSign(){
        mMainSignAdapter=new MainSignAdapter(mMainSignPopRecycler,5);
        mMainSignPopRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        mMainSignPopRecycler.setAdapter(mMainSignAdapter);

        mMainSignPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignPopDownAnim.start();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMainSignPop.setVisibility(View.GONE);
                        mMainCommonBackground.setVisibility(View.GONE);
                    }
                },550);
            }
        });
        mMainSignPopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"签到了");
            }
        });
    }

    private void initProgress(){
        mMainProgress=new MainProgressDialog(this);
        mMainProgress.setMax(500);
        mMainMusicProgress.setProgress(0);
        mMainMusicProgress.setSecondaryProgress(0);
        mMainMusicProgress.setMax(400);
        mMainMusicDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMainMusicDown.getText().toString().equals("下载")){
                    mMainMusicProgress.setVisibility(View.VISIBLE);
                    mMainMusicDown.setBackgroundColor(getResources().getColor(R.color.black_2));
                    mMainMusicDown.setTextColor(getResources().getColor(R.color.black_1));
                    mMainMusicDown.setText("待下载");
                    mMainMusicDown.setEnabled(false);
                    status=1;
                    MainProgressStart();
                }else if(mMainMusicDown.getText().toString().equals("暂停")){
                    mMainMusicDown.setEnabled(true);
                    mMainMusicDown.setText("继续");
                    isStop=false;
                    MusicStatus=false;
                }else if(mMainMusicDown.getText().toString().equals("继续")){
                    mMainMusicDown.setText("暂停");
                    isStop=true;
                    MusicStatus=true;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getIntExtra("status",1 )==2){
            Message msg=new Message();
            msg.what=0x1127;
            mHandler.sendMessage(msg);
        }

    }

    private void MainDialogProgressStart(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                int dataCount=0;
                try {
                    while (isDialogClose){
                        if(Level==1){
                            Log.d("Thread","dataCount="+dataCount);
                            while (dataCount<=300&&!isDialogStop){
                                Log.d("Progress","data="+dataCount);
                                mMainProgress.setProgress(dataCount);
                                dataCount+=5;
                                Thread.sleep(500);
                            }
                            dataCount=0;
                            Level=2;
                            Message msg1=new Message();
                            msg1.what=0x1128;
                            mHandler.sendMessage(msg1);
                            mHandler.sendEmptyMessage(0x1128);
                        }else if(Level==2){
                            Log.d("Thread1","dataCount="+dataCount);
                            mMainProgress.setMax(200);
                            while (dataCount<=200&&!isDialogStop){
                                Log.d("Progress1","data="+dataCount);
                                mMainProgress.setOneProgress(dataCount);
                                dataCount+=5;
                                Thread.sleep(500);
                            }
                            dataCount=0;
                            Level=3;
                            Message msg=new Message();
                            msg.what=0x1129;
                            mHandler.sendMessage(msg);
                            mHandler.sendEmptyMessage(0x1129);
                        }else if(Level==3){
                            Log.d("Thread2","dataCount="+dataCount);
                            mMainProgress.setMax(100);
                            while (dataCount<=100&&!isDialogStop){
                                Log.d("Progress2","data="+dataCount);
                                mMainProgress.setTwoProgress(dataCount);
                                dataCount+=5;
                                Thread.sleep(500);
                            }
                            mMainProgress.cancel();
                            isDialogClose=false;
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void MainProgressStart(){
        mProgressThread=new Thread(new Runnable() {
            @Override
            public void run() {
                int max=mMainMusicProgress.getMax();
                try {
                    while (isClose){
                            if(status==1&&!isStop){
                                if(MusicProgress==0){
                                    Message msg1=new Message();
                                    msg1.what=0x1123;
                                    mHandler.sendMessage(msg1);
                                }
                                while (MusicProgress<max&&MusicStatus){
                                    MusicProgress+=5;
                                    Message msg2=new Message();
                                    msg2.what=0x1121;
                                    mHandler.sendMessage(msg2);
                                    Thread.sleep(500);
                                }
                                if(MusicProgress>=max){
                                    Message msg3=new Message();
                                    msg3.what=0x1124;
                                    mHandler.sendMessage(msg3);
                                }
                            }else if(status==2){
                                if(MusicProgress==0){
                                    Message msg4=new Message();
                                    msg4.what=0x1125;
                                    mHandler.sendMessage(msg4);
                                }
                                while (MusicProgress<max&&MusicStatus){
                                    MusicProgress+=5;
                                    Message msg5=new Message();
                                    msg5.what=0x1122;
                                    mHandler.sendMessage(msg5);
                                    Thread.sleep(500);
                                }
                                if(MusicProgress>=max){
                                    Message msg6=new Message();
                                    msg6.what=0x1126;
                                    mHandler.sendMessage(msg6);
                                }
                            }
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mProgressThread.start();
    }

    public void initAnim(){
        mImgAnim.setImageResource(R.drawable.main_anim);
        mAnimation=(AnimationDrawable) mImgAnim.getDrawable();
        mAnimIcon= AnimationUtils.loadAnimation(getBaseContext(),R.anim.main_tab_icon_anim);
        mMusicAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.word_book_pop_in_anim);
        mAnimIcon.setRepeatCount(3);

        DisplayMetrics metric=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float MaxHeight=metric.widthPixels;
        float curTranslationY=mMainTaskPop.getTranslationY();

        mTaskTopOneAnim= ObjectAnimator.ofFloat(mMainTaskPop,"translationY",MaxHeight,curTranslationY-70,curTranslationY);
        mTaskTopOneAnim.setDuration(500);
        mTaskTopTwoAnim= ObjectAnimator.ofFloat(mMainTaskPop,"translationY",curTranslationY,curTranslationY-70,MaxHeight+350);
        mTaskTopTwoAnim.setDuration(500);

        mSignPopUpAnim= ObjectAnimator.ofFloat(mMainSignPop,"translationY",MaxHeight,curTranslationY-70,curTranslationY);
        mSignPopUpAnim.setDuration(500);
        mSignPopDownAnim= ObjectAnimator.ofFloat(mMainSignPop,"translationY",curTranslationY,curTranslationY-70,MaxHeight+350);
        mSignPopDownAnim.setDuration(500);
    }

    public void initFragment(){
        for (int j=0;j<mTitles.length;j++){
            mTabEntities.add(new MainTabEntity(mTitles[j],mIconSelectIds[j],mIconUnSelectIds[j]));
        }
    }

    @Override
    public void StartAnim() {
        mAnimation.start();
    }

    @Override
    public void StopAnim() {
        mAnimation.stop();
    }

    @Override
    public void ShowAnimLayout() {
        mAnim.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideAnimLayout() {
        mAnim.setVisibility(View.GONE);
    }

    @Override
    public void ShowTaskLayout() {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMainTaskPop.setVisibility(View.VISIBLE);
        mTaskTopOneAnim.start();
    }

    @Override
    public void HideTaskLayout() {
        mTaskTopTwoAnim.start();
        mHandler.sendEmptyMessageDelayed(0x1120,500);
    }

    @Override
    public void ShowSignLayout(int status) {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMainSignPop.setVisibility(View.VISIBLE);
        mSignPopUpAnim.start();
    }

    @Override
    public void HideSignLayout() {
        mSignPopDownAnim.start();
        mHandler.sendEmptyMessageDelayed(0x1130,500);
    }

    @Override
    public void ShowMusicDownLayout() {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMusicPop.setVisibility(View.VISIBLE);
        mMusicPop.startAnimation(mMusicAnim);
    }

    @Override
    public void HideMusicDownLayout() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimation.stop();
    }
}








