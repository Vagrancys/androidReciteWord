package com.tramp.word.module.setting.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/4.
 */

public class SettingScreenActivity extends RxBaseActivity{
    @BindView(R.id.screen_lock_check)
    CheckBox ScreenLockCheck;
    @BindView(R.id.screen_back)
    RelativeLayout ScreenBack;
    @BindView(R.id.screen_back_text)
    TextView ScreenBackText;
    @BindView(R.id.screen_recover)
    RelativeLayout ScreenRecover;
    @BindView(R.id.screen_recover_title)
    TextView ScreenRecoverTitle;
    @BindView(R.id.screen_recover_img)
    ImageView ScreenRecoverImg;
    @BindView(R.id.screen_life)
    RelativeLayout ScreenLife;
    @BindView(R.id.screen_life_title)
    TextView ScreenLifeTitle;
    @BindView(R.id.screen_life_img)
    ImageView ScreenLifeImg;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_screen;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.screen_title));
    }

    @Override
    public void initView(Bundle save) {
        initData();
        initClick();

    }

    private void initData(){
        if(PreferencesUtils.getInt(ConstantUtils.SETTING_SCREEN_CODE,0)==1){
            ScreenLockCheck.setChecked(true);
            ScreenBack.setClickable(true);
            ScreenBackText.setTextColor(getResources().getColor(R.color.black));
            ScreenRecover.setClickable(true);
            ScreenRecoverTitle.setTextColor(getResources().getColor(R.color.black));
            ScreenLife.setClickable(true);
            ScreenLifeTitle.setTextColor(getResources().getColor(R.color.black));
        }else{
            ScreenLockCheck.setChecked(false);
            PreferencesUtils.putInt(ConstantUtils.SETTING_SCREEN_CODE,0);
            ScreenBack.setClickable(false);
            ScreenBackText.setTextColor(getResources().getColor(R.color.black_1));
            ScreenRecover.setClickable(false);
            ScreenRecoverTitle.setTextColor(getResources().getColor(R.color.black));
            ScreenLife.setClickable(false);
            ScreenLifeTitle.setTextColor(getResources().getColor(R.color.black));
        }

        if(PreferencesUtils.getInt(ConstantUtils.SETTING_WORD_CODE,0)==1){
            ScreenLifeImg.setVisibility(View.VISIBLE);
            ScreenRecoverImg.setVisibility(View.GONE);
        }else{
            ScreenRecoverImg.setVisibility(View.VISIBLE);
            ScreenLifeImg.setVisibility(View.GONE);
        }
    }

    private void initClick(){
        ScreenLockCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.SETTING_SCREEN_CODE,1);
                    ScreenBack.setClickable(true);
                    ScreenBackText.setTextColor(getResources().getColor(R.color.black));
                    ScreenRecover.setClickable(true);
                    ScreenRecoverTitle.setTextColor(getResources().getColor(R.color.black));
                    ScreenLife.setClickable(true);
                    ScreenLifeTitle.setTextColor(getResources().getColor(R.color.black));
                }else{
                    PreferencesUtils.putInt(ConstantUtils.SETTING_SCREEN_CODE,0);
                    ScreenBack.setClickable(false);
                    ScreenBackText.setTextColor(getResources().getColor(R.color.black_1));
                    ScreenRecover.setClickable(false);
                    ScreenRecoverTitle.setTextColor(getResources().getColor(R.color.black));
                    ScreenLife.setClickable(false);
                    ScreenLifeTitle.setTextColor(getResources().getColor(R.color.black));
                }
            }
        });

        ScreenLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenLifeImg.setVisibility(View.VISIBLE);
                ScreenRecoverImg.setVisibility(View.GONE);
                PreferencesUtils.putInt(ConstantUtils.SCREEN_STATIC,1);
            }
        });

        ScreenRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenRecoverImg.setVisibility(View.VISIBLE);
                ScreenLifeImg.setVisibility(View.GONE);
                PreferencesUtils.putInt(ConstantUtils.SCREEN_STATIC,0);
            }
        });

        ScreenBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingScreenActivity.this,SettingScreenBackActivity.class));
                Utils.StarActivityInAnim(SettingScreenActivity.this);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}








