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

public class AudioAnimActivity extends RxBaseActivity {
    @BindView(R.id.audio_anim_out)
    ImageView mAudioAnimOut;

    @BindView(R.id.audio_anim_music)
    LinearLayout mAudioAnimMusic;
    @BindView(R.id.audio_anim_music_img)
    ImageView mAudioAnimMusicImg;
    @BindView(R.id.audio_anim_relative)
    RelativeLayout mAudioAnimRelative;

    @BindView(R.id.audio_anim_text_one)
    TextView mAudioAnimTextOne;
    @BindView(R.id.audio_anim_text_two)
    TextView mAudioAnimTextTwo;
    @BindView(R.id.audio_anim_text_three)
    TextView mAudioAnimTextThree;
    @BindView(R.id.audio_anim_text_four)
    TextView mAudioAnimTextFour;

    @BindView(R.id.audio_anim_on_back)
    RelativeLayout mAudioAnimOnBack;
    @BindView(R.id.audio_anim_on_relative)
    RelativeLayout mAudioAnimOnRelative;
    @BindView(R.id.audio_content_text_down)
    ImageView mAudioContentTextDown;
    @BindView(R.id.audio_anim_on_title)
    TextView mAudioAnimOnTitle;
    @BindView(R.id.audio_anim_mark)
    RelativeLayout mAudioAnimMark;
    @BindView(R.id.audio_anim_win_img)
    ImageView mAudioAnimWinImg;
    @BindView(R.id.audio_anim_title)
    TextView mAudioAnimTitle;
    @BindView(R.id.audio_anim_on)
    RelativeLayout mAudioAnimOn;

    @BindView(R.id.audio_anim_head_back)
    TextView mAudioAnimHeadBack;
    @BindView(R.id.audio_anim_head)
    TextView mAudioAnimHead;

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
                    mAudioAnimRelative.startAnimation(mScrollAnim);
                    mAudioAnimTitle.setText(String.valueOf(NewNumber));
                    mAudioAnimOnBack.setVisibility(View.GONE);
                    mAudioAnimOnRelative.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1112:
                    mAudioAnimOnBack.setVisibility(View.VISIBLE);
                    mAudioAnimOnRelative.setVisibility(View.VISIBLE);
                    mAudioAnimOnRelative.startAnimation(mTopAnim);
                    break;
                case 0x1113:
                    mAudioAnimOnBack.setVisibility(View.GONE);
                    mAudioAnimOnRelative.setVisibility(View.GONE);
                    mAudioAnimRelative.startAnimation(mScrollAnim);
                    mAudioAnimHead.setVisibility(View.GONE);
                    mAudioAnimHeadBack.setVisibility(View.VISIBLE);
                    initData();
                    break;
            }
            return false;
        }
    });
    @Override
    public int getLayoutId() {
        return R.layout.activity_audio_anim;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        mAudioAnimMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicAnim.start();
            }
        });

        mAudioAnimHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioAnimHeadBack.setVisibility(View.GONE);
                mAudioAnimHead.setVisibility(View.VISIBLE);
            }
        });
        initButton();
    }

    public void initAnim(){
        mAudioAnimMusicImg.setImageDrawable(getResources().getDrawable(R.drawable.recite_music_anim));
        mMusicAnim=(AnimationDrawable) mAudioAnimMusicImg.getDrawable();
        mMusicAnim.setOneShot(false);
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.alert_word_manage_top_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mWinScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_win_scroll_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.common_scroll_anim);
    }

    public void initButton(){
        mAudioAnimTextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAudioAnimTextOne.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
                    mAudioAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mAudioAnimTextTwo.startAnimation(mAlphaAnim);
                    mAudioAnimTextThree.startAnimation(mAlphaAnim);
                    mAudioAnimTextFour.startAnimation(mAlphaAnim);
                    mAudioAnimOnTitle.startAnimation(mTopAnim);
                    mAudioAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mAudioAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mAudioAnimTextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAudioAnimTextTwo.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
                    mAudioAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mAudioAnimTextOne.startAnimation(mAlphaAnim);
                    mAudioAnimTextThree.startAnimation(mAlphaAnim);
                    mAudioAnimTextFour.startAnimation(mAlphaAnim);
                    mAudioAnimOnTitle.startAnimation(mTopAnim);
                    mAudioAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mAudioAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mAudioAnimTextThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAudioAnimTextThree.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
                    mAudioAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mAudioAnimTextOne.startAnimation(mAlphaAnim);
                    mAudioAnimTextTwo.startAnimation(mAlphaAnim);
                    mAudioAnimTextFour.startAnimation(mAlphaAnim);
                    mAudioAnimOnTitle.startAnimation(mTopAnim);
                    mAudioAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mAudioAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mAudioAnimTextFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAudioAnimTextFour.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
                    mAudioAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mAudioAnimTextOne.startAnimation(mAlphaAnim);
                    mAudioAnimTextTwo.startAnimation(mAlphaAnim);
                    mAudioAnimTextThree.startAnimation(mAlphaAnim);
                    mAudioAnimOnTitle.startAnimation(mTopAnim);
                    mAudioAnimWinImg.startAnimation(mWinScrollAnim);
                    mHandle.sendEmptyMessageDelayed(0x1111,300);
                    NewNumber=NewNumber-1;
                }else{
                    mAudioAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
            }
        });

        mAudioContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mAudioAnimMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mAudioAnimOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1112,300);
            }
        });
    }

    public void initStaticBack(){
        if(mAudioAnimTextOne.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
            mAudioAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mAudioAnimTextTwo.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
            mAudioAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mAudioAnimTextThree.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
            mAudioAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(mAudioAnimTextFour.getText().toString().equals(getResources().getString(R.string.audio_anim_text_one))){
            mAudioAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        mHandle.sendEmptyMessageDelayed(0x1112,250);
    }

    public void initData(){
        if(NewNumber==0){
            initIntent();
            finish();
        }
        mAudioAnimTextOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mAudioAnimTextTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mAudioAnimTextThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        mAudioAnimTextFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
    }

    @Override
    protected void initToolBar() {
        mAudioAnimOut.setOnClickListener(new View.OnClickListener() {
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

