package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.AnimWinViewSection;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.pk.PkDetailsActivity;
import com.tramp.word.module.revise.ReviseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/7.
 */

public class ReciteAnimWinActivity extends RxBaseActivity {
    @BindView(R.id.win_out)
    ImageView WinOut;

    @BindView(R.id.win_recycler)
    RecyclerView WinRecycler;
    @BindView(R.id.win_revise)
    TextView WinRevise;
    @BindView(R.id.win_pk)
    TextView WinPk;
    @BindView(R.id.win_recite)
    TextView WinRecite;
    private SectionedRecyclerViewAdapter mSection;
    private int word_gate;
    private int word_star;
    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private Handler mHandler=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_anim_win;
    }

    @Override
    protected void initToolBar() {
        WinOut.setOnClickListener(new View.OnClickListener() {
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
            word_gate=intent.getIntExtra(ConstantUtils.WORD_GATE,1);
            word_star=intent.getIntExtra(ConstantUtils.WORD_STAR,0);
        }
        mUser=new UserSqlHelper(getBaseContext());
        initDb();
        initData();
    }

    private void initDb(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=mUser.GateAll(mUser.WordId(),word_gate);
                while (cursor.moveToNext()){
                    word=new DefaultWordInfo();
                    word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_error(cursor.getInt(cursor.getColumnIndex("word_error")));
                    word.setWord_error_text(cursor.getString(cursor.getColumnIndex("word_error_text")));
                    words.add(word);
                }
                cursor.close();
                finishTask();
            }
        }).start();
    }

    private void initData(){
        mSection=new SectionedRecyclerViewAdapter();
        WinRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        WinRecycler.setAdapter(mSection);

        WinPk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PkDetailsActivity.class));
                Utils.StarActivityInAnim(ReciteAnimWinActivity.this);
            }
        });

        WinRecite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReciteDetailsActivity.launch(ReciteAnimWinActivity.this,word_gate+1);
            }
        });
        WinRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteAnimWinActivity.this,ReviseActivity.class));
                Utils.StarActivityInAnim(ReciteAnimWinActivity.this);
            }
        });
    }

    public void finishTask(){
        mSection.addSection(new AnimWinViewSection(this,getBaseContext(),words,word_star,word_gate,mHandler));
        mSection.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int word_gate,int word_star){
        Intent intent=new Intent(activity,ReciteAnimWinActivity.class);
        intent.putExtra(ConstantUtils.WORD_GATE,word_gate);
        intent.putExtra(ConstantUtils.WORD_STAR,word_star);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
