package com.tramp.word.module.setting;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.module.setting.account.SettingAccountActivity;
import com.tramp.word.module.setting.inform.SettingInformActivity;
import com.tramp.word.module.setting.screen.SettingScreenActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/26.
 */

public class MeSettingActivity extends RxBaseActivity implements View.OnClickListener{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.setting_account)
    RelativeLayout SettingAccount;
    @BindView(R.id.setting_return)
    RelativeLayout SettingReturn;
    @BindView(R.id.setting_automatic)
    RelativeLayout SettingAutoMatic;
    @BindView(R.id.setting_english)
    RelativeLayout SettingEnglish;
    @BindView(R.id.setting_inform)
    RelativeLayout SettingInform;
    @BindView(R.id.setting_input)
    RelativeLayout SettingInput;
    @BindView(R.id.setting_down)
    RelativeLayout SettingDown;
    @BindView(R.id.setting_play_check)
    CheckBox SettingPlayCheck;
    @BindView(R.id.setting_news_check)
    CheckBox SettingNewsCheck;
    @BindView(R.id.setting_sound_check)
    CheckBox SettingSoundCheck;
    @BindView(R.id.setting_show_check)
    CheckBox SettingShowCheck;
    @BindView(R.id.setting_pk_check)
    CheckBox SettingPkCheck;
    @BindView(R.id.setting_screen)
    RelativeLayout SettingScreen;
    @BindView(R.id.setting_help)
    RelativeLayout SettingHelp;
    @BindView(R.id.setting_praise)
    RelativeLayout SettingPraise;
    @BindView(R.id.setting_about)
    RelativeLayout SettingAbout;

    @BindView(R.id.setting_account_title)
    TextView SettingAccountTitle;
    @BindView(R.id.setting_return_title)
    TextView SettingReturnTitle;
    @BindView(R.id.setting_automatic_title)
    TextView SettingAutoMaticTitle;
    @BindView(R.id.setting_english_title)
    TextView SettingEnglishTitle;
    @BindView(R.id.setting_input_title)
    TextView SettingInputTitle;
    @BindView(R.id.setting_down_title)
    TextView SettingDownTitle;
    private final int AUTOMATIC_CODE=3;
    private final int ENGLISH_CODE=4;
    private final int INFORM_CODE=5;
    private final int INPUT_CODE=6;
    private final int DOWN_CODE=7;
    private final int SCREEN_CODE=8;
    private Intent mIntent=new Intent();
    private String[] AutoTitle={
            "关闭","一次","两次","三次"
    };
    private String[] EnglishTitle={
            "默认","英音","美音"
    };

    private String[] InputTitle={
            "点选","键盘"
    };

    private String[] DownTitle={
        "手机","存储1"
    };
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
            case R.id.setting_account:
                mIntent.setClass(MeSettingActivity.this,SettingAccountActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
            case R.id.setting_return:
                mIntent.setClass(MeSettingActivity.this,SettingReturnActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
            case R.id.setting_automatic:
                mIntent.setClass(MeSettingActivity.this,SettingAutoMaticActivity.class);
                startActivityForResult(mIntent,AUTOMATIC_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_english:
                mIntent.setClass(MeSettingActivity.this,SettingEnglishActivity.class);
                startActivityForResult(mIntent,ENGLISH_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_inform:
                mIntent.setClass(MeSettingActivity.this,SettingInformActivity.class);
                startActivityForResult(mIntent,INFORM_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_input:
                mIntent.setClass(MeSettingActivity.this,SettingInputActivity.class);
                startActivityForResult(mIntent,INPUT_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_down:
                mIntent.setClass(MeSettingActivity.this,SettingDownActivity.class);
                startActivityForResult(mIntent,DOWN_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_screen:
                mIntent.setClass(MeSettingActivity.this,SettingScreenActivity.class);
                startActivityForResult(mIntent,SCREEN_CODE);
                StartActivityAnim();
                break;
            case R.id.setting_help:
                if(Utils.getNetWork(this)){
                    mIntent.setClass(MeSettingActivity.this, WedCommonActivity.class);
                    startActivity(mIntent);
                    StartActivityAnim();
                }
                break;
            case R.id.setting_praise:
                if(Utils.getNetWork(this)){
                    mIntent.setClass(MeSettingActivity.this, WedCommonActivity.class);
                    startActivity(mIntent);
                    StartActivityAnim();
                }
                break;
            case R.id.setting_about:
                mIntent.setClass(MeSettingActivity.this,SettingAboutActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
        }
    }


    @Override
    public void initView(Bundle save) {
        initData();
        initClick();
        initCheck();
    }

    private void initData(){
        Cursor cursor=new UserSqlHelper(getBaseContext()).UserQuery();
        SettingAccountTitle.setText(cursor.getString(cursor.getColumnIndex("user_name")));
        SettingReturnTitle.setText("￥10.10");

        int auto=PreferencesUtils.getInt(ConstantUtils.SETTING_AUTO_CODE,0);
        SettingAutoMaticTitle.setText(AutoTitle[auto]);

        int english=PreferencesUtils.getInt(ConstantUtils.SETTING_ENGLISH_CODE,0);
        SettingEnglishTitle.setText(EnglishTitle[english]);

        int input=PreferencesUtils.getInt(ConstantUtils.SETTING_INPUT_CODE,0);
        SettingInputTitle.setText(InputTitle[input]);

        int down=PreferencesUtils.getInt(ConstantUtils.SETTING_DOWN_CODE,0);
        SettingDownTitle.setText(DownTitle[down]);

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_PLAY_CODE,false)){
            SettingPlayCheck.setChecked(true);
        }else{
            SettingPlayCheck.setChecked(false);
        }

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_NEWS_CODE,false)){
            SettingNewsCheck.setChecked(true);
        }else{
            SettingNewsCheck.setChecked(false);
        }

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_SOUND_CODE,false)){
            SettingSoundCheck.setChecked(true);
        }else{
            SettingSoundCheck.setChecked(false);
        }

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_SOUND_CODE,false)){
            SettingSoundCheck.setChecked(true);
        }else{
            SettingSoundCheck.setChecked(false);
        }

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_SHOW_CODE,false)){
            SettingShowCheck.setChecked(true);
        }else{
            SettingShowCheck.setChecked(false);
        }

        if(PreferencesUtils.getBoolean(ConstantUtils.SETTING_PK_CODE,false)){
            SettingPkCheck.setChecked(true);
        }else{
            SettingPkCheck.setChecked(false);
        }
    }

    public void initCheck(){
        SettingPlayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PLAY_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_PLAY_CODE,false);
                }
            }
        });

        SettingNewsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_NEWS_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_NEWS_CODE,false);
                }
            }
        });

        SettingSoundCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SOUND_CODE,true);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SOUND_CODE,false);
                }
                recreate();
            }
        });

        SettingShowCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SHOW_CODE,true);
                }else{
                    PreferencesUtils.putBoolean(ConstantUtils.SETTING_SHOW_CODE,false);
                }
            }
        });

        SettingPkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        SettingAccount.setOnClickListener(this);
        SettingReturn.setOnClickListener(this);
        SettingAutoMatic.setOnClickListener(this);
        SettingEnglish.setOnClickListener(this);
        SettingInform.setOnClickListener(this);
        SettingInput.setOnClickListener(this);
        SettingDown.setOnClickListener(this);
        SettingScreen.setOnClickListener(this);
        SettingHelp.setOnClickListener(this);
        SettingPraise.setOnClickListener(this);
        SettingAbout.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AUTOMATIC_CODE:
                if(resultCode==20&&data !=null){
                    SettingAutoMaticTitle.setText(AutoTitle[data.getIntExtra(ConstantUtils.SETTING_AUTO_CODE,0)]);
                }
                break;
            case ENGLISH_CODE:
                if(resultCode==21&&data !=null){
                    SettingEnglishTitle.setText(EnglishTitle[data.getIntExtra(ConstantUtils.SETTING_ENGLISH_CODE,0)]);
                }
                break;
            case INPUT_CODE:
                if(resultCode==22&&data !=null){
                    SettingInputTitle.setText(InputTitle[data.getIntExtra(ConstantUtils.SETTING_INPUT_CODE,0)]);
                }
                break;
            case DOWN_CODE:
                if(resultCode==23&&data !=null){
                    SettingDownTitle.setText(DownTitle[data.getIntExtra(ConstantUtils.SETTING_DOWN_CODE,0)]);
                }
                break;
            default:
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
