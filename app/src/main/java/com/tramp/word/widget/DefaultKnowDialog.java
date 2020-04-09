package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/15
 * version:1.0
 */
public class DefaultKnowDialog extends Dialog {
    private TextView DefaultKnow;
    private ImageView DefaultImg;
    private TextView DefaultTitle;
    private TextView DefaultMessage;
    private TextView DefaultHint;
    private int mTitle;
    private int mImg;
    private int mMessage;
    private int mHint;
    private String mKnow;
    private PkBookDialog.OkOnClickListener OkOnClickListener;

    public void setOkOnClickListener(String str, PkBookDialog.OkOnClickListener okOnclickListener) {
        if(str!=null){
            mKnow=str;
        }
        this.OkOnClickListener = okOnclickListener;
    }

    public DefaultKnowDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_default_know);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    private void initEvent(){
        DefaultKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkOnClickListener !=null){
                    OkOnClickListener.onOkClick();
                }
            }
        });
    }

    private void initData(){
        if(mImg !=0){
            DefaultImg.setImageResource(mImg);
        }
        if(mTitle !=0){
            DefaultTitle.setText(mTitle);
        }
        if(mMessage !=0){
            DefaultMessage.setText(mMessage);
        }
        if(mHint !=0){
            DefaultHint.setText(mHint);
        }

        if(mKnow!=null){
            DefaultKnow.setText(mKnow);
        }
    }

    private void initView(){
        DefaultKnow=(TextView) findViewById(R.id.default_know);
        DefaultImg=(ImageView) findViewById(R.id.default_img);
        DefaultTitle=(TextView) findViewById(R.id.default_title);
        DefaultMessage=(TextView) findViewById(R.id.default_message);
        DefaultHint=(TextView) findViewById(R.id.default_hint);
    }

    public void setDefaultImg(int defaultImg) {
        mImg = defaultImg;
    }

    public void setDefaultTitle(int defaultTitle) {
        mTitle = defaultTitle;
    }

    public void setDefaultMessage(int defaultMessage) {
        mMessage = defaultMessage;
    }

    public void setDefaultHint(int defaultHint) {
        mHint = defaultHint;
    }

    public interface OkOnClickListener{
        void onOkClick();
    }
}
