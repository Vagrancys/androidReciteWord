package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

public class AccountNameActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;

    @BindView(R.id.account_name_edit)
    EditText AccountNameEdit;
    @BindView(R.id.account_name_delete)
    ImageView AccountNameDelete;
    @BindView(R.id.account_name_start)
    TextView AccountNameStart;
    private String title;
    private final int ACCOUNT_CODE=11;
    public final String ACCOUNT_NAME="account_name";
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_name;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.account_name_edit_text));
    }

    @Override
    public void initView(Bundle save) {
        if(getIntent()==null){
            title=getIntent().getStringExtra(ACCOUNT_NAME);
            AccountNameEdit.setText(title);
            AccountNameStart.setEnabled(false);
            AccountNameDelete.setVisibility(View.VISIBLE);
        }else{
            AccountNameEdit.setText("");
        }
        AccountNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!AccountNameEdit.getText().toString().equals(title)){
                    AccountNameStart.setEnabled(true);
                }else{
                    AccountNameStart.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AccountNameDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountNameDelete.setVisibility(View.GONE);
                AccountNameEdit.setText("");
            }
        });

        AccountNameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AccountNameEdit.getText().toString().equals(title)){
                    Utils.ShowToast(AccountNameActivity.this,"修改用户名成功！");
                    Intent intent=new Intent();
                    intent.putExtra(ACCOUNT_NAME,AccountNameEdit.getText().toString());
                    setResult(ACCOUNT_CODE,intent);
                    finish();
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







