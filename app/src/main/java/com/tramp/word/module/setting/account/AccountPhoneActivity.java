package com.tramp.word.module.setting.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

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

    private String phone_name;
    private final int NEW_PHONE_CODE=13;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_phone;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            AccountPhoneNumber.setText(intent.getStringExtra(ConstantUtils.ACCOUNT_PHONE));
        }
        AccountPhoneStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountPhoneActivity.this,AccountNewPhoneActivity.class);
                startActivityForResult(intent,NEW_PHONE_CODE);
                Utils.StarActivityInAnim(AccountPhoneActivity.this);
            }
        });
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
                DefaultTitle.setText(getResources().getString(R.string.account_phone_toolbar));
            }
        });
    }

    private void initResult(){
        int PHONE_CODE=34;
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.ACCOUNT_PHONE,phone_name);
        setResult(PHONE_CODE,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==NEW_PHONE_CODE
                &&resultCode==35&&data !=null){
             phone_name=data.getStringExtra(ConstantUtils.ACCOUNT_NEW_PHONE);
             AccountPhoneNumber.setText(phone_name);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity, String user_phone, int code){
        Intent intent=new Intent(activity,AccountPhoneActivity.class);
        intent.putExtra(ConstantUtils.ACCOUNT_PHONE,user_phone);
        activity.startActivityForResult(intent,code);
        Utils.StarActivityInAnim(activity);
    }
}
