package com.tramp.word.module.group;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/23.
 */

public class GroupMedalDetailsActivity extends RxBaseActivity{
    @BindView(R.id.group_medal_out)
    ImageView mGroupMedalOut;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_medal_details;
    }

    @Override
    protected void initToolBar() {
        mGroupMedalOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
