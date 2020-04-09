package com.tramp.word.module.pk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.ConstantUtils;

import java.io.InputStream;
import java.util.Random;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class PkAnimActivity extends RxBaseActivity{
    @BindView(R.id.pk_anim_relative)
    RelativeLayout PkAnimRelative;
    @BindView(R.id.pk_anim_avatar_bg)
    TextView PkAnimAvatarBg;
    @BindView(R.id.pk_anim_avatar_enemy_bg)
    TextView PkAnimAvatarEnemyBg;
    @BindView(R.id.pk_anim_title)
    TextView PkAnimTitle;
    @BindView(R.id.pk_anim_header)
    RelativeLayout PkAnimHeader;
    @BindView(R.id.pk_anim_number_left)
    TextView PkAnimNumberLeft;
    @BindView(R.id.pk_anim_number_right)
    TextView PkAnimNumberRight;
    @BindView(R.id.pk_anim_join_bg)
    ImageView PkAnimJoinBg;
    @BindView(R.id.pk_anim_join_left)
    ImageView PkAnimJoinLeft;
    @BindView(R.id.pk_anim_join_right)
    ImageView PkAnimJoinRight;
    @BindView(R.id.pk_anim_join)
    RelativeLayout PkAnimJoin;
    @BindView(R.id.pk_anim_exit)
    ImageView PkAnimExit;
    @BindView(R.id.pk_anim_time)
    TextView PkAnimTime;

    @BindView(R.id.pk_anim_select_one)
    TextView PkAnimSelectOne;
    @BindView(R.id.pk_anim_select_two)
    TextView PkAnimSelectTwo;
    @BindView(R.id.pk_anim_select_three)
    TextView PkAnimSelectThree;
    @BindView(R.id.pk_anim_select_four)
    TextView PkAnimSelectFour;
    @BindView(R.id.pk_anim_select_left_img)
    ImageView PkAnimSelectLeftImg;
    @BindView(R.id.pk_anim_select_right_img)
    ImageView PkAnimSelectRightImg;
    @BindView(R.id.pk_anim_select)
    RelativeLayout PkAnimSelect;

    private Bitmap PkAnimBg;
    private Bitmap mPkAnimAvatarBg;
    private Bitmap PkQuestionBg;

    private Bitmap BtnTrue;
    private Bitmap BtnNull;
    private Bitmap BtnFalse;
    private Bitmap BtnSword;
    private int Number=1;
    private int True=1;
    private Random mRandom=new Random();

    private Animation TitleAnim;
    private Animation TopAnim;
    private Animation LeftAnim;
    private Animation RightAnim;
    private final Handler mHandler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x1111:

                    break;
                case 0x1112:
                    DataShow();
                    break;
                case 0x1113:
                    PkAnimNumberLeft.setVisibility(View.GONE);
                    PkAnimNumberRight.setVisibility(View.GONE);
                    PkAnimSelect.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1114:
                    PkAnimAvatarEnemyBg.setVisibility(View.VISIBLE);
                    PkAnimAvatarBg.setVisibility(View.VISIBLE);
                    PkAnimSelect.setVisibility(View.GONE);
                    initData();
                    break;
                case 0x1115:
                    PkAnimTitle.setVisibility(View.GONE);
                    PkAnimSelect.setVisibility(View.VISIBLE);
                    mTimer.start();
                    break;
            }
            return false;
        }
    });
    @Override
    public int getLayoutId() {
        return R.layout.activity_pk_anim;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        initBg();
        initAnim();
        initClick();
        StartAnim();
    }

    public void StartAnim(){
        mHandler.sendEmptyMessageDelayed(0x1114,2000);
    }

    public CountDownTimer mTimer=new CountDownTimer(20000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            PkAnimTime.setText(millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            DataShow();
        }
    };

    public void initBg(){
        InputStream back=AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_strect@2x.png");
        PkAnimBg= BitmapFactory.decodeStream(back);
        PkAnimRelative.setBackground(new BitmapDrawable(PkAnimBg));

        InputStream avatarBg=AssetsUtils.getAssetsOpen(getBaseContext(),"pic_bowen@2x.png");
        mPkAnimAvatarBg= BitmapFactory.decodeStream(avatarBg);
        PkAnimAvatarBg.setBackground(new BitmapDrawable(mPkAnimAvatarBg));
        PkAnimAvatarEnemyBg.setBackground(new BitmapDrawable(mPkAnimAvatarBg));

        InputStream pkQuestion=AssetsUtils.getAssetsOpen(getBaseContext(),"pk_question_assets@2x.png");
        PkQuestionBg=BitmapFactory.decodeStream(pkQuestion);

        PkAnimTitle.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,602,4,418,133)));

        PkAnimHeader.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,4,4,594,109)));

        PkAnimNumberLeft.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,40,990,32,22)));
        PkAnimNumberRight.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,4,990,32,22)));

        PkAnimJoinBg.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,575,141,427,434)));
        PkAnimJoinLeft.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,4,891,203,95)));
        PkAnimJoinRight.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,721,683,77,66)));

        PkAnimExit.setBackground(new BitmapDrawable(Bitmap.createBitmap(PkQuestionBg,797,579,157,62)));

        BtnTrue=Bitmap.createBitmap(PkQuestionBg,958,579,48,48);
        BtnNull=Bitmap.createBitmap(PkQuestionBg,958,631,48,48);
        BtnFalse=Bitmap.createBitmap(PkQuestionBg,721,579,48,48);

        PkAnimSelectOne.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectTwo.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectThree.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectFour.setBackground(new BitmapDrawable(BtnNull));

        BtnSword=Bitmap.createBitmap(PkQuestionBg,505,973,94,37);

        PkAnimSelectLeftImg.setBackground(new BitmapDrawable(BtnSword));
        PkAnimSelectRightImg.setBackground(new BitmapDrawable(BtnSword));
    }

    public void DataShow(){
        if(Number==17){
            if(True>5){
                startActivity(new Intent(PkAnimActivity.this,PkErrorActivity.class));
                finish();
            }else{
                startActivity(new Intent(PkAnimActivity.this,PkWinActivity.class));
                finish();
            }

        }else{
            Number++;
        }

        mTimer.cancel();
        PkAnimNumberLeft.setVisibility(View.VISIBLE);
        PkAnimNumberLeft.setText("+114");
        PkAnimNumberLeft.startAnimation(TopAnim);
        PkAnimNumberRight.setVisibility(View.VISIBLE);
        PkAnimNumberRight.setText("+135");
        PkAnimNumberRight.startAnimation(TopAnim);

        PkAnimAvatarBg.setText("542");
        PkAnimAvatarEnemyBg.setText("1045");
        PkAnimSelectRightImg.setTop(140);
        PkAnimSelectRightImg.setVisibility(View.VISIBLE);
        PkAnimSelectRightImg.startAnimation(RightAnim);

        if(True>1){
            PkAnimJoin.setVisibility(View.VISIBLE);
        }

        if(mRandom.nextInt(4)==1){
            PkAnimSelectFour.setBackground(new BitmapDrawable(BtnTrue));
        }

        mHandler.sendEmptyMessageDelayed(0x1113,2000);
    }

    public void initAnim(){
        TitleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_left_anim);

        TopAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_anim_top_anim);

        LeftAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_anim_left_anim);
        RightAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_anim_right_anim);
    }

    public void initData(){
        PkAnimTitle.setVisibility(View.VISIBLE);
        PkAnimTitle.setText("第9题");
        PkAnimTitle.startAnimation(TitleAnim);

        PkAnimSelectOne.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectTwo.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectThree.setBackground(new BitmapDrawable(BtnNull));
        PkAnimSelectFour.setBackground(new BitmapDrawable(BtnNull));

        PkAnimSelectLeftImg.setVisibility(View.GONE);
        PkAnimSelectRightImg.setVisibility(View.GONE);

        PkAnimJoin.setVisibility(View.GONE);

        mHandler.sendEmptyMessageDelayed(0x1115,1000);
    }

    public void initClick(){
        PkAnimExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.cancel();
                finish();
            }
        });

        PkAnimSelectOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PkAnimSelectOne.getText().toString().equals("但是")){
                    PkAnimSelectOne.setBackground(new BitmapDrawable(BtnTrue));
                    True++;
                }else{
                    PkAnimSelectOne.setBackground(new BitmapDrawable(BtnFalse));
                    True=0;
                }
                PkAnimSelectLeftImg.setVisibility(View.VISIBLE);
                PkAnimSelectLeftImg.startAnimation(LeftAnim);
                mHandler.sendEmptyMessageDelayed(0x1112,3000);
            }
        });

        PkAnimSelectTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PkAnimSelectTwo.getText().toString().equals("但是")){
                    PkAnimSelectTwo.setBackground(new BitmapDrawable(BtnTrue));
                    True++;
                }else{
                    PkAnimSelectTwo.setBackground(new BitmapDrawable(BtnFalse));
                    True=0;
                }
                PkAnimSelectLeftImg.setTop(70);
                PkAnimSelectLeftImg.setVisibility(View.VISIBLE);
                PkAnimSelectLeftImg.startAnimation(LeftAnim);
                mHandler.sendEmptyMessageDelayed(0x1112,3000);
            }
        });

        PkAnimSelectThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PkAnimSelectThree.getText().toString().equals("但是")){
                    PkAnimSelectTwo.setBackground(new BitmapDrawable(BtnTrue));
                    True++;
                }else{
                    PkAnimSelectThree.setBackground(new BitmapDrawable(BtnFalse));
                    True=0;
                }
                PkAnimSelectLeftImg.setTop(140);
                PkAnimSelectLeftImg.setVisibility(View.VISIBLE);
                PkAnimSelectLeftImg.startAnimation(LeftAnim);
                mHandler.sendEmptyMessageDelayed(0x1112,3000);
            }
        });

        PkAnimSelectFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PkAnimSelectFour.getText().toString().equals("但是")){
                    PkAnimSelectFour.setBackground(new BitmapDrawable(BtnTrue));
                    True++;
                }else{
                    PkAnimSelectFour.setBackground(new BitmapDrawable(BtnFalse));
                    True=0;
                }
                PkAnimSelectLeftImg.setTop(210);
                PkAnimSelectLeftImg.setVisibility(View.VISIBLE);
                PkAnimSelectLeftImg.startAnimation(LeftAnim);
                mHandler.sendEmptyMessageDelayed(0x1112,3000);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int pk_id){
        Intent intent=new Intent(activity,PkAnimActivity.class);
        intent.putExtra(ConstantUtils.PK_ID,pk_id);
        activity.startActivity(intent);
        activity.finish();
    }
}
