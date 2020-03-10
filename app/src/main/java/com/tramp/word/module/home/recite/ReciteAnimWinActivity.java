package com.tramp.word.module.home.recite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.AnimErrorViewSection;
import com.tramp.word.adapter.section.AnimWinViewSection;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/7.
 */

public class ReciteAnimWinActivity extends RxBaseActivity {
    @BindView(R.id.recite_anim_win_out)
    ImageView mReciteAnimOut;
    @BindView(R.id.recite_anim_win)
    TextView mReciteAnimWin;
    @BindView(R.id.anim_win_recycler)
    RecyclerView mAnimWinRecycler;
    @BindView(R.id.recite_anim_win_two)
    TextView mReciteAnimWinTwo;
    private AnimWinViewSection mAnimWinView;
    private int win=2;
    private SectionedRecyclerViewAdapter mSection;
    @Override
    public int getLayoutId() {
        return R.layout.activity_anim_win;
    }

    @Override
    protected void initToolBar() {
        mReciteAnimOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mSection=new SectionedRecyclerViewAdapter();
        mSection.addSection(new AnimWinViewSection(getBaseContext(),win));
        mAnimWinRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mAnimWinRecycler.setAdapter(mSection);
        mReciteAnimWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getBaseContext(),ReciteWordDetailsActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mReciteAnimWinTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteAnimWinActivity.this,ReciteAnimActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
