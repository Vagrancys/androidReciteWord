package com.tramp.word.module.forget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetEmailActivity extends RxBaseActivity {
    @BindView(R.id.forget_out)
    ImageView mForgetOut;
    @BindView(R.id.forget_email_hint_text)
    EditText mForgetEmailPhoneHintText;
    @BindView(R.id.forget_code_text)
    TextView mForgetCodeText;
    @BindView(R.id.forget_code_hint_text)
    EditText mForgetCodeHintText;
    @BindView(R.id.forget_code_error_text)
    TextView mForgetCodeErrorText;
    @BindView(R.id.forget_email_button_start)
    TextView mForgetEmailButtonStart;
    @BindView(R.id.forget_bottom_right_text)
    TextView mForgetBottomRightText;
    
    private Random mRandom=new Random();
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_email;
    }

    @Override
    public void initView(Bundle save) {
        mForgetCodeHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mForgetCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    mForgetCodeErrorText.setVisibility(View.GONE);
                }else{
                    if(mForgetCodeHintText.getText().length()==0){
                        mForgetCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        mForgetCodeErrorText.setVisibility(View.VISIBLE);
                    }else{
                        mForgetCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        mForgetCodeErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });

        mForgetCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetCodeText.setText(mRandom.nextInt(10000));
            }
        });

        mForgetEmailButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });
        mForgetBottomRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"没有制作申诉哦!");
            }
        });
    }
    
    private void Next(){
        if(mForgetCodeHintText.getText().length()==0&&mForgetEmailPhoneHintText.getText().length()==0){
            return;
        }
        if(!mForgetCodeHintText.getText().equals("125891")){
            return;
        }
        startActivity(new Intent(ForgetEmailActivity.this,ForgetResetActivity.class));
    }
    @Override
    protected void initToolBar() {
        mForgetOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
