package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import java.io.File;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingDownActivity  extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.down_one_relative)
    RelativeLayout DownOneRelative;
    @BindView(R.id.down_one_img)
    ImageView DownOneImg;
    @BindView(R.id.down_two_relative)
    RelativeLayout DownTwoRelative;
    @BindView(R.id.down_two_img)
    ImageView DownTwoImg;
    @BindView(R.id.down_three_title)
    TextView DownThreeTitle;

    @BindView(R.id.down_one_left_number)
    TextView DownOneLeftNumber;
    @BindView(R.id.down_one_right_number)
    TextView DownOneRightNumber;
    @BindView(R.id.down_two_left_number)
    TextView DownTwoLeftNumber;
    @BindView(R.id.down_two_right_number)
    TextView DownTwoRightNumber;

    private String path="/storage/";
    private File mPath;
    private int status;
    private int DOWN_RESULT=23;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_down;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.down_title));
    }

    @Override
    public void initView(Bundle save) {
        initData();
        DownOneImg.setVisibility(View.GONE);
        DownTwoImg.setVisibility(View.GONE);
        if(PreferencesUtils.getInt(ConstantUtils.SETTING_DOWN_CODE,0)==0){
            DownOneImg.setVisibility(View.VISIBLE);
        }else{
            DownTwoImg.setVisibility(View.VISIBLE);
        }
        DownOneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownOneImg.setVisibility(View.VISIBLE);
                status=0;
                DownTwoImg.setVisibility(View.GONE);
                mPath=Environment.getExternalStorageDirectory();
                DownThreeTitle.setText(path+mPath.getPath());
                PreferencesUtils.putInt(ConstantUtils.SETTING_DOWN_CODE,0);
                initResult();
            }
        });

        DownTwoRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownTwoImg.setVisibility(View.VISIBLE);
                status=1;
                DownOneImg.setVisibility(View.GONE);
                mPath=Environment.getDataDirectory();
                DownThreeTitle.setText(path+mPath.getPath());
                PreferencesUtils.putInt(ConstantUtils.SETTING_DOWN_CODE,1);
                initResult();
            }
        });
    }
    private void initResult(){
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.SETTING_DOWN_CODE,status);
        setResult(DOWN_RESULT,intent);
        finish();
    }

    private void initData(){
        DownOneLeftNumber.setText(Utils.getAvailableSize(getBaseContext()));
        DownOneRightNumber.setText(Utils.getTotalSize(getBaseContext()));
        DownTwoLeftNumber.setText(Utils.getRowAvailableSize(getBaseContext()));
        DownTwoRightNumber.setText(Utils.getRowTotalSize(getBaseContext()));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

}




