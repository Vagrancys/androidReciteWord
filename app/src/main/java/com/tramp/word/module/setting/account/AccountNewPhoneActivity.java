package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.account_new_phone_right)
    TextView AccountNewPhoneRight;
    @BindView(R.id.account_new_phone_left)
    TextView AccountNewPhoneLeft;
    private static final int NEW_PHONE_CODE=35;
    private static final int NOW_PHONE_CODE=14;
    private String phone_number;
    private String phone_title;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_new_phone;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initResult();
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
                Intent intent=new Intent(AccountNewPhoneActivity.this,AccountPhoneAdapterActivity.class);
                startActivityForResult(intent,NOW_PHONE_CODE);
                Utils.StarActivityInAnim(AccountNewPhoneActivity.this);
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
                if(AccountNewPhoneEdit.getText().length()==11){
                    Retrofits.getUserAPI()
                            .getUpdatePhoneInfo(new UserSqlHelper(getBaseContext()).UserId(),AccountNewPhoneEdit.getText().toString())
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body()!=null&&response.body().getCode()==200){
                                        Utils.ShowToast(getBaseContext(),"修改手机号成功!");
                                        initResult();
                                    }else{
                                        Utils.ShowToast(getBaseContext(),"修改手机号失败!");
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),"网络出错了!");
                                }
                            });
                }else{
                    Utils.ShowToast(getBaseContext(),"手机号填写错误");
                }

            }
        });
    }

    private void initResult(){
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.ACCOUNT_NEW_PHONE,AccountNewPhoneEdit.getText().toString());
        setResult(NEW_PHONE_CODE,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==NOW_PHONE_CODE
                &&resultCode==36&&data !=null){
            phone_number=data.getStringExtra(ConstantUtils.ACCOUNT_PHONE_NUMBER);
            phone_title=data.getStringExtra(ConstantUtils.ACCOUNT_PHONE_TITLE);
            AccountNewPhoneRight.setText("+"+phone_number);
            AccountNewPhoneLeft.setText(phone_title);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}














