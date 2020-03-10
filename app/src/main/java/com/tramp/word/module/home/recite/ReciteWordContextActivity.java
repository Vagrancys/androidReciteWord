package com.tramp.word.module.home.recite;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.PullWordRefreshLayout;
import com.tramp.word.widget.WordContentScrollView;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/25.
 */

public class ReciteWordContextActivity extends RxBaseActivity {
    @BindView(R.id.pull_word_refresh)
    PullWordRefreshLayout mPullRefresh;
    @BindView(R.id.view_scroll_view)
    WordContentScrollView mScrollView;
    @BindView(R.id.word_content_text_symbol)
    TextView mContentSymbol;
    @BindView(R.id.word_content_linear)
    LinearLayout mWordContentLinear;
    @BindView(R.id.word_content_linear_two)
    LinearLayout mWordContentLinearTwo;
    @BindView(R.id.word_content_text_head)
    TextView mWordContentTextHead;
    @BindView(R.id.word_content_relative)
    RelativeLayout mWordRelative;
    @BindView(R.id.word_content_relative_one)
    RelativeLayout mWordRelativeOne;
    @BindView(R.id.word_content_out)
    ImageView mWordContentOut;
    private String status;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mWordContentLinear.setVisibility(View.VISIBLE);
                mWordContentLinear.setScrollY(0);
                mPullRefresh.refreshFinished();
            } else if (msg.what == 2) {
                mWordContentLinearTwo.setVisibility(View.VISIBLE);
                mWordContentLinearTwo.setScrollY(0);
                mPullRefresh.loadMoreFinished();
            }
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word_context;
    }

    @Override
    public void initView(Bundle save) {
        status=getIntent().getStringExtra("status");
        if(status=="details"){
            mWordRelative.setVisibility(View.GONE);
            mWordRelative.setVisibility(View.GONE);
        }
        mPullRefresh.setRefreshListener(new PullWordRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                mHandler.sendEmptyMessageDelayed(1,1000);
            }

            @Override
            public void loadMoreFinished() {
                mHandler.sendEmptyMessageDelayed(2,1000);
            }

            @Override
            public void HeaderLayout(int y) {
                if(y>-50){
                    mWordContentLinear.setScrollY(-y);
                }else{
                    mWordContentLinear.setVisibility(View.GONE);
                }
            }

            @Override
            public void FooterLayout(int y) {
                if(y<100){
                    mWordContentLinearTwo.setScrollY(-y);
                }else{
                    mWordContentLinearTwo.setVisibility(View.GONE);
                }
            }
        });
        mScrollView.setScrollViewListener(new WordContentScrollView.ScrollViewListener() {
            @Override
            public void onScrollItem(int y, int dy) {
            }
        });

        mWordContentOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
