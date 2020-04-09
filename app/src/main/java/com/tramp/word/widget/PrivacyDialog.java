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
 * time  : 2019/05/15
 * version:1.0
 */
public class PrivacyDialog extends Dialog {
    private TextView ok;
    private TextView cancel;
    private String okStr;
    private String cancelStr;
    private String TitleStr;
    private String MessageStr;
    private String HintStr;
    private TextView PrivacyTitle;
    private TextView PrivacyMessage;
    private TextView PrivacyHint;
    private PkBookDialog.OkOnClickListener OkOnClickListener;
    private PkBookDialog.CancelOnClickListener CancelOnClickListener;

    public void setCancelOnClickListener(String str, PkBookDialog.CancelOnClickListener cancelOnclickListener) {
        if(str !=null){
            cancelStr=str;
        }
        this.CancelOnClickListener = cancelOnclickListener;
    }

    public void setOkOnClickListener(String str, PkBookDialog.OkOnClickListener okOnclickListener) {
        if(str!=null){
            okStr=str;
        }
        this.OkOnClickListener = okOnclickListener;
    }

    public PrivacyDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_privacy_layout);
        setCanceledOnTouchOutside(false);
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
        if(TitleStr!=null){
            PrivacyTitle.setText(TitleStr);
        }
        if(MessageStr!=null){
            PrivacyMessage.setText(MessageStr);
        }
        if(HintStr!=null){
            PrivacyHint.setText(HintStr);
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
        PrivacyTitle=(TextView) findViewById(R.id.privacy_title);
        PrivacyMessage=(TextView) findViewById(R.id.privacy_message);
        PrivacyHint=(TextView) findViewById(R.id.privacy_hint);
    }

    public void setMessage(String message) {
        MessageStr = message;
    }

    public void setTitle(String title) {
        TitleStr = title;
    }

    public void setHint(String hint) {
        HintStr = hint;
    }

    public interface OkOnClickListener{
        void onOkClick();
    }

    public interface CancelOnClickListener{
        void onCancelClick();
    }
}
