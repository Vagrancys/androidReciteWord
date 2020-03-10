package com.tramp.word.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.forget.ForgetPassActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/8.
 */

public class LoginActivity extends RxBaseActivity {
    @BindView(R.id.login_out)
    ImageView mLoginOut;
    @BindView(R.id.login_register)
    TextView mLoginRegister;
    @BindView(R.id.login_select_ordinary)
    TextView mLoginSelectOrdinary;
    @BindView(R.id.login_select_ordinary_view)
    View mLoginSelectOrdinaryView;
    @BindView(R.id.login_select_phone)
    TextView mLoginSelectPhone;
    @BindView(R.id.login_select_phone_view)
    View mLoginSelectPhoneView;
    @BindView(R.id.login_ordinary)
    RelativeLayout mLoginOrdinary;
    @BindView(R.id.login_phone)
    RelativeLayout mLoginPhone;
    @BindView(R.id.login_user_hint_text)
    EditText mLoginUserHintText;
    @BindView(R.id.login_user_linear)
    LinearLayout mLoginUserLinear;
    @BindView(R.id.login_user_img)
    ImageView mLoginUserImg;
    @BindView(R.id.login_button_start)
    TextView mLoginButtonStart;
    @BindView(R.id.login_pass_hint_text)
    EditText mLoginPassHintText;
    @BindView(R.id.login_ordinary_forget)
    TextView mLoginOrdinaryForget;
    @BindView(R.id.login_cc)
    LinearLayout mLoginCc;
    @BindView(R.id.login_qq)
    LinearLayout mLoginQq;
    @BindView(R.id.login_phone_hint_text)
    EditText mLoginPhoneHintText;
    @BindView(R.id.login_phone_img)
    ImageView mLoginPhoneImg;
    @BindView(R.id.login_code_hint_text)
    EditText mLoginCodeHintText;
    @BindView(R.id.login_code_text)
    TextView mLoginCodeText;
    @BindView(R.id.login_phone_button_start)
    TextView mLoginPhoneButtonStart;
    @BindView(R.id.login_code_error_text)
    TextView mLoginCodeErrorText;
    @BindView(R.id.login_phone_error_text)
    TextView mLoginPhoneErrorText;
    private CountDownTimer mCountTimer;
    private Snackbar mSnackBar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle save) {
        mSnackBar=Snackbar.make(mLoginCc,"第三方登录qq",Snackbar.LENGTH_LONG).setAction("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.dismiss();
            }
        });
        mLoginSelectOrdinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginOrdinary.setVisibility(View.VISIBLE);
                mLoginPhone.setVisibility(View.GONE);
                mLoginSelectOrdinaryView.setBackgroundColor(getResources().getColor(R.color.green_1));
                mLoginSelectPhoneView.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        mLoginSelectPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPhone.setVisibility(View.VISIBLE);
                mLoginOrdinary.setVisibility(View.GONE);
                mLoginSelectOrdinaryView.setBackgroundColor(getResources().getColor(R.color.white));
                mLoginSelectPhoneView.setBackgroundColor(getResources().getColor(R.color.green_1));
            }
        });

        mLoginUserHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mLoginUserLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    mLoginUserImg.setImageResource(R.drawable.icon_login_delate_normal);
                    if(mLoginUserHintText.getText().length()>0){
                        mLoginUserImg.setVisibility(View.VISIBLE);
                    }else{
                        mLoginUserImg.setVisibility(View.GONE);
                    }
                    mLoginPhoneErrorText.setVisibility(View.GONE);
                }else{
                    mLoginUserLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                    mLoginUserImg.setVisibility(View.GONE);
                    mLoginPhoneErrorText.setVisibility(View.GONE);
                }
            }
        });
        mLoginUserHintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLoginUserImg.setImageResource(R.drawable.icon_login_delate_normal);
                if(mLoginUserHintText.getText().length()>0){
                    mLoginUserImg.setVisibility(View.VISIBLE);
                }else{
                    mLoginUserImg.setVisibility(View.GONE);
                }
            }
        });

        mLoginUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginUserHintText.setText("");
                mLoginUserImg.setVisibility(View.GONE);
            }
        });
        mLoginButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });

        mLoginOrdinaryForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mLoginCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.show();
            }
        });

        mLoginQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"qq");
            }
        });

        mLoginPhoneHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mLoginPhoneImg.setVisibility(View.GONE);
                    mLoginPhoneHintText.setBackgroundResource(R.drawable.register_edit_selector_focused);
                }else{
                    if(mLoginPhoneHintText.getText().toString().length()<11){
                        mLoginPhoneHintText.setBackgroundResource(R.drawable.register_edit_selector_error);
                        mLoginPhoneImg.setVisibility(View.VISIBLE);
                    }else{
                        mLoginPhoneHintText.setBackgroundResource(R.drawable.register_edit_selector_window);
                    }
                }
            }
        });
        mCountTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mLoginCodeText.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                mLoginCodeText.setText(getResources().getString(R.string.login_code_text));
            }
        };

        mLoginCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountTimer.start();
            }
        });

        mLoginPhoneButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneLogin();
            }
        });

        mLoginCodeHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mLoginCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    mLoginCodeErrorText.setVisibility(View.GONE);
                }else{
                    if( mLoginCodeHintText.getText().length()==0){
                        mLoginCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        mLoginCodeErrorText.setVisibility(View.VISIBLE);
                    }else{
                        mLoginCodeHintText.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        mLoginCodeErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void PhoneLogin(){
        if(mLoginPhoneHintText.getText().length()==0&&mLoginCodeHintText.getText().length()==0){
            Utils.ShowToast(getBaseContext(),"请填写好帐号和密码!");
            return;
        }
        if(!mLoginPassHintText.getText().toString().equals("13489807056")){
            Utils.ShowToast(getBaseContext(),"该手机号未注册!");
            return;
        }
        if(!mLoginCodeHintText.getText().toString().equals("125891")){
            Utils.ShowToast(getBaseContext(),"验证码错误!");
            return;
        }
        PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,true);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }

    private void UserLogin(){
        if(mLoginUserHintText.getText().length()==0&&mLoginPassHintText.getText().length()==0){
            Utils.ShowToast(getBaseContext(),"请填写好帐号和密码!");
            return;
        }
        if(!mLoginUserHintText.getText().toString().equals("w937895433")){
            Utils.ShowToast(getBaseContext(),"该帐号未注册!");
            return;
        }
        if(!mLoginPassHintText.getText().toString().equals("wangzhihua519.")){
            Utils.ShowToast(getBaseContext(),"密码错误!");
            return;
        }
        PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,true);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }

    @Override
    protected void initToolBar() {
        mLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountTimer.cancel();
    }
}
