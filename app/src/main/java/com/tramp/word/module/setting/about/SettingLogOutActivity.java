package com.tramp.word.module.setting.about;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.UserDecideActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/24
 * version:1.0
 */
public class SettingLogOutActivity extends RxBaseActivity {
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.log_out)
    TextView LogOut;
    @BindView(R.id.log_in)
    TextView LogIn;
    @BindView(R.id.log_check)
    CheckBox LogCheck;
    @BindView(R.id.log_check_title)
    TextView LogCheckTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_log_out;
    }

    @Override
    public void initView(Bundle save) {
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingLogOutActivity.this, UserDecideActivity.class));
                ExitActivity();
                finish();
            }
        });
        LogCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    LogIn.setBackground(getResources().getDrawable(R.drawable.btn_log_in_win));
                    LogIn.setTextColor(getResources().getColor(R.color.black));
                    LogIn.setClickable(true);
                }else{
                    LogIn.setBackground(getResources().getDrawable(R.drawable.btn_log_in_fail));
                    LogIn.setTextColor(getResources().getColor(R.color.black_1));
                    LogIn.setClickable(false);
                }
            }
        });
        LogCheckTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingLogOutActivity.this,SettingSummaryActivity.class));
                Utils.StarActivityInAnim(SettingLogOutActivity.this);
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
        DefaultTitle.setText(getResources().getString(R.string.log_title));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
