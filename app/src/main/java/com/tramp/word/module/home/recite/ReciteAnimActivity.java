package com.tramp.word.module.home.recite;

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
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/30.
 */

public class ReciteAnimActivity extends RxBaseActivity {
    @BindView(R.id.recite_anim_back)
    RelativeLayout mReciteAnimBack;
    @BindView(R.id.recite_anim_arrow)
    TextView mReciteAnimArrow;
    @BindView(R.id.recite_anim_progress)
    ProgressBar mReciteAnimProgress;
    @BindView(R.id.recite_anim_fate)
    ImageView mReciteAnimFate;
    @BindView(R.id.recite_anim_out)
    ImageView mReciteAnimOut;
    @BindView(R.id.recite_anim_fate_text)
    TextView mReciteAnimFateText;
    @BindView(R.id.recite_bottom_relative)
    RelativeLayout mReciteBottomRelative;
    @BindView(R.id.recite_anim_progress_text)
    TextView mReciteAnimProgressText;
    @BindView(R.id.recite_anim_relative)
    RelativeLayout mReciteAnimRelative;

    @BindView(R.id.recite_anim_spell_recycler)
    RecyclerView mReciteAnimSpellRecycler;
    @BindView(R.id.recite_anim_spell_letter)
    RecyclerView mReciteAnimSpellLetterRecycler;
    @BindView(R.id.recite_anim_spell_music)
    LinearLayout mReciteAnimSpellMusic;
    @BindView(R.id.recite_anim_spell_delete)
    ImageView mReciteAnimSpellDelete;



    @BindView(R.id.recite_anim_select)
    RelativeLayout mReciteAnimSelectRelative;
    @BindView(R.id.recite_anim_select_title)
    TextView mReciteAnimSelectTitle;
    @BindView(R.id.recite_anim_select_music_img)
    ImageView mReciteAnimSelectMusicImg;
    @BindView(R.id. recite_anim_select_1)
    TextView mReciteAnimSelect1;
    @BindView(R.id. recite_anim_select_2)
    TextView mReciteAnimSelect2;
    @BindView(R.id. recite_anim_select_3)
    TextView mReciteAnimSelect3;
    @BindView(R.id. recite_anim_select_4)
    TextView mReciteAnimSelect4;
    @BindView(R.id.recite_anim_select_music)
    LinearLayout mReciteAnimSelectMusic;

    @BindView(R.id.recite_anim_next)
    TextView mReciteAnimNext;
    @BindView(R.id.recite_anim_live)
    LinearLayout mReciteAnimLive;
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
                mReciteAnimSelectRelative.setVisibility(View.GONE);
                ContentStatus();
            }else if(msg.what==0x1111){
                mReciteAnimSelectRelative.setVisibility(View.GONE);
                mReciteBottomRelative.setVisibility(View.GONE);
                ContentStatus();
            }else if(msg.what==0x1113){
                mReciteAnimRelative.setVisibility(View.VISIBLE);
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
        return R.layout.activity_recite_anim;
    }

    @Override
    public void initView(Bundle save) {
        mLayoutAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.word_book_right_layout_top);
        mAlpha=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mArrowAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_anim_arrow_anim);
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_street@2x.png");
        mReciteAnimBack.setBackground(new BitmapDrawable(back));
        InputStream question=AssetsUtils.getAssetsOpen(getBaseContext(),"question_assets@2x.png");
        mQuestion= BitmapFactory.decodeStream(question);
        mAnimFate=Bitmap.createBitmap(mQuestion,55,400,115,55,mMatrix,false);
        mAnimMusicBack=Bitmap.createBitmap(mQuestion,250,205,150,50,mMatrix,false);
        mMatrix.setRotate(-90);
        mLetterOut=Bitmap.createBitmap(mQuestion,0,400,50,60,mMatrix,false);
        mReciteAnimArrow.startAnimation(mArrowAnim);
        mHandler.sendEmptyMessageDelayed(0x1113,500);
        mReciteAnimFate.setImageBitmap(mAnimFate);
        initCommon();
        initSpell();
        initListen();
        initSelect();
        initFill();
        //WordStart=mRandom.nextInt(4);
        BindSelect();
    }

    public void initCommon(){
        mRandom=new Random();
        mReciteAnimOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mReciteAnimFateText.setText("8");

        mReciteAnimNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciteAnimSelectRelative.getVisibility()==View.VISIBLE){
                    if(!mReciteAnimSelect1.getText().equals(Meanings[5])){
                        mReciteAnimSelect1.startAnimation(mAlpha);
                    }
                    if(!mReciteAnimSelect2.getText().equals(Meanings[5])){
                        mReciteAnimSelect2.startAnimation(mAlpha);
                    }
                    if(!mReciteAnimSelect3.getText().equals(Meanings[5])){
                        mReciteAnimSelect3.startAnimation(mAlpha);
                    }
                    if(!mReciteAnimSelect4.getText().equals(Meanings[5])){
                        mReciteAnimSelect4.startAnimation(mAlpha);
                    }
                    mHandler.sendEmptyMessageDelayed(0x1111,500);
                }
            }
        });

        mReciteAnimLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteAnimActivity.this,ReciteAnimContentActivity.class));
            }
        });
    }

    public void initSpell(){
        mReciteAnimSpellMusic.setBackground(new BitmapDrawable(mAnimMusicBack));
        mReciteAnimSpellDelete.setImageBitmap(mLetterOut);
        mReciteAnimSpellView=new ReciteAnimSpellViewAdapter(mReciteAnimSpellRecycler,Titles[0]);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        mReciteAnimSpellRecycler.setLayoutManager(linearLayout);
        mReciteAnimSpellRecycler.setAdapter(mReciteAnimSpellView);
        mReciteAnimLetterView=new ReciteAnimLetterViewAdapter(mReciteAnimSpellLetterRecycler,Titles[0]);
        mReciteAnimSpellLetterRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),5));
        mReciteAnimSpellLetterRecycler.setAdapter(mReciteAnimLetterView);
    }

    public void initSelect(){
        mReciteAnimSelectMusic.setBackground(new BitmapDrawable(mAnimMusicBack));
        mReciteAnimSelectMusicImg.setImageResource(R.drawable.recite_music_anim);
        mSelectMusicAnim=(AnimationDrawable) mReciteAnimSelectMusicImg.getDrawable();
        mSelectMusicAnim.setOneShot(true);
        mReciteAnimSelectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectMusicAnim.start();
            }
        });

        mReciteAnimSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciteAnimSelect1.getText().toString().equals(Meanings[5])){
                    mReciteAnimSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    mReciteAnimSelect1.setTextColor(Color.WHITE);
                    mReciteAnimSelect2.startAnimation(mAlpha);
                    mReciteAnimSelect3.startAnimation(mAlpha);
                    mReciteAnimSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else{
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        mReciteAnimSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciteAnimSelect2.getText().toString().equals(Meanings[5])){
                    mReciteAnimSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    mReciteAnimSelect2.setTextColor(Color.WHITE);
                    mReciteAnimSelect1.startAnimation(mAlpha);
                    mReciteAnimSelect3.startAnimation(mAlpha);
                    mReciteAnimSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else{
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        mReciteAnimSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciteAnimSelect3.getText().toString().equals(Meanings[5])){
                    mReciteAnimSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    mReciteAnimSelect3.setTextColor(Color.WHITE);
                    mReciteAnimSelect1.startAnimation(mAlpha);
                    mReciteAnimSelect2.startAnimation(mAlpha);
                    mReciteAnimSelect4.startAnimation(mAlpha);
                    mHandler.sendEmptyMessageDelayed(0x1112,500);
                }else {
                    SelectStartButton();
                    Fete--;
                    mReciteBottomRelative.setVisibility(View.VISIBLE);
                }
            }
        });

        mReciteAnimSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReciteAnimSelect4.getText().toString().equals(Meanings[5])){
                    mReciteAnimSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    mReciteAnimSelect4.setTextColor(Color.WHITE);
                    mReciteAnimSelect1.startAnimation(mAlpha);
                    mReciteAnimSelect2.startAnimation(mAlpha);
                    mReciteAnimSelect3.startAnimation(mAlpha);
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
        if(mReciteAnimSelect1.getText().toString().equals(Meanings[5])){
            mReciteAnimSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            mReciteAnimSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(mReciteAnimSelect2.getText().toString().equals(Meanings[5])){
            mReciteAnimSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            mReciteAnimSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(mReciteAnimSelect3.getText().toString().equals(Meanings[5])){
            mReciteAnimSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            mReciteAnimSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        if(mReciteAnimSelect4.getText().toString().equals(Meanings[5])){
            mReciteAnimSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }else{
            mReciteAnimSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
        }
        mReciteAnimSelect1.setTextColor(Color.WHITE);
        mReciteAnimSelect2.setTextColor(Color.WHITE);
        mReciteAnimSelect3.setTextColor(Color.WHITE);
        mReciteAnimSelect4.setTextColor(Color.WHITE);
    }

    public void BindSelect(){
        mReciteAnimSelectRelative.setVisibility(View.VISIBLE);
        mReciteAnimSelectRelative.startAnimation(mLayoutAnim);
        mReciteAnimSelectTitle.setText(Titles[5]);
        mReciteAnimSelect1.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        mReciteAnimSelect1.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        mReciteAnimSelect1.setTextColor(Color.BLACK);
        mReciteAnimSelect2.setText(Meanings[5]);
        mReciteAnimSelect2.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        mReciteAnimSelect1.setTextColor(Color.BLACK);
        mReciteAnimSelect3.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        mReciteAnimSelect3.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        mReciteAnimSelect1.setTextColor(Color.BLACK);
        mReciteAnimSelect4.setText(Meanings[mRandom.nextInt(Meanings.length)]);
        mReciteAnimSelect4.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        mReciteAnimSelect1.setTextColor(Color.BLACK);
    }

    public void initFill(){

    }

    public void ContentStatus(){
        WordStart++;
        mReciteAnimProgress.setProgress(WordStart);
        mReciteAnimFateText.setText(""+Fete);
        if(WordStart==20){
            startActivity(new Intent(ReciteAnimActivity.this,ReciteAnimWinActivity.class));
        }
        if(Fete==0){
            startActivity(new Intent(ReciteAnimActivity.this,ReciteAnimErrorActivity.class));
        }
        mReciteAnimProgressText.setText(WordStart+"/40");
        //BindStart=mRandom.nextInt(4);
        BindStart=4;
        switch (BindStart){
            case 1:
                BindSpell();
                break;
            case 2:
                BindListen();
                break;
            case 3:
                BindFill();
                break;
            case 4:
                BindSelect();
                break;
        }
    }

    public void initListen(){

    }

    public void BindSpell(){
    }

    public void BindListen(){

    }

    public void BindFill(){

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













