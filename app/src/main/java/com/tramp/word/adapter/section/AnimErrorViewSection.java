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

public class AnimErrorViewSection extends StatelessSection {
    private Context mContext;
    private ArrayList<DefaultWordInfo> mWords;
    private Random mRandom=new Random();
    private MediaPlayer mMedia;
    private Animation mRotateAnim;
    private int mGate;
    private Activity mActivity;
    private Handler mHandler;
    public AnimErrorViewSection(Activity activity,Context context, ArrayList<DefaultWordInfo> words, int word_gate,Handler handler){
        super(R.layout.item_anim_error_head,R.layout.item_anim_error_item);
        mContext=context;
        mWords=words;
        mGate=word_gate;
        mActivity=activity;
        mMedia= MediaPlayer.create(mContext,R.raw.fail);
        mRotateAnim= AnimationUtils.loadAnimation(mContext,R.anim.rotate_y_anim);
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
        String[] temp=mContext.getString(R.string.iword_level_failed).split("|");
        String feel=temp[mRandom.nextInt(temp.length)];
        mMedia.start();
        mHeader.ErrorGate.setText("第"+mGate+"关");
        mHeader.ErrorFeel.setText(feel);
        mHeader.ErrorHeadText.setText("闯关单词 "+mWords.size());
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
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHolder.NameLinear.startAnimation(mRotateAnim);
                        mHolder.NameLinear.setVisibility(View.VISIBLE);
                        mHolder.MeaningRelative.startAnimation(mRotateAnim);
                        mHolder.MeaningRelative.setVisibility(View.GONE);
                    }
                },3000);
            }
        });
        mHolder.ErrorName.setText(mWords.get(position).getWord_name());
        if(mWords.get(position).getWord_error()==1){
            String[] Temp=mWords.get(position).getWord_error_text().split(",");
            String mError="";
            for (String error:Temp){
                mError=mError.concat(error);
            }
            mHolder.ErrorLinear.setVisibility(View.VISIBLE);
            mHolder.ErrorText.setText(mError);
        }else{
            mHolder.ErrorLinear.setVisibility(View.GONE);
        }
        mHolder.ErrorMeaning.setText(mWords.get(position).getWord_meaning());
        mHolder.ErrorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordContextActivity.launch(mActivity,position,7,mGate);
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return mWords.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.error_gate)
        TextView ErrorGate;
        @BindView(R.id.error_feel)
        TextView ErrorFeel;
        @BindView(R.id.error_head_text)
        TextView ErrorHeadText;
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.error_title)
        LinearLayout NameLinear;
        @BindView(R.id.error_name)
        TextView ErrorName;
        @BindView(R.id.error_linear)
        LinearLayout ErrorLinear;
        @BindView(R.id.error_text)
        TextView ErrorText;
        @BindView(R.id.meaning_relative)
        RelativeLayout MeaningRelative;
        @BindView(R.id.error_meaning)
        TextView ErrorMeaning;
        @BindView(R.id.error_search)
        ImageView ErrorSearch;
        ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
