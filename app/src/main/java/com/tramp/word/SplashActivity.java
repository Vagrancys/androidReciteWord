package com.tramp.word;

import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.AdvertiseActivity;
import com.tramp.word.module.common.MainActivity;
import com.tramp.word.module.common.UserDecideActivity;
import com.tramp.word.utils.BitmapUtils;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/7.
 */

public class SplashActivity extends RxBaseActivity {
    @BindView(R.id.splash_time)
    TextView mSplashTime;
    @BindView(R.id.splash_advertise)
    ImageView mSplashAdvertise;
    @BindView(R.id.view_splash)
    RelativeLayout mRelative;
    public CountDownTimer mTimer;
    private static Boolean Login;
    private Bitmap mSplashImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle save) {
        mSplashImage= BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.splash_img);
        mSplashAdvertise.setImageBitmap(mSplashImage);
        Login=PreferencesUtils.getBoolean(ConstantUtils.LOGIN_STATIC,false);
        mTimer=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long time=millisUntilFinished/1000;
                mSplashTime.setText("跳过 "+time);
            }

            @Override
            public void onFinish() {
                if(!Login){
                    startActivity(new Intent(SplashActivity.this,UserDecideActivity.class));
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
                finish();
            }
        };
        mSplashAdvertise.setBackgroundResource(R.drawable.splash_img);
        mSplashAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this,AdvertiseActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
                mTimer.cancel();
            }
        });
        mSplashTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Login){
                    startActivity(new Intent(SplashActivity.this, UserDecideActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
                mTimer.cancel();
            }
        });
        mTimer.start();
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashImage.recycle();
    }
}















