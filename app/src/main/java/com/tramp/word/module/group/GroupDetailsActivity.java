package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.GroupDetailsTagAdapter;
import com.tramp.word.adapter.UserBadgeAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupDetailsInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.GroupAddDialog;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/21.
 */

public class GroupDetailsActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.details_medal_number)
    TextView DetailsMedalNumber;
    @BindView(R.id.details_medal)
    RelativeLayout DetailsMedal;
    @BindView(R.id.details_medal_recycler)
    RecyclerView DetailsMedalRecycler;
    @BindView(R.id.admin_linear)
    LinearLayout AdminLinear;
    @BindView(R.id.group_details_admin)
    TextView GroupDetailsAdmin;
    @BindView(R.id.details_tag_recycler)
    RecyclerView DetailsTagRecycler;
    @BindView(R.id.group_details_add)
    TextView GroupDetailsAdd;
    @BindView(R.id.group_details_img)
    ImageView GroupDetailsImg;
    @BindView(R.id.group_details_title)
    TextView GroupDetailsTitle;
    @BindView(R.id.group_details_level)
    ImageView GroupDetailsLevel;
    @BindView(R.id.group_details_number)
    TextView GroupDetailsNumber;
    @BindView(R.id.details_star_number)
    TextView DetailsStarNumber;
    @BindView(R.id.details_time)
    TextView DetailsTime;
    @BindView(R.id.details_summary)
    TextView DetailsSummary;
    private UserBadgeAdapter mUserBadgeAdapter;
    private GroupDetailsTagAdapter mDetailsTagAdapter;
    private int group_id;
    private GroupDetailsInfo.Details details;
    private GroupAddDialog mGroupAddDialog;
    private Animation mScaleAnim;
    private int user_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_details;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setVisibility(View.GONE);
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        loadData();
        initClick();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupDetailsInfo(group_id,user_id)
                .enqueue(new Callback<GroupDetailsInfo>() {
                    @Override
                    public void onResponse(Call<GroupDetailsInfo> call, Response<GroupDetailsInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            details=response.body().getDetails();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupDetailsInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }
    public void finishTask(){
        Glide.with(getBaseContext())
                .load(details.getGroup_avatar())
                .placeholder(R.drawable.user_avater)
                .into(GroupDetailsImg);
        GroupDetailsTitle.setText(details.getGroup_name());
        GroupDetailsLevel.setImageResource(Utils.getGroupLevelImg(details.getGroup_level()));
        GroupDetailsNumber.setText("组员: "+details.getGroup_member()+"/"+details.getGroup_all_member()+"人");
        GroupDetailsAdmin.setText(details.getGroup_admin());
        DetailsStarNumber.setText(details.getGroup_star());
        DetailsMedalNumber.setText("共"+details.getMedals().size()+"枚");
        DetailsTime.setText(details.getGroup_time());
        DetailsSummary.setText(details.getGroup_summary());

        mUserBadgeAdapter=new UserBadgeAdapter(DetailsMedalRecycler,details.getMedals());
        DetailsMedalRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        DetailsMedalRecycler.setAdapter(mUserBadgeAdapter);

        mDetailsTagAdapter=new GroupDetailsTagAdapter(DetailsTagRecycler,details.getTags());
        DetailsTagRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        DetailsTagRecycler.setAdapter(mDetailsTagAdapter);
        switch (details.getGroup_status()){
            case 0:
                GroupDetailsAdd.setText(getResources().getString(R.string.group_details_text0));
                break;
            case 1:
                GroupDetailsAdd.setText(getResources().getString(R.string.group_details_text1));
                break;
            case 2:
                GroupDetailsAdd.setVisibility(View.GONE);
                break;
        }
    }

    public void initClick(){
        DetailsMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMedalActivity.launch(GroupDetailsActivity.this,details.getGroup_id(),0);
            }
        });

        AdminLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(GroupDetailsActivity.this,details.getGroup_admin_id());
            }
        });

        mGroupAddDialog=new GroupAddDialog(this);
        mGroupAddDialog.setCancelOnClickListener("取消", new GroupAddDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mGroupAddDialog.dismiss();
            }
        });
        mGroupAddDialog.setOkOnClickListener("确定", new GroupAddDialog.OkOnClickListener() {
            @Override
            public void onOkClick(String text) {
                Retrofits.getGroupAPI()
                        .getAddTextInfo(group_id,mGroupAddDialog.getEditText(),user_id)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body() !=null&&response.body().getCode()==200){
                                    Utils.ShowToast(getBaseContext(),"等待审核中!");
                                    mGroupAddDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });

            }
        });
        GroupDetailsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDetailsAdd.startAnimation(mScaleAnim);
                if(details.getGroup_status()==0){
                    Retrofits.getGroupAPI()
                            .getGroupAddInfo(group_id,user_id)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body() !=null&&response.body().getCode()==200){
                                        startActivity(new Intent(GroupDetailsActivity.this, GroupMainActivity.class));
                                        Utils.StarActivityInAnim(GroupDetailsActivity.this);
                                    }else if(response.body().getCode()==201){
                                        mGroupAddDialog.setMessage("申请加入"+details.getGroup_name()+"小组");
                                        mGroupAddDialog.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                }
                            });
                }else if(details.getGroup_status()==1){
                    startActivity(new Intent(GroupDetailsActivity.this,GroupMainActivity.class));
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int group_id){
        Intent intent=new Intent(activity,GroupDetailsActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
