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
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteAnimErrorActivity extends RxBaseActivity {
    @BindView(R.id.recite_anim_error_out)
    ImageView mReciteAnimOut;
    @BindView(R.id.recite_anim_error)
    TextView mReciteAnimError;
    @BindView(R.id.anim_error_recycler)
    RecyclerView mAnimErrorRecycler;
    private AnimErrorViewSection mAnimErrorView;
    private SectionedRecyclerViewAdapter mSection;
    @Override
    public int getLayoutId() {
        return R.layout.activity_anim_error;
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
        mSection.addSection(new AnimErrorViewSection(getBaseContext()));
        mAnimErrorRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mAnimErrorRecycler.setAdapter(mSection);
        mReciteAnimError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),ReciteWordDetailsActivity.class));
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
