package com.tramp.word.module.forget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/4.
 * 
 */

public class ForgetPassActivity extends RxBaseActivity {
    @BindView(R.id.app_toolbar)
    AppBarLayout AppToolbar;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.forget_short)
    LinearLayout ForgetShort;
    @BindView(R.id.forget_email)
    LinearLayout ForgetEmail;
    @BindView(R.id.forget_weixin)
    LinearLayout ForgetWeiXin;
    @BindView(R.id.forget_qq)
    LinearLayout ForgetQq;
    @BindView(R.id.forget_weibo)
    LinearLayout ForgetWeiBo;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initView(Bundle save) {

        ForgetShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetShortActivity.launch(ForgetPassActivity.this);
            }
        });

        ForgetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetEmailActivity.launch(ForgetPassActivity.this);
            }
        });

        ForgetWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"微信");
            }
        });

        ForgetQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"qq");
            }
        });

        ForgetWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"微博");
            }
        });
    }

    public void Animation(){
        //rotation translationX scaleY
        ObjectAnimator moveIn= ObjectAnimator.ofFloat(ForgetQq,"translationX",-500f,0f);
        ObjectAnimator rotate=ObjectAnimator.ofFloat(ForgetQq,"rotation",0f,360f);
        ObjectAnimator fadeInOut=ObjectAnimator.ofFloat(ForgetQq,"alpha",1f,0f,1f);
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
        animator.setTarget(ForgetQq);
        animator.start();
    }

    @Override
    protected void initToolBar() {
        AppToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        DefaultOut.setImageResource(R.drawable.common_back_normal);
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.forget_title_text));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,ForgetPassActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}











