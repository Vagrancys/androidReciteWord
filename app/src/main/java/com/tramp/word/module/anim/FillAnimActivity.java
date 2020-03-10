package com.tramp.word.module.anim;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/13.
 *
 */

public class FillAnimActivity extends RxBaseActivity {

    @BindView(R.id.fill_anim_out)
    ImageView mFillAnimOut;
    @BindView(R.id.fill_anim_relative)
    RelativeLayout mFillAnimRelative;

    @BindView(R.id.fill_anim_text_one)
    TextView mFillAnimTextOne;
    @BindView(R.id.fill_anim_text_two)
    TextView mFillAnimTextTwo;
    @BindView(R.id.fill_anim_text_three)
    TextView mFillAnimTextThree;
    @BindView(R.id.fill_anim_text_four)
    TextView mFillAnimTextFour;

    @BindView(R.id.fill_anim_on_back)
    RelativeLayout mFillAnimOnBack;
    @BindView(R.id.fill_anim_on_relative)
    RelativeLayout mFillAnimOnRelative;
    @BindView(R.id.fill_content_text_down)
    ImageView mFillContentTextDown;
    @BindView(R.id.fill_anim_on_title)
    TextView mFillAnimOnTitle;
    @BindView(R.id.fill_anim_mark)
    RelativeLayout mFillAnimMark;
    @BindView(R.id.fill_anim_win_img)
    ImageView mFillAnimWinImg;
    @BindView(R.id.fill_anim_title)
    TextView mFillAnimTitle;
    @BindView(R.id.fill_anim_on)
    RelativeLayout mFillAnimOn;

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
                    mFillAnimRelative.startAnimation(mScrollAnim);
                    mFillAnimTitle.setText(String.valueOf(NewNumber));
                    mFillAnimOnBack.setVisibility(View.GONE);
                    mFillAnimOnRelative.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1112:
                    mFillAnimOnBack.setVisibility(View.VISIBLE);
                    mFillAnimOnRelative.setVisibility(View.VISIBLE);
                    mFillAnimOnRelative.startAnimation(mTopAnim);
                    break;
                case 0x1113:
                    mFillAnimOnBack.setVisibility(View.GONE);
                    mFillAnimOnRelative.setVisibility(View.GONE);
                    mFillAnimRelative.startAnimation(mScrollAnim);
                    initData();
                    break;
            }
            return false;
        }
    });
    @Override
    public int getLayoutId() {
        return R.layout.activity_fill_anim;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initButton();
    }

    public void initAnim(){
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.alert_word_manage_top_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mWinScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_win_scroll_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_scroll_anim);
    }

    public void initButton(){
        mFillAnimTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFillAnimTextOne.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
                    mFillAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mFillAnimTextTwo.startAnimation(mAlphaAnim);
                    mFillAnimTextThree.startAnimation(mAlphaAnim);
                    mFillAnimTextFour.startAnimation(mAlphaAnim);
                    mFillAnimOnTitle.startAnimation(mTopAnim);
                    mFillAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mFillAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mFillAnimTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFillAnimTextTwo.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
                    mFillAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mFillAnimTextOne.startAnimation(mAlphaAnim);
                    mFillAnimTextThree.startAnimation(mAlphaAnim);
                    mFillAnimTextFour.startAnimation(mAlphaAnim);
                    mFillAnimOnTitle.startAnimation(mTopAnim);
                    mFillAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mFillAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mFillAnimTextThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFillAnimTextThree.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
                    mFillAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mFillAnimTextOne.startAnimation(mAlphaAnim);
                    mFillAnimTextTwo.startAnimation(mAlphaAnim);
                    mFillAnimTextFour.startAnimation(mAlphaAnim);
                    mFillAnimOnTitle.startAnimation(mTopAnim);
                    mFillAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mFillAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mFillAnimTextFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFillAnimTextFour.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
                    mFillAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mFillAnimTextOne.startAnimation(mAlphaAnim);
                    mFillAnimTextTwo.startAnimation(mAlphaAnim);
                    mFillAnimTextThree.startAnimation(mAlphaAnim);
                    mFillAnimOnTitle.startAnimation(mTopAnim);
                    mFillAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mFillAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mFillContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mFillAnimMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mFillAnimOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1112,300);
            }
        });
    }

    public void initStaticBack(){
        if(mFillAnimTextOne.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
            mFillAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mFillAnimTextTwo.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
            mFillAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mFillAnimTextThree.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
            mFillAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mFillAnimTextFour.getText().toString().equals(getResources().getString(R.string.fill_anim_text_one))){
            mFillAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        mHandle.sendEmptyMessageDelayed(0x1112,250);
    }

    public void initData(){
        if(NewNumber==0){
            initIntent();
            finish();
        }
        mFillAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mFillAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mFillAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mFillAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
    }

    @Override
    protected void initToolBar() {
        mFillAnimOut.setOnClickListener(new View.OnClickListener() {
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

