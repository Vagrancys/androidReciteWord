package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.GroupMedalViewSection;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/21.
 */

public class GroupMedalActivity extends RxBaseActivity {
    @BindView(R.id.group_medal_out)
    ImageView mGroupMedalOut;
    @BindView(R.id.group_medal_recycler)
    RecyclerView mGroupMedalRecycler;
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_medal;
    }

    @Override
    public void initView(Bundle save) {
        mSectionedRecyclerViewAdapter=new SectionedRecyclerViewAdapter();
        for (int i=0;i<3;i++){
            mSectionedRecyclerViewAdapter.addSection(new GroupMedalViewSection(getBaseContext(),this));
        }
        mGroupMedalRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        mGroupMedalRecycler.setAdapter(mSectionedRecyclerViewAdapter);
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}







