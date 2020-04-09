package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.ReciteAnimLetterViewAdapter;
import com.tramp.word.adapter.ReciteAnimSpellViewAdapter;
import com.tramp.word.adapter.SpellContentAdapter;
import com.tramp.word.adapter.SpellLetterAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultWordsInfo;
import com.tramp.word.entity.recite.AudioAnimInfo;
import com.tramp.word.entity.recite.DefaultAnimInfo;
import com.tramp.word.entity.recite.FillAnimInfo;
import com.tramp.word.entity.recite.SelectAnimInfo;
import com.tramp.word.entity.recite.SpellAnimInfo;
import com.tramp.word.entity.revise.DefaultPinyinInfo;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.DefaultKnowHintDialog;
import com.tramp.word.widget.WordErrorDialog;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/30.
 */

public class ReciteAnimActivity extends RxBaseActivity {
    private static final String LOG="ReciteAnimActivity";
    @BindView(R.id.anim_back)
    RelativeLayout AnimBack;
    @BindView(R.id.anim_arrow)
    TextView AnimArrow;
    @BindView(R.id.anim_progress)
    ProgressBar AnimProgress;
    @BindView(R.id.anim_fate)
    ImageView AnimFate;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.anim_fate_text)
    TextView AnimFateText;
    @BindView(R.id.anim_progress_text)
    TextView AnimProgressText;

    @BindView(R.id.anim_spell)
    LinearLayout AnimSpell;
    @BindView(R.id.anim_spell_title)
    TextView AnimSpellTitle;
    @BindView(R.id.anim_spell_error)
    TextView AnimSpellError;
    @BindView(R.id.anim_spell_letter)
    RecyclerView AnimSpellLetter;
    @BindView(R.id.anim_spell_context)
    RecyclerView AnimSpellContext;
    @BindView(R.id.anim_spell_music)
    LinearLayout AnimSpellMusic;
    @BindView(R.id.anim_spell_music_img)
    ImageView AnimSpellMusicImg;
    @BindView(R.id.anim_spell_delete)
    ImageView AnimSpellDelete;

    @BindView(R.id.anim_select)
    LinearLayout AnimSelect;
    @BindView(R.id.anim_select_title)
    TextView AnimSelectTitle;
    @BindView(R.id.anim_select_music)
    LinearLayout AnimSelectMusic;
    @BindView(R.id.anim_select_music_img)
    ImageView AnimSelectMusicImg;
    @BindView(R.id. anim_select_one)
    TextView AnimSelectOne;
    @BindView(R.id. anim_select_two)
    TextView AnimSelectTwo;
    @BindView(R.id. anim_select_three)
    TextView AnimSelectThree;
    @BindView(R.id. anim_select_four)
    TextView AnimSelectFour;

    @BindView(R.id.anim_audio)
    LinearLayout AnimAudio;
    @BindView(R.id.anim_audio_url)
    ImageView AnimAudioUrl;
    @BindView(R.id.anim_audio_one)
    TextView AnimAudioOne;
    @BindView(R.id.anim_audio_two)
    TextView AnimAudioTwo;
    @BindView(R.id.anim_audio_three)
    TextView AnimAudioThree;
    @BindView(R.id.anim_audio_four)
    TextView AnimAudioFour;

    @BindView(R.id.anim_fill)
    LinearLayout AnimFill;
    @BindView(R.id.anim_fill_title)
    TextView AnimFillTitle;
    @BindView(R.id.anim_fill_meaning)
    TextView AnimFillMeaning;
    @BindView(R.id.anim_fill_one)
    TextView AnimFillOne;
    @BindView(R.id.anim_fill_two)
    TextView AnimFillTwo;
    @BindView(R.id.anim_fill_three)
    TextView AnimFillThree;
    @BindView(R.id.anim_fill_four)
    TextView AnimFillFour;

    @BindView(R.id.bottom_next)
    LinearLayout BottomNext;
    @BindView(R.id.anim_next)
    TextView AnimNext;
    @BindView(R.id.anim_live)
    LinearLayout AnimLive;

    @BindView(R.id.default_back)
    RelativeLayout DefaultBack;
    @BindView(R.id.default_up_relative)
    RelativeLayout DefaultUpRelative;
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

    private int Number;
    private int Now_number;
    private int Heart_number;
    private int Heart;
    private int Total_number;
    private WordErrorDialog mError;
    private Bitmap mAnimFate;
    private Bitmap mAnimMusicBack;
    private Bitmap mLetterOut;
    private Animation mScaleAnim;
    private Animation mArrowAnim;
    private Animation mLayoutAnim;
    private Bitmap mQuestion;
    private Matrix mMatrix=new Matrix();
    private ReciteAnimSpellViewAdapter mReciteAnimSpellView;
    private ReciteAnimLetterViewAdapter mReciteAnimLetterView;
    private MediaPlayer mSuccessMusic;
    private MediaPlayer mErrorMusic;
    private AnimationDrawable mDetailsMusicAnim;
    private AnimationDrawable mDetailsSentenceAnim;
    private AnimationDrawable mAudioAnim;
    private AnimationDrawable mSelectAnim;
    private AnimationDrawable mSpellAnim;
    private Animation mTopAnim;
    private DefaultAnimInfo Anim;
    private SelectAnimInfo Select;
    private SpellAnimInfo Spell;
    private FillAnimInfo Fill;
    private AudioAnimInfo Audio;
    private List<DefaultAnimInfo> Anims=new ArrayList<>();
    private DefaultWordsInfo list;
    private ArrayList<Integer> integer=new ArrayList<>();
    private UserSqlHelper mUser;
    private static final int UPDATE_FILL=0x112;
    private static final int UPDATE_SELECT=0x114;
    private static final int UPDATE_AUDIO=0x115;
    private static final int UPDATE_SPELL=0x116;
    private static final int UPDATE_TIME=500;
    private static final int NUMBER_ZERO=0;
    private static final int NUMBER_ONE=1;
    private static final int NUMBER_TWO=2;
    private static final int NUMBER_THREE=3;
    private Random mRandom=new Random();
    private DefaultPinyinInfo pin;
    private ArrayList<DefaultPinyinInfo> pins=new ArrayList<>();
    private DefaultPinyinInfo yin;
    private ArrayList<DefaultPinyinInfo> yins=new ArrayList<>();
    private int list_number=0;
    private SpellLetterAdapter mSpellLetterAdapter;
    private SpellContentAdapter mSpellContentAdapter;
    private int status;
    private int success=0;
    private String text="";
    private int star=0;
    private int letter_number=15;
    private int Gate=0;
    private DefaultKnowHintDialog mDialog;

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_SELECT:
                    AnimSelect.setVisibility(View.GONE);
                    DefaultBack.setVisibility(View.GONE);
                    DefaultUpRelative.setVisibility(View.GONE);
                    initAdapter();
                    break;
                case UPDATE_SPELL:
                    AnimSpell.setVisibility(View.GONE);
                    DefaultBack.setVisibility(View.GONE);
                    DefaultUpRelative.setVisibility(View.GONE);
                    initAdapter();
                    break;
                case UPDATE_AUDIO:
                    AnimAudio.setVisibility(View.GONE);
                    DefaultBack.setVisibility(View.GONE);
                    DefaultUpRelative.setVisibility(View.GONE);
                    initAdapter();
                    break;
                case UPDATE_FILL:
                    AnimFill.setVisibility(View.GONE);
                    DefaultBack.setVisibility(View.GONE);
                    DefaultUpRelative.setVisibility(View.GONE);
                    initAdapter();
                    break;
            }

            return false;
        }
    });

    private AnimationDrawable mSelectMusicAnim;
    private Animation mAlphaAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_anim;
    }

    @Override
    public void initView(Bundle save) {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            list=bundle.getParcelable(ConstantUtils.RECITE_WORD);
            Log.e(LOG,"list="+list.getWords().size());
            Gate=bundle.getInt(ConstantUtils.WORD_GATE);
        }
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initBack();
        initDb();
        initData();
        initFillData();
        initAudioData();
        initSelectData();
        initSpellData();
    }

    private void initAnim(){
        mLayoutAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mArrowAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);

        mTopAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mSuccessMusic= MediaPlayer.create(getBaseContext(),R.raw.answer_right);
        mErrorMusic=MediaPlayer.create(getBaseContext(),R.raw.answer_wrong);


        DetailsMusicUrl.setImageResource(R.drawable.default_music_anim);
        mDetailsMusicAnim=(AnimationDrawable) DetailsMusicUrl.getDrawable();
        DetailsSentenceUrl.setImageResource(R.drawable.default_music_blue_bg);
        mDetailsSentenceAnim=(AnimationDrawable) DetailsSentenceUrl.getDrawable();

        AnimAudioUrl.setImageResource(R.drawable.recite_anim_audio);
        mAudioAnim=(AnimationDrawable) AnimAudioUrl.getDrawable();
        mAudioAnim.setOneShot(false);
        AnimSelectMusicImg.setImageResource(R.drawable.default_music_anim);
        mSelectAnim=(AnimationDrawable) AnimSelectMusicImg.getDrawable();
        mSelectAnim.setOneShot(false);
        AnimSpellMusicImg.setImageResource(R.drawable.default_music_anim);
        mSpellAnim=(AnimationDrawable) AnimSpellMusicImg.getDrawable();
        mSpellAnim.setOneShot(false);
    }

    private void initBack(){
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_street@2x.png");
        AnimBack.setBackground(new BitmapDrawable(back));

        InputStream question=AssetsUtils.getAssetsOpen(getBaseContext(),"question_assets@2x.png");
        mQuestion= BitmapFactory.decodeStream(question);

        mAnimFate=Bitmap.createBitmap(mQuestion,55,400,115,55,mMatrix,false);
        mAnimMusicBack=Bitmap.createBitmap(mQuestion,250,205,150,50,mMatrix,false);
        mMatrix.setRotate(-90);
        mLetterOut=Bitmap.createBitmap(mQuestion,0,400,50,60,mMatrix,false);
        AnimArrow.startAnimation(mArrowAnim);
        AnimFate.setImageBitmap(mAnimFate);
    }

    private void initDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int number=NUMBER_ZERO;
                int size=list.getWords().size();
                for (int i=NUMBER_ZERO;i<size;i++){
                    if(list.getWords().get(i).getWord_start()==NUMBER_ONE){
                        Anim=new DefaultAnimInfo();
                        Anim.setWord_number(i);
                        Anim.setWord_name(list.getWords().get(i).getWord_name());
                        Anim.setWord_music_url(list.getWords().get(i).getWord_music_url());
                        Anim.setWord_meaning(list.getWords().get(i).getWord_meaning());
                        Anim.setWord_sentence(list.getWords().get(i).getWord_sentence());
                        Anim.setWord_sentence_meaning(list.getWords().get(i).getWord_sentence_meaning());
                        switch (mRandom.nextInt(NUMBER_THREE)){
                            case NUMBER_ZERO:
                                Anim.setAnim_number(NUMBER_ZERO);
                                Select=new SelectAnimInfo();
                                Select.setSelect_one(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Select.setSelect_two(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Select.setSelect_three(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Select.setSelect_four(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                switch (mRandom.nextInt(NUMBER_THREE)){
                                    case NUMBER_ZERO:
                                        Select.setSelect_one(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_ONE:
                                        Select.setSelect_two(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_TWO:
                                        Select.setSelect_three(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_THREE:
                                        Select.setSelect_four(list.getWords().get(i).getWord_name());
                                        break;
                                }
                                Anim.setSelect(Select);
                                break;
                            case NUMBER_ONE:
                                Anim.setAnim_number(NUMBER_ONE);
                                Spell=new SpellAnimInfo();
                                Spell.setSpell_word(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Anim.setSpell(Spell);
                                break;
                            case NUMBER_TWO:
                                Anim.setAnim_number(NUMBER_TWO);
                                Audio=new AudioAnimInfo();
                                Audio.setAudio_one(list.getWords().get(mRandom.nextInt(size)).getWord_meaning());
                                Audio.setAudio_one(list.getWords().get(mRandom.nextInt(size)).getWord_meaning());
                                Audio.setAudio_one(list.getWords().get(mRandom.nextInt(size)).getWord_meaning());
                                Audio.setAudio_one(list.getWords().get(mRandom.nextInt(size)).getWord_meaning());
                                switch (mRandom.nextInt(NUMBER_THREE)){
                                    case NUMBER_ZERO:
                                        Audio.setAudio_one(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_ONE:
                                        Audio.setAudio_two(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_TWO:
                                        Audio.setAudio_three(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_THREE:
                                        Audio.setAudio_four(list.getWords().get(i).getWord_name());
                                        break;
                                }
                                Anim.setAudio(Audio);
                                break;
                            case NUMBER_THREE:
                                Anim.setAnim_number(NUMBER_THREE);
                                Fill=new FillAnimInfo();
                                Fill.setFill_one(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Fill.setFill_two(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Fill.setFill_three(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                Fill.setFill_four(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                switch (mRandom.nextInt(NUMBER_THREE)){
                                    case NUMBER_ZERO:
                                        Fill.setFill_one(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_ONE:
                                        Fill.setFill_two(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_TWO:
                                        Fill.setFill_three(list.getWords().get(i).getWord_name());
                                        break;
                                    case NUMBER_THREE:
                                        Fill.setFill_four(list.getWords().get(i).getWord_name());
                                        break;
                                }
                                Anim.setFill(Fill);
                                break;
                        }
                        Anims.add(Anim);
                        number++;
                    }else{
                        for (int j=NUMBER_ZERO;j<4;j++){
                            Anim=new DefaultAnimInfo();
                            Anim.setAnim_number(j);
                            Anim.setWord_number(i);
                            Anim.setWord_name(list.getWords().get(i).getWord_name());
                            Anim.setWord_music_url(list.getWords().get(i).getWord_music_url());
                            Anim.setWord_meaning(list.getWords().get(i).getWord_meaning());
                            Anim.setWord_sentence(list.getWords().get(i).getWord_sentence());
                            Anim.setWord_sentence_meaning(list.getWords().get(i).getWord_sentence_meaning());
                            switch (j){
                                case NUMBER_ZERO:
                                    Select=new SelectAnimInfo();
                                    Select.setSelect_one(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Select.setSelect_two(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Select.setSelect_three(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Select.setSelect_four(getWordName(list.getWords().get(i).getWord_name(),size));
                                    switch (mRandom.nextInt(NUMBER_THREE)){
                                        case NUMBER_ZERO:
                                            Select.setSelect_one(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_ONE:
                                            Select.setSelect_two(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_TWO:
                                            Select.setSelect_three(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_THREE:
                                            Select.setSelect_four(list.getWords().get(i).getWord_name());
                                            break;
                                    }
                                    Anim.setSelect(Select);
                                    break;
                                case NUMBER_ONE:
                                    Spell=new SpellAnimInfo();
                                    Spell.setSpell_word(list.getWords().get(mRandom.nextInt(size)).getWord_name());
                                    Anim.setSpell(Spell);
                                    break;
                                case NUMBER_TWO:
                                    Audio=new AudioAnimInfo();
                                    Audio.setAudio_one(getWordMeaning(list.getWords().get(i).getWord_name(),size));
                                    Audio.setAudio_two(getWordMeaning(list.getWords().get(i).getWord_name(),size));
                                    Audio.setAudio_three(getWordMeaning(list.getWords().get(i).getWord_name(),size));
                                    Audio.setAudio_four(getWordMeaning(list.getWords().get(i).getWord_name(),size));
                                    switch (mRandom.nextInt(NUMBER_THREE)){
                                        case NUMBER_ZERO:
                                            Audio.setAudio_one(list.getWords().get(i).getWord_meaning());
                                            break;
                                        case NUMBER_ONE:
                                            Audio.setAudio_two(list.getWords().get(i).getWord_meaning());
                                            break;
                                        case NUMBER_TWO:
                                            Audio.setAudio_three(list.getWords().get(i).getWord_meaning());
                                            break;
                                        case NUMBER_THREE:
                                            Audio.setAudio_four(list.getWords().get(i).getWord_meaning());
                                            break;
                                    }
                                    Anim.setAudio(Audio);
                                    break;
                                case NUMBER_THREE:
                                    Fill=new FillAnimInfo();
                                    Fill.setFill_one(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Fill.setFill_two(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Fill.setFill_three(getWordName(list.getWords().get(i).getWord_name(),size));
                                    Fill.setFill_four(getWordName(list.getWords().get(i).getWord_name(),size));
                                    switch (mRandom.nextInt(NUMBER_THREE)){
                                        case NUMBER_ZERO:
                                            Fill.setFill_one(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_ONE:
                                            Fill.setFill_two(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_TWO:
                                            Fill.setFill_three(list.getWords().get(i).getWord_name());
                                            break;
                                        case NUMBER_THREE:
                                            Fill.setFill_four(list.getWords().get(i).getWord_name());
                                            break;
                                    }
                                    Anim.setFill(Fill);
                                    break;
                            }
                            Anims.add(Anim);
                            number++;
                        }
                    }
                }
                Number=NUMBER_ZERO;
                Now_number=NUMBER_ONE;
                Total_number=Anims.size();
                Heart_number=size;
                mHandler.post(()->{
                    initAdapter();
                });
            }
        }).start();
    }

    private String getWordName(String name,int size){
        String title;
        if(name.equals(title=list.getWords().get(mRandom.nextInt(size)).getWord_name())){
           title=getWordName(name,size);
        }
        return title;
    }

    private String getWordMeaning(String name,int size){
        String title;
        if(name.equals(title=list.getWords().get(mRandom.nextInt(size)).getWord_meaning())){
            title=getWordMeaning(name, size);
        }
        return title;
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

        mDialog=new DefaultKnowHintDialog(getBaseContext());

        mDialog.setOkOnClickListener("知道了", new DefaultKnowHintDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                PreferencesUtils.putBoolean(ConstantUtils.RECITE_DATA,false);
                PreferencesUtils.putBoolean(ConstantUtils.SAVE_STATUS,true);
                PreferencesUtils.putInt(ConstantUtils.SAVE_GATE,Gate);
                mDialog.dismiss();
                finish();
            }
        });

        AnimFateText.setText(String.valueOf(Heart_number));

        AnimProgress.setMax(Total_number);

        AnimNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNext.setVisibility(View.GONE);
                int number=Number;
                Number++;
                Now_number++;
                switch (Anims.get(number).getAnim_number()){
                    case NUMBER_ZERO:

                        initSelectEnd();
                        break;
                    case NUMBER_ONE:
                        initSpellEnd();
                        break;
                    case NUMBER_TWO:
                        initAudioEnd();
                        break;
                    case NUMBER_THREE:
                        initFillEnd();
                        break;
                }

            }
        });

        AnimLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDetails();
                DefaultBack.setVisibility(View.VISIBLE);
                DefaultUpRelative.setVisibility(View.VISIBLE);
                DefaultUpRelative.startAnimation(mTopAnim);
            }
        });

        DefaultBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultBack.setVisibility(View.GONE);
            }
        });

        DefaultUpLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size=Anims.get(Number).getAnim_number();
                if(list.getWords().get(size).getWord_life()==NUMBER_ONE){
                    list.getWords().get(size).setWord_life(NUMBER_ZERO);
                    mUser.UpdateWordLife(list.getWords().get(size).getWord_id(),NUMBER_ZERO);
                    DefaultUpLife.setImageResource(R.drawable.icon_newwords);
                }else{
                    list.getWords().get(size).setWord_life(NUMBER_ONE);
                    mUser.UpdateWordLife(list.getWords().get(size).getWord_id(),NUMBER_ONE);
                    DefaultUpLife.setImageResource(R.drawable.icon_newwords_add);
                }

            }
        });

        DetailsMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailsMusicAnim.setOneShot(false);
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

        DefaultUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultBack.setVisibility(View.GONE);
            }
        });
    }

    private void initDetails(){
        int size=Anims.get(Number).getWord_number();
        DetailsTitle.setText(list.getWords().get(size).getWord_name());
        DetailsMusicText.setText(list.getWords().get(size).getWord_music());
        DetailsMeaning.setText(list.getWords().get(size).getWord_meaning());
        if(list.getWords().get(size).getWord_root()!=null
                &&list.getWords().get(size).getWord_root().length()>NUMBER_ZERO){
            DetailsRoot.setVisibility(View.VISIBLE);
            DetailsRootText.setText(list.getWords().get(size).getWord_root());
        }else{
            DetailsRoot.setVisibility(View.GONE);
        }
        if(list.getWords().get(size).getWord_sentence()!=null
                &&list.getWords().get(size).getWord_sentence().length()>NUMBER_ZERO){
            DetailsSentence.setVisibility(View.VISIBLE);
            DetailsSentenceMeaning.setText(list.getWords().get(size).getWord_sentence_meaning());
            DetailsSentenceTitle.setText(list.getWords().get(size).getWord_sentence());
        }else{
            DetailsSentence.setVisibility(View.GONE);
        }
        if(list.getWords().get(size).getWord_life()==NUMBER_ONE){
            DefaultUpLife.setImageResource(R.drawable.icon_newwords_add);
        }else{
            DefaultUpLife.setImageResource(R.drawable.icon_newwords);
        }
    }

    private void initAdapter(){
        AnimProgress.setProgress(Now_number);
        AnimFateText.setText(""+Heart_number);
        Now_number=Total_number;
        if(Now_number==Total_number){
            if(Heart_number>Heart-NUMBER_TWO){
                star=NUMBER_THREE;
            }else if(Heart_number>Heart-5){
                star=NUMBER_TWO;
            }else if(Heart_number>Heart-8&&Heart>NUMBER_ZERO){
                star=NUMBER_ONE;
            }else if(Heart_number>-Heart+NUMBER_ONE){
                star=NUMBER_ZERO;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    int time=7*24*60*60;
                    for (DefaultWordsInfo.Word word :list.getWords()){
                        mUser.UpdateWordWin(word.getWord_id(),
                                word.getWord_error()
                                ,word.getWord_error_text(),
                                Calendar.getInstance().getTime().getTime()+time,
                                Calendar.getInstance().getTime().getTime());
                        Log.e(LOG,"time="+Calendar.getInstance().getTime().getTime());
                    }
                    Cursor cursor=mUser.BookQuery(mUser.WordId());
                    ContentValues values=new ContentValues();
                    values.put("new_num",cursor.getInt(cursor.getColumnIndex("new_num")+list.getWords().size()));
                    values.put("star",cursor.getInt(cursor.getColumnIndex("star")+star));
                    values.put("now_gate",Gate);
                    mUser.UpdateBookRecite(mUser.WordId(),values);
                }
            }).start();
            int word=PreferencesUtils.getInt(ConstantUtils.GATE_WORD,0)+list.getWords().size();
            PreferencesUtils.putInt(ConstantUtils.GATE_WORD,word);
            PreferencesUtils.putBoolean(ConstantUtils.RECITE_SIGN,true);
            ReciteAnimWinActivity.launch(ReciteAnimActivity.this,Gate,star);
            if(PreferencesUtils.getInt(ConstantUtils.RECITE_TIME,NUMBER_ZERO)>Calendar.getInstance().getTime().getTime()){
                PreferencesUtils.putInt(ConstantUtils.RECITE_TIME,(int)Calendar.getInstance().getTime().getTime()+24*60*60);
                ReciteCheckActivity.launch(ReciteAnimActivity.this);
            }
            if(Gate==mUser.QueryWordGate(mUser.WordId())){
                ReciteOpenActivity.launch(ReciteAnimActivity.this);
            }
            finish();
        }
        if(Heart_number==NUMBER_ZERO){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (DefaultWordsInfo.Word word:list.getWords()){
                        mUser.UpdateWordError(word.getWord_id(),word.getWord_error(),word.getWord_error_text());
                    }
                    if(Heart_number>Heart-NUMBER_TWO){
                        star=NUMBER_THREE;
                    }else if(Heart_number>Heart-5){
                        star=NUMBER_TWO;
                    }else if(Heart_number>Heart-8&&Heart>NUMBER_ZERO){
                        star=NUMBER_ONE;
                    }else if(Heart_number>-Heart+NUMBER_ONE){
                        star=NUMBER_ZERO;
                    }
                    mUser.UpdateWordGate(mUser.WordId(),Gate,star);
                }
            }).start();
            ReciteAnimErrorActivity.launch(ReciteAnimActivity.this,Gate);
            finish();
        }
        AnimProgressText.setText(Now_number+"/"+Total_number);
        Log.e(LOG,"switch="+Anims.get(Number).getAnim_number());
        switch (Anims.get(Number).getAnim_number()){
            case NUMBER_ZERO:
                initSelect();
                break;
            case NUMBER_ONE:
                initSpell();
                break;
            case NUMBER_TWO:
                initAudio();
                break;
            case NUMBER_THREE:
                initFill();
                break;
        }
    }

    private void initSpellData(){
        mSpellLetterAdapter=new SpellLetterAdapter(AnimSpellLetter,pins);
        LinearLayoutManager linear=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        AnimSpellLetter.setLayoutManager(linear);
        AnimSpellLetter.setAdapter(mSpellLetterAdapter);

        mSpellContentAdapter=new SpellContentAdapter(AnimSpellContext,yins,status,success);
        AnimSpellContext.setLayoutManager(new GridLayoutManager(getBaseContext(),5));
        AnimSpellContext.setAdapter(mSpellContentAdapter);

        mSpellContentAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(position!=yins.size()-NUMBER_ONE){
                    if(list_number<=text.length()&&yins.get(position).getSelect_status()==NUMBER_ZERO){
                        yins.get(position).setContent_select(pins.get(list_number).getWord_title());
                        yins.get(position).setSelect_status(NUMBER_ONE);
                        pins.get(list_number).setWord_select(yins.get(position).getContent_letter());
                        pins.get(list_number).setWord_id(position);
                        mSpellLetterAdapter.notifyItemChanged(list_number);
                        list_number++;
                        if(list_number==text.length()){
                            status=NUMBER_ONE;
                        }
                    }else{
                        list_number--;
                        yins.get(position).setSelect_status(NUMBER_ZERO);
                        yins.get(position).setContent_select("");
                        pins.get(list_number).setWord_select("");
                        pins.get(list_number).setWord_id(NUMBER_ZERO);
                        mSpellLetterAdapter.notifyItemChanged(list_number);
                        if(list_number<text.length()){
                            status=NUMBER_ZERO;
                        }
                    }
                    if(list_number>NUMBER_ZERO){
                        AnimSpellDelete.setVisibility(View.VISIBLE);
                    }else{
                        AnimSpellDelete.setVisibility(View.GONE);
                    }
                    if(status==NUMBER_ZERO){
                        mSpellContentAdapter.notifyItemChanged(position);
                    }else if(status==NUMBER_ONE){
                        mSpellContentAdapter.notifyDataSetChanged();
                    }
                }else{
                    if(isSpell()){
                        return;
                    }
                    mSuccessMusic.start();
                    Now_number++;
                    Number++;
                    initSpellEnd();
                    initSpellClicked();
                }

            }
        });

        AnimSpellDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_number--;
                yins.get(pins.get(list_number).getWord_id()).setSelect_status(NUMBER_ZERO);
                yins.get(pins.get(list_number).getWord_id()).setContent_select("");
                mSpellContentAdapter.notifyItemChanged(pins.get(list_number).getWord_id());
                pins.get(list_number).setWord_select("");
                pins.get(list_number).setWord_id(NUMBER_ZERO);
                if(list_number==text.length()){
                    mSpellContentAdapter.notifyDataSetChanged();
                    status=NUMBER_ZERO;
                }
                mSpellLetterAdapter.notifyItemChanged(list_number);
                if(list_number<=NUMBER_ZERO){
                    AnimSpellDelete.setVisibility(View.GONE);
                }
            }
        });

        AnimSpellMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpellAnim.start();
            }
        });
    }

    private void initSpellEnd(){
        AnimSpell.startAnimation(mAlphaAnim);
        mHandler.sendEmptyMessageDelayed(UPDATE_SPELL,UPDATE_TIME);
    }

    public boolean isSpell(){
        for (DefaultPinyinInfo pin :pins){
            if(!pin.getWord_title().equals(pin.getWord_select())){
                mErrorMusic.start();
                success=NUMBER_ONE;
                mSpellContentAdapter.notifyDataSetChanged();
                initSpellError();
                initSpellClicked();
                return true;
            }
        }
        return false;
    }

    public void initSpell(){
        AnimSpell.setVisibility(View.VISIBLE);
        AnimSpell.startAnimation(mLayoutAnim);
        AnimSpellTitle.setText(Anims.get(Number).getWord_meaning());
        pins.clear();
        yins.clear();
        String str="abcdefghijklmnopqrstuvwxyz";
        for (int j=NUMBER_ZERO;j<letter_number;j++){
            yin=new DefaultPinyinInfo();
            yin.setContent_letter(""+str.charAt(mRandom.nextInt(26)));
            yin.setContent_select("");
            yin.setSelect_status(NUMBER_ZERO);
            yins.add(yin);
        }
        integer.clear();
        text=Anims.get(Number).getWord_name();
        for (int i=NUMBER_ZERO;i<text.length();i++){
            pin=new DefaultPinyinInfo();
            pin.setWord_title(text.substring(i,i+NUMBER_ONE));
            pin.setWord_select("");
            pin.setWord_id(NUMBER_ZERO);
            yins.get(RandomNumber()).setContent_letter(pin.getWord_title());
            pins.add(pin);
        }
        Log.e(LOG,"yin="+yins.size());
        Log.e(LOG,"pin="+pins.size());
        AnimSpellLetter.setClickable(true);
        mSpellLetterAdapter.notifyDataSetChanged();
        AnimSpellContext.setClickable(true);
        mSpellContentAdapter.notifyDataSetChanged();
    }

    private void initSpellError(){
        list.getWords().get(Anims.get(Number).getWord_number()).setWord_error(NUMBER_ONE);
        if(list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text()!=null
                &&list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text().length()>NUMBER_ZERO){
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text(",2");
        }else{
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text("2");
        }
        Heart_number--;
        BottomNext.setVisibility(View.VISIBLE);
    }

    private void initSpellClicked(){
        AnimSpellLetter.setClickable(false);
        AnimSpellContext.setClickable(false);
    }

    private int RandomNumber(){
        int number=mRandom.nextInt(letter_number);
        for (Integer i:integer){
            if(number==i){
                return RandomNumber();
            }
        }
        integer.add(number);
        return number;
    }

    private void initSelectData(){
        AnimSelectMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectAnim.start();
            }
        });
        AnimSelectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimSelectOne.startAnimation(mScaleAnim);
                AnimSelectOne.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getSelect().getSelect_one().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimSelectOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimSelectTwo.startAnimation(mAlphaAnim);
                    AnimSelectThree.startAnimation(mAlphaAnim);
                    AnimSelectFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initSelectEnd();
                }else{
                    mErrorMusic.start();
                    AnimSelectOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initSelectError();
                }
                initSelectClicked();
            }
        });

        AnimSelectTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimSelectTwo.startAnimation(mScaleAnim);
                AnimSelectTwo.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getSelect().getSelect_two().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimSelectOne.startAnimation(mAlphaAnim);
                    AnimSelectTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimSelectThree.startAnimation(mAlphaAnim);
                    AnimSelectFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initSelectEnd();
                }else{
                    mErrorMusic.start();
                    AnimSelectTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    AnimSelectTwo.setTextColor(getResources().getColor(R.color.white));
                    initSelectError();
                }
                initSelectClicked();
            }
        });

        AnimSelectThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimSelectThree.startAnimation(mScaleAnim);
                AnimSelectThree.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getSelect().getSelect_three().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimSelectOne.startAnimation(mAlphaAnim);
                    AnimSelectTwo.startAnimation(mAlphaAnim);
                    AnimSelectThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimSelectFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initSelectEnd();
                }else{
                    mErrorMusic.start();
                    AnimSelectThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));

                    initSelectError();
                }
                initSelectClicked();
            }
        });

        AnimSelectFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimSelectFour.startAnimation(mScaleAnim);
                AnimSelectFour.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getSelect().getSelect_four().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimSelectOne.startAnimation(mAlphaAnim);
                    AnimSelectTwo.startAnimation(mAlphaAnim);
                    AnimSelectThree.startAnimation(mAlphaAnim);
                    AnimSelectFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    Now_number++;
                    Number++;
                    initSelectEnd();
                }else{
                    mErrorMusic.start();
                    AnimSelectFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initSelectError();
                }
                initSelectClicked();
            }
        });
    }

    private void initSelectEnd(){
        AnimSelect.startAnimation(mAlphaAnim);
        mHandler.sendEmptyMessageDelayed(UPDATE_SELECT,UPDATE_TIME);
    }

    public void initSelect(){
        AnimSelect.setVisibility(View.VISIBLE);
        AnimSelect.startAnimation(mLayoutAnim);
        AnimSelectTitle.setText(Anims.get(Number).getWord_meaning());
        AnimSelectOne.setText(Anims.get(Number).getSelect().getSelect_one());
        AnimSelectOne.setClickable(true);
        AnimSelectOne.setTextColor(getResources().getColor(R.color.black));
        AnimSelectOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimSelectTwo.setText(Anims.get(Number).getSelect().getSelect_two());
        AnimSelectTwo.setClickable(true);
        AnimSelectTwo.setTextColor(getResources().getColor(R.color.black));
        AnimSelectTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimSelectThree.setText(Anims.get(Number).getSelect().getSelect_three());
        AnimSelectThree.setClickable(true);
        AnimSelectThree.setTextColor(getResources().getColor(R.color.black));
        AnimSelectThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimSelectFour.setText(Anims.get(Number).getSelect().getSelect_four());
        AnimSelectFour.setClickable(true);
        AnimSelectFour.setTextColor(getResources().getColor(R.color.black));
        AnimSelectFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
    }

    private void initSelectError(){
        list.getWords().get(Anims.get(Number).getWord_number()).setWord_error(NUMBER_ONE);
        if(list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text()!=null
                &&list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text().length()>NUMBER_ZERO){
             list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text(",1");
        }else{
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text("1");
        }
        if(Anims.get(Number).getSelect().getSelect_one().equals(Anims.get(Number).getWord_name())){
            AnimSelectOne.setTextColor(getResources().getColor(R.color.white));
            AnimSelectOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getSelect().getSelect_two().equals(Anims.get(Number).getWord_name())){
            AnimSelectTwo.setTextColor(getResources().getColor(R.color.white));
            AnimSelectTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getSelect().getSelect_three().equals(Anims.get(Number).getWord_name())){
            AnimSelectThree.setTextColor(getResources().getColor(R.color.white));
            AnimSelectThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getSelect().getSelect_four().equals(Anims.get(Number).getWord_name())){
            AnimSelectFour.setTextColor(getResources().getColor(R.color.white));
            AnimSelectFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        Heart_number--;
        BottomNext.setVisibility(View.VISIBLE);
    }

    private void initSelectClicked(){
        AnimSelectOne.setClickable(false);
        AnimSelectTwo.setClickable(false);
        AnimSelectThree.setClickable(false);
        AnimSelectFour.setClickable(false);
    }

    private void initFillData(){
        AnimFillOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimFillOne.startAnimation(mScaleAnim);
                AnimFillOne.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getFill().getFill_one().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimFillOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimFillTwo.startAnimation(mAlphaAnim);
                    AnimFillThree.startAnimation(mAlphaAnim);
                    AnimFillFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                   initFillEnd();
                }else{
                    mErrorMusic.start();
                    AnimFillOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    AnimFillOne.setTextColor(getResources().getColor(R.color.white));
                    initFillError();
                }
                initFillClicked();
            }
        });

        AnimFillTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimFillTwo.startAnimation(mScaleAnim);
                AnimFillTwo.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getFill().getFill_two().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimFillOne.startAnimation(mAlphaAnim);
                    AnimFillTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimFillThree.startAnimation(mAlphaAnim);
                    AnimFillFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initFillEnd();
                }else{
                    mErrorMusic.start();
                    AnimFillTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    AnimFillTwo.setTextColor(getResources().getColor(R.color.white));
                    initFillError();
                }
                initFillClicked();
            }
        });

        AnimFillThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimFillThree.startAnimation(mScaleAnim);
                AnimFillThree.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getFill().getFill_three().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimFillOne.startAnimation(mAlphaAnim);
                    AnimFillTwo.startAnimation(mAlphaAnim);
                    AnimFillThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimFillFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initFillEnd();
                }else{
                    mErrorMusic.start();
                    AnimFillThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    AnimFillThree.setTextColor(getResources().getColor(R.color.white));
                    initFillError();
                }
                initFillClicked();
            }
        });

        AnimFillFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimFillFour.startAnimation(mScaleAnim);
                AnimFillFour.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getFill().getFill_four().equals(Anims.get(Number).getWord_name())){
                    mSuccessMusic.start();
                    AnimFillOne.startAnimation(mAlphaAnim);
                    AnimFillTwo.startAnimation(mAlphaAnim);
                    AnimFillThree.startAnimation(mAlphaAnim);
                    AnimFillFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    Now_number++;
                    Number++;
                    initFillEnd();
                }else{
                    mErrorMusic.start();
                    AnimFillFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    AnimFillFour.setTextColor(getResources().getColor(R.color.white));
                    initFillError();
                }
                initFillClicked();
            }
        });
    }

    private void initFillEnd(){
        AnimFill.startAnimation(mAlphaAnim);
        mHandler.sendEmptyMessageDelayed(UPDATE_FILL,UPDATE_TIME);
    }

    public void initFill(){
        AnimFill.setVisibility(View.VISIBLE);
        AnimFill.startAnimation(mLayoutAnim);
        AnimFillTitle.setText(Anims.get(Number).getWord_sentence());
        AnimFillMeaning.setText(Anims.get(Number).getWord_sentence_meaning());
        AnimFillOne.setText(Anims.get(Number).getFill().getFill_one());
        AnimFillOne.setClickable(true);
        AnimFillOne.setTextColor(getResources().getColor(R.color.black));
        AnimFillOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimFillTwo.setText(Anims.get(Number).getFill().getFill_two());
        AnimFillTwo.setClickable(true);
        AnimFillTwo.setTextColor(getResources().getColor(R.color.black));
        AnimFillTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimFillThree.setText(Anims.get(Number).getFill().getFill_three());
        AnimFillThree.setClickable(true);
        AnimFillThree.setTextColor(getResources().getColor(R.color.black));
        AnimFillThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimFillFour.setText(Anims.get(Number).getFill().getFill_four());
        AnimFillFour.setClickable(true);
        AnimFillFour.setTextColor(getResources().getColor(R.color.black));
        AnimFillFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        list_number=0;
    }

    private void initFillError(){
        list.getWords().get(Anims.get(Number).getWord_number()).setWord_error(NUMBER_ONE);
        if(list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text()!=null
                &&list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text().length()>NUMBER_ZERO){
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text(",4");
        }else{
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text("4");
        }
        if(Anims.get(Number).getFill().getFill_one().equals(Anims.get(Number).getWord_name())){
            AnimFillOne.setTextColor(getResources().getColor(R.color.white));
            AnimFillOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getFill().getFill_two().equals(Anims.get(Number).getWord_name())){
            AnimFillTwo.setTextColor(getResources().getColor(R.color.white));
            AnimFillTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getFill().getFill_three().equals(Anims.get(Number).getWord_name())){
            AnimFillThree.setTextColor(getResources().getColor(R.color.white));
            AnimFillThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getFill().getFill_four().equals(Anims.get(Number).getWord_name())){
            AnimFillFour.setTextColor(getResources().getColor(R.color.white));
            AnimFillFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        Heart_number--;
        BottomNext.setVisibility(View.VISIBLE);
    }

    private void initFillClicked(){
        AnimFillOne.setClickable(false);
        AnimFillTwo.setClickable(false);
        AnimFillThree.setClickable(false);
        AnimFillFour.setClickable(false);
    }

    private void initAudioData(){
        AnimAudioOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimAudioOne.startAnimation(mScaleAnim);
                AnimAudioOne.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getAudio().getAudio_one().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimAudioOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimAudioTwo.startAnimation(mAlphaAnim);
                    AnimAudioThree.startAnimation(mAlphaAnim);
                    AnimAudioFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initAudioEnd();
                }else{
                    mErrorMusic.start();
                    AnimAudioOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initAudioError();
                }
                initAudioClicked();
            }
        });

        AnimAudioTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimAudioTwo.startAnimation(mScaleAnim);
                AnimAudioTwo.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getAudio().getAudio_two().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimAudioOne.startAnimation(mAlphaAnim);
                    AnimAudioTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimAudioThree.startAnimation(mAlphaAnim);
                    AnimAudioFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initAudioEnd();
                }else{
                    mErrorMusic.start();
                    AnimAudioTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initAudioError();
                }
                initAudioClicked();
            }
        });

        AnimAudioThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimAudioThree.startAnimation(mScaleAnim);
                AnimAudioThree.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getAudio().getAudio_three().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimAudioOne.startAnimation(mAlphaAnim);
                    AnimAudioTwo.startAnimation(mAlphaAnim);
                    AnimAudioThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    AnimAudioFour.startAnimation(mAlphaAnim);
                    Now_number++;
                    Number++;
                    initAudioEnd();
                }else{
                    mErrorMusic.start();
                    AnimAudioThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initAudioError();
                }
                initAudioClicked();
            }
        });

        AnimAudioFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimAudioFour.startAnimation(mScaleAnim);
                AnimAudioFour.setTextColor(getResources().getColor(R.color.white));
                if(Anims.get(Number).getAudio().getAudio_four().equals(Anims.get(Number).getWord_meaning())){
                    mSuccessMusic.start();
                    AnimAudioOne.startAnimation(mAlphaAnim);
                    AnimAudioTwo.startAnimation(mAlphaAnim);
                    AnimAudioThree.startAnimation(mAlphaAnim);
                    AnimAudioFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
                    Now_number++;
                    Number++;
                    initAudioEnd();
                }else{
                    mErrorMusic.start();
                    AnimAudioFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_red));
                    initAudioError();
                }
                initAudioClicked();
            }
        });

        AnimAudioUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioAnim.start();
            }
        });
    }

    private void initAudioEnd(){
        AnimAudio.startAnimation(mAlphaAnim);
        mHandler.sendEmptyMessageDelayed(UPDATE_AUDIO,UPDATE_TIME);
    }

    public void initAudio(){
        AnimAudio.setVisibility(View.VISIBLE);
        AnimAudio.startAnimation(mLayoutAnim);
        AnimAudioOne.setText(Anims.get(Number).getAudio().getAudio_one());
        AnimAudioOne.setClickable(true);
        AnimAudioOne.setTextColor(getResources().getColor(R.color.black));
        AnimAudioOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimAudioTwo.setText(Anims.get(Number).getAudio().getAudio_two());
        AnimAudioTwo.setClickable(true);
        AnimAudioTwo.setTextColor(getResources().getColor(R.color.black));
        AnimAudioTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimAudioThree.setText(Anims.get(Number).getAudio().getAudio_three());
        AnimAudioThree.setClickable(true);
        AnimAudioThree.setTextColor(getResources().getColor(R.color.black));
        AnimAudioThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
        AnimAudioFour.setText(Anims.get(Number).getAudio().getAudio_four());
        AnimAudioFour.setClickable(true);
        AnimAudioFour.setTextColor(getResources().getColor(R.color.black));
        AnimAudioFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_back));
    }

    private void initAudioError(){
        list.getWords().get(Anims.get(Number).getWord_number()).setWord_error(NUMBER_ONE);
        if(list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text()!=null
                &&list.getWords().get(Anims.get(Number).getWord_number()).getWord_error_text().length()>NUMBER_ZERO){
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text(",3");
        }else{
            list.getWords().get(Anims.get(Number).getWord_number()).setWord_error_text("3");
        }
        if(Anims.get(Number).getAudio().getAudio_one().equals(Anims.get(Number).getWord_meaning())){
            AnimAudioOne.setTextColor(getResources().getColor(R.color.white));
            AnimAudioOne.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getAudio().getAudio_two().equals(Anims.get(Number).getWord_meaning())){
            AnimAudioTwo.setTextColor(getResources().getColor(R.color.white));
            AnimAudioTwo.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getAudio().getAudio_three().equals(Anims.get(Number).getWord_meaning())){
            AnimAudioThree.setTextColor(getResources().getColor(R.color.white));
            AnimAudioThree.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        if(Anims.get(Number).getAudio().getAudio_four().equals(Anims.get(Number).getWord_meaning())){
            AnimAudioFour.setTextColor(getResources().getColor(R.color.white));
            AnimAudioFour.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_green));
        }
        Heart_number--;
        BottomNext.setVisibility(View.VISIBLE);
    }

    private void initAudioClicked(){
        AnimAudioOne.setClickable(false);
        AnimAudioTwo.setClickable(false);
        AnimAudioThree.setClickable(false);
        AnimAudioFour.setClickable(false);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PreferencesUtils.getBoolean(ConstantUtils.RECITE_DATA,true)){
                    mDialog.show();
                    return;
                }
                PreferencesUtils.putBoolean(ConstantUtils.SAVE_STATUS,true);
                PreferencesUtils.putInt(ConstantUtils.SAVE_GATE,Gate);
                finish();
            }
        });
    }

    public static void launch(Activity activity, DefaultWordsInfo words,int gate){
        Intent intent=new Intent(activity,ReciteAnimActivity.class);
        Bundle bundle=new Bundle();
        bundle.putParcelable(ConstantUtils.RECITE_WORD,words);
        bundle.putInt(ConstantUtils.WORD_GATE,gate);
        Log.e(LOG,"bundle="+words.getWords().size());
        intent.putExtras(bundle);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(PreferencesUtils.getBoolean(ConstantUtils.RECITE_DATA,true)){
            mDialog.show();
            return;
        }
        PreferencesUtils.putBoolean(ConstantUtils.SAVE_STATUS,true);
        PreferencesUtils.putInt(ConstantUtils.SAVE_GATE,Gate);
    }
}













