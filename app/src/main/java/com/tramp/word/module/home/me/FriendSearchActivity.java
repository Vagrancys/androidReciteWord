package com.tramp.word.module.home.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/21.
 */

public class FriendSearchActivity extends RxBaseActivity {
    @BindView(R.id.friend_search_out)
    ImageView mFriendSearchOut;
    @BindView(R.id.friend_search_edit)
    TextView mFriendSearchEdit;
    @BindView(R.id.friend_add_phone)
    ImageView mFriendAddPhone;
    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_search;
    }

    @Override
    public void initView(Bundle save) {
        mFriendSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FriendSearchActivity.this,SearchMemberActivity.class);
                int location[]=new int[2];
                mFriendSearchEdit.getLocationOnScreen(location);
                intent.putExtra("x",location[0]);
                intent.putExtra("y",location[1]);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        mFriendAddPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendSearchActivity.this,PhoneContactActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    protected void initToolBar() {
        mFriendSearchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
