package com.tramp.word.base;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/1/7.
 */

public abstract class RxBaseActivity extends RxAppCompatActivity {
    private Unbinder bind;

    public abstract int getLayoutId();

    protected abstract void initToolBar();

    public abstract void initView(Bundle save);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind= ButterKnife.bind(this);
        initView(savedInstanceState);
        initToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}


















