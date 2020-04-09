package com.tramp.word.module.setting.inform;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import com.tramp.word.widget.InformTimeDialog;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingInformActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.inform_weixin_check)
    CheckBox InformWeiXinCheck;
    @BindView(R.id.inform_time_text)
    TextView InformWeiXinTimeText;
    @BindView(R.id.inform_time_title)
    TextView InformWeiXinTimeTitle;
    @BindView(R.id.inform_time)
    RelativeLayout InformWeiXinTime;

    @BindView(R.id.inform_system_check)
    CheckBox InformSystemCheck;
    @BindView(R.id.inform_system_time_text)
    TextView InformSystemTimeText;
    @BindView(R.id.inform_system_time_title)
    TextView InformSystemTimeTitle;
    @BindView(R.id.inform_system_time)
    RelativeLayout InformSystemTime;

    @BindView(R.id.inform_pk_check)
    CheckBox InformPkCheck;
    @BindView(R.id.inform_pk_text)
    TextView InformPkText;

    @BindView(R.id.inform_friend_check)
    CheckBox InformFriendCheck;
    @BindView(R.id.inform_friend_text)
    TextView InformFriendText;

    @BindView(R.id.inform_group_check)
    CheckBox InformGroupCheck;
    @BindView(R.id.inform_group_text)
    TextView InformGroupText;
    private InformTimeDialog mInformTimeDialog;
    private AlarmManager mAlarmManager;
    private PendingIntent pi;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_inform;
    }

    @Override
    public void initView(Bundle save) {

        initData();
        initClick();
    }

    private void initData(){
        if(PreferencesUtils.getInt(ConstantUtils.INFORM_WEIXIN_CODE,0)==1){
            InformWeiXinCheck.setChecked(true);
            InformWeiXinTime.setEnabled(true);
            InformWeiXinTimeText.setTextColor(getResources().getColor(R.color.black));
            InformWeiXinTimeTitle.setTextColor(getResources().getColor(R.color.blue));
        }else{
            InformWeiXinCheck.setChecked(false);
            InformWeiXinTime.setEnabled(false);
            InformWeiXinTimeText.setTextColor(getResources().getColor(R.color.black_2));
            InformWeiXinTimeTitle.setTextColor(getResources().getColor(R.color.black_2));
        }

        if(PreferencesUtils.getInt(ConstantUtils.INFORM_SYSTEM_CODE,0)==1){
            InformSystemCheck.setChecked(true);
            InformSystemTime.setEnabled(true);
            InformSystemTimeText.setTextColor(getResources().getColor(R.color.black));
            InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.blue));
        }else{
            InformSystemCheck.setChecked(false);
            InformSystemTime.setEnabled(false);
            InformSystemTimeText.setTextColor(getResources().getColor(R.color.black_2));
            InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.black_2));
        }

        if(PreferencesUtils.getInt(ConstantUtils.INFORM_PK_CODE,0)==1){
            InformPkCheck.setChecked(true);
            InformPkText.setTextColor(getResources().getColor(R.color.black));
        }else{
            InformPkCheck.setChecked(false);
            InformPkText.setTextColor(getResources().getColor(R.color.black_2));
        }

        if (PreferencesUtils.getInt(ConstantUtils.INFORM_FRIEND_CODE,0)==1){
            InformFriendCheck.setChecked(true);
            InformFriendText.setTextColor(getResources().getColor(R.color.black));
        }else{
            InformFriendCheck.setChecked(false);
            InformFriendText.setTextColor(getResources().getColor(R.color.black_2));
        }

        if(PreferencesUtils.getInt(ConstantUtils.INFORM_GROUP_CODE,0)==1){
            InformGroupCheck.setChecked(true);
            InformGroupText.setTextColor(getResources().getColor(R.color.black));
        }else{
            InformGroupCheck.setChecked(false);
            InformGroupText.setTextColor(getResources().getColor(R.color.black_2));
        }

        Intent intent=new Intent(this,AlarmInformReceiver.class);
        intent.setAction("NOTIFICATION");
        pi=PendingIntent.getBroadcast(this,0,intent,0);
        mAlarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        mInformTimeDialog=new InformTimeDialog(this);
    }

    private void initClick(){
        InformWeiXinCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.INFORM_WEIXIN_CODE,1);
                    InformWeiXinTime.setEnabled(true);
                    InformWeiXinTimeText.setTextColor(getResources().getColor(R.color.black));
                    InformWeiXinTimeTitle.setTextColor(getResources().getColor(R.color.blue));
                    //mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,new Date().getTime(),1000*60,pi);
                }else{
                    PreferencesUtils.putInt(ConstantUtils.INFORM_WEIXIN_CODE,0);
                    InformWeiXinTime.setEnabled(false);
                    InformWeiXinTimeText.setTextColor(getResources().getColor(R.color.black_2));
                    InformWeiXinTimeTitle.setTextColor(getResources().getColor(R.color.black_2));
                    //mAlarmManager.cancel(pi);
                }
            }
        });

        InformSystemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.INFORM_SYSTEM_CODE,1);
                    InformSystemTime.setEnabled(true);
                    InformSystemTimeText.setTextColor(getResources().getColor(R.color.black));
                    InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.blue));
                    //mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,new Date().getTime(),1000*60,pi);
                }else{
                    PreferencesUtils.putInt(ConstantUtils.INFORM_SYSTEM_CODE,0);
                    InformSystemTime.setEnabled(false);
                    InformSystemTimeText.setTextColor(getResources().getColor(R.color.black_2));
                    InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.black_2));
                    //mAlarmManager.cancel(pi);
                }
            }
        });

        InformPkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.INFORM_PK_CODE,1);
                    InformPkText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    PreferencesUtils.putInt(ConstantUtils.INFORM_PK_CODE,0);
                    InformPkText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });

        InformFriendCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.INFORM_FRIEND_CODE,1);
                    InformFriendText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    PreferencesUtils.putInt(ConstantUtils.INFORM_FRIEND_CODE,0);
                    InformFriendText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });

        InformGroupCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferencesUtils.putInt(ConstantUtils.INFORM_GROUP_CODE,1);
                    InformGroupText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    PreferencesUtils.putInt(ConstantUtils.INFORM_GROUP_CODE,0);
                    InformGroupText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });


        mInformTimeDialog.setOkOnClickListener("设定时间", new InformTimeDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                InformSystemTimeTitle.setText("每天"+mInformTimeDialog.getOne()+":"+mInformTimeDialog.getTwo());
                mInformTimeDialog.dismiss();
            }
        });

        mInformTimeDialog.setCancelOnClickListener("取消", new InformTimeDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mInformTimeDialog.dismiss();
            }
        });

        InformSystemTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInformTimeDialog.show();
            }
        });

        InformWeiXinTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInformTimeDialog.show();
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

        DefaultTitle.setText(getResources().getString(R.string.inform_title));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}








