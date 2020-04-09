package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.main.ReciteOpenInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/7.
 */

public class ReciteOpenActivity extends RxBaseActivity {
    @BindView(R.id.open_out)
    ImageView OpenOut;
    @BindView(R.id.open_share)
    LinearLayout OpenShare;
    @BindView(R.id.open_title)
    TextView OpenTitle;
    @BindView(R.id.open_day)
    TextView OPenDay;
    @BindView(R.id.open_word)
    TextView OPenWord;
    @BindView(R.id.open_avatar)
    ImageView OpenAvatar;
    @BindView(R.id.open_time)
    TextView OpenTime;
    @BindView(R.id.open_weixin)
    ImageView OpenWeixin;
    @BindView(R.id.open_weibo)
    ImageView OpenWeibo;
    @BindView(R.id.open_qq)
    ImageView OpenQq;
    @BindView(R.id.open_down)
    ImageView OpenDown;
    @BindView(R.id.open_help)
    ImageView OpenHelp;
    private Animation mScaleAnim;
    private UserSqlHelper mUser;
    private ReciteOpenInfo.Open open;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_open;
    }

    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        loadData();
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        initClick();
    }
    private void loadData(){
        Retrofits.getMainAPI().getReciteOpenInfo(mUser.UserId(),mUser.WordId())
                .enqueue(new Callback<ReciteOpenInfo>() {
                    @Override
                    public void onResponse(Call<ReciteOpenInfo> call, Response<ReciteOpenInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            open=response.body().getOpen();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReciteOpenInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络失效了!");
                    }
                });
    }

    private void finishTask(){
        OpenTitle.setText(open.getOpen_title());
        OPenDay.setText(open.getOpen_day());
        OPenWord.setText(open.getOpen_word());
        Glide.with(getBaseContext()).load(open.getOpen_avatar()).placeholder(R.drawable.user_avater)
                .into(OpenAvatar);
        OpenTime.setText(open.getOpen_time());
    }
    private void initClick(){
        OpenShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenShare.startAnimation(mScaleAnim);
                Utils.ShowToast(getBaseContext(),"点击了分享朋友圈!");
            }
        });

        OpenHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteOpenActivity.this, WedCommonActivity.class));
                Utils.StarActivityInAnim(ReciteOpenActivity.this);
            }
        });

        OpenWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享微信!");
            }
        });

        OpenWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享微博!");
            }
        });

        OpenQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享QQ!");
            }
        });

        OpenDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享下载!");
            }
        });
    }

    @Override
    protected void initToolBar() {
        OpenOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,ReciteOpenActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
