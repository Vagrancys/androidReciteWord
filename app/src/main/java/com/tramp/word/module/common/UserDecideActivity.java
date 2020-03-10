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
    @BindView(R.id.user_decide_register)
    TextView mUserDecideRegister;
    @BindView(R.id.user_decide_login)
    TextView mUserDecideLogin;
    private Animation mBtnAnim;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_decide;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        mBtnAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        mUserDecideRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDecideRegister.startAnimation(mBtnAnim);
                startActivity(new Intent(UserDecideActivity.this,RegisterActivity.class));
            }
        });

        mUserDecideLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDecideLogin.startAnimation(mBtnAnim);
                startActivity(new Intent(UserDecideActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBtnAnim.cancel();
    }
}
