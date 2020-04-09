package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/2/9.
 */

public class MainProgressDialog extends Dialog {
    private TextView ProgressNumber;
    private TextView ProgressDown;
    private TextView ProgressTitle;
    private int mMainProgressVal;
    private int Max=300;
    private String Title;
    private String Down;
    private String Number;
    private ProgressBar MainProgress;
    private Drawable mDrawable;
    public MainProgressDialog(Context context){
        super(context,R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_progress_dialog);
        setCanceledOnTouchOutside(false);
        MainProgress=(ProgressBar) findViewById(R.id.main_progress);
        ProgressNumber=(TextView) findViewById(R.id.main_progress_number);
        ProgressDown=(TextView) findViewById(R.id.main_progress_down);
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

    public void setProgress(int value){
        MainProgress.setProgress(value);
    }

    public void setOneProgress(int value){
        MainProgress.setSecondaryProgress(value);
    }

    public void setMax(int max) {
        if(MainProgress !=null){
            MainProgress.setMax(max);
        }else {
            Max = max;
        }
    }

    public void setDrawable(Drawable drawable){
        if(MainProgress !=null){
            MainProgress.setProgressDrawable(drawable);
        }else {
            mDrawable=drawable;
        }
    }

    public void setTitle(String title) {
        if(ProgressTitle !=null){
            ProgressTitle.setText(title);
        }else {
            Title = title;
        }
    }

    public void setDown(String down) {
        if(ProgressDown !=null){
            ProgressDown.setText(down);
        }else {
            Down = down;
        }
    }

    public void setNumber(String number) {
        if(ProgressNumber !=null){
            ProgressNumber.setText(number);
        }else{
            Number = number;
        }
    }
}

















