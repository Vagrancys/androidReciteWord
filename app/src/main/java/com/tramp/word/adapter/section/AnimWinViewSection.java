package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.module.word.WordContextActivity;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/2/1.
 */

public class AnimWinViewSection extends StatelessSection {
    private Context mContext;
    private ArrayList<DefaultWordInfo> mWord;
    private int mGate;
    private int mStar;
    private Animation mWinStarAnim;
    private Animation mRotateAnim;
    private MediaPlayer mMedia;
    private Random mRandom=new Random();
    private Activity mActivity;
    private Handler mHandler;
    public AnimWinViewSection(Activity activity, Context context, ArrayList<DefaultWordInfo> words, int gate, int star, Handler handler){
        super(R.layout.item_anim_win_head,R.layout.item_anim_win_item);
        mContext=context;
        mWord=words;
        mStar=star;
        mGate=gate;
        mActivity=activity;
        mMedia=MediaPlayer.create(mContext,R.raw.success);
        mWinStarAnim= AnimationUtils.loadAnimation(mContext,R.anim.win_star_anim);
        mRotateAnim=AnimationUtils.loadAnimation(mContext,R.anim.rotate_y_anim);
        mHandler=handler;
    }
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        String[] temp=null;
        String feel=null;
        if(mStar==0){
            temp=mContext.getString(R.string.iword_level_pass_0_star).split("|");
            feel=temp[mRandom.nextInt(temp.length)];
        }
        if(mStar>0){
            temp=mContext.getString(R.string.iword_level_pass_1_star).split("|");
            feel=temp[mRandom.nextInt(temp.length)];
            mHeader.WinImgLeft.setVisibility(View.VISIBLE);
            mHeader.WinImgLeft.startAnimation(mWinStarAnim);
        }
        if(mStar>1){
            temp=mContext.getString(R.string.iword_level_pass_2_star).split("|");
            feel=temp[mRandom.nextInt(temp.length)];
            mHeader.WinImgContext.setVisibility(View.VISIBLE);
            mHeader.WinImgContext.startAnimation(mWinStarAnim);
        }
        if(mStar>2){
            temp=mContext.getString(R.string.iword_level_pass_3_star).split("|");
            feel=temp[mRandom.nextInt(temp.length)];
            mHeader.WinImgRight.setVisibility(View.VISIBLE);
            mHeader.WinImgRight.startAnimation(mWinStarAnim);
        }
        mMedia.start();
        mHeader.WinGateText.setText("第"+mGate+"关");
        mHeader.WinGateFeel.setText(feel);
        mHeader.WinHeadText.setText(String.valueOf(mWord.size()));
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.NameLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.NameLinear.startAnimation(mRotateAnim);
                mHolder.NameLinear.setVisibility(View.GONE);
                mHolder.MeaningRelative.startAnimation(mRotateAnim);
                mHolder.MeaningRelative.setVisibility(View.VISIBLE);
                mHandler.post(()->{
                    mHolder.NameLinear.startAnimation(mRotateAnim);
                    mHolder.NameLinear.setVisibility(View.VISIBLE);
                    mHolder.MeaningRelative.startAnimation(mRotateAnim);
                    mHolder.MeaningRelative.setVisibility(View.GONE);
                });
            }
        });
        mHolder.WinName.setText(mWord.get(position).getWord_name());
        if(mWord.get(position).getWord_error()==1){
            String[] Temp=mWord.get(position).getWord_error_text().split(",");
            String mError="";
            for (String error:Temp){
                mError=mError.concat(error);
            }
            mHolder.ErrorLinear.setVisibility(View.VISIBLE);
            mHolder.ErrorText.setText(mError);
        }else{
            mHolder.ErrorLinear.setVisibility(View.GONE);
        }
        mHolder.WinMeaning.setText(mWord.get(position).getWord_meaning());
        mHolder.WinSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordContextActivity.launch(mActivity,position,7,mGate);
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return mWord.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.win_img_left)
        ImageView WinImgLeft;
        @BindView(R.id.win_img_right)
        ImageView WinImgRight;
        @BindView(R.id.win_img_context)
        ImageView WinImgContext;
        @BindView(R.id.win_gate_text)
        TextView WinGateText;
        @BindView(R.id.win_gate_feel)
        TextView WinGateFeel;
        @BindView(R.id.win_head_text)
        TextView WinHeadText;
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name_linear)
        LinearLayout NameLinear;
        @BindView(R.id.win_name)
        TextView WinName;
        @BindView(R.id.error_linear)
        LinearLayout ErrorLinear;
        @BindView(R.id.error_text)
        TextView ErrorText;
        @BindView(R.id.meaning_relative)
        RelativeLayout MeaningRelative;
        @BindView(R.id.win_meaning)
        TextView WinMeaning;
        @BindView(R.id.win_search)
        ImageView WinSearch;
        ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
