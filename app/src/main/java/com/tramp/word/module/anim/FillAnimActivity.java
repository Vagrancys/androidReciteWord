package com.tramp.word.module.anim;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.revise.DefaultFillInfo;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.revise.ReviseFinishActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.WordErrorDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/13.
 *
 */

public class FillAnimActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView FillOut;
    @BindView(R.id.default_number)
    TextView DefaultNumber;
    @BindView(R.id.fill_relative)
    RelativeLayout FillRelative;

    @BindView(R.id.fill_title)
    TextView FillTitle;
    @BindView(R.id.fill_sentence_meaning)
    TextView FillSentenceMeaning;
    @BindView(R.id.fill_win_img)
    ImageView FillWinImg;
    @BindView(R.id.fill_button_one)
    TextView FillButtonOne;
    @BindView(R.id.fill_button_two)
    TextView FillButtonTwo;
    @BindView(R.id.fill_button_three)
    TextView FillButtonThree;
    @BindView(R.id.fill_button_four)
    TextView FillButtonFour;

    @BindView(R.id.default_back)
    RelativeLayout DefaultBack;
    @BindView(R.id.default_up_relative)
    RelativeLayout DefaultUpRelative;
    @BindView(R.id.default_up_title)
    TextView DefaultUpTitle;
    @BindView(R.id.default_life)
    LinearLayout DefaultLife;
    @BindView(R.id.default_up)
    LinearLayout DefaultUp;

    @BindView(R.id.cloud_go)
    RelativeLayout CloudGo;
    @BindView(R.id.cloud_down_2)
    ImageView CloudDown2;
    @BindView(R.id.cloud_down_3)
    ImageView CloudDown3;
    @BindView(R.id.cloud_up_2)
    ImageView CloudUp2;
    @BindView(R.id.cloud_up_3)
    ImageView CloudUp3;
    @BindView(R.id.cloud_img)
    ImageView CloudImg;

    @BindView(R.id.details_title)
    TextView DetailsTitle;
    @BindView(R.id.details_music)
    LinearLayout DetailsMusic;
    @BindView(R.id.details_music_text)
    TextView DetailsMusicText;
    @BindView(R.id.details_music_url)
    ImageView DetailsMusicUrl;
    @BindView(R.id.details_meaning)
    TextView DetailsMeaning;
    @BindView(R.id.details_root)
    LinearLayout DetailsRoot;
    @BindView(R.id.details_root_text)
    TextView DetailsRootText;
    @BindView(R.id.details_sentence)
    RelativeLayout DetailsSentence;
    @BindView(R.id.details_sentence_title)
    TextView DetailsSentenceTitle;
    @BindView(R.id.details_sentence_url)
    ImageView DetailsSentenceUrl;
    @BindView(R.id.details_show)
    TextView DetailsShow;
    @BindView(R.id.details_sentence_meaning)
    TextView DetailsSentenceMeaning;
    @BindView(R.id.details_error)
    TextView DetailsError;
    @BindView(R.id.default_up_life)
    ImageView DefaultUpLife;
    @BindView(R.id.default_up_down)
    ImageView DefaultUpDown;

    private WordErrorDialog mError;
    private Animation mScaleAnim;
    private Animation mAlphaAnim;
    private Animation mWinScrollAnim;
    private Animation mScrollAnim;
    private Animation mButtonAnim;
    private Animation mBottomEnterAnim;
    private Animation mTopAnim;
    private AnimationDrawable mDetailsMusicAnim;
    private AnimationDrawable mDetailsSentenceAnim;
    private MediaPlayer mSuccessMusic;
    private MediaPlayer mErrorMusic;
    private int WordNumber;
    private int Number=0;
    private int Details=0;
    private static final int UPDATE_STATUS=0x112;
    private static final int DETAIL_STATUS=0x1113;
    private static final int UPDATE_TIME=300;
    private Calendar mCalender=Calendar.getInstance();

    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private List<DefaultWordInfo> words=new ArrayList<>();
    private DefaultFillInfo fill;
    private List<DefaultFillInfo> fills=new ArrayList<>();
    private Random mRandom=new Random();
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_STATUS:
                    FillRelative.startAnimation(mScrollAnim);
                    DefaultBack.setVisibility(View.GONE);
                    DefaultUpRelative.setVisibility(View.GONE);
                    initUpdate();
                    break;
                case DETAIL_STATUS:
                    initDetailWord();
                    DefaultBack.setVisibility(View.VISIBLE);
                    DefaultUpRelative.setVisibility(View.VISIBLE);
                    DefaultUpRelative.startAnimation(mBottomEnterAnim);
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
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initBack();
        initData();
        initDb();
        initClick();
    }

    private void initBack(){
        CloudGo.setVisibility(View.VISIBLE);
        CloudImg.startAnimation(mAlphaAnim);
        CloudUp2.startAnimation(mButtonAnim);
        CloudUp3.startAnimation(mButtonAnim);
        CloudDown2.startAnimation(mTopAnim);
        CloudDown3.startAnimation(mTopAnim);
    }

    private void initDetailWord(){
        int size;
        if(Details==0){
            size=Number-1;
        }else{
            size=Number;
        }
        DetailsTitle.setText(words.get(size).getWord_name());
        DetailsMusicText.setText(words.get(size).getWord_music());
        DetailsMeaning.setText(words.get(size).getWord_meaning());
        if(words.get(size).getWord_root()!=null&&words.get(size).getWord_root().length()>0){
            DetailsRoot.setVisibility(View.VISIBLE);
            DetailsRootText.setText(words.get(size).getWord_root());
        }else{
            DetailsRoot.setVisibility(View.GONE);
        }
        if(words.get(size).getWord_sentence()!=null&&words.get(size).getWord_sentence().length()>0){
            DetailsSentence.setVisibility(View.VISIBLE);
            DetailsSentenceMeaning.setText(words.get(size).getWord_sentence_meaning());
            DetailsSentenceTitle.setText(words.get(size).getWord_sentence());
        }else{
            DetailsSentence.setVisibility(View.GONE);
        }
        if(words.get(size).getWord_life()==1){
            DefaultUpLife.setImageResource(R.drawable.icon_newwords_add);
        }else{
            DefaultUpLife.setImageResource(R.drawable.icon_newwords);
        }
    }

    private void initUpdate(){
        mSuccessMusic.stop();
        mErrorMusic.stop();
        if(WordNumber==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (DefaultWordInfo word :words){
                        mUser.UpdateWordTime(word.getWord_id(),word.getWord_time(),word.getWord_time()+60*60*24*7);
                    }
                    mHandle.post(()->{
                        finishSql();
                    });
                }
            }).start();

        }else{
            if(Number==0){
                DefaultUp.setVisibility(View.GONE);
            }else{
                DefaultUp.setVisibility(View.VISIBLE);
                DefaultUpTitle.setText(words.get(Number-1).getWord_name());
            }
            DefaultNumber.setText(String.valueOf(WordNumber));
            FillTitle.setText(fills.get(Number).getWord_sentence());
            FillSentenceMeaning.setText(fills.get(Number).getWord_sentence_meaning());
            FillButtonOne.setClickable(true);
            FillButtonOne.setText(fills.get(Number).getWord_fill_one());
            FillButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            FillButtonTwo.setClickable(true);
            FillButtonTwo.setText(fills.get(Number).getWord_fill_two());
            FillButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            FillButtonThree.setClickable(true);
            FillButtonThree.setText(fills.get(Number).getWord_fill_three());
            FillButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            FillButtonFour.setClickable(true);
            FillButtonFour.setText(fills.get(Number).getWord_fill_four());
            FillButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        }

    }

    public void finishSql(){
        startActivity(new Intent(FillAnimActivity.this, ReviseFinishActivity.class));
        finish();
    }

    public void initAnim(){
        mBottomEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mWinScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_right_enter_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);
        mButtonAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);

        DetailsMusicUrl.setImageResource(R.drawable.default_music_anim);
        mDetailsMusicAnim=(AnimationDrawable) DetailsMusicUrl.getDrawable();
        DetailsSentenceUrl.setImageResource(R.drawable.default_music_blue_bg);
        mDetailsSentenceAnim=(AnimationDrawable) DetailsSentenceUrl.getDrawable();
    }

    private void initData(){
        mError=new WordErrorDialog(getBaseContext());
        mError.setOkOnClickListener("提示", new WordErrorDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                Utils.ShowToast(getBaseContext(),mError.getEdit());
            }
        });
        mError.setCancelOnClickListener("取消", new WordErrorDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mError.dismiss();
            }
        });

        mSuccessMusic= MediaPlayer.create(getBaseContext(),R.raw.answer_right);
        mErrorMusic=MediaPlayer.create(getBaseContext(),R.raw.answer_wrong);
    }

    public void initClick(){
        FillButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillButtonOne.startAnimation(mScaleAnim);
                if(fills.get(Number).getWord_fill_one().equals(fills.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    FillButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    FillButtonTwo.startAnimation(mAlphaAnim);
                    FillButtonThree.startAnimation(mAlphaAnim);
                    FillButtonFour.startAnimation(mAlphaAnim);
                    DefaultUpTitle.startAnimation(mTopAnim);
                    FillWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    FillButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        FillButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillButtonTwo.startAnimation(mScaleAnim);
                if(fills.get(Number).getWord_fill_two().equals(fills.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    FillButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    FillButtonOne.startAnimation(mAlphaAnim);
                    FillButtonThree.startAnimation(mAlphaAnim);
                    FillButtonFour.startAnimation(mAlphaAnim);
                    DefaultUpTitle.startAnimation(mTopAnim);
                    FillWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    FillButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        FillButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillButtonThree.startAnimation(mScaleAnim);
                if(fills.get(Number).getWord_fill_three().equals(fills.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    FillButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    FillButtonOne.startAnimation(mAlphaAnim);
                    FillButtonTwo.startAnimation(mAlphaAnim);
                    FillButtonFour.startAnimation(mAlphaAnim);
                    DefaultUpTitle.startAnimation(mTopAnim);
                    FillWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    FillButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        FillButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillButtonFour.startAnimation(mScaleAnim);
                if(fills.get(Number).getWord_fill_four().equals(fills.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    FillButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    FillButtonOne.startAnimation(mAlphaAnim);
                    FillButtonTwo.startAnimation(mAlphaAnim);
                    FillButtonThree.startAnimation(mAlphaAnim);
                    DefaultUpTitle.startAnimation(mTopAnim);
                    FillWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    FillButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        DefaultUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Details==0){
                    DefaultBack.setVisibility(View.GONE);
                }else{
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }

            }
        });

        DefaultLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.UpdateWordLife(words.get(Number).getWord_id(),1);
                WordNumber--;
                Number++;
                mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
            }
        });

        DefaultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details=0;
                mHandle.sendEmptyMessageDelayed(DETAIL_STATUS,UPDATE_TIME);
            }
        });

        DefaultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Details==0){
                    DefaultBack.setVisibility(View.GONE);
                }else{
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }
            }
        });

        DefaultUpLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size;
                if(Details==0){
                    size=Number-1;
                }else{
                    size=Number;
                }
                if(words.get(size).getWord_life()==1){
                    words.get(size).setWord_life(0);
                    mUser.UpdateWordLife(words.get(Number).getWord_id(),0);
                    DefaultUpLife.setImageResource(R.drawable.icon_newwords);
                }else{
                    words.get(size).setWord_life(1);
                    mUser.UpdateWordLife(words.get(Number).getWord_id(),1);
                    DefaultUpLife.setImageResource(R.drawable.icon_newwords_add);
                }

            }
        });

        DetailsMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailsMusicAnim.start();
            }
        });

        DetailsSentenceUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailsSentenceAnim.start();
            }
        });

        DetailsShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsShow.setVisibility(View.GONE);
                DetailsSentenceMeaning.setVisibility(View.VISIBLE);
            }
        });

        DetailsError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mError.show();
            }
        });
    }

    private void initClicked(){
        FillButtonOne.setClickable(false);
        FillButtonTwo.setClickable(false);
        FillButtonThree.setClickable(false);
        FillButtonFour.setClickable(false);
    }

    public void initStaticBack(){
        if(fills.get(Number).getWord_name().equals(fills.get(Number).getWord_fill_one())){
            FillButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(fills.get(Number).getWord_name().equals(fills.get(Number).getWord_fill_two())){
            FillButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(fills.get(Number).getWord_name().equals(fills.get(Number).getWord_fill_three())){
            FillButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(fills.get(Number).getWord_name().equals(fills.get(Number).getWord_fill_four())){
            FillButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        Details=1;
        mHandle.sendEmptyMessageDelayed(DETAIL_STATUS,UPDATE_TIME);
    }

    public void initDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=mUser.WordReviseAll(mUser.WordId(),mCalender.getTime().getTime());
                while (cursor.moveToNext()){
                    word=new DefaultWordInfo();
                    word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                    word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                    word.setWord_music_url(cursor.getString(cursor.getColumnIndex("word_music_url")));
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_music(cursor.getString(cursor.getColumnIndex("word_music")));
                    word.setWord_sentence(cursor.getString(cursor.getColumnIndex("word_sentence")));
                    word.setWord_sentence_url(cursor.getString(cursor.getColumnIndex("word_sentence_url")));
                    word.setWord_sentence_meaning(cursor.getString(cursor.getColumnIndex("word_sentence_meaning")));
                    word.setWord_root(cursor.getString(cursor.getColumnIndex("word_root")));
                    word.setWord_life(cursor.getInt(cursor.getColumnIndex("word_life")));
                    words.add(word);
                }
                cursor.close();
                for (int i=0;i<words.size();i++){
                    fill=new DefaultFillInfo();
                    fill.setWord_name(words.get(i).getWord_name());
                    fill.setWord_sentence(words.get(i).getWord_sentence());
                    fill.setWord_sentence_meaning(words.get(i).getWord_sentence_meaning());
                    fill.setWord_fill_one(getWordName(words.get(i).getWord_name()));
                    fill.setWord_fill_two(getWordName(words.get(i).getWord_name()));
                    fill.setWord_fill_three(getWordName(words.get(i).getWord_name()));
                    fill.setWord_fill_four(getWordName(words.get(i).getWord_name()));
                    switch (mRandom.nextInt(3)){
                        case 0:
                            fill.setWord_fill_one(words.get(i).getWord_name());
                            break;
                        case 1:
                            fill.setWord_fill_two(words.get(i).getWord_name());
                            break;
                        case 2:
                            fill.setWord_fill_three(words.get(i).getWord_name());
                            break;
                        case 3:
                            fill.setWord_fill_four(words.get(i).getWord_name());
                            break;
                    }
                    fills.add(fill);
                }
                mHandle.post(()->{
                   finishTask();
                });
            }
        }).start();
    }

    public String getWordName(String word){
        String title;
        if(word.equals(title=words.get(mRandom.nextInt(words.size())).getWord_name())){
            title=getWordName(word);
        }
        return title;
    }

    public void finishTask(){
        CloudGo.setVisibility(View.GONE);
        WordNumber=words.size();
        initUpdate();
    }

    @Override
    protected void initToolBar() {
        FillOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initIntent(){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putInt("data",WordNumber);
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

