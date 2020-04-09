package com.tramp.word.module.setting.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/4.
 */

public class SettingScreenImgActivity extends RxBaseActivity{
    @BindView(R.id.screen_relative)
    RelativeLayout ScreenRelative;
    @BindView(R.id.screen_click)
    TextView ScreenClick;
    @BindView(R.id.screen_bottom_linear)
    LinearLayout ScreenBottomLinear;
    @BindView(R.id.screen_close)
    TextView ScreenClose;
    @BindView(R.id.screen_setting)
    TextView ScreenSetting;
    @BindView(R.id.photo_img)
    ImageView PhotoImg;
    private String photo_url;
    private int photo_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_img;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            photo_url=intent.getStringExtra("photo_url");
            photo_id=intent.getIntExtra("photo_id",0);
        }
        Glide.with(getBaseContext())
                .load(photo_url)
                .placeholder(R.drawable.user_avater)
                .into(PhotoImg);
        ScreenRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ScreenClick.getVisibility()==View.VISIBLE){
                    ScreenClick.setVisibility(View.GONE);
                    ScreenBottomLinear.setVisibility(View.GONE);
                }else{
                    ScreenClick.setVisibility(View.VISIBLE);
                    ScreenBottomLinear.setVisibility(View.VISIBLE);
                }
            }
        });

        ScreenClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initResult(false);
            }
        });

        ScreenSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initResult(true);
            }
        });
    }

    private void initResult(boolean status){
        Intent intent=new Intent();
        intent.putExtra("img_status",status);
        intent.putExtra("number",photo_id);
        setResult(41,intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,String photo_url,int request,int photo_id){
        Intent intent=new Intent(activity,SettingScreenImgActivity.class);
        intent.putExtra("photo_url",photo_url);
        intent.putExtra("photo_id",photo_id);
        activity.startActivityForResult(intent,request);
    }
}
