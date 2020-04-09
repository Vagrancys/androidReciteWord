package com.tramp.word.module.pk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.pk.PkSearchInfo;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.Utils;

import java.io.InputStream;

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

public class PkSearchActivity extends RxBaseActivity{
    @BindView(R.id.pk_search_enemy_bg)
    ImageView SearchEnemyBg;
    @BindView(R.id.pk_search_exit)
    ImageView SearchExit;
    @BindView(R.id.pk_search_sweep)
    ImageView SearchSweep;
    @BindView(R.id.pk_search_enemy_avatar_linear)
    LinearLayout SearchEnemyAvatarLinear;
    @BindView(R.id.pk_search_enemy_avatar)
    ImageView SearchEnemyAvatar;
    @BindView(R.id.pk_search_enemy_avatar_text)
    TextView SearchEnemyAvatarText;
    @BindView(R.id.pk_search_hint)
    ImageView SearchHint;
    @BindView(R.id.pk_search_avatar_linear)
    LinearLayout SearchAvatarLinear;
    @BindView(R.id.pk_search_avatar)
    ImageView SearchAvatar;
    @BindView(R.id.pk_search_avatar_text)
    TextView SearchAvatarText;
    @BindView(R.id.pk_search_sweep_outside)
    ImageView SearchSweepOutside;
    @BindView(R.id.pk_search_fight)
    ImageView SearchFight;
    @BindView(R.id.pk_search_top)
    ImageView SearchTop;
    @BindView(R.id.pk_search_bottom)
    ImageView SearchBottom;
    @BindView(R.id.pk_search_bg_left)
    ImageView SearchBgLeft;
    @BindView(R.id.pk_search_bg_right)
    ImageView SearchBgRight;
    @BindView(R.id.pk_search_pop_text)
    TextView SearchPopText;
    private Animation mRotateAnim;
    private Animation mScaleOutAnim;
    private Animation mScaleInAnim;
    private Animation mSpringAnim;
    private ObjectAnimator mAlphaHintAnim;
    private Animation mScaleAlphaAnim;
    private Animation mAlphaAnim;
    private ObjectAnimator mAvatarLeftAnim;
    private ObjectAnimator mAvatarRightAnim;
    private Animation mAlphaTopAnim;
    private Animation mAlphaBottomAnim;
    private ObjectAnimator mAvatarTopAnim;
    private ObjectAnimator mAvatarEnemyTopAnim;
    private Animation mLeftAnim;
    private Animation mRightAnim;

    private Handler mHandle=new Handler();

    private Bitmap AssetsBg;
    private PkSearchInfo.Search search;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pk_search;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initData();
        SearchEnemyBg.startAnimation(mScaleOutAnim);
        SearchSweep.startAnimation(mRotateAnim);
        loadData();
    }

    private void loadData(){
        Retrofits.getPkAPI().getPkSearchInfo()
                .enqueue(new Callback<PkSearchInfo>() {
                    @Override
                    public void onResponse(Call<PkSearchInfo> call, Response<PkSearchInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            search=response.body().getSearch();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkSearchInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"pk失败了");
                        finish();
                    }
                });
    }
    private void finishTask(){
        Glide.with(getBaseContext())
                .load(search.getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(SearchEnemyAvatar);
        SearchEnemyAvatarText.setText(search.getUser_name());
        SearchEnemyAvatar.startAnimation(mSpringAnim);
        SearchEnemyBg.clearAnimation();
        SearchEnemyBg.setVisibility(View.GONE);
        SearchSweep.clearAnimation();
        SearchSweep.setVisibility(View.GONE);
        SearchSweepOutside.clearAnimation();
        SearchSweepOutside.setVisibility(View.GONE);
        mAlphaHintAnim.start();
    }

    private void initAnim(){
        mScaleOutAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_scale_out_anim);
        mScaleOutAnim.setRepeatCount(Animation.INFINITE);
        mScaleInAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_scale_in_anim);
        mScaleInAnim.setRepeatCount(1);

        mRotateAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_rotate_anim);
        mRotateAnim.setRepeatCount(Animation.INFINITE);

        mAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.recite_alpha_anim);

        mLeftAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_left_anim);
        mRightAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_right_anim);


        mAlphaHintAnim= ObjectAnimator.ofFloat(SearchHint,"alpha",1f,0f);
        mAlphaHintAnim.setDuration(1000);
        mAlphaHintAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAvatarLeftAnim.start();
                mAvatarRightAnim.start();
                SearchHint.setVisibility(View.GONE);
            }
        });
        mAlphaTopAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_alpha_top_anim);
        mAlphaBottomAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_alpha_bottom_anim);

        mAvatarLeftAnim=ObjectAnimator.ofFloat(SearchAvatarLinear,"translationX",SearchAvatar.getTranslationX(),-250);
        mAvatarLeftAnim.setDuration(1000);
        mAvatarLeftAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                SearchFight.setVisibility(View.VISIBLE);
                SearchTop.setVisibility(View.VISIBLE);
                SearchBottom.setVisibility(View.VISIBLE);
                SearchFight.startAnimation(mScaleAlphaAnim);
                SearchBottom.startAnimation(mAlphaBottomAnim);
                SearchTop.startAnimation(mAlphaTopAnim);
                mHandle.post(()->{
                    SearchFight.startAnimation(mAlphaAnim);
                    SearchBottom.startAnimation(mAlphaAnim);
                    SearchTop.startAnimation(mAlphaAnim);
                    SearchFight.setVisibility(View.GONE);
                    SearchTop.setVisibility(View.GONE);
                    SearchBottom.setVisibility(View.GONE);
                    mAvatarTopAnim.start();
                    mAvatarEnemyTopAnim.start();
                    SearchBgLeft.startAnimation(mLeftAnim);
                    SearchBgRight.startAnimation(mRightAnim);
                });
                SearchPopText.setVisibility(View.VISIBLE);
            }
        });

        mAvatarRightAnim=ObjectAnimator.ofFloat(SearchEnemyAvatarLinear,"translationX",SearchEnemyAvatar.getTranslationX(),250);
        mAvatarRightAnim.setDuration(1000);

        mScaleAlphaAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.pk_search_scale_alpha_anim);

        mSpringAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);

        mAvatarTopAnim=ObjectAnimator.ofFloat(SearchAvatarLinear,"translationY",SearchAvatarLinear.getTranslationY(),-700);
        mAvatarTopAnim.setDuration(1000);
        mAvatarTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long current=animation.getCurrentPlayTime();
                if(current>500){
                    SearchAvatarText.setVisibility(View.GONE);
                }else{
                    SearchAvatar.setScaleX(current/1000);
                    SearchAvatar.setScaleY(current/1000);
                }

            }
        });

        mAvatarEnemyTopAnim=ObjectAnimator.ofFloat(SearchEnemyAvatarLinear,"translationY",SearchEnemyAvatarLinear.getTranslationY(),-50);
        mAvatarEnemyTopAnim.setDuration(1000);
        mAvatarEnemyTopAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                SearchPopText.setVisibility(View.GONE);
            }
        });
        mAvatarEnemyTopAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long current=animation.getCurrentPlayTime();
                if(current>500){
                    SearchEnemyAvatarText.setVisibility(View.GONE);
                }else{
                    SearchEnemyAvatar.setScaleX(current/1000);
                    SearchEnemyAvatar.setScaleY(current/1000);
                }


            }
        });

        mAvatarTopAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                PkAnimActivity.launch(PkSearchActivity.this,search.getSearch_id());
            }
        });

    }

    public void initData(){
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"pk_assets@2x.png");
        AssetsBg= BitmapFactory.decodeStream(back);

        InputStream bg= AssetsUtils.getAssetsOpen(getBaseContext(),"bg_half@2x.png");
        SearchBgLeft.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(bg)));
        SearchBgRight.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(bg)));

        SearchExit.setImageBitmap(Bitmap.createBitmap(AssetsBg,532,926,152,61));
        SearchSweepOutside.setImageBitmap(Bitmap.createBitmap(AssetsBg,382,154,355,355));

        SearchTop.setImageBitmap(Bitmap.createBitmap(AssetsBg,330,517,322,407));

        SearchPopText.setBackground(new BitmapDrawable(Bitmap.createBitmap(AssetsBg,891,2,97,74)));

        SearchSweep.setImageBitmap(Bitmap.createBitmap(AssetsBg,2,135,378,380));

        SearchBottom.setImageBitmap(Bitmap.createBitmap(AssetsBg,2,517,326,461));

        SearchFight.setImageBitmap(Bitmap.createBitmap(AssetsBg,739,328,280,181));

        SearchHint.setImageBitmap(Bitmap.createBitmap(AssetsBg,502,2,387,150));
    }

    @Override
    protected void initToolBar() {
        SearchExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,PkSearchActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
