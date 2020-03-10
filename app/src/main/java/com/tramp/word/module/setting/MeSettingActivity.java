package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.module.setting.account.SettingAccountActivity;
import com.tramp.word.module.setting.inform.SettingInformActivity;
import com.tramp.word.module.setting.screen.SettingScreenActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/26.
 */

public class MeSettingActivity extends RxBaseActivity implements View.OnClickListener{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.me_setting_account)
    RelativeLayout MeSettingAccount;
    @BindView(R.id.me_setting_return)
    RelativeLayout MeSettingReturn;
    @BindView(R.id.me_setting_automatic)
    RelativeLayout MeSettingAutoMatic;
    @BindView(R.id.me_setting_english)
    RelativeLayout MeSettingEnglish;
    @BindView(R.id.me_setting_inform)
    RelativeLayout MeSettingInform;
    @BindView(R.id.me_setting_input)
    RelativeLayout MeSettingInput;
    @BindView(R.id.me_setting_down)
    RelativeLayout MeSettingDown;
    @BindView(R.id.me_setting_play_check)
    CheckBox MeSettingPlayCheck;
    @BindView(R.id.me_setting_news_check)
    CheckBox MeSettingNewsCheck;
    @BindView(R.id.me_setting_sound_check)
    CheckBox MeSettingSoundCheck;
    @BindView(R.id.me_setting_show_check)
    CheckBox MeSettingShowCheck;
    @BindView(R.id.me_setting_pk_check)
    CheckBox MeSettingPkCheck;
    @BindView(R.id.me_setting_screen)
    RelativeLayout MeSettingScreen;
    @BindView(R.id.me_setting_help)
    RelativeLayout MeSettingHelp;
    @BindView(R.id.me_setting_about)
    RelativeLayout MeSettingAbout;
    private final int ACCOUNT_CODE=1;
    private final int RETURN_CODE=2;
    private final int AUTOMATIC_CODE=3;
    private final int ENGLISH_CODE=4;
    private final int INFORM_CODE=5;
    private final int INPUT_CODE=6;
    private final int DOWN_CODE=7;
    private final int SCREEN_CODE=8;
    private Intent mIntent=new Intent();
    @Override
    public int getLayoutId() {
        return R.layout.activity_me_setting;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.me_setting_account:
                mIntent.setClass(MeSettingActivity.this,SettingAccountActivity.class);
                startActivityForResult(mIntent,ACCOUNT_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_return:
                mIntent.setClass(MeSettingActivity.this,SettingReturnActivity.class);
                startActivityForResult(mIntent,RETURN_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_automatic:
                mIntent.setClass(MeSettingActivity.this,SettingAutoMaticActivity.class);
                startActivityForResult(mIntent,AUTOMATIC_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_english:
                mIntent.setClass(MeSettingActivity.this,SettingEnglishActivity.class);
                startActivityForResult(mIntent,ENGLISH_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_inform:
                mIntent.setClass(MeSettingActivity.this,SettingInformActivity.class);
                startActivityForResult(mIntent,INFORM_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_input:
                mIntent.setClass(MeSettingActivity.this,SettingInputActivity.class);
                startActivityForResult(mIntent,INPUT_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_down:
                mIntent.setClass(MeSettingActivity.this,SettingDownActivity.class);
                startActivityForResult(mIntent,DOWN_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_screen:
                mIntent.setClass(MeSettingActivity.this,SettingScreenActivity.class);
                startActivityForResult(mIntent,SCREEN_CODE);
                StartActivityAnim();
                break;
            case R.id.me_setting_help:
                mIntent.setClass(MeSettingActivity.this, WedCommonActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
            case R.id.me_setting_about:
                mIntent.setClass(MeSettingActivity.this,SettingAboutActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
        }

    }


    @Override
    public void initView(Bundle save) {
        initClick();
        initCheck();
    }

    public void initCheck(){
        MeSettingPlayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PLAY_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PLAY_CODE,false);
                }
            }
        });

        MeSettingNewsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_NEWS_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_NEWS_CODE,false);
                }
            }
        });

        MeSettingSoundCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SOUND_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SOUND_CODE,false);
                }
            }
        });

        MeSettingShowCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SHOW_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SHOW_CODE,false);
                }
            }
        });

        MeSettingPkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PK_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PK_CODE,false);
                }
            }
        });
    }

    public void initClick(){
        MeSettingAccount.setOnClickListener(this);
        MeSettingReturn.setOnClickListener(this);
        MeSettingAutoMatic.setOnClickListener(this);
        MeSettingEnglish.setOnClickListener(this);
        MeSettingInform.setOnClickListener(this);
        MeSettingInput.setOnClickListener(this);
        MeSettingDown.setOnClickListener(this);
        MeSettingScreen.setOnClickListener(this);
        MeSettingHelp.setOnClickListener(this);
        MeSettingAbout.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case ACCOUNT_CODE:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public void StartActivityAnim(){
        overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }
}
