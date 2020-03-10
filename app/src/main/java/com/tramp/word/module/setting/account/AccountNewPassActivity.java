package com.tramp.word.module.setting.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/28.
 */

public class AccountNewPassActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.account_new_pass_edit)
    EditText AccountNewPassEdit;
    @BindView(R.id.account_new_pass_edit_1)
    EditText AccountNewPassEdit1;
    @BindView(R.id.account_new_pass_delete)
    ImageView AccountNewPassDelete;
    @BindView(R.id.account_new_pass_delete_1)
    ImageView AccountNewPassDelete1;
    @BindView(R.id.account_new_pass_show)
    ImageView AccountNewPassShow;
    @BindView(R.id.account_new_pass_start)
    TextView AccountNewPassStart;
    @Override
    public int getLayoutId() {
        return R.layout.account_new_pass;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.account_new_pass_edit_text));
    }

    @Override
    public void initView(Bundle save) {
        AccountNewPassEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(AccountNewPassEdit.getText().length()>0){
                    AccountNewPassDelete.setVisibility(View.VISIBLE);
                }else{
                    AccountNewPassDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AccountNewPassEdit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(AccountNewPassEdit1.getText().length()>0){
                    AccountNewPassDelete1.setVisibility(View.VISIBLE);
                }else{
                    AccountNewPassDelete1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AccountNewPassDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountNewPassDelete.setVisibility(View.GONE);
                AccountNewPassEdit.setText("");
            }
        });

        AccountNewPassDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountNewPassEdit1.setText("");
                AccountNewPassDelete1.setVisibility(View.GONE);
            }
        });

        AccountNewPassShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountNewPassEdit1.getInputType()==InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    AccountNewPassEdit1.setInputType(InputType.TYPE_MASK_VARIATION);
                }else{
                    AccountNewPassEdit1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        AccountNewPassStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountNewPassEdit.getText().length()>0
                        &&AccountNewPassEdit1.getText().length()>0
                        &&AccountNewPassEdit.getText().toString()
                        .equals(AccountNewPassEdit1.getText().toString())){
                    Utils.ShowToast(AccountNewPassActivity.this,"修改密码成功！");
                    finish();
                }else{
                    Utils.ShowToast(AccountNewPassActivity.this,"修改密码失败！");
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}














