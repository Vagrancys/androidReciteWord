package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/1/26.
 */

public class InformTimeDialog extends Dialog {
    private TextView ok;
    private TextView cancel;
    private TextView title;
    private String titleStr;
    private String okStr;
    private String cancelStr;
    private OkOnClickListener OkOnClickListener;
    private CancelOnClickListener CancelOnClickListener;
    private TimeNumberPicker mTimeOne;
    private TimeNumberPicker mTimeTwo;
    private int One;
    private int Two;

    public int getOne() {
        return One;
    }

    public int getTwo() {
        return Two;
    }

    public void setOne(int one) {
        One = one;
    }

    public void setTwo(int two) {
        Two = two;
    }

    public void setCancelOnClickListener(String str, CancelOnClickListener cancelOnclickListener) {
        if(str !=null){
            cancelStr=str;
        }
        this.CancelOnClickListener = cancelOnclickListener;
    }

    public void setOkOnClickListener(String str,OkOnClickListener okOnclickListener) {
        if(str!=null){
            okStr=str;
        }
        this.OkOnClickListener = okOnclickListener;
    }

    public InformTimeDialog(Context context){
        super(context,R.style.WordManageDialog);
    }

    public TimeNumberPicker getTimeOne() {
        return mTimeOne;
    }

    public TimeNumberPicker getTimeTwo() {
        return mTimeTwo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_inform_time);
        setCanceledOnTouchOutside(false);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        initView();
        initData();
        initEvent();
    }

    private void initEvent(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkOnClickListener !=null){
                    OkOnClickListener.onOkClick();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CancelOnClickListener !=null){
                    CancelOnClickListener.onCancelClick();
                }
            }
        });
    }

    private void initData(){
        if(titleStr !=null){
            title.setText(titleStr);
        }
        if(okStr!=null){
            ok.setText(okStr);
        }
        if(cancelStr !=null){
            cancel.setText(cancelStr);
        }
    }

    private void initView(){
        ok=(TextView) findViewById(R.id.alert_word_manage_ok);
        cancel=(TextView) findViewById(R.id.alert_word_manage_cancel);
        title=(TextView) findViewById(R.id.alert_word_manage_title);
        mTimeOne=(TimeNumberPicker) findViewById(R.id.alert_inform_time);
        mTimeTwo=(TimeNumberPicker) findViewById(R.id.alert_inform_times);
        mTimeOne.setNumberPickerDividerColor(mTimeOne);
        mTimeOne.setMaxValue(24);
        mTimeOne.setMinValue(0);
        mTimeOne.setValue(20);
        mTimeOne.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mTimeOne.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setOne(newVal);
            }
        });

        mTimeTwo.setNumberPickerDividerColor(mTimeOne);
        mTimeTwo.setMaxValue(60);
        mTimeTwo.setMinValue(0);
        mTimeTwo.setValue(30);
        mTimeTwo.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mTimeTwo.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setTwo(newVal);
            }
        });
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }


    public interface OkOnClickListener{
        public void onOkClick();
    }

    public interface CancelOnClickListener{
        public void onCancelClick();
    }

}




































