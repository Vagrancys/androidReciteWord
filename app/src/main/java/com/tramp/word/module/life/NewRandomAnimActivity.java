package com.tramp.word.module.life;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.ReciteAnimLetterViewAdapter;
import com.tramp.word.adapter.ReciteAnimSpellViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.word.WordContextActivity;
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;
import java.util.Random;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/14
 * version:1.0
 */

public class NewRandomAnimActivity extends RxBaseActivity{
    @BindView(R.id.new_random_back)
    RelativeLayout NewRandomBack;
    @BindView(R.id.new_random_progress)
    ProgressBar NewRandomProgress;
    @BindView(R.id.new_random_out)
    ImageView NewRandomOut;
    @BindView(R.id.recite_bottom_relative)
    RelativeLayout mReciteBottomRelative;
    @BindView(R.id.new_random_progress_text)
    TextView NewRandomProgressText;
    @BindView(R.id.new_random_relative)
    RelativeLayout NewRandomRelative;

    @BindView(R.id.new_random_spell_recycler)
    RecyclerView NewRandomSpellRecycler;
    @BindView(R.id.new_random_spell_letter)
    RecyclerView NewRandomSpellLetterRecycler;
    @BindView(R.id.new_random_spell_music)
    LinearLayout NewRandomSpellMusic;
    @BindView(R.id.new_random_spell_delete)
    ImageView NewRandomSpellDelete;



    @BindView(R.id.new_random_select)
    RelativeLayout NewRandomSelectRelative;
    @BindView(R.id.new_random_select_title)
    TextView NewRandomSelectTitle;
    @BindView(R.id.new_random_select_music_img)
    ImageView NewRandomSelectMusicImg;
    @BindView(R.id. new_random_select_1)
    TextView NewRandomSelect1;
    @BindView(R.id. new_random_select_2)
    TextView NewRandomSelect2;
    @BindView(R.id. new_random_select_3)
    TextView NewRandomSelect3;
    @BindView(R.id. new_random_select_4)
    TextView NewRandomSelect4;
    @BindView(R.id.new_random_select_music)
    LinearLayout NewRandomSelectMusic;

    @BindView(R.id.new_random_next)
    TextView NewRandomNext;
    @BindView(R.id.new_random_live)
    LinearLayout NewRandomLive;
    private int Fete=10;
    private Random mRandom;
    private int WordStart;
    private int BindStart;
    private Bitmap mAnimFate;
    private Bitmap mAnimMusicBack;
    private Bitmap mLetterOut;
    private Animation mArrowAnim;
    private Animation mLayoutAnim;
    private Bitmap mQuestion;
    private Matrix mMatrix=new Matrix();
    private ReciteAnimSpellViewAdapter mReciteAnimSpellView;
    private ReciteAnimLetterViewAdapter mReciteAnimLetterView;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0x1112){
                NewRandomSelectRelative.setVisibility(View.GONE);
                ContentStatus();
            }else if(msg.what==0x1111){
                NewRandomSelectRelative.setVisibility(View.GONE);
                mReciteBottomRelative.setVisibility(View.GONE);
                ContentStatus();
            }else if(msg.what==0x1113){
                NewRandomRelative.setVisibility(View.VISIBLE);
            }
            return false;
        }
    });

    private String[] Titles={
            "club","craft","project","next","classroom",
            "first","arrive","parent","noticeboard","choir"
    };

    private String[] Meanings={
            "n.俱乐部","n.工艺;诡计;飞行器","n.计划,方案,事业,工程","adv.紧接着;随后","n.教室",
            "adv.首先","v.到达","n.父或母","n.布告栏","n.合唱队"
    };


    private AnimationDrawable mSelectMusicAnim;
    private Animation mAlpha;
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_random;
    }

    @Override
    public void initView(Bundle save) {
        mLayoutAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);
        mAlpha=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mArrowAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_street@2x.png");
        NewRandomBack.setBackground(new BitmapDrawable(back));
        InputStream question=AssetsUtils.getAssetsOpen(getBaseContext(),"question_assets@2x.png");
        mQuestion= BitmapFactory.decodeStream(question);
        mAnimFate=Bitmap.createBitmap(mQuestion,55,400,115,55,mMatrix,false);
        mAnimMusicBack=Bitmap.createBitmap(mQuestion,250,205,150,50,mMatrix,false);
        mMatrix.setRotate(-90);
        initCommon();
        initSpell();
        initSelect();
        //WordStart=mRandom.nextInt(4);
        BindSelect();
    }

    public void initCommon(){
        mRandom=new Random();
        NewRandomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NewRandomNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewRandomSelectRelative.getVisibility()==View.VISIBLE){
                    if(!NewRandomSelect1.getText().equals(Meanings[5])){
                        NewRandomSelect1.startAnimation(mAlpha);
                    }
                    if(!NewRandomSelect2.getText().equals(Meanings[5])){
                        NewRandomSelect2.startAnimation(mAlpha);
                    }
                    if(!NewRandomSelect3.getText().equals(Meanings[5])){
                        NewRandomSelect3.startAnimation(mAlpha);
                    }
                    if(!NewRandomSelect4.getText().equals(Meanings[5])){
                        NewRandomSelect4.startAnimation(mAlpha);
                    }
                    mHandler.sendEmptyMessageDelayed(0x1111,500);
                }
            }
        });

        NewRandomLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewRandomAnimActivity.this,WordContextActivity.class));
            }
        });
    }

    public void initSpell(){
        NewRandomSpellMusic.setBackground(new BitmapDrawable(mAnimMusicBack));
        NewRandomSpellDelete.setImageBitmap(mLetterOut);
        mReciteAnimSpellView=new ReciteAnimSpellViewAdapter(NewRandomSpellRecycler,Titles[0]);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        NewRandomSpellRecycler.setLayoutManager(linearLayout);
        NewRandomSpellRecycler.setAdapter(mReciteAnimSpellView);
        mReciteAnimLetterView=new ReciteAnimLetterViewAdapter(NewRandomSpellLetterRecycler,Titles[0]);
        NewRandomSpellLetterRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),5));
        NewRandomSpellLetterRecycler.setAdapter(mReciteAnimLetterView);
    }

    public void initSelect(){
        NewRandomSelectMusic.setBackground(new BitmapDrawable(mAnimMusicBack));
        NewRandomSelectMusicImg.setImageResource(R.drawable.default_music_anim);
        mSelectMusicAnim=(AnimationDrawable) NewRandomSelectMusicImg.getDrawable();
        mSelectMusicAnim.setOneShot(true);
        NewRandomSelectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectMusicAnim.start();
            }
        });

        NewRandomSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewRandomSelect1.getText().toString().equals(Meanings[5])){
                    NewRandomSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    NewRandomSelect1.setTextColor(Color.WHITE);
                    NewRandomSelect2.startAnimation(mAlpha);
                    NewRandomSelect3.startAnimation(mAlpha);
                    NewRandomSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else{
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        NewRandomSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewRandomSelect2.getText().toString().equals(Meanings[5])){
                    NewRandomSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    NewRandomSelect2.setTextColor(Color.WHITE);
                    NewRandomSelect1.startAnimation(mAlpha);
                    NewRandomSelect3.startAnimation(mAlpha);
                    NewRandomSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else{
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        NewRandomSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewRandomSelect3.getText().toString().equals(Meanings[5])){
                    NewRandomSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    NewRandomSelect3.setTextColor(Color.WHITE);
                    NewRandomSelect1.startAnimation(mAlpha);
                    NewRandomSelect2.startAnimation(mAlpha);
                    NewRandomSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else {
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        NewRandomSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewRandomSelect4.getText().toString().equals(Meanings[5])){
                    NewRandomSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    NewRandomSelect4.setTextColor(Color.WHITE);
                    NewRandomSelect1.startAnimation(mAlpha);
                    NewRandomSelect2.startAnimation(mAlpha);
                    NewRandomSelect3.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else{
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public void SelectStartButton(){
        if(NewRandomSelect1.getText().toString().equals(Meanings[5])){
            NewRandomSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            NewRandomSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(NewRandomSelect2.getText().toString().equals(Meanings[5])){
            NewRandomSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            NewRandomSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(NewRandomSelect3.getText().toString().equals(Meanings[5])){
            NewRandomSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            NewRandomSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(NewRandomSelect4.getText().toString().equals(Meanings[5])){
            NewRandomSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            NewRandomSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        NewRandomSelect1.setTextColor(Color.WHITE);
        NewRandomSelect2.setTextColor(Color.WHITE);
        NewRandomSelect3.setTextColor(Color.WHITE);
        NewRandomSelect4.setTextColor(Color.WHITE);
    }

    public void BindSelect(){
        NewRandomSelectRelative.setVisibility(View.VISIBLE);
        NewRandomSelectRelative.startAnimation(mLayoutAnim);
        NewRandomSelectTitle.setText(Titles[5]);
        NewRandomSelect1.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        NewRandomSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        NewRandomSelect1.setTextColor(Color.BLACK);
        NewRandomSelect2.setText(Meanings[5]);
        NewRandomSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        NewRandomSelect1.setTextColor(Color.BLACK);
        NewRandomSelect3.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        NewRandomSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        NewRandomSelect1.setTextColor(Color.BLACK);
        NewRandomSelect4.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        NewRandomSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        NewRandomSelect1.setTextColor(Color.BLACK);
    }

    public void ContentStatus(){
        WordStart++;
        NewRandomProgress.setProgress(WordStart);
        if(WordStart==20){
            startActivity(new Intent(NewRandomAnimActivity.this,NewRandomWinActivity.class));
        }
        NewRandomProgressText.setText(WordStart+"/40");
        //BindStart=mRandom.nextInt(4);
        BindStart=4;
        switch (BindStart){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                BindSelect();
                break;
        }
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
