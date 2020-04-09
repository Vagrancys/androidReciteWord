package com.tramp.word.module.revise;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.FinishListAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.anim.AudioAnimActivity;
import com.tramp.word.module.anim.FillAnimActivity;
import com.tramp.word.module.anim.PinYinAnimActivity;
import com.tramp.word.module.anim.SelectAnimActivity;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/08
 * version:1.0
 */
public class ReviseFinishActivity extends RxBaseActivity {
    @BindView(R.id.finish_number)
    TextView FinishNumber;
    @BindView(R.id.finish_select)
    TextView FinishSelect;
    @BindView(R.id.finish_book_text)
    TextView FinishBookText;
    @BindView(R.id.finish_time_text)
    TextView FinishTimeText;
    @BindView(R.id.finish_recycler)
    RecyclerView FinishRecycler;
    @BindView(R.id.finish_start)
    TextView FinishStart;
    @BindView(R.id.finish_back)
    RelativeLayout FinishBack;
    @BindView(R.id.class_linear)
    LinearLayout ClassLinear;
    @BindView(R.id.class_linear_1)
    RelativeLayout ClassLinear1;
    @BindView(R.id.class_linear_2)
    RelativeLayout ClassLinear2;
    @BindView(R.id.class_linear_3)
    RelativeLayout ClassLinear3;
    @BindView(R.id.class_linear_4)
    RelativeLayout ClassLinear4;
    @BindView(R.id.class_out)
    TextView ClassOut;
    private Animation mTopAnim;
    private Animation mBottomAnim;
    private Animation mScaleAnim;
    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private FinishListAdapter mAdapter;
    private PopupWindow mPop;
    private Calendar mCalender=Calendar.getInstance();
    private Handler mHandler=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_revise_finish;
    }

    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initDb();
        initData();
        initClick();
    }
    private void initData(){
        mAdapter=new FinishListAdapter(FinishRecycler,words,this,6);
        FinishRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        FinishRecycler.setAdapter(mAdapter);
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
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_time(cursor.getInt(cursor.getColumnIndex("word_time")));
                    words.add(word);
                }
                cursor.close();
                mHandler.post(()->{
                    initUpdate();
                });
            }
        }).start();
    }

    private void initUpdate(){
        FinishNumber.setText(String.valueOf(words.size()));
        FinishBookText.setText("待复习"+words.size());
        mAdapter.notifyDataSetChanged();
    }

    private void initClick(){
        FinishSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishSelect.startAnimation(mScaleAnim);
                FinishBack.setVisibility(View.VISIBLE);
                ClassLinear.startAnimation(mTopAnim);
                ClassLinear.setVisibility(View.VISIBLE);
            }
        });
        ClassOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishBack.setVisibility(View.GONE);
                ClassLinear.startAnimation(mBottomAnim);
                ClassLinear.setVisibility(View.GONE);
            }
        });
        FinishStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ClassLinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseFinishActivity.this, SelectAnimActivity.class));
                Utils.StarActivityInAnim(ReviseFinishActivity.this);
                finish();
            }
        });

        ClassLinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseFinishActivity.this, PinYinAnimActivity.class));
                Utils.StarActivityInAnim(ReviseFinishActivity.this);
                finish();
            }
        });

        ClassLinear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseFinishActivity.this, AudioAnimActivity.class));
                Utils.StarActivityInAnim(ReviseFinishActivity.this);
                finish();
            }
        });

        ClassLinear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseFinishActivity.this, FillAnimActivity.class));
                Utils.StarActivityInAnim(ReviseFinishActivity.this);
                finish();
            }
        });

        FinishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinishBack.setVisibility(View.GONE);
                ClassLinear.startAnimation(mBottomAnim);
                ClassLinear.setVisibility(View.GONE);
            }
        });

        mPop=new PopupWindow();
        View mView=LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_recite_list_lobby,null);
        mPop.setContentView(mView);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_recite_item_button));
        mPop.setFocusable(true);
        mPop.setAnimationStyle(R.style.popup_recite_lobby_style_anim);
        LinearLayout mPopupLinear=(LinearLayout) mView.findViewById(R.id.popup_recite_linear);
        mPopupLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
            }
        });

        FinishTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.showAsDropDown(FinishTimeText,0,20);
            }
        });
    }

    private void initAnim(){
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_enter_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mBottomAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_bottom_exit_anim);
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
