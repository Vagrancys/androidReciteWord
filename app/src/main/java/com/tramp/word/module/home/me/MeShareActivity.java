package com.tramp.word.module.home.me;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.MeShareAdapter;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/25.
 */

public class MeShareActivity extends RxBaseActivity{
    @BindView(R.id.me_share_out)
    ImageView mMeShareOut;
    @BindView(R.id.me_share_recycler)
    RecyclerView mMeShareRecycler;
    private MeShareAdapter mMeShareAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_me_share;
    }

    @Override
    public void initView(Bundle save) {
        mMeShareAdapter=new MeShareAdapter(mMeShareRecycler);
        mMeShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),7));
        mMeShareRecycler.setAdapter(mMeShareAdapter);
    }

    @Override
    protected void initToolBar() {
        mMeShareOut.setOnClickListener(new View.OnClickListener() {
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
