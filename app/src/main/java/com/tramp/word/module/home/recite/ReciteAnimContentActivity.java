package com.tramp.word.module.home.recite;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/7.
 */

public class ReciteAnimContentActivity extends RxBaseActivity {
    @BindView(R.id.recite_content_text_moise)
    ImageView mReciteContentTextMoise;

    @BindView(R.id.recite_content_text_down)
    ImageView mReciteContentTextDown;

    private AnimationDrawable mMoiseAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_anim_content;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        mReciteContentTextMoise.setImageResource(R.drawable.recite_music_anim);
        mMoiseAnim=(AnimationDrawable) mReciteContentTextMoise.getDrawable();
        mReciteContentTextMoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoiseAnim.start();
            }
        });
        mReciteContentTextDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
