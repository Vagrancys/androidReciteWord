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
import com.tramp.word.widget.InformTimeDialog;

import java.util.Date;

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
    CheckBox InformWeixinCheck;

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
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_inform;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=new Intent(this,AlarmInformReceiver.class);
        intent.setAction("NOTIFICATION");
        PendingIntent pi=PendingIntent.getBroadcast(this,0,intent,0);
        mAlarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);


        InformSystemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    InformSystemTime.setEnabled(true);
                    InformSystemTimeText.setTextColor(getResources().getColor(R.color.black));
                    InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.blue));
                    mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,new Date().getTime(),1000*60,pi);
                }else{
                    InformSystemTime.setEnabled(false);
                    InformSystemTimeText.setTextColor(getResources().getColor(R.color.black_2));
                    InformSystemTimeTitle.setTextColor(getResources().getColor(R.color.black_2));
                    mAlarmManager.cancel(pi);
                }
            }
        });
        InformPkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    InformPkText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    InformPkText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });
        InformFriendCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    InformFriendText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    InformFriendText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });

        InformGroupCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    InformGroupText.setTextColor(getResources().getColor(R.color.black));
                }else{
                    InformGroupText.setTextColor(getResources().getColor(R.color.black_2));
                }
            }
        });
        mInformTimeDialog=new InformTimeDialog(this);

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








