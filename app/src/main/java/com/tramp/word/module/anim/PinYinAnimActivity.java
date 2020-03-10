package com.tramp.word.module.anim;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.PinContentAdapter;
import com.tramp.word.adapter.PinLetterAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/15.
 */

public class PinYinAnimActivity extends RxBaseActivity{
    @BindView(R.id.pin_anim_out)
    ImageView mPinAnimOut;

    @BindView(R.id.pin_anim_relative)
    RelativeLayout mPinAnimRelative;

    @BindView(R.id.pin_anim_on_back)
    RelativeLayout mPinAnimOnBack;
    @BindView(R.id.pin_anim_on_relative)
    RelativeLayout mPinAnimOnRelative;
    @BindView(R.id.pin_content_text_down)
    ImageView mPinContentTextDown;
    @BindView(R.id.pin_anim_on_title)
    TextView mPinAnimOnTitle;
    @BindView(R.id.pin_anim_mark)
    RelativeLayout mPinAnimMark;
    @BindView(R.id.pin_anim_win_img)
    ImageView mPinAnimWinImg;
    @BindView(R.id.pin_anim_title)
    TextView mPinAnimTitle;
    @BindView(R.id.pin_anim_on)
    RelativeLayout mPinAnimOn;

    @BindView(R.id.pin_title_recycler)
    RecyclerView mPinTitleRecycler;
    @BindView(R.id.pin_title_recycler_img)
    ImageView mPinTitleRecyclerImg;
    @BindView(R.id.pin_content_recycler)
    RecyclerView mPinContentRecycler;

    private static String title="trip";
    private ArrayList<DefaultLetterEntity> letter=new ArrayList<>();
    private ArrayList<DefaultLetterEntity> content=new ArrayList<>();
    private PinLetterAdapter mPinLetterAdapter;
    private PinContentAdapter mPinContentAdapter;
    private static DefaultLetterEntity mentity=new DefaultLetterEntity();;
    private static Animation mScaleAnim;
    private static Animation mAlphaAnim;
    private static Animation mWinScrollAnim;
    private static Animation mScrollAnim;
    private static AnimationDrawable mMusicAnim;
    private static AnimatorSet mLinearAnim;
    private static Animation mTopAnim;
    public int NewNumber=50;
    private Random mRandom;
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x1111:
                    mPinAnimRelative.startAnimation(mScrollAnim);
                    mPinAnimTitle.setText(String.valueOf(NewNumber));
                    mPinAnimOnBack.setVisibility(View.GONE);
                    mPinAnimOnRelative.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1112:
                    mPinAnimOnBack.setVisibility(View.VISIBLE);
                    mPinAnimOnRelative.setVisibility(View.VISIBLE);
                    mPinAnimOnRelative.startAnimation(mTopAnim);
                    break;
                case 0x1113:
                    mPinAnimOnBack.setVisibility(View.GONE);
                    mPinAnimOnRelative.setVisibility(View.GONE);
                    mPinAnimRelative.startAnimation(mScrollAnim);
                    initData();
                    break;
            }
            return false;
        }
    });
    private int list=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pin_anim;
    }
    @Override
    public void initView(Bundle save) {
        initData();
        initRecycler();
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

        mPinContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mPinAnimMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1111,300);
            }
        });

        mPinAnimOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandle.sendEmptyMessageDelayed(0x1112,300);
            }
        });
    }

    public void initRecycler(){

        mPinLetterAdapter=new PinLetterAdapter(mPinTitleRecycler,letter);
        LinearLayoutManager linear=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        mPinTitleRecycler.setLayoutManager(linear);
        mPinTitleRecycler.setAdapter(mPinLetterAdapter);

        mPinContentAdapter=new PinContentAdapter(mPinContentRecycler,content);
        mPinContentRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),5));
        mPinContentRecycler.setAdapter(mPinContentAdapter);

        mPinContentAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {

            }
        });

        mPinTitleRecyclerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letter.get(list).setTitle(null);
                list--;
                mPinLetterAdapter.notifyItemChanged(list);
            }
        });
    }

    public void initStaticBack(){

        mHandle.sendEmptyMessageDelayed(0x1112,250);
    }

    public void initData(){
        if(NewNumber==0){
            initIntent();
            finish();
        }
        int number=0;
        content.clear();
        for (int i=0;i<15;i++){
            if(content.size()<15){
                mentity.setId(i);
                mentity.setTitle(""+(char)(Math.random()*26+'a'));
                mentity.setStatus(false);
            }
            if(i==5||i==8||i==11||i==13){
                mentity.setId(i);
                mentity.setTitle(title.substring(number,number+1));
                mentity.setStatus(false);
                number++;
            }
            content.add(mentity);
        }
        letter.clear();
        for (int j=0;j<title.length();j++){
            mentity.setId(j);
            mentity.setTitle(title.substring(j,j+1));
            mentity.setSelect(null);
            letter.add(mentity);
        }
    }

    @Override
    protected void initToolBar() {
        mPinAnimOut.setOnClickListener(new View.OnClickListener() {
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
