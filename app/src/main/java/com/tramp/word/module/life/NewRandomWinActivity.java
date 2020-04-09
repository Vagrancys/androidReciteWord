package com.tramp.word.module.life;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.NewRandomWinAdapter;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/15
 * version:1.0
 */

public class NewRandomWinActivity extends RxBaseActivity{
    @BindView(R.id.new_win_out)
    ImageView NewWinOut;
    @BindView(R.id.new_win_start_img)
    ImageView NewWinStartImg;
    @BindView(R.id.new_win_recycler)
    RecyclerView NewWinRecycler;
    private NewRandomWinAdapter mNewRandomWinAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_random_win;
    }

    @Override
    public void initView(Bundle save) {
        mNewRandomWinAdapter=new NewRandomWinAdapter(NewWinRecycler);
        NewWinRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        NewWinRecycler.setAdapter(mNewRandomWinAdapter);
    }

    @Override
    protected void initToolBar() {
        NewWinOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        NewWinStartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
