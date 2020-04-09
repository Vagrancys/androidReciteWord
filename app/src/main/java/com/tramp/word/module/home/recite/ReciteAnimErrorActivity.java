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
import com.tramp.word.adapter.section.AnimErrorViewSection;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteAnimErrorActivity extends RxBaseActivity {
    @BindView(R.id.error_out)
    ImageView ErrorOut;
    @BindView(R.id.error_text)
    TextView ErrorText;
    @BindView(R.id.error_recycler)
    RecyclerView ErrorRecycler;
    private SectionedRecyclerViewAdapter mSection;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private UserSqlHelper mUser;
    private int word_gate;
    private Handler mHandler=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_anim_error;
    }

    @Override
    protected void initToolBar() {
        ErrorOut.setOnClickListener(new View.OnClickListener() {
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

    private void finishTask(){
        mSection.addSection(new AnimErrorViewSection(this,getBaseContext(),words,word_gate,mHandler));
        mSection.notifyDataSetChanged();
    }

    private void initData(){
        mSection=new SectionedRecyclerViewAdapter();
        ErrorRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        ErrorRecycler.setAdapter(mSection);

        ErrorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReciteDetailsActivity.launch(ReciteAnimErrorActivity.this,word_gate);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int word_gate){
        Intent intent=new Intent(activity,ReciteAnimErrorActivity.class);
        intent.putExtra(ConstantUtils.WORD_GATE,word_gate);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
