package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/24
 * version:1.0
 */
public class AboutUpdateDialog extends Dialog {
    private TextView ok;
    private TextView cancel;
    private TextView title;
    private TextView message;
    private String titleStr;
    private String messageStr;
    private String okStr;
    private String cancelStr;
    private OkOnClickListener OkOnClickListener;
    private CancelOnClickListener CancelOnClickListener;

    public void setCancelOnClickListener(String str,CancelOnClickListener cancelOnclickListener) {
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

    public AboutUpdateDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_about_layout);
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
        if(messageStr !=null){
            message.setText(messageStr);
        }
        if(okStr!=null){
            ok.setText(okStr);
        }
        if(cancelStr !=null){
            cancel.setText(cancelStr);
        }
    }

    private void initView(){
        ok=(TextView) findViewById(R.id.default_ok);
        cancel=(TextView) findViewById(R.id.default_cancel);
        title=(TextView) findViewById(R.id.default_title);
        message=(TextView) findViewById(R.id.default_message);
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }

    public void setMessage(String message) {
        this.messageStr = message;
    }

    public interface OkOnClickListener{
        void onOkClick();
    }

    public interface CancelOnClickListener{
        void onCancelClick();
    }
}
