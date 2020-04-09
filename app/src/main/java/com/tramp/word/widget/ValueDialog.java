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
 * time  : 2019/06/15
 * version:1.0
 */
public class ValueDialog extends Dialog {
    private TextView ok;
    private ImageView close;
    private TextView cancel;
    private OkClickListener OkClickListener;
    private CancelClickListener CancelClickListener;
    private CloseClickListener CloseClickListener;

    public void setOkClickListener(OkClickListener okClickListener) {
        this.OkClickListener = okClickListener;
    }

    public void setCloseClickListener(CloseClickListener closeClickListener) {
        this.CloseClickListener = CloseClickListener;
    }

    public void setCancelClickListener(CancelClickListener cancelClickListener) {
        this.CancelClickListener = cancelClickListener;
    }

    public ValueDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_value);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initEvent(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkClickListener !=null){
                    OkClickListener.onOkClick();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CloseClickListener !=null){
                    CloseClickListener.onCloseClick();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CancelClickListener !=null){
                    CancelClickListener.onCancelClick();
                }
            }
        });
    }

    private void initView(){
        ok=(TextView) findViewById(R.id.alert_ok);
        cancel=(TextView) findViewById(R.id.alert_cancel);
        close=(ImageView) findViewById(R.id.alert_close);
    }

    public interface OkClickListener{
        void onOkClick();
    }
    public interface CancelClickListener{
        void onCancelClick();
    }

    public interface CloseClickListener{
        void onCloseClick();
    }

}
