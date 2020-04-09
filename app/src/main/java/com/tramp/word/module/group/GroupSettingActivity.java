package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupSettingInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class GroupSettingActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_setting_text3)
    TextView GroupSettingText3;
    @BindView(R.id.group_setting_text1)
    TextView GroupSettingText1;
    @BindView(R.id.group_setting_check1)
    CheckBox GroupSettingCheck1;
    @BindView(R.id.group_setting_check2)
    CheckBox GroupSettingCheck2;
    private int group_id;
    private int group_search=0;
    private int group_add=0;
    private GroupSettingInfo.Setting Setting;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_setting;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getGroupAPI()
                        .getSettingAddInfo(group_id,group_search,group_add)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    Utils.ShowToast(getBaseContext(),"修改成功！");
                                }
                                finish();
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),"网络出错!");
                                finish();
                            }
                        });
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.group_setting_title));
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        initNet();
        initData();
    }

    public void initNet(){
        Retrofits.getGroupAPI()
                .getGroupSettingInfo(group_id)
                .enqueue(new Callback<GroupSettingInfo>() {
                    @Override
                    public void onResponse(Call<GroupSettingInfo> call, Response<GroupSettingInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Setting=response.body().getSetting();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupSettingInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络失效!");
                    }
                });
    }

    public void finishTask(){
        if(Setting.getGroup_search()==1){
            GroupSettingCheck1.setChecked(true);
            GroupSettingText1.setText(getResources().getString(R.string.group_setting_text4));
        }else{
            GroupSettingCheck1.setChecked(false);
            GroupSettingText1.setText(getResources().getString(R.string.group_setting_text1));
        }

        if(Setting.getGroup_add()==1){
            GroupSettingCheck2.setChecked(true);
            GroupSettingText3.setText(getResources().getString(R.string.group_setting_text5));
        }else{
            GroupSettingCheck2.setChecked(false);
            GroupSettingText3.setText(getResources().getString(R.string.group_setting_text3));
        }
    }

    public void initData(){
        GroupSettingCheck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GroupSettingText1.setText(getResources().getString(R.string.group_setting_text4));
                    group_search=1;
                }else{
                    GroupSettingText1.setText(getResources().getString(R.string.group_setting_text1));
                    group_search=0;
                }
            }
        });
        GroupSettingCheck2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GroupSettingText3.setText(getResources().getString(R.string.group_setting_text5));
                    group_add=1;
                }else{
                    GroupSettingText3.setText(getResources().getString(R.string.group_setting_text3));
                    group_add=0;
                }
            }
        });
    }

    public static void launch(Activity activity, int group_id){
        Intent intent=new Intent(activity,GroupSettingActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
