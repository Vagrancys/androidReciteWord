package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingReturnActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.return_swipe_refresh)
    SwipeRefreshLayout ReturnSwipeRefresh;
    @BindView(R.id.return_help)
    TextView ReturnHelp;
    private boolean IsRefreshing;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_return;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultTitle.setText(getResources().getString(R.string.return_default_title));
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        ReturnSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.blue));
        ReturnSwipeRefresh.post(()->{
            ReturnSwipeRefresh.setRefreshing(true);
            IsRefreshing=true;
            loadData();
        });
        ReturnSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReturnSwipeRefresh.setRefreshing(true);
                IsRefreshing=true;
                loadData();
            }
        });

        ReturnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingReturnActivity.this, WedCommonActivity.class));
                Utils.StarActivityInAnim(SettingReturnActivity.this);
            }
        });


    }

    private void loadData(){
        finisTask();
    }

    private void finisTask(){
        ReturnSwipeRefresh.setRefreshing(false);
        IsRefreshing=false;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}













