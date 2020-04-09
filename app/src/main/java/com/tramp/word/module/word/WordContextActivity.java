package com.tramp.word.module.word;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.PullWordRefreshLayout;
import com.tramp.word.widget.WordContentScrollView;
import com.tramp.word.widget.WordErrorDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/25.
 */

public class WordContextActivity extends RxBaseActivity {
    private final String LOG="WordContextActivity";
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.pull_word_refresh)
    PullWordRefreshLayout mPullRefresh;
    @BindView(R.id.view_scroll_view)
    WordContentScrollView mScrollView;
    @BindView(R.id.content_linear)
    LinearLayout ContentLinear;
    @BindView(R.id.content_linear_two)
    LinearLayout ContentLinearTwo;

    @BindView(R.id.content_text_name)
    TextView ContentTextName;
    @BindView(R.id.content_music_linear)
    LinearLayout ContentMusicLinear;
    @BindView(R.id.content_text_music)
    TextView ContentTextMusic;
    @BindView(R.id.content_text_music_url)
    ImageView ContentTextMusicUrl;
    @BindView(R.id.content_text_meaning)
    TextView ContentTextMeaning;
    @BindView(R.id.content_relative_one)
    RelativeLayout ContentRelativeOne;
    @BindView(R.id.content_text_root)
    TextView ContentTextRoot;
    @BindView(R.id.content_relative_two)
    RelativeLayout ContentRelativeTwo;
    @BindView(R.id.content_text_sentence)
    TextView ContentTextSentence;
    @BindView(R.id.content_text_sentence_url)
    ImageView ContentTextSentenceUrl;
    @BindView(R.id.content_text_show)
    TextView ContentTextShow;
    @BindView(R.id.content_text_sentence_meaning)
    TextView ContentTextSentenceMeaning;
    @BindView(R.id.content_text_error)
    TextView ContentTextError;

    @BindView(R.id.content_text_add)
    ImageView ContentTextAdd;
    @BindView(R.id.content_text_lessen)
    ImageView ContentTextLessen;
    @BindView(R.id.content_text_done)
    TextView ContentTextDone;
    @BindView(R.id.content_text_down)
    ImageView ContentTextDown;
    @BindView(R.id.content_text_up)
    ImageView ContentTextUp;
    private int list_number;
    //1=已认识关卡 2=已认识字母 3=需复习 4=未学习关卡 5=未学习字母
    private int word_status;
    private int word_gate;
    private DefaultWordInfo word;
    private List<DefaultWordInfo> words=new ArrayList<>();
    private UserSqlHelper mUser;
    private AnimationDrawable mMusicAnim;
    private AnimationDrawable mMusicBlueAnim;
    private WordErrorDialog mError;
    private Handler mHandler =new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_word_context;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            list_number=intent.getIntExtra(ConstantUtils.LIST_NUMBER,0);
            word_status=intent.getIntExtra(ConstantUtils.WORD_STATUS,1);
            word_gate=intent.getIntExtra(ConstantUtils.WORD_GATE,1);
        }
        Log.e(LOG,"list_number="+list_number);
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initDb();
        initClick();
    }

    private void initAnim(){
        ContentTextMusicUrl.setImageResource(R.drawable.default_music_anim);
        mMusicAnim=(AnimationDrawable) ContentTextMusicUrl.getDrawable();
        ContentTextSentenceUrl.setImageResource(R.drawable.default_music_blue_bg);
        mMusicBlueAnim=(AnimationDrawable) ContentTextSentenceUrl.getDrawable();
    }

    private void initClick(){
        mPullRefresh.setRefreshListener(new PullWordRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                list_number--;
                loadData();
                if(list_number==1){
                    ContentTextUp.setClickable(true);
                    ContentTextUp.setImageResource(R.drawable.icon_pageup_disable);
                }
                if(list_number==words.size()-1){
                    ContentTextDone.setVisibility(View.GONE);
                    ContentTextDown.setVisibility(View.VISIBLE);
                }
                mHandler.postDelayed(()->{
                    ContentLinear.setVisibility(View.VISIBLE);
                    ContentLinear.setScrollY(0);
                    loadData();
                },1000);
            }

            @Override
            public void loadMoreFinished() {
                list_number++;
                loadData();
                if(list_number==words.size()){
                    ContentTextDone.setVisibility(View.VISIBLE);
                    ContentTextDown.setVisibility(View.GONE);
                }
                if(list_number==2){
                    ContentTextUp.setClickable(false);
                    ContentTextUp.setImageResource(R.drawable.icon_pageup);
                }
                mHandler.postDelayed(()->{
                    ContentLinearTwo.setVisibility(View.VISIBLE);
                    ContentLinearTwo.setScrollY(0);
                    loadData();
                },1000);
            }

            @Override
            public void HeaderLayout(int y) {
                if(y>-50){
                    ContentLinear.setScrollY(-y);
                }else{
                    ContentLinear.setVisibility(View.GONE);
                }
            }

            @Override
            public void FooterLayout(int y) {
                if(y<100){
                    ContentLinearTwo.setScrollY(-y);
                }else{
                    ContentLinearTwo.setVisibility(View.GONE);
                }
            }
        });

        ContentTextShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentTextShow.setVisibility(View.GONE);
                ContentTextSentenceMeaning.setVisibility(View.VISIBLE);
            }
        });

        ContentTextMusicUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicAnim.start();
            }
        });

        ContentTextSentenceUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicBlueAnim.start();
            }
        });

        mError=new WordErrorDialog(this);
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
        ContentTextError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mError.show();
            }
        });

        ContentTextAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.UpdateWordLife(words.get(list_number).getWord_id(),0);
                ContentTextAdd.setVisibility(View.GONE);
                ContentTextLessen.setVisibility(View.VISIBLE);
            }
        });
        ContentTextLessen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser.UpdateWordLife(words.get(list_number).getWord_id(),1);
                ContentTextLessen.setVisibility(View.GONE);
                ContentTextAdd.setVisibility(View.VISIBLE);
            }
        });

        ContentTextDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ContentTextUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_number--;

                if(list_number<=-1){
                    ContentTextUp.setClickable(false);
                    ContentTextUp.setImageResource(R.drawable.icon_pageup_disable);
                    list_number=0;
                    return;
                }
                loadData();
                if(list_number<words.size()){
                    ContentTextDone.setVisibility(View.GONE);
                    ContentTextDown.setVisibility(View.VISIBLE);
                }
            }
        });

        ContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_number++;
                if(list_number>words.size()-1){
                    ContentTextDone.setVisibility(View.VISIBLE);
                    ContentTextDown.setVisibility(View.GONE);
                    list_number=words.size()-1;
                    return;
                }
                loadData();

                if(list_number>0){
                    ContentTextUp.setClickable(true);
                    ContentTextUp.setImageResource(R.drawable.icon_pageup);
                }
            }
        });
    }

    private void loadData(){
        ContentTextName.setText(words.get(list_number).getWord_name());
        ContentTextMusic.setText(words.get(list_number).getWord_music());
        ContentTextMeaning.setText(words.get(list_number).getWord_meaning());
        if(words.get(list_number).getWord_sentence()!=null
                &&words.get(list_number).getWord_sentence().length()>0){
            ContentRelativeTwo.setVisibility(View.VISIBLE);
            ContentTextSentence.setText(words.get(list_number).getWord_sentence());
            ContentTextSentenceMeaning.setText(words.get(list_number).getWord_sentence_meaning());
            ContentTextShow.setVisibility(View.VISIBLE);
            ContentTextSentenceMeaning.setVisibility(View.GONE);
        }else{
            ContentRelativeTwo.setVisibility(View.GONE);
        }
        if(words.get(list_number).getWord_root()!=null
                &&words.get(list_number).getWord_root().length()>0){
            ContentRelativeOne.setVisibility(View.VISIBLE);
            ContentTextRoot.setText(words.get(list_number).getWord_root());
        }else{
            ContentRelativeOne.setVisibility(View.GONE);
        }
        if(words.get(list_number).getWord_life()==1){
            ContentTextAdd.setVisibility(View.VISIBLE);
            ContentTextLessen.setVisibility(View.GONE);
        }else{
            ContentTextAdd.setVisibility(View.GONE);
            ContentTextLessen.setVisibility(View.VISIBLE);
        }
    }

    private void initDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=mUser.WordContentAll(mUser.WordId(),word_status,word_gate);
                while (cursor.moveToNext()){
                    word=new DefaultWordInfo();
                    word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                    word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                    word.setWord_music(cursor.getString(cursor.getColumnIndex("word_music")));
                    word.setWord_music_url(cursor.getString(cursor.getColumnIndex("word_music_url")));
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_sentence(cursor.getString(cursor.getColumnIndex("word_sentence")));
                    word.setWord_sentence_meaning(cursor.getString(cursor.getColumnIndex("word_sentence_meaning")));
                    word.setWord_sentence_url(cursor.getString(cursor.getColumnIndex("word_sentence_url")));
                    word.setWord_root(cursor.getString(cursor.getColumnIndex("word_root")));
                    word.setWord_life(cursor.getInt(cursor.getColumnIndex("word_life")));
                    words.add(word);
                }
                cursor.close();
                Log.e(LOG,"words="+words.size());
                finishTask();
            }
        }).start();
    }
    public void finishTask(){
        mPullRefresh.setWords(words);
        mHandler.post(()->{
            if(list_number==0){
                ContentTextUp.setClickable(false);
                ContentTextUp.setImageResource(R.drawable.icon_pageup_disable);
            }else if(list_number==words.size()-1){
                ContentTextDone.setVisibility(View.VISIBLE);
                ContentTextDown.setVisibility(View.GONE);
            }
            loadData();
        });
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

    public static void launch(Activity activity,int list_number,int word_status,int word_gate){
        Intent intent=new Intent(activity,WordContextActivity.class);
        intent.putExtra(ConstantUtils.LIST_NUMBER,list_number);
        intent.putExtra(ConstantUtils.WORD_STATUS,word_status);
        intent.putExtra(ConstantUtils.WORD_GATE,word_gate);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
