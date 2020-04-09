package com.tramp.word.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/3.
 */

public class UserDecideActivity extends RxBaseActivity {
    @BindView(R.id.decide_register)
    TextView DecideRegister;
    @BindView(R.id.decide_login)
    TextView DecideLogin;
    private Animation mScaleAnim;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_decide;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        DecideRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecideRegister.startAnimation(mScaleAnim);
                startActivity(new Intent(UserDecideActivity.this,RegisterActivity.class));
            }
        });

        DecideLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecideLogin.startAnimation(mScaleAnim);
                startActivity(new Intent(UserDecideActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScaleAnim.cancel();
    }
}













