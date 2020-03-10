package com.tramp.word.module.setting.account;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.AccountPhoneAdapter;
import com.tramp.word.base.RxBaseActivity;
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
    private AccountPhoneAdapter mAccountPhoneAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_phone_adapter;
    }

    @Override
    public void initView(Bundle save) {
        mAccountPhoneAdapter=new AccountPhoneAdapter(AccountPhoneRecycler);
        AccountPhoneRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        AccountPhoneRecycler.setAdapter(mAccountPhoneAdapter);
        mAccountPhoneAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"选中了");
                finish();
            }
        });
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
