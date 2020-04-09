package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/28.
 */

public class AccountSafetyActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.account_safety_relative)
    RelativeLayout AccountSafetyRelative;
    @BindView(R.id.account_safety_title)
    TextView AccountSafetyTitle;
    private final int SAFETY_CODE=12;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_safety;
    }

    @Override
    public void initView(Bundle save) {
        AccountSafetyRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountSafetyActivity.this,AccountNewPassActivity.class);
                startActivityForResult(intent,SAFETY_CODE);
                Utils.StarActivityInAnim(AccountSafetyActivity.this);
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

        DefaultTitle.setText(getResources().getString(R.string.account_safety_text));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SAFETY_CODE
                &&resultCode==33&&data !=null){
            int number=data.getIntExtra(ConstantUtils.ACCOUNT_SAFETY,0);
            if(number==1){
                AccountSafetyTitle.setText(getResources().getString(R.string.account_safety_title_yes));
            }else{
                AccountSafetyTitle.setText(getResources().getString(R.string.account_safety_title_no));
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
