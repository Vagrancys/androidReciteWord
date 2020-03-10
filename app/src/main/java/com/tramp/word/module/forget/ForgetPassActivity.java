package com.tramp.word.module.forget;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.forgetpoint.ForgetPoint;
import com.tramp.word.widget.forgetpoint.PointEvaluator;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/4.
 * 
 */

public class ForgetPassActivity extends RxBaseActivity {
    @BindView(R.id.forget_out)
    ImageView mForgetOut;
    @BindView(R.id.forget_short)
    LinearLayout mForgetShort;
    @BindView(R.id.forget_email)
    LinearLayout mForgetEmail;
    @BindView(R.id.forget_weixin)
    LinearLayout mForgetWeiXin;
    @BindView(R.id.forget_qq)
    LinearLayout mForgetQq;
    @BindView(R.id.forget_weibo)
    LinearLayout mForgetWeiBo;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initView(Bundle save) {

        mForgetShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassActivity.this,ForgetShortActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mForgetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassActivity.this,ForgetEmailActivity.class));
            }
        });

        mForgetWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"微信");
            }
        });

        mForgetQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"qq");
            }
        });

        mForgetWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"微博");
            }
        });
    }

    public void Animation(){
        //rotation translationX scaleY
        ObjectAnimator moveIn=ObjectAnimator.ofFloat(mForgetQq,"translationX",-500f,0f);
        ObjectAnimator rotate=ObjectAnimator.ofFloat(mForgetQq,"rotation",0f,360f);
        ObjectAnimator fadeInOut=ObjectAnimator.ofFloat(mForgetQq,"alpha",1f,0f,1f);
        AnimatorSet animSet=new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        Animator animator= AnimatorInflater.loadAnimator(getBaseContext(),R.animator.forget_title_anim);
        animator.setTarget(mForgetQq);
        animator.start();
        ForgetPoint point1=new ForgetPoint(0,0);
        ForgetPoint point2=new ForgetPoint(300,300);
        ValueAnimator anim=ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
        anim.setDuration(5000);
        anim.start();
    }

    @Override
    protected void initToolBar() {
        mForgetOut.setOnClickListener(new View.OnClickListener() {
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











