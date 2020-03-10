package com.tramp.word.module.common;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/26.
 */

public class WedCommonActivity extends RxBaseActivity{
    @BindView(R.id.web_common_out)
    ImageView mWebCommonOut;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web_common;
    }

    @Override
    protected void initToolBar() {
        mWebCommonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}

