package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultGroupCreateInfo;
import com.tramp.word.entity.group.GroupCreateInfo;
import com.tramp.word.entity.user.UserMainInfo;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.BookSeekBar;
import com.tramp.word.widget.DefaultDialog;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/17
 * version:1.0
 */

public class GroupAddStarActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_add_star_linear)
    LinearLayout GroupAddStarLinear;
    @BindView(R.id.group_add_star_number)
    TextView GroupAddStarNumber;
    @BindView(R.id.group_add_star_seek)
    BookSeekBar GroupAddStarSeek;
    @BindView(R.id.group_setting_check1)
    CheckBox GroupSettingCheck1;
    @BindView(R.id.group_setting_check2)
    CheckBox GroupSettingCheck2;
    @BindView(R.id.group_add_next)
    LinearLayout GroupAddNext;
    @BindView(R.id.group_setting_text1)
    TextView GroupSettingText1;
    @BindView(R.id.group_setting_text3)
    TextView GroupSettingText3;
    private String group_name;
    private int group_avatar;
    private String group_tag;
    private String group_summary;
    private int group_link;
    private String group_links;
    private int group_star=5;
    private int group_re=0;
    private int group_as=0;
    private DefaultDialog mCreate;
    private DefaultGroupCreateInfo CreateInfo;
    private String user_money;
    private int group_admin;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_add_star;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_name=intent.getStringExtra("group_name");
            group_avatar=intent.getIntExtra("group_avatar",0);
            group_tag=intent.getStringExtra("group_tag");
            group_summary=intent.getStringExtra("group_summary");
            group_link=intent.getIntExtra("group_link",0);
            group_links=intent.getStringExtra("group_links");
        }
        initNet();
        initData();
    }
    public void initNet(){
        group_admin=new UserSqlHelper(getBaseContext()).UserId();
        Retrofits.getUserAPI().getUserMainInfo(group_admin)
                .enqueue(new Callback<UserMainInfo>() {
                    @Override
                    public void onResponse(Call<UserMainInfo> call, Response<UserMainInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            user_money=response.body().getMains().getUser_money();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserMainInfo> call, Throwable t) {

                    }
                });
    }
    public void initData(){
        GroupSettingCheck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GroupSettingText1.setText(getResources().getString(R.string.group_setting_text4));
                    group_re=1;
                }else{
                    GroupSettingText1.setText(getResources().getString(R.string.group_setting_text1));
                    group_re=0;
                }
            }
        });
        GroupSettingCheck2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    GroupSettingText3.setText(getResources().getString(R.string.group_setting_text5));
                    group_as=1;
                }else{
                    GroupSettingText3.setText(getResources().getString(R.string.group_setting_text3));
                    group_as=0;
                }
            }
        });
        mCreate=new DefaultDialog(this);
        mCreate.setTitle(getResources().getString(R.string.alert_create_title));
        mCreate.setMessage("创建小组将花费200泸元，你当前有"+user_money+"泸元。确定创建吗?");
        mCreate.setCancelOnClickListener("取消", new DefaultDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mCreate.dismiss();
            }
        });
        mCreate.setOkOnClickListener("确定", new DefaultDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                CreateInfo.setGroup_name(group_name);
                CreateInfo.setGroup_avatar(group_avatar);
                CreateInfo.setGroup_tag(group_tag);
                CreateInfo.setGroup_summary(group_summary);
                CreateInfo.setGroup_link(group_link);
                CreateInfo.setGroup_links(group_links);
                CreateInfo.setGroup_star(group_star);
                CreateInfo.setGroup_re(group_re);
                CreateInfo.setGroup_as(group_as);
                CreateInfo.setGroup_admin(group_admin);
                Retrofits.getGroupAPI()
                        .getGroupCreateInfo(CreateInfo)
                        .enqueue(new Callback<GroupCreateInfo>() {
                            @Override
                            public void onResponse(Call<GroupCreateInfo> call, Response<GroupCreateInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    GroupMainActivity.launch(GroupAddStarActivity.this,1,response.body().getGroup_id());
                                    finish();
                                }else{
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.message_error));
                                }
                            }

                            @Override
                            public void onFailure(Call<GroupCreateInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });
            }
        });
        GroupAddNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreate.show();
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
        DefaultTitle.setText(getResources().getString(R.string.group_add_title));
    }

    public static void launch(Activity activity,String group_name,int group_avatar,
                              String group_tag,String group_summary,int group_link,
                              String group_links){
        Intent intent=new Intent(activity,GroupAddStarActivity.class);
        intent.putExtra("group_name",group_name);
        intent.putExtra("group_avatar",group_avatar);
        intent.putExtra("group_tag",group_tag);
        intent.putExtra("group_summary",group_summary);
        intent.putExtra("group_link",group_link);
        intent.putExtra("group_links",group_links);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

}
