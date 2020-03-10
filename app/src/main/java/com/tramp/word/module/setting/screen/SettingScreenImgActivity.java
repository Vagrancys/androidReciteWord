package com.tramp.word.module.setting.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

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
    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_img;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
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
                Intent intent=new Intent();
                intent.putExtra("boolean",false);
                setResult(13,intent);
                finish();
            }
        });

        ScreenSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("boolean",true);
                setResult(13,intent);
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
