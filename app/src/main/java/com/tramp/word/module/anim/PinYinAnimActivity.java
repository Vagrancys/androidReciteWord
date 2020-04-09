package com.tramp.word.module.anim;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.PinContentAdapter;
import com.tramp.word.adapter.PinLetterAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.revise.DefaultLetterInfo;
import com.tramp.word.entity.revise.DefaultPinyinInfo;
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
 * Created by Administrator on 2019/2/15.
 */

public class PinYinAnimActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView PinOut;
    @BindView(R.id.default_number)
    TextView DefaultNumber;

    @BindView(R.id.pin_relative)
    RelativeLayout PinRelative;

    @BindView(R.id.pin_title)
    TextView PinTitle;
    @BindView(R.id.pin_title_recycler)
    RecyclerView PinTitleRecycler;
    @BindView(R.id.pin_delete_img)
    ImageView PinDeleteImg;
    @BindView(R.id.pin_content_recycler)
    RecyclerView PinContentRecycler;
    @BindView(R.id.pin_music)
    LinearLayout PinMusic;
    @BindView(R.id.pin_music_img)
    ImageView PinMusicImg;
    @BindView(R.id.pin_win_img)
    ImageView PinWinImg;
    @BindView(R.id.pin_button_start)
    TextView PinButtonStart;

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
    private Animation mScrollAnim;
    private AnimationDrawable mMusicAnim;
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
    private DefaultLetterInfo letter;
    private ArrayList<DefaultLetterInfo> letters=new ArrayList<>();
    private DefaultPinyinInfo pin;
    private ArrayList<DefaultPinyinInfo> pins=new ArrayList<>();
    private DefaultPinyinInfo yin;
    private ArrayList<DefaultPinyinInfo> yins=new ArrayList<>();
    private PinLetterAdapter mPinLetterAdapter;
    private PinContentAdapter mPinContentAdapter;
    private int letter_number=15;
    private int status;
    private int success=0;
    private String text="";
    private Random mRandom=new Random();
    private ArrayList<Integer> integer=new ArrayList<>();
    private Handler mHandle=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_STATUS:
                    PinRelative.startAnimation(mScrollAnim);
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
    private int list=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pin_anim;
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

    public void initAnim(){
        PinMusicImg.setImageDrawable(getResources().getDrawable(R.drawable.default_music_anim));
        mMusicAnim=(AnimationDrawable) PinMusicImg.getDrawable();
        mMusicAnim.setOneShot(false);

        mBottomEnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mButtonAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);
        mScrollAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_left_exit_anim);

        DetailsMusicUrl.setImageResource(R.drawable.default_music_anim);
        mDetailsMusicAnim=(AnimationDrawable) DetailsMusicUrl.getDrawable();
        DetailsSentenceUrl.setImageResource(R.drawable.default_music_blue_bg);
        mDetailsSentenceAnim=(AnimationDrawable) DetailsSentenceUrl.getDrawable();
    }

    private void initData(){
        mPinLetterAdapter=new PinLetterAdapter(PinTitleRecycler,pins);
        LinearLayoutManager linear=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        PinTitleRecycler.setLayoutManager(linear);
        PinTitleRecycler.setAdapter(mPinLetterAdapter);

        mPinContentAdapter=new PinContentAdapter(PinContentRecycler,yins,status,success);
        PinContentRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),5));
        PinContentRecycler.setAdapter(mPinContentAdapter);

        mPinContentAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(list<=text.length()&&yins.get(position).getSelect_status()==0){
                    yins.get(position).setContent_select(pins.get(list).getWord_title());
                    yins.get(position).setSelect_status(1);
                    pins.get(list).setWord_select(yins.get(position).getContent_letter());
                    pins.get(list).setWord_id(position);
                    mPinLetterAdapter.notifyItemChanged(list);
                    list++;
                    if(list==text.length()){
                        status=1;
                    }
                }else{
                    list--;
                    yins.get(position).setSelect_status(0);
                    yins.get(position).setContent_select("");
                    pins.get(list).setWord_select("");
                    pins.get(list).setWord_id(0);
                    mPinLetterAdapter.notifyItemChanged(list);
                    if(list<text.length()){
                        status=0;
                    }
                }
                if(list>0){
                    PinDeleteImg.setVisibility(View.VISIBLE);
                }else{
                    PinDeleteImg.setVisibility(View.GONE);
                }
                if(status==0){
                    mPinContentAdapter.notifyItemChanged(position);
                }else if(status==1){
                    mPinContentAdapter.notifyDataSetChanged();
                }
            }
        });

        PinDeleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list--;
                yins.get(pins.get(list).getWord_id()).setSelect_status(0);
                yins.get(pins.get(list).getWord_id()).setContent_select("");
                mPinContentAdapter.notifyItemChanged(pins.get(list).getWord_id());
                pins.get(list).setWord_select("");
                pins.get(list).setWord_id(0);
                if(list==text.length()){
                    mPinContentAdapter.notifyDataSetChanged();
                    status=0;
                }
                mPinLetterAdapter.notifyItemChanged(list);
                if(list<=0){
                    PinDeleteImg.setVisibility(View.GONE);
                }
            }
        });
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
                    letter=new DefaultLetterInfo();
                    letter.setWord_name(words.get(i).getWord_name());
                    letter.setWord_meaning(words.get(i).getWord_meaning());
                    letters.add(letter);
                }
                mHandle.post(()->{
                    finishTask();
                });
            }
        }).start();
    }

    private void finishTask(){
        CloudGo.setVisibility(View.GONE);
        WordNumber=words.size();
        initUpdate();
    }

    private void initClick(){
        PinMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicAnim.start();
            }
        });

        PinButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PinButtonStart.startAnimation(mScaleAnim);
                for (DefaultPinyinInfo pin :pins){
                    if(!pin.getWord_title().equals(pin.getWord_select())){
                        mErrorMusic.start();
                        success=1;
                        mPinContentAdapter.notifyDataSetChanged();
                        initStaticBack();
                    }
                }
                mSuccessMusic.start();
                WordNumber--;
                Number++;
                mHandle.sendEmptyMessageDelayed(UPDATE_STATUS,UPDATE_TIME);
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
                }
            }).start();
            startActivity(new Intent(PinYinAnimActivity.this, ReviseFinishActivity.class));
            finish();
        }
        if(Number==0){
            DefaultUp.setVisibility(View.GONE);
        }else{
            DefaultUp.setVisibility(View.VISIBLE);
            DefaultUpTitle.setText(words.get(Number-1).getWord_name());
        }
        pins.clear();
        yins.clear();
        String str="abcdefghijklmnopqrstuvwxyz";
        for (int j=0;j<letter_number;j++){
            yin=new DefaultPinyinInfo();
            yin.setContent_letter(""+str.charAt(mRandom.nextInt(26)));
            yin.setContent_select("");
            yin.setSelect_status(0);
            yins.add(yin);
        }
        integer.clear();
        text=letters.get(Number).getWord_name();
        for (int i=0;i<text.length();i++){
            pin=new DefaultPinyinInfo();
            pin.setWord_title(text.substring(i,i+1));
            pin.setWord_select("");
            pin.setWord_id(0);
            yins.get(RandomNumber()).setContent_letter(pin.getWord_title());
            pins.add(pin);
        }
        mPinLetterAdapter.notifyDataSetChanged();
        mPinContentAdapter.notifyDataSetChanged();
        DefaultNumber.setText(String.valueOf(WordNumber));
        PinTitle.setText(letters.get(Number).getWord_name());
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


    private void initStaticBack(){
        Details=1;
        mHandle.sendEmptyMessageDelayed(DETAIL_STATUS,UPDATE_TIME);
    }

    @Override
    protected void initToolBar() {
        PinOut.setOnClickListener(new View.OnClickListener() {
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
