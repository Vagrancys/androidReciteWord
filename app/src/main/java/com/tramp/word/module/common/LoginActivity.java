package com.tramp.word.module.common;

import android.content.ContentValues;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.LoginInfo;
import com.tramp.word.module.forget.ForgetPassActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;


import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/8.
 */

public class LoginActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.login_register)
    TextView LoginRegister;
    @BindView(R.id.login_user)
    LinearLayout LoginUser;
    @BindView(R.id.login_user_view)
    View LoginUserView;
    @BindView(R.id.login_phone)
    LinearLayout LoginPhone;
    @BindView(R.id.login_phone_view)
    View LoginPhoneView;
    @BindView(R.id.login_user_scroll)
    ScrollView LoginUserScroll;
    @BindView(R.id.login_phone_layout)
    RelativeLayout LoginPhoneLayout;
    @BindView(R.id.login_user_name)
    EditText LoginUserName;
    @BindView(R.id.login_user_linear)
    LinearLayout LoginUserLinear;
    @BindView(R.id.login_user_img)
    ImageView LoginUserImg;
    @BindView(R.id.login_start)
    TextView LoginStart;
    @BindView(R.id.login_user_pass)
    EditText LoginUserPass;
    @BindView(R.id.login_forget)
    TextView LoginForget;
    @BindView(R.id.login_cc)
    LinearLayout LoginCc;
    @BindView(R.id.login_qq)
    LinearLayout LoginQq;
    @BindView(R.id.login_phone_name)
    EditText LoginPhoneName;
    @BindView(R.id.login_phone_img)
    ImageView LoginPhoneImg;
    @BindView(R.id.login_phone_code)
    EditText LoginPhoneCode;
    @BindView(R.id.login_code_text)
    TextView LoginCodeText;
    @BindView(R.id.login_phone_start)
    TextView LoginPhoneStart;
    @BindView(R.id.code_error_text)
    TextView CodeErrorText;
    @BindView(R.id.hint_error_text)
    TextView HintErrorText;
    private CountDownTimer mCountTimer;
    private Snackbar mSnackBar;
    private UserSqlHelper mUserSql;
    private LoginInfo mLoginInfo;
    private Animation mScaleAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle save) {
        mUserSql=new UserSqlHelper(getBaseContext());
        mSnackBar=Snackbar.make(LoginCc,"第三方登录qq",Snackbar.LENGTH_LONG).setAction("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.dismiss();
            }
        });
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        initClick();
    }

    private void initClick(){
        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserScroll.setVisibility(View.VISIBLE);
                LoginPhoneLayout.setVisibility(View.GONE);
                LoginUserView.setBackgroundColor(getResources().getColor(R.color.green_1));
                LoginPhoneView.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        LoginPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserScroll.setVisibility(View.VISIBLE);
                LoginPhoneLayout.setVisibility(View.GONE);
                LoginUserView.setBackgroundColor(getResources().getColor(R.color.white));
                LoginPhoneView.setBackgroundColor(getResources().getColor(R.color.green_1));
            }
        });

        LoginUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    LoginUserLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    LoginUserImg.setImageResource(R.drawable.icon_login_delate_normal);
                    if(LoginUserName.getText().length()>0){
                        LoginUserImg.setVisibility(View.VISIBLE);
                    }else{
                        LoginUserImg.setVisibility(View.GONE);
                    }
                    HintErrorText.setVisibility(View.GONE);
                }else{
                    LoginUserLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                    LoginUserImg.setVisibility(View.GONE);
                    HintErrorText.setVisibility(View.GONE);
                }
            }
        });

        LoginUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LoginUserImg.setImageResource(R.drawable.icon_login_delate_normal);
                if(LoginUserName.getText().length()>0){
                    LoginUserImg.setVisibility(View.VISIBLE);
                }else{
                    LoginUserImg.setVisibility(View.GONE);
                }
            }
        });

        LoginUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserName.setText("");
                LoginUserImg.setVisibility(View.GONE);
            }
        });

        LoginStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginStart.startAnimation(mScaleAnim);
                UserLogin();
            }
        });

        LoginForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPassActivity.launch(LoginActivity.this);
            }
        });

        LoginCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackBar.show();
            }
        });

        LoginQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"qq");
            }
        });

        LoginPhoneName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    LoginPhoneImg.setVisibility(View.GONE);
                    LoginPhoneName.setBackgroundResource(R.drawable.register_edit_selector_focused);
                }else{
                    if(LoginPhoneName.getText().toString().length()<11){
                        LoginPhoneName.setBackgroundResource(R.drawable.register_edit_selector_error);
                        LoginPhoneImg.setVisibility(View.VISIBLE);
                    }else{
                        LoginPhoneName.setBackgroundResource(R.drawable.register_edit_selector_window);
                    }
                }
            }
        });

        mCountTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LoginCodeText.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                LoginCodeText.setText(getResources().getString(R.string.login_code_text));
            }
        };

        LoginCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountTimer.start();
            }
        });

        LoginPhoneStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginPhoneStart.startAnimation(mScaleAnim);
                PhoneLogin();
            }
        });

        LoginPhoneCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    LoginPhoneCode.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    CodeErrorText.setVisibility(View.GONE);
                }else{
                    if( LoginPhoneCode.getText().length()==0){
                        LoginPhoneCode.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        CodeErrorText.setVisibility(View.VISIBLE);
                    }else{
                        LoginPhoneCode.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        CodeErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void PhoneLogin(){
        if(LoginPhoneName.getText().length()==0&&LoginPhoneCode.getText().length()==0){
            Utils.ShowToast(getBaseContext(),"请填写好手机和验证码!");
            return;
        }
        if(!LoginPhoneName.getText().toString().equals("13489807056")){
            Utils.ShowToast(getBaseContext(),"该手机号未注册!");
            return;
        }
        if(!LoginPhoneCode.getText().toString().equals("125891")){
            Utils.ShowToast(getBaseContext(),"验证码错误!");
            return;
        }
        PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,true);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }

    private void UserLogin(){
        if(LoginUserName.getText().length()==0){
            Utils.ShowToast(getBaseContext(),"请填写帐号!");
            return;
        }
        if(LoginUserPass.getText().length()==0){
            Utils.ShowToast(getBaseContext(),"请填写密码!");
            return;
        }
        netLogin();
        mLoginInfo=new LoginInfo();
        mLoginInfo.setUser_name("你好");
        mLoginInfo.setToken("11");
        mLoginInfo.setUser_id(5);
        mLoginInfo.setAvatar("11111");
        mLoginInfo.setLast_login_at("dada");
        mLoginInfo.setRecited_book(5);
        insertUser();
        PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,true);
        Log.e("LoginActivity","mLoginInfo="+mLoginInfo.getBook_status());
        ExitActivity();
        MainActivity.launch(LoginActivity.this,mLoginInfo.getBook_status());
        finish();


    }

    private void netLogin(){
        Retrofits.getUserAPI()
                .getLoginInfo(LoginUserName.getText().toString(),LoginUserPass.getText().toString())
                .enqueue(new Callback<LoginInfo>() {
                    @Override
                    public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                        if(response.code() !=200){
                            Utils.ShowToast(getBaseContext(),"code="+response.code());
                        }else{
                            switch (response.body().getCode()){
                                //帐号未注册
                                case 101:
                                    Utils.ShowToast(getBaseContext(),"该帐号未注册!");
                                    break;
                                //密码错误
                                case 102:
                                    Utils.ShowToast(getBaseContext(),"密码错误!");
                                    break;
                                //登录成功
                                case 103:
                                    mLoginInfo=response.body();
                                    insertUser();
                                    PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,true);
                                    Log.e("LoginActivity","mLoginInfo="+mLoginInfo.getBook_status());
                                    ExitActivity();
                                    MainActivity.launch(LoginActivity.this,mLoginInfo.getBook_status());
                                    finish();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void insertUser(){
        ContentValues values=new ContentValues();
        values.put("_id","1");
        values.put("user_name",mLoginInfo.getUser_name());
        values.put("token",mLoginInfo.getToken());
        values.put("user_id",mLoginInfo.getUser_id());
        values.put("avatar",mLoginInfo.getAvatar());
        values.put("last_login_at",mLoginInfo.getLast_login_at());
        values.put("recited_book",mLoginInfo.getRecited_book());
        mUserSql.insert(values);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LoginRegister.setOnClickListener(new View.OnClickListener() {
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
