package com.tramp.word.module.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.FriendBadgeAdapter;
import com.tramp.word.adapter.UserTimeAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.friend.FriendMainInfo;
import com.tramp.word.module.group.GroupDetailsActivity;
import com.tramp.word.module.pk.PkSearchActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/21.
 */

public class FriendDetailsActivity extends RxBaseActivity {
    @BindView(R.id.friend_out)
    ImageView FriendOut;
    @BindView(R.id.friend_avatar)
    ImageView FriendAvatar;
    @BindView(R.id.friend_status)
    TextView FriendStatus;
    @BindView(R.id.friend_name)
    TextView FriendName;
    @BindView(R.id.friend_sign_linear)
    LinearLayout FriendSignLinear;
    @BindView(R.id.friend_sign)
    TextView FriendSign;
    @BindView(R.id.friend_pk)
    RelativeLayout FriendPk;
    @BindView(R.id.friend_pk_img)
    ImageView FriendPkImg;
    @BindView(R.id.friend_pk_name)
    TextView FriendPkName;
    @BindView(R.id.friend_pk_start)
    ImageView FriendPkStart;
    @BindView(R.id.friend_group)
    RelativeLayout FriendGroup;
    @BindView(R.id.friend_group_img)
    ImageView FriendGroupImg;
    @BindView(R.id.friend_group_title)
    TextView FriendGroupTitle;
    @BindView(R.id.friend_group_level)
    ImageView FriendGroupLevel;
    @BindView(R.id.friend_group_recycler)
    RecyclerView FriendGroupRecycler;
    @BindView(R.id.friend_group_badge)
    TextView FriendGroupBadge;
    @BindView(R.id.friend_time_pager)
    ViewPager FriendTimePager;
    @BindView(R.id.friend_time_sliding)
    SlidingTabLayout FriendTimeSliding;
    @BindView(R.id.friend_pk_empty)
    RelativeLayout FriendPkEmpty;
    private UserTimeAdapter mUserTimeAdapter;
    private FriendBadgeAdapter mFriendBadgeAdapter;
    private FriendMainInfo.Friend Friends;
    private int friend_id;
    private int user_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_details;
    }

    @Override
    protected void initToolBar() {
        FriendOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            friend_id=intent.getIntExtra(ConstantUtils.FRIEND_ID,0);
        }
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        loadData();
        initData();
        intClick();
    }
    private void initData(){
        mUserTimeAdapter=new UserTimeAdapter(getSupportFragmentManager(),getBaseContext(),friend_id);
        FriendTimePager.setOffscreenPageLimit(2);
        FriendTimePager.setAdapter(mUserTimeAdapter);
        FriendTimeSliding.setViewPager(FriendTimePager);
        FriendTimeSliding.setCurrentTab(0);
    }

    private void intClick(){
        FriendStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getFriendAPI()
                        .getFriendAddInfo(user_id,friend_id)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    FriendStatus.setText(getResources().getString(R.string.friend_status_two));
                                    FriendStatus.setClickable(false);
                                }else{
                                    Utils.ShowToast(getBaseContext(),"添加好友失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),"网络出错!");
                            }
                        });

            }
        });

        FriendPkStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Friends.getWord().getWord_pk()==1){
                    startActivity(new Intent(FriendDetailsActivity.this,PkSearchActivity.class));
                    Utils.StarActivityInAnim(FriendDetailsActivity.this);
                }
            }
        });

        FriendGroupRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDetailsActivity.launch(FriendDetailsActivity.this,Friends.getGroup().getGroup_id());
            }
        });
    }

    private void loadData(){
        Retrofits.getFriendAPI().getFriendMainInfo(user_id,friend_id)
                .enqueue(new Callback<FriendMainInfo>() {
                    @Override
                    public void onResponse(Call<FriendMainInfo> call, Response<FriendMainInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Friends=response.body().getFriends();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据加载失败!");
                        }
                    }

                    @Override
                    public void onFailure(Call<FriendMainInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    private void finishTask(){
        Glide.with(getBaseContext())
            .load(Friends.getFriend_avatar())
            .placeholder(R.drawable.user_avater)
            .into(FriendAvatar);
        switch (Friends.getFriend_status()){
            case 1:
                FriendStatus.setText(getString(R.string.friend_status_one));
                FriendStatus.setVisibility(View.VISIBLE);
                break;
            case 2:
                FriendStatus.setText(getString(R.string.friend_status_two));
                FriendStatus.setClickable(false);
                FriendStatus.setVisibility(View.VISIBLE);
                break;
            case 3:
                FriendStatus.setText(getString(R.string.friend_status_three));
                FriendStatus.setClickable(false);
                break;
        }
        FriendName.setText(Friends.getFriend_name());
        if(Friends.getFriend_sign().length()<=0){
            FriendSignLinear.setVisibility(View.GONE);
        }else{
            FriendSignLinear.setVisibility(View.VISIBLE);
            FriendSign.setText(Friends.getFriend_sign());
        }
        if(Friends.getWord_status()==1){
            FriendPk.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .load(Friends.getWord().getWord_avatar())
                    .placeholder(R.drawable.user_avater)
                    .into(FriendPkImg);
            FriendPkName.setText(Friends.getWord().getWord_name());
            switch (Friends.getWord().getWord_pk()){
                case 0:
                    FriendPkStart.setImageResource(R.drawable.btn_pk_3);
                    break;
                case 1:
                    FriendPkStart.setImageResource(R.drawable.btn_pk_2);
                    break;
            }
        }else{
            FriendPk.setVisibility(View.GONE);
        }

        if(Friends.getGroup_status()==1){
            FriendGroup.setVisibility(View.VISIBLE);
            Glide.with(getBaseContext())
                    .load(Friends.getGroup().getGroup_name())
                    .placeholder(R.drawable.user_avater)
                    .into(FriendGroupImg);
            FriendGroupTitle.setText(Friends.getGroup().getGroup_name());
            FriendGroupLevel.setImageResource(Utils.getGroupLevelImg(Friends.getGroup().getGroup_level()));
            if(Friends.getGroup().getMedals()!=null&&Friends.getGroup().getMedals().size()>0){
                FriendGroupBadge.setText("共"+Friends.getGroup().getMedals().size()+"枚");
            }else{
                FriendGroupBadge.setVisibility(View.GONE);
            }

            mFriendBadgeAdapter=new FriendBadgeAdapter(FriendGroupRecycler,Friends.getGroup().getMedals());
            FriendGroupRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
            FriendGroupRecycler.setAdapter(mFriendBadgeAdapter);
        }else{
            FriendGroup.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int friend_id){
        Intent intent=new Intent(activity,FriendDetailsActivity.class);
        intent.putExtra(ConstantUtils.FRIEND_ID,friend_id);
        activity.startActivity(intent);
    }
}
