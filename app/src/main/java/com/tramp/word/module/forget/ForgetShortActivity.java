package com.tramp.word.module.forget;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.LoginActivity;
import com.tramp.word.utils.Utils;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetShortActivity extends RxBaseActivity {
    @BindView(R.id.forget_out)
    ImageView mForgetOut;
    @BindView(R.id.forget_short_linear)
    LinearLayout mForgetShortLinear;
    @BindView(R.id.forget_short_phone_hint_text)
    EditText mForgetShortPhoneHintText;
    @BindView(R.id.forget_short_img)
    ImageView mForgetShortImg;
    @BindView(R.id.forget_short_error_text)
    TextView mForgetShortErrorText;
    @BindView(R.id.forget_code_text)
    TextView mForgetCodeText;
    @BindView(R.id.forget_code_hint_text)
    EditText mForgetCodeHintText;
    @BindView(R.id.forget_code_error_text)
    TextView mForgetCodeErrorText;
    @BindView(R.id.forget_short_button_start)
    TextView mForgetShortButtonStart;
    @BindView(R.id.forget_bottom_right_text)
    TextView mForgetBottomRightText;

    private CountDownTimer mCountTimer;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_short;
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
    public void initView(Bundle save) {
        mForgetShortPhoneHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    mForgetShortImg.setImageResource(R.drawable.icon_login_delate_normal);
                    mForgetShortImg.setVisibility(View.VISIBLE);
                    mForgetShortErrorText.setVisibility(View.GONE);
                }else{
                    if(mForgetShortPhoneHintText.getText().length()<11){
                        mForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        mForgetShortImg.setImageResource(R.drawable.image_seletor_picdelete);
                        mForgetShortImg.setVisibility(View.VISIBLE);
                        mForgetShortErrorText.setVisibility(View.VISIBLE);
                    }else{
                        mForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        mForgetShortImg.setVisibility(View.GONE);
                        mForgetShortErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });

        mCountTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mForgetCodeText.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                mForgetCodeText.setText(getResources().getString(R.string.login_code_text));
            }
        };

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
                mCountTimer.start();
            }
        });

        mForgetShortButtonStart.setOnClickListener(new View.OnClickListener() {
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
        if(mForgetShortPhoneHintText.getText().length()==0&&mForgetCodeHintText.getText().length()==0){
            return;
        }
        if(!mForgetShortPhoneHintText.getText().equals("13489807056")){
            return;
        }
        if(!mForgetCodeHintText.getText().equals("125891")){
            return;
        }
        startActivity(new Intent(ForgetShortActivity.this,ForgetResetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountTimer.cancel();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
