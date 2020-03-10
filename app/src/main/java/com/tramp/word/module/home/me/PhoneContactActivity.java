package com.tramp.word.module.home.me;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/25.
 */

public class PhoneContactActivity extends RxBaseActivity{
    @BindView(R.id.phone_contact_start)
    TextView mPhoneContactStart;
    @BindView(R.id.phone_contact_img)
    ImageView mPhoneContactImg;
    @Override
    public int getLayoutId() {
        return R.layout.activity_phone_contact;
    }

    @Override
    protected void initToolBar() {
        mPhoneContactImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mPhoneContactStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("/");
                ComponentName cm=new ComponentName("com.android.settings","com.android.settings.ApplicationSettings");
                intent.setComponent(cm);
                intent.setAction("android.intent.action.VIEW");
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}









