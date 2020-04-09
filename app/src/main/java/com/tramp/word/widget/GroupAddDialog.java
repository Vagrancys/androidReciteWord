package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupAddDialog extends Dialog{
    private TextView ok;
    private TextView cancel;
    private TextView title;
    private TextView message;
    private String titleStr;
    private String messageStr;
    private String okStr;
    private String cancelStr;
    private String EditString;
    private OkOnClickListener OkOnClickListener;
    private CancelOnClickListener CancelOnClickListener;
    private LinearLayout linear;
    private EditText editText;

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

    public GroupAddDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_group_add);
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
                    EditString = editText.getText().toString();
                    OkOnClickListener.onOkClick(EditString);
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
        ok=(TextView) findViewById(R.id.alert_word_manage_ok);
        cancel=(TextView) findViewById(R.id.alert_word_manage_cancel);
        title=(TextView) findViewById(R.id.alert_word_manage_title);
        message=(TextView) findViewById(R.id.alert_word_manage_message);
        linear=(LinearLayout) findViewById(R.id.linear);
        editText=(EditText) findViewById(R.id.alert_me_edit);
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }

    public void setMessage(String message) {
        this.messageStr = message;
    }

    public interface OkOnClickListener{
        void onOkClick(String text);
    }

    public interface CancelOnClickListener{
        void onCancelClick();
    }

    public String getEditText(){
        return editText.getText().toString();
    }
}
