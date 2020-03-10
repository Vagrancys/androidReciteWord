package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/1.
 */

public class AccountPhoneActivity extends RxBaseActivity {
    @BindView(R.id.account_phone_number)
    TextView AccountPhoneNumber;
    @BindView(R.id.account_phone_start)
    TextView AccountPhoneStart;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_phone;
    }

    @Override
    public void initView(Bundle save) {
        if(getIntent()==null){
            AccountPhoneNumber.setText(getIntent().getStringExtra(ConstantUtils.ACCOUNT_PHONE));
        }
        AccountPhoneStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountPhoneActivity.this,AccountNewPhoneActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
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
                DefaultTitle.setText(getResources().getString(R.string.account_phone_toolbar));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
