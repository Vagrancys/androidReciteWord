package com.tramp.word.module.anim;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/10.
 */

public class SelectAnimActivity extends RxBaseActivity {
    @BindView(R.id.select_anim_out)
    ImageView mSelectAnimOut;

    @BindView(R.id.select_anim_music)
    LinearLayout mSelectAnimMusic;
    @BindView(R.id.select_anim_music_img)
    ImageView mSelectAnimMusicImg;
    @BindView(R.id.select_anim_relative)
    RelativeLayout mSelectAnimRelative;

    @BindView(R.id.select_anim_text_one)
    TextView mSelectAnimTextOne;
    @BindView(R.id.select_anim_text_two)
    TextView mSelectAnimTextTwo;
    @BindView(R.id.select_anim_text_three)
    TextView mSelectAnimTextThree;
    @BindView(R.id.select_anim_text_four)
    TextView mSelectAnimTextFour;

    @BindView(R.id.select_anim_on_back)
    RelativeLayout mSelectAnimOnBack;
    @BindView(R.id.select_anim_on_relative)
    RelativeLayout mSelectAnimOnRelative;
    @BindView(R.id.select_content_text_down)
    ImageView mSelectContentTextDown;
    @BindView(R.id.select_anim_on_title)
    TextView mSelectAnimOnTitle;
    @BindView(R.id.select_anim_mark)
    RelativeLayout mSelectAnimMark;
    @BindView(R.id.select_anim_win_img)
    ImageView mSelectAnimWinImg;
    @BindView(R.id.select_anim_title)
    TextView mSelectAnimTitle;
    @BindView(R.id.select_anim_on)
    RelativeLayout mSelectAnimOn;

    private static Animation mScaleAnim;
    private static Animation mAlphaAnim;
    private static Animation mWinScrollAnim;
    private static Animation mScrollAnim;
    private static AnimationDrawable mMusicAnim;
    private static AnimatorSet mLinearAnim;
    private static Animation mTopAnim;
    private int NewNumber=50;
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x1111:
                    mSelectAnimRelative.startAnimation(mScrollAnim);
                    mSelectAnimTitle.setText(String.valueOf(NewNumber));
                    mSelectAnimOnBack.setVisibility(View.GONE);
                    mSelectAnimOnRelative.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1112:
                    mSelectAnimOnBack.setVisibility(View.VISIBLE);
                    mSelectAnimOnRelative.setVisibility(View.VISIBLE);
                    mSelectAnimOnRelative.startAnimation(mTopAnim);
                    break;
                case 0x1113:
                    mSelectAnimOnBack.setVisibility(View.GONE);
                    mSelectAnimOnRelative.setVisibility(View.GONE);
                    mSelectAnimRelative.startAnimation(mScrollAnim);
                    initData();
                    break;
            }
            return false;
        }
    });
    @Override
    public int getLayoutId() {
        return R.layout.activity_select_anim;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        mSelectAnimMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicAnim.start();
            }
        });
        initButton();
    }

    public void initAnim(){
        mSelectAnimMusicImg.setImageDrawable(getResources().getDrawable(R.drawable.recite_music_anim));
        mMusicAnim=(AnimationDrawable) mSelectAnimMusicImg.getDrawable();
        mMusicAnim.setOneShot(false);
        mTopAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.alert_word_manage_top_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mWinScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_win_scroll_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_scroll_anim);
    }

    public void initButton(){
        mSelectAnimTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectAnimTextOne.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
                    mSelectAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mSelectAnimTextTwo.startAnimation(mAlphaAnim);
                    mSelectAnimTextThree.startAnimation(mAlphaAnim);
                    mSelectAnimTextFour.startAnimation(mAlphaAnim);
                    mSelectAnimOnTitle.startAnimation(mTopAnim);
                    mSelectAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mSelectAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mSelectAnimTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectAnimTextTwo.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
                    mSelectAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mSelectAnimTextOne.startAnimation(mAlphaAnim);
                    mSelectAnimTextThree.startAnimation(mAlphaAnim);
                    mSelectAnimTextFour.startAnimation(mAlphaAnim);
                    mSelectAnimOnTitle.startAnimation(mTopAnim);
                    mSelectAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mSelectAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mSelectAnimTextThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectAnimTextThree.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
                    mSelectAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mSelectAnimTextOne.startAnimation(mAlphaAnim);
                    mSelectAnimTextTwo.startAnimation(mAlphaAnim);
                    mSelectAnimTextFour.startAnimation(mAlphaAnim);
                    mSelectAnimOnTitle.startAnimation(mTopAnim);
                    mSelectAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mSelectAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mSelectAnimTextFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectAnimTextFour.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
                    mSelectAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mSelectAnimTextOne.startAnimation(mAlphaAnim);
                    mSelectAnimTextTwo.startAnimation(mAlphaAnim);
                    mSelectAnimTextThree.startAnimation(mAlphaAnim);
                    mSelectAnimOnTitle.startAnimation(mTopAnim);
                    mSelectAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mSelectAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mSelectContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mSelectAnimMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mSelectAnimOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1112,300);
            }
        });
    }

    public void initStaticBack(){
        if(mSelectAnimTextOne.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
            mSelectAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mSelectAnimTextTwo.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
            mSelectAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mSelectAnimTextThree.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
            mSelectAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mSelectAnimTextFour.getText().toString().equals(getResources().getString(R.string.select_anim_text_one))){
            mSelectAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        mHandle.sendEmptyMessageDelayed(0x1112,250);
    }

    public void initData(){
        if(NewNumber==0){
            initIntent();
            finish();
        }
        mSelectAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mSelectAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mSelectAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mSelectAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
    }

    @Override
    protected void initToolBar() {
        mSelectAnimOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initIntent(){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putInt("data",NewNumber);
        intent.putExtras(bundle);
        setResult(10,intent);
    }
    @Override
    public void onBackPressed() {
        initIntent();
        finish();
        super.onBackPressed();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
