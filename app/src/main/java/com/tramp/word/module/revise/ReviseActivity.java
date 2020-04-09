package com.tramp.word.module.revise;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import com.tramp.word.module.anim.AudioAnimActivity;
import com.tramp.word.module.anim.FillAnimActivity;
import com.tramp.word.module.anim.PinYinAnimActivity;
import com.tramp.word.module.anim.SelectAnimActivity;
import com.tramp.word.module.home.recite.ReciteWordActivity;
import com.tramp.word.module.word.WordListActivity;
import com.tramp.word.utils.Utils;

import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/10.
 */

public class ReviseActivity extends RxBaseActivity {
    private static final String LOG="reviseActivity";
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_list)
    TextView DefaultList;

    @BindView(R.id.revise_help)
    ImageView ReviseHelp;

    @BindView(R.id.revise_title)
    TextView ReviseTitle;

    @BindView(R.id.no_word)
    LinearLayout NoWord;
    @BindView(R.id.no_return)
    TextView NoReturn;
    @BindView(R.id.now_word)
    LinearLayout NowWord;
    @BindView(R.id.now_count)
    TextView NowCount;
    @BindView(R.id.now_number)
    TextView NowNumber;
    @BindView(R.id.now_return)
    TextView NowReturn;
    @BindView(R.id.done_word)
    LinearLayout DoneWord;
    @BindView(R.id.done_count)
    TextView DoneCount;
    @BindView(R.id.done_select)
    TextView DoneSelect;
    @BindView(R.id.done_recite)
    TextView DoneRecite;
    @BindView(R.id.relative_layout)
    RelativeLayout RelativeLayout;


    @BindView(R.id.now_class_layout)
    LinearLayout NowClassLayout;
    @BindView(R.id.now_class_title)
    TextView NowClassTitle;
    @BindView(R.id.now_class_img)
    ImageView NowClassImg;
    @BindView(R.id.class_layout)
    LinearLayout ClassLayout;
    @BindView(R.id.class_one)
    RelativeLayout ClassOne;
    @BindView(R.id.class_one_img)
    ImageView ClassOneImg;
    @BindView(R.id.class_two)
    RelativeLayout ClassTwo;
    @BindView(R.id.class_two_img)
    ImageView ClassTwoImg;
    @BindView(R.id.class_three)
    RelativeLayout ClassThree;
    @BindView(R.id.class_three_img)
    ImageView ClassThreeImg;
    @BindView(R.id.class_four)
    RelativeLayout ClassFour;
    @BindView(R.id.class_four_img)
    ImageView ClassFourImg;
    @BindView(R.id.class_out)
    TextView ClassOut;

    @BindView(R.id.now_count_layout)
    LinearLayout NowCountLayout;
    @BindView(R.id.now_count_title)
    TextView NowCountTitle;
    @BindView(R.id.now_count_img)
    ImageView NowCountImg;
    @BindView(R.id.count_layout)
    LinearLayout CountLayout;
    @BindView(R.id.count_one)
    RelativeLayout CountOne;
    @BindView(R.id.count_one_img)
    ImageView CountOneImg;
    @BindView(R.id.count_two)
    RelativeLayout CountTwo;
    @BindView(R.id.count_two_img)
    ImageView CountTwoImg;
    @BindView(R.id.count_three)
    RelativeLayout CountThree;
    @BindView(R.id.count_three_img)
    ImageView CountThreeImg;
    @BindView(R.id.count_four)
    RelativeLayout CountFour;
    @BindView(R.id.count_four_img)
    ImageView CountFourImg;
    @BindView(R.id.count_out)
    TextView CountOut;

    @BindView(R.id.now_select_layout)
    LinearLayout NowSelectLayout;
    @BindView(R.id.now_select_count)
    TextView NowSelectCount;
    @BindView(R.id.select_layout)
    LinearLayout SelectLayout;
    @BindView(R.id.select_one)
    RelativeLayout SelectOne;
    @BindView(R.id.select_two)
    RelativeLayout SelectTwo;
    @BindView(R.id.select_three)
    RelativeLayout SelectThree;
    @BindView(R.id.select_four)
    RelativeLayout SelectFour;
    @BindView(R.id.select_out)
    TextView SelectOut;


    private Animation mBottomEnterAnim;
    private Animation mRotateAnim;
    private Animation mScaleAnim;

    public int Status;
    private int Number;
    private int Class=1;
    private Calendar calendar=Calendar.getInstance();
    private int Word_count;
    private int Study_no_count;
    private int Study_count;
    private int Word_id;
    private UserSqlHelper mUser;
    private String[] mText={
            "看词选释义","拼写题","音频题","选词填空"
    };
    private String[] mNumber={
            "10词","30词","50词","全部"
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_revise;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        mUser=new UserSqlHelper(getBaseContext());
        initData();
        initCount();
        initClass();
        initSelect();
        RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassLayout.setVisibility(View.GONE);
                SelectLayout.setVisibility(View.GONE);
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
            }
        });

        NowReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowReturn.startAnimation(mScaleAnim);
                initIntent();
            }
        });

        DoneSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.setVisibility(View.VISIBLE);
                SelectLayout.startAnimation(mBottomEnterAnim);
                SelectLayout.setVisibility(View.VISIBLE);
            }
        });

        NoReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this, ReciteWordActivity.class));
                finish();
            }
        });

        DoneRecite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this, ReciteWordActivity.class));
                finish();
            }
        });

        ReviseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initData(){
        Word_id=mUser.WordId();
        Word_count=mUser.WordAllCount(Word_id,calendar.getTime().getTime());
        Study_count=mUser.WordStudyCount(Word_id,calendar.getTime().getTime(),1);
        Study_no_count=mUser.WordStudyCount(Word_id,calendar.getTime().getTime(),0);
        Log.e(LOG,"Time="+calendar.getTime().getTime()+"word_count="+Word_count
                +"Study_count="+Study_count
                +"Study_no_count="+Study_no_count);
        if(Word_count>0){
            if(Study_no_count==Word_count){
                NowWord.setVisibility(View.VISIBLE);
                NoWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.GONE);
                NowCount.setText(Word_count);
            }else if(Study_no_count<Word_count){
                NowWord.setVisibility(View.VISIBLE);
                NoWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.GONE);
                NowCount.setText(String.valueOf(Study_no_count));
                NowNumber.setText("共"+Word_count+"词");
                NowNumber.setVisibility(View.VISIBLE);
                NowSelectLayout.setVisibility(View.VISIBLE);
                NowSelectCount.setText(String.valueOf(Study_count));
                NowReturn.setText(getResources().getString(R.string.revise_start_text1));
            }else if(Word_count==Study_count){
                NowWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.VISIBLE);
                NoWord.setVisibility(View.GONE);
                DoneCount.setText(Word_count+"/"+Word_count);
            }
        }else{
            NowWord.setVisibility(View.GONE);
            DoneWord.setVisibility(View.GONE);
            NoWord.setVisibility(View.VISIBLE);
        }
        Cursor cursor2=mUser.BookQuery(Word_id);
        if(cursor2!=null&&cursor2.getCount()>0){
            ReviseTitle.setText(cursor2.getString(cursor2.getColumnIndex("book_name")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.ShowToast(getBaseContext(),"request="+requestCode+"result="+resultCode);
        if(resultCode==10){
            if(data.getExtras() !=null){
                Bundle b=data.getExtras();
                Status=b.getInt("data");
            }else{
                Status=0;
            }
            if(Status==Word_count){
                NowWord.setVisibility(View.GONE);
                NoWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.VISIBLE);
                NowSelectLayout.setVisibility(View.VISIBLE);
            }else if(Status==0){
                NowWord.setVisibility(View.VISIBLE);
                NoWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.GONE);
                NowSelectLayout.setVisibility(View.VISIBLE);
                NowCount.setText(String.valueOf(Status));
            }else{
                NowWord.setVisibility(View.VISIBLE);
                NoWord.setVisibility(View.GONE);
                DoneWord.setVisibility(View.GONE);
                NowNumber.setText(String.valueOf(Number));
                NowCount.setText(String.valueOf(Status));
                NowSelectLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void initAnim(){
        mBottomEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mRotateAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_rotate_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
    }

    public void initCount(){
        NowCountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowCountImg.startAnimation(mRotateAnim);
                NowCountImg.setRotation(180);
                RelativeLayout.setVisibility(View.VISIBLE);
                CountLayout.setVisibility(View.VISIBLE);
                CountLayout.startAnimation(mBottomEnterAnim);
            }
        });

        CountOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountOneImg.setVisibility(View.VISIBLE);
                CountTwoImg.setVisibility(View.GONE);
                CountThreeImg.setVisibility(View.GONE);
                CountFourImg.setVisibility(View.GONE);
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Number=1;
                NowCountTitle.setText(mNumber[0]);
            }
        });

        CountTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountOneImg.setVisibility(View.GONE);
                CountTwoImg.setVisibility(View.VISIBLE);
                CountThreeImg.setVisibility(View.GONE);
                CountFourImg.setVisibility(View.GONE);
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Number=2;
                NowCountTitle.setText(mNumber[1]);
            }
        });

        CountThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountOneImg.setVisibility(View.GONE);
                CountTwoImg.setVisibility(View.GONE);
                CountThreeImg.setVisibility(View.VISIBLE);
                CountFourImg.setVisibility(View.GONE);
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Number=3;
                NowCountTitle.setText(mNumber[2]);
            }
        });

        CountFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountOneImg.setVisibility(View.GONE);
                CountTwoImg.setVisibility(View.GONE);
                CountThreeImg.setVisibility(View.GONE);
                CountFourImg.setVisibility(View.VISIBLE);
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Number=4;
                NowCountTitle.setText(mNumber[3]);
            }
        });

        CountOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                NowCountImg.startAnimation(mRotateAnim);
                NowCountImg.setRotation(-180);
            }
        });
    }

    public void initClass(){
        NowClassLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NowClassImg.startAnimation(mRotateAnim);
                NowClassImg.setRotation(180);
                RelativeLayout.setVisibility(View.VISIBLE);
                ClassLayout.setVisibility(View.VISIBLE);
                ClassLayout.startAnimation(mBottomEnterAnim);
            }
        });

        ClassOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassOneImg.setVisibility(View.VISIBLE);
                ClassTwoImg.setVisibility(View.GONE);
                ClassThreeImg.setVisibility(View.GONE);
                ClassFourImg.setVisibility(View.GONE);
                ClassLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=1;
                NowClassTitle.setText(mText[0]);
            }
        });

        ClassTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassOneImg.setVisibility(View.GONE);
                ClassTwoImg.setVisibility(View.VISIBLE);
                ClassThreeImg.setVisibility(View.GONE);
                ClassFourImg.setVisibility(View.GONE);
                ClassLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=2;
                NowClassTitle.setText(mText[1]);
            }
        });

        ClassThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassOneImg.setVisibility(View.GONE);
                ClassTwoImg.setVisibility(View.GONE);
                ClassThreeImg.setVisibility(View.VISIBLE);
                ClassFourImg.setVisibility(View.GONE);
                ClassLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=3;
                NowClassTitle.setText(mText[2]);
            }
        });

        ClassFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassOneImg.setVisibility(View.GONE);
                ClassTwoImg.setVisibility(View.GONE);
                ClassThreeImg.setVisibility(View.GONE);
                ClassFourImg.setVisibility(View.VISIBLE);
                ClassLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=4;
                NowClassTitle.setText(mText[3]);
            }
        });

        ClassOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                NowClassImg.startAnimation(mRotateAnim);
                NowClassImg.setRotation(-180);
            }
        });
    }

    public void initSelect(){
        NowSelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout.setVisibility(View.VISIBLE);
                SelectLayout.setVisibility(View.VISIBLE);
                SelectLayout.startAnimation(mBottomEnterAnim);
            }
        });

        SelectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=1;
                initIntent();
            }
        });

        SelectTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=2;
                initIntent();
            }
        });

        SelectThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=3;
                initIntent();
            }
        });

        SelectFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
                Class=4;
                initIntent();
            }
        });

        SelectOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLayout.setVisibility(View.GONE);
                RelativeLayout.setVisibility(View.GONE);
            }
        });
    }

    public void initIntent(){
        Intent intent=new Intent();
        switch (Class){
            case 1:
                intent.setClass(ReviseActivity.this,SelectAnimActivity.class);
                break;
            case 2:
                intent.setClass(ReviseActivity.this,PinYinAnimActivity.class);
                break;
            case 3:
                intent.setClass(ReviseActivity.this,AudioAnimActivity.class);
                break;
            case 4:
                intent.setClass(ReviseActivity.this,FillAnimActivity.class);
                break;
            default:
                break;
        }
        startActivityForResult(intent,5);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this, WordListActivity.class));
                Utils.StarActivityInAnim(ReviseActivity.this);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
