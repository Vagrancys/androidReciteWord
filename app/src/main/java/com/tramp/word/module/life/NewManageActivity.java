package com.tramp.word.module.life;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.NewManageViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.NewManageDialog;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/14
 * version:1.0
 */

public class NewManageActivity extends RxBaseActivity{
    @BindView(R.id.new_manage_out)
    TextView NewManageOut;
    @BindView(R.id.new_manage_text)
    TextView NewManageText;
    @BindView(R.id.new_manage_recycler)
    RecyclerView NewManageRecycler;
    private NewManageDialog mNewManageDialog;
    private NewManageViewAdapter mNewManageViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_manage;
    }

    @Override
    protected void initToolBar() {
        NewManageOut.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void initView(Bundle save) {
        mNewManageDialog=new NewManageDialog(this);
        mNewManageDialog.setTitle("新建生词本");
        mNewManageDialog.setOkOnClickListener("确定", new NewManageDialog.OkOnClickListener() {
            @Override
            public void onOkClick(String text) {
                mNewManageDialog.dismiss();
            }
        });
        mNewManageDialog.setCancelOnClickListener("取消", new NewManageDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mNewManageDialog.dismiss();
            }
        });

        NewManageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewManageDialog.show();
            }
        });

        mNewManageViewAdapter=new NewManageViewAdapter(NewManageRecycler);
        NewManageRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        NewManageRecycler.setAdapter(mNewManageViewAdapter);
    }
}





