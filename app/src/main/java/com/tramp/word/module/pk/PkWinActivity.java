package com.tramp.word.module.pk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.PkDetailsViewAdapter;
import com.tramp.word.adapter.ShareViewAdapter;
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

public class PkWinActivity extends RxBaseActivity{
    @BindView(R.id.win_out)
    ImageView WinOut;
    @BindView(R.id.win_again)
    ImageView WinAgain;
    @BindView(R.id.win_change)
    ImageView WinChange;
    @BindView(R.id.win_relative)
    RelativeLayout WinRelative;

    @BindView(R.id.win_main)
    LinearLayout WinMain;
    @BindView(R.id.win_img)
    ImageView WinImg;
    @BindView(R.id.win_img_feel)
    ImageView WinImgFeel;
    @BindView(R.id.win_feel)
    TextView WinFeel;
    @BindView(R.id.win_vs)
    ImageView WinVs;

    @BindView(R.id.win_one_back)
    ImageView WinOneBack;
    @BindView(R.id.win_one_avatar)
    ImageView WinOneAvatar;
    @BindView(R.id.win_one_head)
    ImageView WinOneHead;
    @BindView(R.id.win_one_foot)
    ImageView WinOneFoot;
    @BindView(R.id.win_one_name)
    TextView WinOneName;
    @BindView(R.id.win_one_number)
    TextView WinOneNumber;

    @BindView(R.id.win_two_avatar)
    ImageView WinTwoAvatar;
    @BindView(R.id.win_two_name)
    TextView WinTwoName;
    @BindView(R.id.win_two_number)
    TextView WinTwoNumber;

    @BindView(R.id.win_star)
    ImageView WinStar;
    @BindView(R.id.show_details)
    LinearLayout ShowDetails;
    @BindView(R.id.win_open)
    ImageView WinOpen;

    @BindView(R.id.win_details)
    LinearLayout WinDetails;

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

    @BindView(R.id.share_back)
    LinearLayout ShareBack;
    @BindView(R.id.share_recycler)
    RecyclerView ShareRecycler;
    @BindView(R.id.share_linear)
    LinearLayout ShareLinear;
    @BindView(R.id.share_delete)
    TextView ShareDelete;

    private Animation mScaleAnim;
    private Animation mEnterAnim;
    private Animation mExitAnim;
    private Bitmap PkScoreBg;
    private Bitmap PkQuestionBg;
    private PkDetailsViewAdapter mDetailsAdapter;
    private ShareViewAdapter mShareAdapter;
    private UserSqlHelper mUser;
    private PkDetailsInfo.Details wins;
    private Random mRandom=new Random();
    private String[] mTitle={
            "微信好友","朋友圈","新浪微博","QQ好友","QQ空间"
    };
    private int[] mImg={
            R.drawable.btn_share_wechat_normal,
            R.drawable.btn_share_pyq_normal,
            R.drawable.btn_share_sina_normal,
            R.drawable.btn_share_qq_normal,
            R.drawable.btn_share_qzone_normal
    };
    private int[] mUnImg={
            R.drawable.btn_share_wechat_pressed,
            R.drawable.btn_share_pyq_pressed,
            R.drawable.btn_share_sina_pressed,
            R.drawable.btn_share_qq_pressed,
            R.drawable.btn_share_qzone_pressed
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_pk_win;
    }

    @Override
    protected void initToolBar() {
        WinOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WinDetails.getVisibility()==View.VISIBLE){
                    WinDetails.setVisibility(View.GONE);
                    WinOut.setRotation(90);
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
    }
    private void loadData(){
        Retrofits.getPkAPI().getPkWinInfo(mUser.UserId())
                .enqueue(new Callback<PkDetailsInfo>() {
                    @Override
                    public void onResponse(Call<PkDetailsInfo> call, Response<PkDetailsInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            wins=response.body().getDetails();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkDetailsInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络出错了!");
                    }
                });
    }

    private void finishTask(){
        Glide.with(getBaseContext())
                .load(wins.getDetails_one_avatar())
                .placeholder(R.drawable.user_avater)
                .into(WinOneAvatar);
        Glide.with(getBaseContext())
                .load(wins.getDetails_two_avatar())
                .placeholder(R.drawable.user_avater)
                .into(WinTwoAvatar);
        WinOneName.setText(wins.getDetails_one_name());
        WinOneNumber.setText(wins.getDetails_one_number());
        WinTwoName.setText(wins.getDetails_two_name());
        WinTwoNumber.setText(wins.getDetails_two_number());
        Glide.with(getBaseContext())
                .load(wins.getDetails_one_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsOneAvatar);
        Glide.with(getBaseContext())
                .load(wins.getDetails_two_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsTwoAvatar);
        DetailsOneNumber.setText(wins.getDetails_one_number());
        DetailsTwoNumber.setText(wins.getDetails_two_number());
        mDetailsAdapter.notifyDataSetChanged();
    }

    private void initAnim(){
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mEnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
    }

    public void initBg(){
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"pk_score_assets@2x.png");
        PkScoreBg= BitmapFactory.decodeStream(back);

        WinAgain.setBackground(getDrawable(PkScoreBg,1542,529,275,106));
        WinChange.setBackground(getDrawable(PkScoreBg,1702,282,275,106));
        WinImgFeel.setBackground(getDrawable(PkScoreBg,1100,474,438,155));
        WinImg.setBackground(getDrawable(PkScoreBg,1089,219,609,157));

        InputStream vs=AssetsUtils.getAssetsOpen(getBaseContext(),"pk_vs.png");
        WinVs.setImageBitmap(BitmapFactory.decodeStream(vs));

        WinRelative.setBackground(getDrawable(PkScoreBg,1100,380,506,90));

        InputStream avatarBg=AssetsUtils.getAssetsOpen(getBaseContext(),"pk_question_assets@2x.png");

        PkQuestionBg=BitmapFactory.decodeStream(avatarBg);
        WinOneBack.setBackground(getDrawable(PkQuestionBg,575,141,427,434));
        InputStream circleBg=AssetsUtils.getAssetsOpen(getBaseContext(),"result_circle_yellow@2x.png");
        WinOneAvatar.setImageBitmap(BitmapFactory.decodeStream(circleBg));
        WinTwoAvatar.setImageBitmap(BitmapFactory.decodeStream(circleBg));
        WinOneHead.setBackground(getDrawable(PkScoreBg,830,539,122,95));
        WinOpen.setBackground(getDrawable(PkScoreBg,943,167,48,48));

        InputStream pkBg=AssetsUtils.getAssetsOpen(getBaseContext(),"bg_half@2x.png");
        WinRelative.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(pkBg)));
        WinTwoNumber.setBackground(getDrawable(PkScoreBg,830,647,625,121));
        WinOneNumber.setBackground(getDrawable(PkScoreBg,830,647,625,121));

        WinStar.setBackground(getDrawable(PkScoreBg,956,539,104,104));
    }

    public void initData(){
        WinImg.startAnimation(mScaleAnim);
        WinImgFeel.startAnimation(mScaleAnim);
        switch (mRandom.nextInt(5)){
            case 0:
                WinFeel.setText(getResources().getString(R.string.pk_success_result_encourage_0));
                break;
            case 1:
                WinFeel.setText(getResources().getString(R.string.pk_success_result_encourage_1));
                break;
            case 2:
                WinFeel.setText(getResources().getString(R.string.pk_success_result_encourage_2));
                break;
            case 3:
                WinFeel.setText(getResources().getString(R.string.pk_success_result_encourage_3));
                break;
            case 4:
                WinFeel.setText(getResources().getString(R.string.pk_success_result_encourage_4));
                break;
        }

        mDetailsAdapter=new PkDetailsViewAdapter(DetailsRecycler,getBaseContext(),wins.getItems());
        DetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        DetailsRecycler.setAdapter(mDetailsAdapter);

        mShareAdapter=new ShareViewAdapter(ShareRecycler,mTitle,mImg,mUnImg);
        ShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        ShareRecycler.setAdapter(mShareAdapter);
        mShareAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"选中了"+position);
            }
        });
    }

    public void initClick(){
        WinAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareBack.setVisibility(View.VISIBLE);
                ShareLinear.setVisibility(View.VISIBLE);
                ShareLinear.startAnimation(mEnterAnim);
            }
        });
        ShareDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinear.startAnimation(mExitAnim);
                ShareLinear.setVisibility(View.GONE);
                ShareBack.setVisibility(View.GONE);
            }
        });
        ShareBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinear.startAnimation(mExitAnim);
                ShareLinear.setVisibility(View.GONE);
                ShareBack.setVisibility(View.GONE);
            }
        });

        WinChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkSearchActivity.launch(PkWinActivity.this);
                finish();
            }
        });

        ShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WinMain.startAnimation(mEnterAnim);
                WinMain.setVisibility(View.GONE);
                WinDetails.setVisibility(View.VISIBLE);
                WinDetails.startAnimation(mEnterAnim);
                WinOut.setRotation(-90);
            }
        });

    }

    public Drawable getDrawable(Bitmap bitmap, int left, int top, int width, int height){
        return new BitmapDrawable(Bitmap.createBitmap(bitmap,left,top,width,height));
    }


}
