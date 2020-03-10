package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingEnglishActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.english_one_relative)
    RelativeLayout EnglishOneRelative;
    @BindView(R.id.english_one_img)
    ImageView EnglishOneImg;
    @BindView(R.id.english_two_relative)
    RelativeLayout EnglishTwoRelative;
    @BindView(R.id.english_two_img)
    ImageView EnglishTwoImg;
    @BindView(R.id.english_three_relative)
    RelativeLayout EnglishThreeRelative;
    @BindView(R.id.english_three_img)
    ImageView EnglishThreeImg;
    private int status=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_english;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.english_title));
    }

    @Override
    public void initView(Bundle save) {
        EnglishOneImg.setVisibility(View.GONE);
        EnglishTwoImg.setVisibility(View.GONE);
        EnglishThreeImg.setVisibility(View.GONE);
        if(status==1){
            EnglishOneImg.setVisibility(View.VISIBLE);
        }else if(status==2){
            EnglishTwoImg.setVisibility(View.VISIBLE);
        }else if(status==3){
            EnglishThreeImg.setVisibility(View.VISIBLE);
        }
        EnglishOneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishOneImg.setVisibility(View.VISIBLE);
                status=1;
                EnglishTwoImg.setVisibility(View.GONE);
                EnglishThreeImg.setVisibility(View.GONE);
                Intent intent=new Intent();
                intent.putExtra("english",status);
                setResult(15,intent);
                finish();
            }
        });
        EnglishTwoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishTwoImg.setVisibility(View.VISIBLE);
                status=2;
                EnglishOneImg.setVisibility(View.GONE);
                EnglishThreeImg.setVisibility(View.GONE);
                Intent intent=new Intent();
                intent.putExtra("english",status);
                setResult(15,intent);
                finish();
            }
        });
        EnglishThreeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishThreeImg.setVisibility(View.VISIBLE);
                status=3;
                EnglishOneImg.setVisibility(View.GONE);
                EnglishTwoImg.setVisibility(View.GONE);
                Intent intent=new Intent();
                intent.putExtra("english",status);
                setResult(15,intent);
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











