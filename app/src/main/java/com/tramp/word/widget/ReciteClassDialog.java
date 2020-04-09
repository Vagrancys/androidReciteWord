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
public class ReciteClassDialog extends Dialog {
    private TextView ok;
    private TextView size;
    private String sizeStr;
    private String okStr;
    private PkBookDialog.OkOnClickListener OkOnClickListener;

    public void setOkOnClickListener(String str, PkBookDialog.OkOnClickListener okOnclickListener) {
        if(str!=null){
            okStr=str;
        }
        this.OkOnClickListener = okOnclickListener;
    }

    public ReciteClassDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_recite_class);
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
    }

    private void initData(){
        if(sizeStr !=null){
            size.setText(sizeStr);
        }

        if(okStr!=null){
            ok.setText(okStr);
        }
    }

    private void initView(){
        ok=(TextView) findViewById(R.id.alert_word_ok);
        size=(TextView) findViewById(R.id.recite_class_size);
    }

    public String getSize() {
        return sizeStr;
    }

    public void setSize(String size) {
        this.sizeStr = size;
    }

    public interface OkOnClickListener{
        void onOkClick();
    }
}
