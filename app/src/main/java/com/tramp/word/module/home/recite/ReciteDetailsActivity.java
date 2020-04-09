package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.WordDetailsViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultWordsInfo;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.word.WordContextActivity;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.DefaultKnowDialog;
import com.tramp.word.widget.PkBookDialog;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/23.
 */

public class ReciteDetailsActivity extends RxBaseActivity {
    private static final String LOG="ReciteDetailsActivity";
    @BindView(R.id.details_out)
    ImageView DetailsOut;

    @BindView(R.id.details_title)
    TextView DetailsTitle;
    @BindView(R.id.details_num)
    TextView DetailsNum;

    @BindView(R.id.details_select)
    TextView DetailsSelect;
    @BindView(R.id.details_unselect)
    TextView DetailsUnSelect;
    @BindView(R.id.details_recycler)
    RecyclerView DetailsRecycler;
    @BindView(R.id.details_start)
    ImageView DetailsStart;

    @BindView(R.id.details_back)
    RelativeLayout DetailsBack;
    @BindView(R.id.details_pop)
    RelativeLayout DetailsPop;
    @BindView(R.id.details_select_pop)
    LinearLayout DetailsSelectPop;
    @BindView(R.id.details_select_done)
    TextView DetailsSelectDone;
    private Bitmap mDetailsBack;
    private WordDetailsViewAdapter mWordDetailsAdapter;
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private Animation mScaleAnim;
    private int selectStatus=0;
    private Animation mStartAnim;
    private int gate=1;
    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private DefaultWordsInfo list=new DefaultWordsInfo();
    private DefaultWordsInfo.Word detail;
    private ArrayList<DefaultWordsInfo.Word> details=new ArrayList<>();
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private DefaultKnowDialog mDialog;
    private Handler mHandle=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_details;
    }

    @Override
    protected void initToolBar() {
        DetailsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            gate=intent.getIntExtra(ConstantUtils.RECITE_WORD_SELECT,1);
        }
        mUser=new UserSqlHelper(getBaseContext());
        initData();
        initDb();
        initAnim();
        initClick();
    }

    private void initData(){
        mDialog=new DefaultKnowDialog(getBaseContext());
        mDialog.setDefaultImg(R.drawable.u_dialog_top_choose_word);
        mDialog.setDefaultTitle(R.string.dialog_level_pass_select_word);
        mDialog.setDefaultMessage(R.string.dialog_level_pass_select_word_content);
        mDialog.setOkOnClickListener(getResources().getString(R.string.dialog_level_pass_select_word_button), new PkBookDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                PreferencesUtils.putBoolean(ConstantUtils.RECITE_WORD_KNOW,true);
                mDialog.dismiss();
            }
        });

        mStartAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_street@2x.png");
        mDetailsBack= BitmapFactory.decodeStream(back);
        DetailsBack.setBackground(new BitmapDrawable(mDetailsBack));

        Log.e(LOG,"words="+words.size());
        mWordDetailsAdapter=new WordDetailsViewAdapter(DetailsRecycler,words);

        DetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        DetailsRecycler.setAdapter(mWordDetailsAdapter);

        if(PreferencesUtils.getBoolean(ConstantUtils.RECITE_WORD_KNOW,false)) {
            mDialog.show();
        }
    }

    private void initDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=mUser.GateAll(mUser.WordId(),gate);
                words.clear();
                while (cursor.moveToNext()){
                    word=new DefaultWordInfo();
                    word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                    word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                    word.setWord_music(cursor.getString(cursor.getColumnIndex("word_music")));
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_life(cursor.getInt(cursor.getColumnIndex("word_life")));
                    word.setWord_root(cursor.getString(cursor.getColumnIndex("word_root")));
                    word.setWord_sentence(cursor.getString(cursor.getColumnIndex("word_sentence")));
                    word.setWord_sentence_meaning(cursor.getString(cursor.getColumnIndex("word_sentence_meaning")));
                    word.setWord_sentence_url(cursor.getString(cursor.getColumnIndex("word_sentence_url")));
                    word.setWord_music_url(cursor.getString(cursor.getColumnIndex("word_music_url")));
                    word.setWord_start(false);
                    word.setWord_error(cursor.getInt(cursor.getColumnIndex("word_error")));
                    words.add(word);
                }
                cursor.close();
                finishTask();
            }
        }).start();
    }

    public void finishTask(){
        DetailsTitle.setText("第"+gate+"关");
        DetailsNum.setText(String.valueOf(words.size()));
        Log.e(LOG,"size="+words.size());
        mWordDetailsAdapter.initCheckMap();
        mWordDetailsAdapter.notifyDataSetChanged();
    }

    private void initAnim(){
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
    }

    private void initClick(){
        mWordDetailsAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(selectStatus==0){
                    WordContextActivity.launch(ReciteDetailsActivity.this,position,7,gate);
                }
            }
        });

        DetailsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectStatus==0){
                    for (DefaultWordInfo word:words){
                        word.setWord_start(false);
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        details.clear();
                        for (DefaultWordInfo word:words){
                            detail=new DefaultWordsInfo.Word();
                            detail.setWord_id(word.getWord_id());
                            detail.setWord_error(word.getWord_error());
                            detail.setWord_gate(word.getWord_gate());
                            detail.setWord_name(word.getWord_name());
                            detail.setWord_sentence(word.getWord_sentence());
                            detail.setWord_sentence_meaning(word.getWord_sentence_meaning());
                            detail.setWord_sentence_url(word.getWord_sentence_url());
                            detail.setWord_music_url(word.getWord_music_url());
                            detail.setWord_music(word.getWord_music());
                            detail.setWord_life(word.getWord_life());
                            detail.setWord_root(word.getWord_root());
                            if(word.isWord_start()){
                                detail.setWord_start(1);
                            }else{
                                detail.setWord_start(0);
                            }
                            detail.setWord_meaning(word.getWord_meaning());
                            details.add(detail);
                        }
                        Log.e(LOG,"details="+details.size());
                        list.setWords(details);
                        Log.e(LOG,"list="+list.getWords().size());
                        mHandle.post(()->{
                            ReciteAnimActivity.launch(ReciteDetailsActivity.this,list,gate);
                        });
                    }
                }).start();
                DetailsStart.startAnimation(mStartAnim);
            }
        });

        DetailsPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsBack.setVisibility(View.GONE);
                DetailsSelectPop.startAnimation(mTopExitAnim);
                DetailsSelectPop.setVisibility(View.GONE);
            }
        });

        DetailsSelectDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putBoolean(ConstantUtils.RECITE_WORD_SELECT,true);
                DetailsSelectDone.startAnimation(mScaleAnim);
                DetailsSelectPop.startAnimation(mTopExitAnim);
                DetailsSelectPop.setVisibility(View.GONE);
                DetailsBack.setVisibility(View.GONE);
            }
        });

        DetailsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsUnSelect.setVisibility(View.VISIBLE);
                DetailsSelect.setVisibility(View.GONE);
                DetailsOut.setVisibility(View.GONE);
                selectStatus=1;
                mWordDetailsAdapter.checkAll();
                if(!PreferencesUtils.getBoolean(ConstantUtils.RECITE_WORD_SELECT,false)){
                    DetailsBack.setVisibility(View.VISIBLE);
                    DetailsSelectPop.setVisibility(View.VISIBLE);
                    DetailsSelectPop.startAnimation(mTopEnterAnim);
                }
            }
        });

        DetailsUnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsSelect.setVisibility(View.VISIBLE);
                DetailsUnSelect.setVisibility(View.GONE);
                selectStatus=0;
                mWordDetailsAdapter.cancelAll();
            }
        });

        mWordDetailsAdapter.setCheckedChangeListener(new WordDetailsViewAdapter.CheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, int number, CompoundButton buttonView, boolean isChecked) {
                words.get(position).setWord_start(isChecked);
                DetailsNum.setText(String.valueOf(number));
                if(number==0){
                    DetailsStart.setImageResource(R.drawable.btn_start_pressed_disable);
                    DetailsStart.setClickable(false);
                }else{
                    DetailsStart.setImageResource(R.drawable.btn_start);
                    DetailsStart.setClickable(true);
                }
            }
        });
    }

    public static void launch(Activity activity,int gate){
        Intent intent=new Intent(activity,ReciteDetailsActivity.class);
        intent.putExtra(ConstantUtils.WORD_GATE,gate);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}










