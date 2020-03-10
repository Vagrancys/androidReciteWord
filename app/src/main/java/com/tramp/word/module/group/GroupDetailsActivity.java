package com.tramp.word.module.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupDetailsTagAdapter;
import com.tramp.word.adapter.UserBadgeAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.home.me.FriendDetailsActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/21.
 */

public class GroupDetailsActivity extends RxBaseActivity {
    @BindView(R.id.group_details_out)
    ImageView mGroupDetailsOut;
    @BindView(R.id.details_scroll_two)
    TextView mDetailsScrollTwo;
    @BindView(R.id.details_scroll_two_recycler)
    RecyclerView mDetailsScrollTwoRecycler;
    @BindView(R.id.group_details_back)
    ImageView mGroupDetailsBack;
    @BindView(R.id.details_scroll_three_recycler)
    RecyclerView mDetailsScrollThreeRecycler;
    @BindView(R.id.group_details_add)
    TextView mGroupDetailsAdd;
    private UserBadgeAdapter mUserBadgeAdapter;
    private GroupDetailsTagAdapter mGroupDetailsTagAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_details;
    }

    @Override
    protected void initToolBar() {
        mGroupDetailsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mDetailsScrollTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupDetailsActivity.this,GroupMedalActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mUserBadgeAdapter=new UserBadgeAdapter(mDetailsScrollTwoRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false);
        mDetailsScrollTwoRecycler.setLayoutManager(linearLayoutManager);
        mDetailsScrollTwoRecycler.setAdapter(mUserBadgeAdapter);

        mGroupDetailsTagAdapter=new GroupDetailsTagAdapter(mDetailsScrollThreeRecycler);
        mDetailsScrollThreeRecycler.setLayoutManager(linearLayoutManager);
        mDetailsScrollThreeRecycler.setAdapter(mGroupDetailsTagAdapter);

        mGroupDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupDetailsActivity.this, FriendDetailsActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mGroupDetailsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupDetailsActivity.this,GroupContentActivity.class));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
