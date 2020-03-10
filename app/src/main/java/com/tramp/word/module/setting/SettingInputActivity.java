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

public class SettingInputActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.input_one_relative)
    RelativeLayout InputOneRelative;
    @BindView(R.id.input_one_img)
    ImageView InputOneImg;
    @BindView(R.id.input_two_relative)
    RelativeLayout InputTwoRelative;
    @BindView(R.id.input_two_img)
    ImageView InputTwoImg;

    private int status;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_input;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.input_title));
    }

    @Override
    public void initView(Bundle save) {
        InputOneImg.setVisibility(View.GONE);
        InputTwoImg.setVisibility(View.GONE);
        if(status==1){
            InputOneImg.setVisibility(View.VISIBLE);
        }else if(status==2) {
            InputTwoImg.setVisibility(View.VISIBLE);
        }
        InputOneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputOneImg.setVisibility(View.VISIBLE);
                status=1;
                InputTwoImg.setVisibility(View.GONE);
                Intent intent=new Intent();
                intent.putExtra("input",status);
                setResult(17,intent);
                finish();
            }
        });
        InputTwoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputTwoImg.setVisibility(View.VISIBLE);
                status=2;
                InputOneImg.setVisibility(View.GONE);
                Intent intent=new Intent();
                intent.putExtra("input",status);
                setResult(17,intent);
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