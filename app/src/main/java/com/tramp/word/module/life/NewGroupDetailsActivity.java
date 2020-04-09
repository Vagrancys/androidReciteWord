package com.tramp.word.module.life;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.NewGroupViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/14
 * version:1.0
 */

public class NewGroupDetailsActivity  extends RxBaseActivity{
    @BindView(R.id.new_group_out)
    ImageView NewGroupOut;
    @BindView(R.id.new_group_start_img)
    ImageView NewGroupStartImg;
    @BindView(R.id.new_group_down_img)
    ImageView NewGroupDownImg;
    @BindView(R.id.new_group_up_img)
    ImageView NewGroupUpImg;
    @BindView(R.id.new_group_recycler)
    RecyclerView NewGroupRecycler;
    @BindView(R.id.new_group_select)
    TextView NewGroupSelect;
    @BindView(R.id.new_group_relative)
    RelativeLayout NewGroupRelative;
    private Animator mDownAnim;
    private Animator mUpAnim;
    private Animation mScaleAnim;
    private final int Number=1;
    private NewGroupViewAdapter mNewGroupViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_group;
    }

    @Override
    protected void initToolBar() {
        NewGroupOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mDownAnim= ObjectAnimator.ofFloat(NewGroupRelative,"translationY",-100,0);
        mDownAnim.setDuration(200);
        mUpAnim=ObjectAnimator.ofFloat(NewGroupRelative,"translationY",0,100);
        mUpAnim.setDuration(200);
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        NewGroupStartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewGroupStartImg.startAnimation(mScaleAnim);
                startActivity(new Intent(NewGroupDetailsActivity.this,NewRandomAnimActivity.class));
                Utils.StarActivityInAnim(NewGroupDetailsActivity.this);
                finish();
            }
        });
        NewGroupDownImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Number>=10){
                    NewGroupDownImg.setImageResource(R.drawable.btn_test_down_disable);
                    NewGroupDownImg.setEnabled(false);
                }else{
                    mDownAnim.start();
                }
            }
        });
        NewGroupUpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Number<=1){
                    NewGroupUpImg.setImageResource(R.drawable.btn_test_up_disable);
                    NewGroupUpImg.setEnabled(false);
                }else{
                    mUpAnim.start();
                }
            }
        });

        mNewGroupViewAdapter=new NewGroupViewAdapter(NewGroupRecycler);
        NewGroupRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        NewGroupRecycler.setAdapter(mNewGroupViewAdapter);
    }
}
