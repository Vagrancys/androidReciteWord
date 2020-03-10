package com.tramp.word.module.revise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.anim.AudioAnimActivity;
import com.tramp.word.module.anim.FillAnimActivity;
import com.tramp.word.module.anim.PinYinAnimActivity;
import com.tramp.word.module.anim.SelectAnimActivity;
import com.tramp.word.module.common.WordBookActivity;
import com.tramp.word.module.help.HelpActivity;
import com.tramp.word.module.home.recite.ReciteWordManageActivity;
import com.tramp.word.utils.Utils;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/10.
 */

public class ReviseActivity extends RxBaseActivity {
    @BindView(R.id.revise_out)
    ImageView mReviseOut;
    @BindView(R.id.revise_help)
    ImageView mReviseHelp;
    @BindView(R.id.recite)
    TextView mRecite;
    @BindView(R.id.revise_help_one)
    ImageView mReviseHelpOne;
    @BindView(R.id.revise_start)
    TextView mReviseStart;

    @BindView(R.id.revise_word_linear)
    LinearLayout mReviseWordLinear;
    @BindView(R.id.revise_word_left)
    TextView mReviseWordLeft;
    @BindView(R.id.revise_word_img_left)
    ImageView mReviseWordImgLeft;

    @BindView(R.id.revise_class_linear)
    LinearLayout mReviseClassLinear;
    @BindView(R.id.revise_class_left)
    TextView mReviseClassLeft;
    @BindView(R.id.revise_class_img_left)
    ImageView mReviseClassImgLeft;

    @BindView(R.id.revise_relative_three)
    RelativeLayout mReviseRelativeThree;

    @BindView(R.id.revise_class_relative)
    RelativeLayout mReviseClassRelative;
    @BindView(R.id.class_linear_1)
    RelativeLayout mClassLinear1;
    @BindView(R.id.class_linear_1_img)
    ImageView mClassLinear1Img;
    @BindView(R.id.class_linear_2)
    RelativeLayout mClassLinear2;
    @BindView(R.id.class_linear_2_img)
    ImageView mClassLinear2Img;
    @BindView(R.id.class_linear_3)
    RelativeLayout mClassLinear3;
    @BindView(R.id.class_linear_3_img)
    ImageView mClassLinear3Img;
    @BindView(R.id.class_linear_4)
    RelativeLayout mClassLinear4;
    @BindView(R.id.class_linear_4_img)
    ImageView mClassLinear4Img;
    @BindView(R.id.class_out)
    TextView mClassOut;

    @BindView(R.id.revise_number_relative)
    RelativeLayout mReviseNumberRelative;
    @BindView(R.id.number_linear_1)
    RelativeLayout mNumberLinear1;
    @BindView(R.id.number_linear_1_img)
    ImageView mNumberLinear1Img;
    @BindView(R.id.number_linear_2)
    RelativeLayout mNumberLinear2;
    @BindView(R.id.number_linear_2_img)
    ImageView mNumberLinear2Img;
    @BindView(R.id.number_linear_3)
    RelativeLayout mNumberLinear3;
    @BindView(R.id.number_linear_3_img)
    ImageView mNumberLinear3Img;
    @BindView(R.id.number_linear_4)
    RelativeLayout mNumberLinear4;
    @BindView(R.id.number_linear_4_img)
    ImageView mNumberLinear4Img;
    @BindView(R.id.number_out)
    TextView mNumberOut;

    @BindView(R.id.revise_number)
    TextView mReviseNumber;

    @BindView(R.id.revise_select)
    TextView mReviseSelect;
    @BindView(R.id.revise_list)
    TextView mReviseList;

    @BindView(R.id.revise_select_relative)
    RelativeLayout mReviseSelectRelative;
    @BindView(R.id.select_linear_1)
    RelativeLayout mSelectLinear1;
    @BindView(R.id.select_linear_2)
    RelativeLayout mSelectLinear2;
    @BindView(R.id.select_linear_3)
    RelativeLayout mSelectLinear3;
    @BindView(R.id.select_linear_4)
    RelativeLayout mSelectLinear4;
    @BindView(R.id.select_out)
    TextView mSelectOut;
    @BindView(R.id.revise_done_numbers)
    TextView mReviseDoneNumbers;

    @BindView(R.id.revise_relative_one)
    RelativeLayout mReviseRelativeOne;
    @BindView(R.id.revise_relative_two)
    RelativeLayout mReviseRelativeTwo;
    @BindView(R.id.revise_start_linear)
    LinearLayout mReviseStartLinear;
    private Animation mTopAnim;
    private Animation mRotateAnim;
    private Animation mScaleAnim;

    public int Status;
    private int Number;
    private int Class=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_revise;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initHelp();
        initWordNumber();
        initClass();
        initSelect();
        mReviseRelativeThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mReviseNumberRelative.getVisibility()==View.VISIBLE){
                    mReviseNumberRelative.setVisibility(View.GONE);
                }

                mReviseRelativeThree.setVisibility(View.GONE);
            }
        });

        mReviseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseStart.startAnimation(mScaleAnim);
                initIntent();
            }
        });

        mReviseStartLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseClassImgLeft.startAnimation(mRotateAnim);
                mReviseRelativeThree.setVisibility(View.VISIBLE);
                mReviseClassRelative.startAnimation(mTopAnim);
                mReviseClassRelative.setVisibility(View.VISIBLE);
            }
        });

        mRecite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this,WordBookActivity.class));
                ActivityAnim(ReviseActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Utils.ShowToast(getBaseContext(),"request="+requestCode+"result="+resultCode);
        if(resultCode==10){
            if(data !=null){
                Bundle b=data.getExtras();
                Status=b.getInt("data");
                Utils.ShowToast(getBaseContext(),"Status="+Status);
            }else{
                Status=0;
                Utils.ShowToast(getBaseContext(),"Status1="+Status);
            }
            if(Status==40){
                mReviseRelativeOne.setVisibility(View.GONE);
                mReviseRelativeTwo.setVisibility(View.VISIBLE);
                mReviseStartLinear.setVisibility(View.VISIBLE);
            }else if(Status==0){
                mReviseRelativeOne.setVisibility(View.VISIBLE);
                mReviseRelativeTwo.setVisibility(View.GONE);
                mReviseStartLinear.setVisibility(View.VISIBLE);
                mReviseNumber.setText(Status+"");
            }else{
                mReviseRelativeOne.setVisibility(View.VISIBLE);
                mReviseRelativeTwo.setVisibility(View.GONE);
                mReviseDoneNumbers.setText(Number+"");
                mReviseNumber.setText(Status+"");
                mReviseStartLinear.setVisibility(View.VISIBLE);
            }
        }
    }

    public void initAnim(){
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.alert_word_manage_top_anim);
        mRotateAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_know_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
    }

    public void initWordNumber(){
        mReviseWordLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseWordImgLeft.startAnimation(mRotateAnim);
                mReviseRelativeThree.setVisibility(View.VISIBLE);
                mReviseNumberRelative.startAnimation(mTopAnim);
                mReviseNumberRelative.setVisibility(View.VISIBLE);
            }
        });

        mNumberLinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberLinear1Img.setVisibility(View.VISIBLE);
                mNumberLinear2Img.setVisibility(View.GONE);
                mNumberLinear3Img.setVisibility(View.GONE);
                mNumberLinear4Img.setVisibility(View.GONE);
                mReviseNumberRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Number=1;
                mReviseWordLeft.setText("10");
            }
        });

        mNumberLinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberLinear2Img.setVisibility(View.VISIBLE);
                mNumberLinear1Img.setVisibility(View.GONE);
                mNumberLinear3Img.setVisibility(View.GONE);
                mNumberLinear4Img.setVisibility(View.GONE);
                mReviseNumberRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Number=2;
                mReviseWordLeft.setText("30");
            }
        });

        mNumberLinear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberLinear3Img.setVisibility(View.VISIBLE);
                mNumberLinear2Img.setVisibility(View.GONE);
                mNumberLinear1Img.setVisibility(View.GONE);
                mNumberLinear4Img.setVisibility(View.GONE);
                mReviseNumberRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Number=3;
                mReviseWordLeft.setText("50");
            }
        });

        mNumberLinear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumberLinear4Img.setVisibility(View.VISIBLE);
                mNumberLinear2Img.setVisibility(View.GONE);
                mNumberLinear3Img.setVisibility(View.GONE);
                mNumberLinear1Img.setVisibility(View.GONE);
                mReviseNumberRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Number=4;
                mReviseWordLeft.setText("92");
            }
        });

        mNumberOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseNumberRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
            }
        });
    }

    public void initClass(){
        mReviseClassLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseClassImgLeft.startAnimation(mRotateAnim);
                mReviseRelativeThree.setVisibility(View.VISIBLE);
                mReviseClassRelative.startAnimation(mTopAnim);
                mReviseClassRelative.setVisibility(View.VISIBLE);
            }
        });

        mClassLinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassLinear1Img.setVisibility(View.VISIBLE);
                mClassLinear2Img.setVisibility(View.GONE);
                mClassLinear3Img.setVisibility(View.GONE);
                mClassLinear4Img.setVisibility(View.GONE);
                mReviseClassRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=1;
                mReviseClassLeft.setText("看词选释义");
            }
        });

        mClassLinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassLinear2Img.setVisibility(View.VISIBLE);
                mClassLinear1Img.setVisibility(View.GONE);
                mClassLinear3Img.setVisibility(View.GONE);
                mClassLinear4Img.setVisibility(View.GONE);
                mReviseClassRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=2;
                mReviseClassLeft.setText("拼写题");
            }
        });

        mClassLinear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassLinear3Img.setVisibility(View.VISIBLE);
                mClassLinear2Img.setVisibility(View.GONE);
                mClassLinear1Img.setVisibility(View.GONE);
                mClassLinear4Img.setVisibility(View.GONE);
                mReviseClassRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=3;
                mReviseClassLeft.setText("音频题");
            }
        });

        mClassLinear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClassLinear4Img.setVisibility(View.VISIBLE);
                mClassLinear2Img.setVisibility(View.GONE);
                mClassLinear3Img.setVisibility(View.GONE);
                mClassLinear1Img.setVisibility(View.GONE);
                mReviseClassRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=4;
                mReviseClassLeft.setText("选词填空");
            }
        });

        mClassOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseClassRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
            }
        });
    }

    public void initSelect(){
        mReviseSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelect.startAnimation(mScaleAnim);
                mReviseRelativeThree.setVisibility(View.VISIBLE);
                mReviseSelectRelative.startAnimation(mTopAnim);
                mReviseSelectRelative.setVisibility(View.VISIBLE);
            }
        });

        mSelectLinear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelectRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=1;
                initIntent();
            }
        });

        mSelectLinear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelectRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=2;
                initIntent();
            }
        });

        mSelectLinear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelectRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=3;
                initIntent();
            }
        });

        mSelectLinear4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelectRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
                Class=4;
                initIntent();
            }
        });

        mSelectOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReviseSelectRelative.setVisibility(View.GONE);
                mReviseRelativeThree.setVisibility(View.GONE);
            }
        });
    }

    public void initIntent(){
        Intent intent=new Intent();
        switch (Class){
            case 1:
                intent.setClass(ReviseActivity.this,SelectAnimActivity.class);
                break;
            case 2:
                intent.setClass(ReviseActivity.this,PinYinAnimActivity.class);
                break;
            case 3:
                intent.setClass(ReviseActivity.this,AudioAnimActivity.class);
                break;
            case 4:
                intent.setClass(ReviseActivity.this,FillAnimActivity.class);
                break;
            default:
                break;
        }
        startActivityForResult(intent,5);
    }

    public void initHelp(){
        mReviseHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this, HelpActivity.class));
                ActivityAnim(ReviseActivity.this);
            }
        });

        mReviseHelpOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this,HelpActivity.class));
                ActivityAnim(ReviseActivity.this);
            }
        });
    }

    public void ActivityAnim(Activity activity){
        activity.overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }

    @Override
    protected void initToolBar() {
        mReviseOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mReviseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviseActivity.this, WordBookActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
