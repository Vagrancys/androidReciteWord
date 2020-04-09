package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.GroupMemberAdapter;
import com.tramp.word.adapter.GroupMemberAvatarAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupMemberInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupMemberActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_exit)
    TextView DefaultExit;
    @BindView(R.id.default_manage)
    TextView DefaultManage;
    @BindView(R.id.default_done)
    TextView DefaultDone;
    @BindView(R.id.group_member_recycler)
    RecyclerView GroupMemberRecycler;
    @BindView(R.id.back)
    RelativeLayout Back;

    @BindView(R.id.group_member_exit)
    RelativeLayout GroupMemberExit;
    @BindView(R.id.member_exit_delete)
    TextView MemberExitDelete;
    @BindView(R.id.member_exit_cancel)
    TextView MemberExitCancel;
    @BindView(R.id.member_exit_avatar)
    ImageView MemberExitAvatar;
    @BindView(R.id.member_exit_title)
    TextView MemberExitTitle;
    @BindView(R.id.member_exit_warning)
    TextView MemberExitWarning;

    @BindView(R.id.group_member_out)
    RelativeLayout GroupMemberOut;
    @BindView(R.id.group_member_out_recycler)
    RecyclerView GroupMemberOutRecycler;
    @BindView(R.id.group_member_out_number)
    TextView GroupMemberOutNumber;
    @BindView(R.id.group_member_in)
    RelativeLayout GroupMemberIn;
    @BindView(R.id.group_member_in_recycler)
    RecyclerView GroupMemberInRecycler;
    @BindView(R.id.group_member_in_number)
    TextView GroupMemberInNumber;
    @BindView(R.id.item_list_size)
    TextView ItemListSize;


    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private GroupMemberInfo.Member member;
    private int group_id;
    private int manage_status;
    private PopupWindow mPop;
    private GroupMemberAdapter mGroupMember;
    private GroupMemberAvatarAdapter mOutAvatar;
    private GroupMemberAvatarAdapter mInAvatar;
    private int delete_status=0;
    private ArrayList<GroupMemberInfo.Member.list> Members=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
                finish();
            }
        });
        DefaultExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back.setVisibility(View.VISIBLE);
                GroupMemberExit.startAnimation(mTopEnterAnim);
            }
        });

        DefaultManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.showAsDropDown(DefaultManage,0,0);
            }
        });

        DefaultDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultDone.setVisibility(View.GONE);
                DefaultManage.setVisibility(View.VISIBLE);
                mGroupMember.cancelAll();
                mGroupMember.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
            manage_status=intent.getIntExtra(ConstantUtils.MANAGE_STATUS,0);
        }
        if(manage_status==1){
            DefaultExit.setVisibility(View.GONE);
            DefaultManage.setVisibility(View.VISIBLE);
        }else{
            DefaultManage.setVisibility(View.GONE);
            DefaultExit.setVisibility(View.VISIBLE);
        }
        lazyData();
    }
    private void lazyData(){
        initRefreshLayout();
        initRecyclerView();
    }
    private void initRefreshLayout(){
        loadData();
    }
    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupMemberInfo(group_id,manage_status,new UserSqlHelper(getBaseContext()).UserId())
                .enqueue(new Callback<GroupMemberInfo>() {
                    @Override
                    public void onResponse(Call<GroupMemberInfo> call, Response<GroupMemberInfo> response) {
                        if(response.body()!=null &&response.body().getCode()==200){
                            member=response.body().getMember();
                            Members.addAll(response.body().getMember().getLists());
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据失效了!");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupMemberInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void finishTask(){
        if(member.getOuts().size()<=0){
            GroupMemberOut.setVisibility(View.GONE);
        }
        if(member.getIns().size()<=0){
            GroupMemberIn.setVisibility(View.GONE);
        }
        mOutAvatar=new GroupMemberAvatarAdapter(GroupMemberOutRecycler,member.getOuts());
        mInAvatar=new GroupMemberAvatarAdapter(GroupMemberOutRecycler,member.getIns());
        GroupMemberOutRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupMemberInRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupMemberOutRecycler.setAdapter(mOutAvatar);
        GroupMemberInRecycler.setAdapter(mInAvatar);
        GroupMemberOutNumber.setText(member.getOut_number());
        GroupMemberInNumber.setText(member.getIn_number());
        Glide.with(getBaseContext())
                .load(member.getMember_img())
                .placeholder(R.drawable.user_avater)
                .into(MemberExitAvatar);
        if(manage_status==1){
            MemberExitDelete.setText(getResources().getString(R.string.member_dismiss_delete));
            MemberExitCancel.setText(getResources().getString(R.string.member_dismiss_cancel));
            MemberExitTitle.setText("确定要解散小组吗?");
            MemberExitWarning.setText("小组已成立"+member.getMember_day()+"天,累积获得"+member.getMember_star()+"颗词能量,解散后数据会被清除.");
        }else{
            MemberExitDelete.setText(getResources().getString(R.string.member_exit_delete));
            MemberExitCancel.setText(getResources().getString(R.string.member_exit_cancel));
            MemberExitTitle.setText("确定要退出"+member.getMember_name()+"吗?");
            MemberExitWarning.setText("您已经在小组"+member.getMember_day()+"天,累积贡献"+member.getMember_star()+"颗词能量,退出后数据会清空.");
        }
        ItemListSize.setText(String.valueOf(Members.size()));
        mGroupMember=new GroupMemberAdapter(GroupMemberRecycler,this,Members);
        GroupMemberRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupMemberRecycler.setAdapter(mGroupMember);
    }

    public void initRecyclerView(){
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);

        mPop=new PopupWindow(getBaseContext());
        View mView= LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_group_manage,null);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setContentView(mView);
        mPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_background_radius));
        mPop.setAnimationStyle(R.style.popup_recite_style_anim);
        mPop.setFocusable(true);

        TextView MemberSetting=(TextView) mView.findViewById(R.id.member_setting);
        MemberSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                GroupSettingActivity.launch(GroupMemberActivity.this,group_id);
            }
        });
        TextView MemberDelete=(TextView) mView.findViewById(R.id.member_delete);
        MemberDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultManage.setVisibility(View.GONE);
                DefaultDone.setVisibility(View.VISIBLE);
                mGroupMember.CheckAll();
                mGroupMember.notifyDataSetChanged();
                mPop.dismiss();
            }
        });
        TextView MemberExit=(TextView) mView.findViewById(R.id.member_exit);

        MemberExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                Back.setVisibility(View.VISIBLE);
                GroupMemberExit.setVisibility(View.VISIBLE);
                GroupMemberExit.startAnimation(mTopEnterAnim);
            }
        });



        MemberExitDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back.setVisibility(View.GONE);
                GroupMemberExit.startAnimation(mTopExitAnim);
                GroupMemberExit.setVisibility(View.GONE);
                if(manage_status==1){
                    Retrofits.getGroupAPI()
                            .getGroupDismissInfo(new UserSqlHelper(getBaseContext()).UserId(),group_id)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body()!=null&&response.body().getCode()==200){
                                        Utils.ShowToast(getBaseContext(),"解散小组成功!");
                                        startActivity(new Intent(GroupMemberActivity.this,GroupContentActivity.class));
                                        Utils.StarActivityInAnim(GroupMemberActivity.this);
                                        finish();
                                    }else{
                                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.intent_error_message));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                }
                            });
                }else{
                    Retrofits.getGroupAPI()
                            .getGroupOutInfo(new UserSqlHelper(getBaseContext()).UserId(),group_id)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body()!=null&&response.body().getCode()==200){
                                        Utils.ShowToast(getBaseContext(),"退出小组成功!");
                                        startActivity(new Intent(GroupMemberActivity.this,GroupContentActivity.class));
                                        Utils.StarActivityInAnim(GroupMemberActivity.this);
                                        finish();
                                    }else{
                                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.intent_error_message));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                }
                            });
                }
            }
        });

        MemberExitCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberExit.startAnimation(mTopExitAnim);
                GroupMemberExit.setVisibility(View.GONE);
                Back.setVisibility(View.GONE);
            }
        });

        GroupMemberOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberStatusActivity.launch(GroupMemberActivity.this,group_id,1);
            }
        });

        GroupMemberIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberStatusActivity.launch(GroupMemberActivity.this,group_id,2);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberExit.startAnimation(mTopExitAnim);
                GroupMemberExit.setVisibility(View.GONE);
                Back.setVisibility(View.GONE);
            }
        });
    }

    public static void launch(Activity activity,int group_id,int manage_status){
        Intent intent=new Intent(activity,GroupMemberActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        intent.putExtra(ConstantUtils.MANAGE_STATUS,manage_status);
        activity.startActivity(intent);
    }
}