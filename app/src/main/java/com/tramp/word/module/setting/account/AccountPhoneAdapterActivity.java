package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.AccountPhoneAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/1.
 */

public class AccountPhoneAdapterActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.account_phone_adapter)
    RecyclerView AccountPhoneRecycler;
    @BindView(R.id.swipe_view)
    SwipeRefreshLayout SwipeView;
    private String[] Title={
            "阿拉伯联合酋长国","阿根延","奥地利",
            "澳大利亚","德国","俄罗斯","法国",
            "菲律宾","韩国","荷兰","加拿大","東埔寨",
            "马来西亚","美国","缅句","墨西哥","日本",
            "来士","台湾","泰国","西班牙","新加坡","新西兰",
            "意大利","印度","印度尼西亚","英国","越南",
            "中华人民共和国","澳门特别行政区","香港特别行政区"
    };
    private String[] Number={
            "971","54","43",
            "61","49","7","33",
            "63","82","31","1","855",
            "60","1","95","52","81",
            "41","886","66","34","65","64",
            "39","91","62","44","84",
            "86","853","852"
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_phone_adapter;
    }

    @Override
    public void initView(Bundle save) {
        lazyData();
        AccountPhoneAdapter mAccountPhoneAdapter=new AccountPhoneAdapter(AccountPhoneRecycler,Title,Number);
        AccountPhoneRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        AccountPhoneRecycler.setAdapter(mAccountPhoneAdapter);
        mAccountPhoneAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"选中了");
                initResult(position);
            }
        });
    }
    private void lazyData(){
        SwipeView.setColorSchemeColors(getResources().getColor(R.color.blue));
        SwipeView.post(()->{
            SwipeView.setRefreshing(true);
            initNet();
        });
        SwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeView.setRefreshing(true);
                initNet();
            }
        });
    }
    private void initNet(){
        finishTask();
    }

    private void finishTask(){
        SwipeView.setRefreshing(false);
    }

    private void initResult(int number){
        int PHONE_NUMBER_CODE=36;
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.ACCOUNT_PHONE_TITLE,Title[number]);
        intent.putExtra(ConstantUtils.ACCOUNT_PHONE_NUMBER,Number[number]);
        setResult(PHONE_NUMBER_CODE,intent);
        finish();
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
                DefaultTitle.setText(getResources().getString(R.string.account_phone_adapter));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
