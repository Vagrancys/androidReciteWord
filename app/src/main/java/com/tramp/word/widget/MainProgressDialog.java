package com.tramp.word.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tramp.word.R;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/9.
 */

public class MainProgressDialog extends AlertDialog{
    private TextView mMainProgressNumber;

    private TextView mMainProgressDown;
    private TextView mMainProgressTitle;
    private boolean mHasStared=true;
    private int mMainProgressVal;
    private int Max=300;
    private int progress=0;
    private String Title;
    private Handler mHandler;
    private String Down;
    private String Number;
    private ProgressBar mMainProgress;
    private Drawable mDrawable;
    public MainProgressDialog(Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_progress_dialog);
        mMainProgress=(ProgressBar) findViewById(R.id.main_progress);
        mMainProgressNumber=(TextView) findViewById(R.id.main_progress_number);
        mMainProgressDown=(TextView) findViewById(R.id.main_progress_down);
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mMainProgressNumber.setText(progress+"KB / 300KB");
            }
        };
        onProgressChanged();
        if(Title !=null){
            setTitle(Title);
        }
        if(Down !=null){
            setDown(Down);
        }

        if(Number !=null){
            setNumber(Number);
        }
        if(Max >0){
            setMax(Max);
        }
        if(mMainProgressVal>0){
            setProgress(mMainProgressVal);
        }

    }
    private void onProgressChanged(){
        mHandler.sendEmptyMessage(0);
    }


    public void setProgress(int value){
        if(mHasStared){
            mMainProgress.setProgress(value);
            progress=value;
            onProgressChanged();
        }else {
            mMainProgressVal=value;
        }
    }

    public void setOneProgress(int value){
        if(mHasStared){
            mMainProgress.setProgress(value);
            progress=value;
        }else {
            mMainProgressVal=value;
        }
    }

    public void setTwoProgress(int value){
        if(mHasStared){
            mMainProgress.setProgress(value);
            progress=value;
        }else {
            mMainProgressVal=value;
        }
    }

    public void setMax(int max) {
        if(mMainProgress !=null){
            mMainProgress.setMax(max);
        }else {
            Max = max;
        }
    }

    public void setDrawable(Drawable drawable){
        if(mMainProgress !=null){
            mMainProgress.setProgressDrawable(drawable);
        }else {
            mDrawable=drawable;
        }
    }

    public void setTitle(String title) {
        if(mMainProgressTitle !=null){
            mMainProgressTitle.setText(title);
        }else {
            Title = title;
        }
    }

    public void setDown(String down) {
        if(mMainProgressDown !=null){
            mMainProgressDown.setText(down);
        }else {
            Down = down;
        }
    }

    public void setNumber(String number) {
        if(mMainProgressNumber !=null){
            mMainProgressNumber.setText(number);
        }else{
            Number = number;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHasStared=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStared=false;
    }


}

















