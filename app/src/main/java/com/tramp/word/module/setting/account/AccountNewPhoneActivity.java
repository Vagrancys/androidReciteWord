package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/1.
 */

public class AccountNewPhoneActivity extends RxBaseActivity{
    @BindView(R.id.account_new_phone_relative)
    RelativeLayout AccountNewPhoneRelative;
    @BindView(R.id.account_new_phones_edit)
    EditText AccountNewPhoneEdit;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.account_new_phone_delete)
    ImageView AccountNewPhoneImg;
    @BindView(R.id.account_new_phone_start)
    TextView AccountNewPhoneStart;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_new_phone;
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
                DefaultTitle.setText(getResources().getString(R.string.account_new_phones_toolbar));
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        AccountNewPhoneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountNewPhoneActivity.this,AccountPhoneAdapterActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        AccountNewPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(AccountNewPhoneEdit.getText().length()>0){
                    AccountNewPhoneImg.setVisibility(View.VISIBLE);
                }else{
                    AccountNewPhoneImg.setVisibility(View.GONE);
                }
            }
        });

        AccountNewPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountNewPhoneEdit.setText("");
            }
        });

        AccountNewPhoneStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(AccountNewPhoneEdit.getText().length()==11)){
                    return;
                }
                Utils.ShowToast(AccountNewPhoneActivity.this,"更换手机号");
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}














