package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

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
    private int status=0;
    private int ENGLISH_RESULT=21;
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
        status= PreferencesUtils.getInt(ConstantUtils.SETTING_ENGLISH_CODE,0);
        if(status==0){
            EnglishOneImg.setVisibility(View.VISIBLE);
        }else if(status==1){
            EnglishTwoImg.setVisibility(View.VISIBLE);
        }else if(status==2){
            EnglishThreeImg.setVisibility(View.VISIBLE);
        }
        EnglishOneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishOneImg.setVisibility(View.VISIBLE);
                status=0;
                EnglishTwoImg.setVisibility(View.GONE);
                EnglishThreeImg.setVisibility(View.GONE);
                PreferencesUtils.putInt(ConstantUtils.SETTING_ENGLISH_CODE,0);
                initResult();
            }
        });
        EnglishTwoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishTwoImg.setVisibility(View.VISIBLE);
                status=1;
                EnglishOneImg.setVisibility(View.GONE);
                EnglishThreeImg.setVisibility(View.GONE);
                PreferencesUtils.putInt(ConstantUtils.SETTING_ENGLISH_CODE,1);
                initResult();
            }
        });
        EnglishThreeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnglishThreeImg.setVisibility(View.VISIBLE);
                status=2;
                EnglishOneImg.setVisibility(View.GONE);
                EnglishTwoImg.setVisibility(View.GONE);
                PreferencesUtils.putInt(ConstantUtils.SETTING_ENGLISH_CODE,2);
                initResult();
            }
        });
    }

    private void initResult(){
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.SETTING_ENGLISH_CODE,status);
        setResult(ENGLISH_RESULT,intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}











