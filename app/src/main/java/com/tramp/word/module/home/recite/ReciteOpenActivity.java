package com.tramp.word.module.home.recite;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/7.
 */

public class ReciteOpenActivity extends RxBaseActivity {
    @BindView(R.id.recite_open_out)
    ImageView mReciteOpenOut;
    @BindView(R.id.recite_anim_open_linear)
    LinearLayout mReciteAnimOpenLinear;
    private Animation mReciteOpenAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_open;
    }

    @Override
    public void initView(Bundle save) {
        mReciteOpenAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);

        mReciteAnimOpenLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReciteAnimOpenLinear.startAnimation(mReciteOpenAnim);
                Utils.ShowToast(getBaseContext(),"点击了分享朋友圈!");
            }
        });

    }

    @Override
    protected void initToolBar() {
        mReciteOpenOut.setOnClickListener(new View.OnClickListener() {
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
}
