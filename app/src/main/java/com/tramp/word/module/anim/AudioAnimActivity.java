package com.tramp.word.module.anim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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
import com.tramp.word.entity.revise.DefaultAudioInfo;
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

public class AudioAnimActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_number)
    TextView DefaultNumber;


    @BindView(R.id.audio_music)
    LinearLayout AudioMusic;
    @BindView(R.id.audio_music_img)
    ImageView AudioMusicImg;
    @BindView(R.id.audio_title)
    TextView AudioTitle;
    @BindView(R.id.audio_title_show)
    TextView AudioTitleShow;
    @BindView(R.id.audio_relative)
    RelativeLayout AudioRelative;
    @BindView(R.id.audio_win_img)
    ImageView AudioWinImg;

    @BindView(R.id.audio_button_one)
    TextView AudioButtonOne;
    @BindView(R.id.audio_button_two)
    TextView AudioButtonTwo;
    @BindView(R.id.audio_button_three)
    TextView AudioButtonThree;
    @BindView(R.id.audio_button_four)
    TextView AudioButtonFour;

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
    private static Animation mScaleAnim;
    private static Animation mAlphaAnim;
    private static Animation mWinScrollAnim;
    private static Animation mScrollAnim;
    private static Animation mBottomEnterAnim;
    private static Animation mTopEnterAnim;
    private static AnimationDrawable AudioMusicAnim;
    private Animation mButtonAnim;
    private static Animator mTopAnim;
    private AnimationDrawable mDetailsMusicAnim;
    private AnimationDrawable mDetailsSentenceAnim;
    private MediaPlayer mSuccessMusic;
    private MediaPlayer mErrorMusic;
    private int WordNumber;
    private int Number=0;
    private int Details=0;
    private Calendar mCalender=Calendar.getInstance();
    private static final int UPDATE_STATUS=0x112;
    private static final int DETAIL_STATUS=0x1113;
    private static final int UPDATE_TIME=300;

    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private List<DefaultWordInfo> words=new ArrayList<>();
    private DefaultAudioInfo audio;
    private List<DefaultAudioInfo> audios=new ArrayList<>();
    private Random mRandom=new Random();

    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_STATUS:
                    AudioRelative.startAnimation(mScrollAnim);
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
        return R.layout.activity_audio_anim;
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

        mSuccessMusic=MediaPlayer.create(getBaseContext(),R.raw.answer_right);
        mErrorMusic=MediaPlayer.create(getBaseContext(),R.raw.answer_wrong);
    }

    private void initAnim(){
        AudioMusicImg.setImageDrawable(getResources().getDrawable(R.drawable.default_music_anim));
        AudioMusicAnim=(AnimationDrawable) AudioMusicImg.getDrawable();
        AudioMusicAnim.setOneShot(false);

        mTopAnim= ObjectAnimator.ofFloat(DefaultUpTitle,"TranslationY",-50,0);
        mTopAnim.setDuration(200);
        mBottomEnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mTopEnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mButtonAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);

        mWinScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_right_enter_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);

        DetailsMusicUrl.setImageResource(R.drawable.default_music_anim);
        mDetailsMusicAnim=(AnimationDrawable) DetailsMusicUrl.getDrawable();
        DetailsSentenceUrl.setImageResource(R.drawable.default_music_blue_bg);
        mDetailsSentenceAnim=(AnimationDrawable) DetailsSentenceUrl.getDrawable();
    }

    private void initBack(){
        CloudGo.setVisibility(View.VISIBLE);
        CloudImg.startAnimation(mAlphaAnim);
        CloudUp2.startAnimation(mButtonAnim);
        CloudUp3.startAnimation(mButtonAnim);
        CloudDown2.startAnimation(mTopEnterAnim);
        CloudDown3.startAnimation(mTopEnterAnim);
    }

    private void initClicked(){
        AudioButtonOne.setClickable(false);
        AudioButtonTwo.setClickable(false);
        AudioButtonThree.setClickable(false);
        AudioButtonFour.setClickable(false);
    }

    private void initDb(){
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
                    audio=new DefaultAudioInfo();
                    audio.setWord_name(words.get(i).getWord_name());
                    audio.setWord_meaning(words.get(i).getWord_meaning());
                    audio.setWord_music_url(words.get(i).getWord_music_url());
                    audio.setWord_select_one(getWordMeaning(words.get(i).getWord_meaning()));
                    audio.setWord_select_two(getWordMeaning(words.get(i).getWord_meaning()));
                    audio.setWord_select_three(getWordMeaning(words.get(i).getWord_meaning()));
                    audio.setWord_select_four(getWordMeaning(words.get(i).getWord_meaning()));
                    switch (mRandom.nextInt(3)){
                        case 0:
                            audio.setWord_select_one(words.get(i).getWord_meaning());
                            break;
                        case 1:
                            audio.setWord_select_two(words.get(i).getWord_meaning());
                            break;
                        case 2:
                            audio.setWord_select_three(words.get(i).getWord_meaning());
                            break;
                        case 3:
                            audio.setWord_select_four(words.get(i).getWord_meaning());
                            break;
                    }
                    audios.add(audio);
                }
                mHandle.post(()->{
                   finishTask();
                });
            }
        }).start();
    }

    public String getWordMeaning(String word){
        String title;
        if(word.equals(title=words.get(mRandom.nextInt(words.size())).getWord_meaning())){
            title=getWordMeaning(word);
        }
        return title;
    }

    private void finishTask(){
        CloudGo.setVisibility(View.GONE);
        WordNumber=words.size();
        initUpdate();
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

    private void initClick(){

        AudioMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioMusicAnim.start();
            }
        });

        AudioTitleShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioTitleShow.setVisibility(View.GONE);
                AudioTitle.setVisibility(View.VISIBLE);
            }
        });

        AudioButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioButtonOne.startAnimation(mScaleAnim);
                if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_one())){
                    mSuccessMusic.start();
                    AudioButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    AudioButtonTwo.startAnimation(mAlphaAnim);
                    AudioButtonThree.startAnimation(mAlphaAnim);
                    AudioButtonFour.startAnimation(mAlphaAnim);
                    mTopAnim.start();
                    AudioWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    AudioButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    mErrorMusic.start();
                    initStaticBack();
                }
                initClicked();
            }
        });

        AudioButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioButtonTwo.startAnimation(mScaleAnim);
                if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_two())){
                    mSuccessMusic.start();
                    AudioButtonOne.startAnimation(mAlphaAnim);
                    AudioButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    AudioButtonThree.startAnimation(mAlphaAnim);
                    AudioButtonFour.startAnimation(mAlphaAnim);
                    mTopAnim.start();
                    AudioWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    AudioButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        AudioButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioButtonThree.startAnimation(mScaleAnim);
                if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_three())){
                    mSuccessMusic.start();
                    AudioButtonOne.startAnimation(mAlphaAnim);
                    AudioButtonTwo.startAnimation(mAlphaAnim);
                    AudioButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    AudioButtonFour.startAnimation(mAlphaAnim);
                    mTopAnim.start();
                    AudioWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    AudioButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
                    initStaticBack();
                }
                initClicked();
            }
        });

        AudioButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioButtonFour.startAnimation(mScaleAnim);
                if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_four())){
                    mSuccessMusic.start();
                    AudioButtonOne.startAnimation(mAlphaAnim);
                    AudioButtonTwo.startAnimation(mAlphaAnim);
                    AudioButtonThree.startAnimation(mAlphaAnim);
                    AudioButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
                    mTopAnim.start();
                    AudioWinImg.startAnimation(mWinScrollAnim);
                    WordNumber--;
                    Number++;
                    mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
                }else{
                    mErrorMusic.start();
                    AudioButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_error));
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
            AudioTitle.setText(audios.get(Number).getWord_name());
            AudioTitle.setVisibility(View.GONE);
            AudioTitleShow.setVisibility(View.VISIBLE);
            AudioButtonOne.setClickable(true);
            AudioButtonOne.setText(audios.get(Number).getWord_select_one());
            AudioButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            AudioButtonTwo.setClickable(true);
            AudioButtonTwo.setText(audios.get(Number).getWord_select_two());
            AudioButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            AudioButtonThree.setClickable(true);
            AudioButtonThree.setText(audios.get(Number).getWord_select_three());
            AudioButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
            AudioButtonFour.setClickable(true);
            AudioButtonFour.setText(audios.get(Number).getWord_select_four());
            AudioButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back_nu));
        }

    }
    private void finishSql(){
        startActivity(new Intent(AudioAnimActivity.this, ReviseFinishActivity.class));
        finish();
    }

    private void initStaticBack(){
        if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_one())){
            AudioButtonOne.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_two())){
            AudioButtonTwo.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_three())){
            AudioButtonThree.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        if(audios.get(Number).getWord_meaning().equals(audios.get(Number).getWord_select_four())){
            AudioButtonFour.setBackground(getResources().getDrawable(R.drawable.btn_anim_button_back));
        }
        Details=1;
        mHandle.sendEmptyMessageDelayed(DETAIL_STATUS,UPDATE_TIME);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initIntent(){
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

