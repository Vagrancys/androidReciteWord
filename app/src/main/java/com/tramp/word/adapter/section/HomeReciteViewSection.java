package com.tramp.word.adapter.section;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.module.pk.WordPkActivity;
import com.tramp.word.module.home.recite.ReciteCheckActivity;
import com.tramp.word.module.home.recite.ReciteOpenActivity;
import com.tramp.word.module.home.recite.ReciteWordActivity;
import com.tramp.word.module.revise.ReviseActivity;
import com.tramp.word.module.task.TaskListActivity;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.widget.section.StatelessSection;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/11.
 */

public class HomeReciteViewSection extends StatelessSection{
    private Context mContext;
    private MainAnimInterFace mInterFace;
    private Animation mShowStarImgAnim;
    private Animation mHideStarImgAnim;
    private Animation mReciteStartRush;
    private ObjectAnimator mTaskIconTranslationAnim;
    private ObjectAnimator mTaskIconScaleAnim;
    private AnimatorSet mTaskIconAnimSet;
    private int AnimStatic=1;

    public HomeReciteViewSection(Context context){
        super(R.layout.item_recite_head,R.layout.item_recite_footer);
        this.mContext=context;
        mInterFace=(MainAnimInterFace) context;
        mReciteStartRush=AnimationUtils.loadAnimation(context,R.anim.task_list_exit_anim);
        mShowStarImgAnim = AnimationUtils.loadAnimation(context,R.anim.recite_star_hint_show_anim);
        mHideStarImgAnim =AnimationUtils.loadAnimation(context,R.anim.recite_star_hint_hide_anim);
        mTaskIconAnimSet=new AnimatorSet();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.mReciteHeadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.ShowTaskLayout();
            }
        });

        mTaskIconTranslationAnim=ObjectAnimator.ofFloat(mHeader.mReciteTaskIconHeadImg,"translationY",0,-15,0);
        mTaskIconTranslationAnim.setDuration(500);
        mTaskIconScaleAnim=ObjectAnimator.ofFloat(mHeader.mReciteTaskIconHeadImg,"scaleY",1f,1.3f,1f);
        mTaskIconTranslationAnim.setRepeatCount(ValueAnimator.INFINITE);
        mTaskIconScaleAnim.setDuration(500);
        mTaskIconScaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        mTaskIconAnimSet.setDuration(800);
        mTaskIconAnimSet.play(mTaskIconTranslationAnim).with(mTaskIconScaleAnim);
        if(AnimStatic==1){
            mTaskIconAnimSet.start();
        }

        mHeader.mReciteStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHeader.mReciteStarHint.getVisibility()==View.VISIBLE){
                    mHeader.mReciteStarHint.startAnimation(mHideStarImgAnim);
                    mHeader.mReciteStarHint.setVisibility(View.GONE);
                }else{
                    mHeader.mReciteStarHint.startAnimation(mShowStarImgAnim);
                    mHeader.mReciteStarHint.setVisibility(View.VISIBLE);
                }
            }
        });

        mHeader.mReciteHeadTaskIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimStatic=2;
                mTaskIconAnimSet.cancel();
                mContext.startActivity(new Intent(mContext, TaskListActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mHeader.mReciteStartRush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeader.mReciteStartRush.startAnimation(mReciteStartRush);
                mContext.startActivity(new Intent(mContext, ReciteWordActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
        mHeader.mReciteTalent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReciteOpenActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
        mHeader.mReciteSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReciteCheckActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
        mHeader.mReciteDownAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.ShowMusicDownLayout();
            }
        });

        mHeader.mReciteRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ReviseActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mHeader.RecitePk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("pk模块","不会吧");
                mContext.startActivity(new Intent(mContext,WordPkActivity.class));
                ((Activity) mContext).overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recite_head_task)
        LinearLayout mReciteHeadTask;
        @BindView(R.id.recite_star_layout)
        LinearLayout mReciteStarLayout;
        @BindView(R.id.recite_star_hint)
        TextView mReciteStarHint;
        @BindView(R.id.recite_task_icon_head_img)
        RelativeLayout mReciteTaskIconHeadImg;
        @BindView(R.id.recite_head_task_icon)
        LinearLayout mReciteHeadTaskIcon;
        @BindView(R.id.recite_start_rush)
        LinearLayout mReciteStartRush;
        @BindView(R.id.recite_talent)
        TextView mReciteTalent;
        @BindView(R.id.recite_down_audio)
        ImageView mReciteDownAudio;
        @BindView(R.id.recite_sign)
        TextView mReciteSign;
        @BindView(R.id.recite_revise)
        LinearLayout mReciteRevise;
        @BindView(R.id.recite_pk)
        LinearLayout RecitePk;
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder{
        ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}















