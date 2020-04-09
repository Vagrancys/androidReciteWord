package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AboutShareViewAdapter;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.setting.about.SettingReportActivity;
import com.tramp.word.module.setting.about.SettingTitleActivity;
import com.tramp.word.utils.CommonUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.AboutUpdateDialog;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/5.
 */

public class SettingAboutActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.about_img)
    ImageView AboutImg;
    @BindView(R.id.about_level)
    TextView AboutLevel;
    @BindView(R.id.about_offer)
    RelativeLayout AboutOffer;
    @BindView(R.id.about_update)
    RelativeLayout AboutUpdate;
    @BindView(R.id.about_report)
    RelativeLayout AboutReport;
    @BindView(R.id.about_user)
    TextView AboutUser;
    @BindView(R.id.about_common)
    RelativeLayout AboutCommon;
    @BindView(R.id.about_pop_share)
    LinearLayout AboutPopShare;
    @BindView(R.id.about_share_delete)
    TextView AboutShareDelete;
    @BindView(R.id.about_share_recycler)
    RecyclerView AboutShareRecycler;
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private AboutShareViewAdapter mAboutAdapter;
    private AboutUpdateDialog mPop;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_about;
    }

    @Override
    public void initView(Bundle save) {
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        AboutImg.setImageResource(R.mipmap.iword_ic_launcher);
        AboutLevel.setText(CommonUtils.getVersion(getBaseContext()));

        AboutOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutCommon.setVisibility(View.VISIBLE);
                AboutPopShare.setVisibility(View.VISIBLE);
                AboutPopShare.startAnimation(mTopEnterAnim);
            }
        });

        AboutShareDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutPopShare.startAnimation(mTopExitAnim);
                AboutPopShare.setVisibility(View.GONE);
                AboutCommon.setVisibility(View.GONE);
            }
        });

        mAboutAdapter=new AboutShareViewAdapter(AboutShareRecycler);

        AboutShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));

        AboutShareRecycler.setAdapter(mAboutAdapter);

        mAboutAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"分享id"+position);
            }
        });

        AboutCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutPopShare.startAnimation(mTopExitAnim);
                AboutPopShare.setVisibility(View.GONE);
                AboutCommon.setVisibility(View.GONE);
            }
        });

        mPop=new AboutUpdateDialog(this);
        mPop.setTitle(getResources().getString(R.string.about_update_title));
        mPop.setMessage(getResources().getString(R.string.about_update_message));
        mPop.setOkOnClickListener("立即更新", new AboutUpdateDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                Utils.ShowToast(getBaseContext(),"下载中......");
                mPop.dismiss();
            }
        });
        mPop.setCancelOnClickListener("稍后", new AboutUpdateDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mPop.dismiss();
            }
        });
        AboutUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.show();
            }
        });

        AboutReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingAboutActivity.this, SettingReportActivity.class));
                Utils.StarActivityInAnim(SettingAboutActivity.this);
            }
        });

        AboutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingAboutActivity.this, SettingTitleActivity.class));
                Utils.StarActivityInAnim(SettingAboutActivity.this);
            }
        });
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.setting_about));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
