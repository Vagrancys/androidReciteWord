package com.tramp.word.module.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/3.
 */

public class RegisterActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_login)
    TextView DefaultLogin;
    @BindView(R.id.register_name)
    EditText RegisterName;
    @BindView(R.id.register_name_img)
    ImageView RegisterNameImg;
    @BindView(R.id.name_error_text)
    TextView NameErrorText;
    @BindView(R.id.register_pass)
    EditText RegisterPass;
    @BindView(R.id.register_powerful_one)
    TextView RegisterOne;
    @BindView(R.id.register_powerful_two)
    TextView RegisterTwo;
    @BindView(R.id.register_powerful_three)
    TextView RegisterThree;
    @BindView(R.id.register_start)
    TextView RegisterStart;
    @BindView(R.id.register_hint)
    TextView RegisterHint;
    @BindView(R.id.register_more)
    TextView RegisterMore;
    @BindView(R.id.register_more_img)
    ImageView RegisterMoreImg;
    @BindView(R.id.register_more_linear)
    RelativeLayout RegisterMoreLinear;
    @BindView(R.id.register_cc)
    LinearLayout RegisterCc;
    @BindView(R.id.register_qq)
    LinearLayout RegisterQq;
    private int RegisterOneColor;
    private int RegisterTwoColor;
    private int RegisterThreeColor;
    private Animation mRotateAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle save) {
        mRotateAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_rotate_anim);
    }

    public void initClick(){
        RegisterName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    RegisterNameImg.setVisibility(View.GONE);
                    NameErrorText.setVisibility(View.GONE);
                    RegisterName.setBackgroundResource(R.drawable.register_edit_selector_focused);
                }else{
                    if(RegisterName.getText().toString().length()<11){
                        RegisterName.setBackgroundResource(R.drawable.register_edit_selector_error);
                        RegisterNameImg.setVisibility(View.VISIBLE);
                        RegisterName.setVisibility(View.VISIBLE);
                    }else{
                        RegisterName.setBackgroundResource(R.drawable.register_edit_selector_window);
                    }
                }
            }
        });

        RegisterPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passExtra(RegisterPass.getText().toString().trim());
            }
        });

        RegisterStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterData();
            }
        });

        RegisterHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,ContentWedActivity.class));
                Utils.StarActivityInAnim(RegisterActivity.this);
            }
        });

        RegisterMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RegisterMoreLinear.getVisibility()==View.VISIBLE){
                    RegisterMoreImg.startAnimation(mRotateAnim);
                    RegisterMoreLinear.setVisibility(View.GONE);
                }else{
                    RegisterMoreLinear.setVisibility(View.VISIBLE);
                    RegisterMoreImg.startAnimation(mRotateAnim);
                }
            }
        });

        RegisterCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.register_cc_title));
            }
        });

        RegisterQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.register_qq_title));
            }
        });
    }

    private void RegisterData(){
        if(RegisterName.getText().length()==0&&RegisterPass.getText().length()==0){
            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.register_hint_error));
            return;
        }
        Retrofits.getUserAPI().getRegisterNameInfo(RegisterName.getText().toString(),RegisterPass.getText().toString())
                .enqueue(new Callback<DefaultInfo>() {
                    @Override
                    public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            Utils.StarActivityInAnim(RegisterActivity.this);
                            finish();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.register_error_title));
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });

    }

    @Override
    protected void initToolBar() {

        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                Utils.StarActivityInAnim(RegisterActivity.this);
            }
        });


    }

    public void passExtra(String str){
        if(str.length()==0){
            RegisterOneColor=getResources().getColor(R.color.white_1);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }else if(str.matches("^[0-9]+$")){
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }else if (str.matches ("^[a-z]+$"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        } else if (str.matches ("^[A-Z]+$"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Z0-9]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的小写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[a-z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的小写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[a-z0-9]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和小写字母，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Za-z]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和小写字母，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Za-z]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和小写字母和数字，输入的字符小于5个个密码为弱
        else if (str.matches ("^[A-Za-z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.white_1);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于6个个密码为中
        else if (str.matches ("^[A-Za-z0-9]{6,8}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.white_1);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches ("^[A-Za-z0-9]{9,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_three);
            RegisterTwoColor=getResources().getColor(R.color.register_three);
            RegisterThreeColor=getResources().getColor(R.color.register_three);
        }
        RegisterOne.setBackgroundColor(RegisterOneColor);
        RegisterTwo.setBackgroundColor(RegisterTwoColor);
        RegisterThree.setBackgroundColor(RegisterThreeColor);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}





















