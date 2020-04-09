package com.tramp.word.module.pk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.PkDetailsViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.pk.PkDetailsInfo;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.Utils;

import java.io.InputStream;
import java.util.Random;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class PkErrorActivity extends RxBaseActivity{
    @BindView(R.id.win_out)
    ImageView ErrorOut;
    @BindView(R.id.error_again)
    ImageView ErrorAgain;
    @BindView(R.id.error_change)
    ImageView ErrorChange;
    @BindView(R.id.error_relative)
    RelativeLayout ErrorRelative;

    @BindView(R.id.error_main)
    LinearLayout ErrorMain;
    @BindView(R.id.error_img)
    ImageView ErrorImg;
    @BindView(R.id.error_img_feel)
    ImageView ErrorImgFeel;
    @BindView(R.id.error_feel)
    TextView ErrorFeel;
    @BindView(R.id.error_vs)
    ImageView ErrorVs;


    @BindView(R.id.error_one_avatar)
    ImageView ErrorOneAvatar;

    @BindView(R.id.error_one_name)
    TextView ErrorOneName;
    @BindView(R.id.error_one_number)
    TextView ErrorOneNumber;

    @BindView(R.id.error_two_back)
    ImageView ErrorTwoBack;
    @BindView(R.id.error_two_avatar)
    ImageView ErrorTwoAvatar;
    @BindView(R.id.error_two_name)
    TextView ErrorTwoName;
    @BindView(R.id.error_two_head)
    ImageView ErrorTwoHead;
    @BindView(R.id.error_two_number)
    TextView ErrorTwoNumber;

    @BindView(R.id.error_star)
    ImageView ErrorStar;
    @BindView(R.id.show_details)
    LinearLayout ShowDetails;
    @BindView(R.id.error_open)
    ImageView ErrorOpen;

    @BindView(R.id.error_details)
    LinearLayout ErrorDetails;

    @BindView(R.id.details_one_avatar)
    ImageView DetailsOneAvatar;
    @BindView(R.id.details_two_avatar)
    ImageView DetailsTwoAvatar;
    @BindView(R.id.details_one_number)
    TextView DetailsOneNumber;
    @BindView(R.id.details_two_number)
    TextView DetailsTwoNumber;

    @BindView(R.id.details_recycler)
    RecyclerView DetailsRecycler;

    private Animation mScaleAnim;
    private Animation mEnterAnim;
    private Animation mExitAnim;
    private Bitmap PkScoreBg;
    private Bitmap PkQuestionBg;
    private PkDetailsViewAdapter mDetailsAdapter;
    private UserSqlHelper mUser;
    private PkDetailsInfo.Details errors;
    private Random mRandom=new Random();
    @Override
    public int getLayoutId() {
        return R.layout.activity_pk_error;
    }

    @Override
    protected void initToolBar() {
        ErrorOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ErrorDetails.getVisibility()==View.VISIBLE){
                    ErrorDetails.startAnimation(mExitAnim);
                    ErrorDetails.setVisibility(View.GONE);
                    ErrorMain.startAnimation(mExitAnim);
                    ErrorMain.setVisibility(View.VISIBLE);
                    ErrorOut.setRotation(90);
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initBg();
        loadData();
        initData();
        initClick();
        mDetailsAdapter=new PkDetailsViewAdapter(DetailsRecycler,getBaseContext(),errors.getItems());
        DetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        DetailsRecycler.setAdapter(mDetailsAdapter);
    }

    public void initBg(){
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"pk_score_assets@2x.png");
        PkScoreBg= BitmapFactory.decodeStream(back);

        ErrorAgain.setBackground(getDrawable(PkScoreBg,1756,4,275,106));
        ErrorChange.setBackground(getDrawable(PkScoreBg,1702,282,275,106));
        ErrorImgFeel.setBackground(getDrawable(PkScoreBg,1610,392,416,133));
        ErrorImg.setBackground(getDrawable(PkScoreBg,1143,4,609,157));

        InputStream vs=AssetsUtils.getAssetsOpen(getBaseContext(),"pk_vs.png");
        ErrorVs.setImageBitmap(BitmapFactory.decodeStream(vs));

        ErrorRelative.setBackground(getDrawable(PkScoreBg,1100,380,506,90));

        InputStream avatarBg=AssetsUtils.getAssetsOpen(getBaseContext(),"pk_question_assets@2x.png");

        PkQuestionBg=BitmapFactory.decodeStream(avatarBg);
        ErrorTwoBack.setBackground(getDrawable(PkQuestionBg,575,141,427,434));

        InputStream circleBg=AssetsUtils.getAssetsOpen(getBaseContext(),"result_circle_yellow@2x.png");
        ErrorOneAvatar.setImageBitmap(BitmapFactory.decodeStream(circleBg));
        ErrorTwoAvatar.setImageBitmap(BitmapFactory.decodeStream(circleBg));

        ErrorTwoHead.setBackground(getDrawable(PkScoreBg,830,539,122,95));
        ErrorOneNumber.setBackground(getDrawable(PkScoreBg,830,539,122,95));
        ErrorTwoNumber.setBackground(getDrawable(PkScoreBg,830,539,122,95));
        ErrorOpen.setBackground(getDrawable(PkScoreBg,943,167,48,48));

        InputStream pkBg=AssetsUtils.getAssetsOpen(getBaseContext(),"bg_half@2x.png");
        ErrorRelative.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(pkBg)));

        ErrorStar.setBackground(getDrawable(PkScoreBg,830,647,625,121));
    }

    public void initData(){
        ErrorImg.startAnimation(mScaleAnim);
        ErrorImgFeel.startAnimation(mScaleAnim);
        switch (mRandom.nextInt(5)){
            case 0:
                ErrorFeel.setText(getResources().getString(R.string.pk_fail_result_encourage_0));
                break;
            case 1:
                ErrorFeel.setText(getResources().getString(R.string.pk_fail_result_encourage_1));
                break;
            case 2:
                ErrorFeel.setText(getResources().getString(R.string.pk_fail_result_encourage_2));
                break;
            case 3:
                ErrorFeel.setText(getResources().getString(R.string.pk_fail_result_encourage_3));
                break;
            case 4:
                ErrorFeel.setText(getResources().getString(R.string.pk_fail_result_encourage_4));
                break;
        }

        mDetailsAdapter=new PkDetailsViewAdapter(DetailsRecycler,getBaseContext(),errors.getItems());
        DetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        DetailsRecycler.setAdapter(mDetailsAdapter);
    }

    private void finishTask(){
        Glide.with(getBaseContext())
                .load(errors.getDetails_one_avatar())
                .placeholder(R.drawable.user_avater)
                .into(ErrorOneAvatar);
        Glide.with(getBaseContext())
                .load(errors.getDetails_two_avatar())
                .placeholder(R.drawable.user_avater)
                .into(ErrorTwoAvatar);
        ErrorOneName.setText(errors.getDetails_one_name());
        ErrorOneNumber.setText(errors.getDetails_one_number());
        ErrorTwoName.setText(errors.getDetails_two_name());
        ErrorTwoNumber.setText(errors.getDetails_two_number());
        Glide.with(getBaseContext())
                .load(errors.getDetails_one_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsOneAvatar);
        Glide.with(getBaseContext())
                .load(errors.getDetails_two_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsTwoAvatar);
        DetailsOneNumber.setText(errors.getDetails_one_number());
        DetailsTwoNumber.setText(errors.getDetails_two_number());
        mDetailsAdapter.notifyDataSetChanged();

    }

    private void loadData(){
        Retrofits.getPkAPI().getPkErrorInfo(mUser.UserId())
                .enqueue(new Callback<PkDetailsInfo>() {
                    @Override
                    public void onResponse(Call<PkDetailsInfo> call, Response<PkDetailsInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            errors=response.body().getDetails();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkDetailsInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络出错了!");
                    }
                });
    }

    private void initAnim(){
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mEnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
    }

    public void initClick(){
        ErrorAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkSearchActivity.launch(PkErrorActivity.this);
                finish();
            }
        });

        ErrorChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkSearchActivity.launch(PkErrorActivity.this);
                finish();
            }
        });

        ShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMain.startAnimation(mEnterAnim);
                ErrorMain.setVisibility(View.GONE);
                ErrorDetails.setVisibility(View.VISIBLE);
                ErrorDetails.startAnimation(mEnterAnim);
                ErrorOut.setRotation(-90);
            }
        });
    }

    public Drawable getDrawable(Bitmap bitmap,int left,int top,int width,int height){
        return new BitmapDrawable(Bitmap.createBitmap(bitmap,left,top,width,height));
    }
}
