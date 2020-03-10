package com.tramp.word.module.home.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.UserBadgeAdapter;
import com.tramp.word.adapter.UserTimeAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.group.GroupDetailsActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/21.
 */

public class FriendDetailsActivity extends RxBaseActivity {
    @BindView(R.id.user_out)
    ImageView mUserOut;
    @BindView(R.id.user_friend_status)
    TextView mUserFriendStatus;
    @BindView(R.id.user_pk_start)
    ImageView mUserPkStart;
    @BindView(R.id.user_group_img)
    ImageView mUserGroupImg;
    @BindView(R.id.user_group_recycler)
    RecyclerView mUserGroupRecycler;
    @BindView(R.id.user_time_pager)
    ViewPager mUserTimePager;
    @BindView(R.id.user_time_sliding)
    SlidingTabLayout mUserTimeSliding;
    private UserTimeAdapter mUserTimeAdapter;
    private UserBadgeAdapter mUserBadgeAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_details;
    }

    @Override
    protected void initToolBar() {
        mUserOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mUserBadgeAdapter = new UserBadgeAdapter(mUserGroupRecycler);
        mUserFriendStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserFriendStatus.setText(getResources().getString(R.string.user_friend_exit));
                mUserFriendStatus.setEnabled(false);
            }
        });

        mUserPkStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUserGroupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendDetailsActivity.this, GroupDetailsActivity.class));
                overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_stay);
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        mUserGroupRecycler.setLayoutManager(layoutManager);
        mUserGroupRecycler.setAdapter(mUserBadgeAdapter);

        mUserTimeAdapter=new UserTimeAdapter(getSupportFragmentManager(),getBaseContext());
        mUserTimePager.setOffscreenPageLimit(2);
        mUserTimePager.setAdapter(mUserTimeAdapter);
        mUserTimeSliding.setViewPager(mUserTimePager);
        mUserTimeSliding.setCurrentTab(0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
