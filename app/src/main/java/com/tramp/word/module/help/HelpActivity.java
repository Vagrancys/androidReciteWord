package com.tramp.word.module.help;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/10.
 */

public class HelpActivity extends RxBaseActivity {
    @BindView(R.id.help_out)
    ImageView mHelpOut;
    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initToolBar() {
        mHelpOut.setOnClickListener(new View.OnClickListener() {
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
