package com.tramp.word.module.search;

import android.os.Bundle;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordSearchActivity extends RxBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_search;
    }

    @Override
    public void initView(Bundle save) {

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
