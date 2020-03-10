package com.tramp.word.module.common;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/3.
 */

public class RegisterActivity extends RxBaseActivity {
    @BindView(R.id.register_out)
    ImageView mRegisterOut;
    @BindView(R.id.register_login)
    TextView mRegisterLogin;
    @BindView(R.id.register_number_hint_text)
    EditText mRegisterNumberHintText;
    @BindView(R.id.register_number_img)
    ImageView mRegisterNumberImg;
    @BindView(R.id.register_number_error_text)
    TextView mRegisterNumberErrorText;
    @BindView(R.id.register_pass_hint_text)
    EditText mRegisterPassHintText;
    @BindView(R.id.register_powerful_one)
    TextView mRegisterOne;
    @BindView(R.id.register_powerful_two)
    TextView mRegisterTwo;
    @BindView(R.id.register_powerful_three)
    TextView mRegisterThree;
    @BindView(R.id.register_button_start)
    TextView mRegisterButtonStart;
    @BindView(R.id.register_caption_right)
    TextView mRegisterCaptionRight;
    @BindView(R.id.register_extra)
    TextView mRegisterExtra;
    @BindView(R.id.register_extra_img)
    ImageView mRegisterExtraImg;
    @BindView(R.id.register_relative)
    RelativeLayout mRegisterRelative;
    @BindView(R.id.register_cc)
    LinearLayout mRegisterCc;
    @BindView(R.id.register_qq)
    LinearLayout mRegisterQq;
    private int RegisterOneColor;
    private int RegisterTwoColor;
    private int RegisterThreeColor;
    private Animation mTopAnim;
    private Animation mBottomAnim;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle save) {
        mTopAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.word_language_img_top);
        mBottomAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.word_language_img_bottom);
        mRegisterNumberHintText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mRegisterNumberImg.setVisibility(View.GONE);
                    mRegisterNumberErrorText.setVisibility(View.GONE);
                    mRegisterNumberHintText.setBackgroundResource(R.drawable.register_edit_selector_focused);
                }else{
                    if(mRegisterNumberHintText.getText().toString().length()<11){
                        mRegisterNumberHintText.setBackgroundResource(R.drawable.register_edit_selector_error);
                        mRegisterNumberImg.setVisibility(View.VISIBLE);
                        mRegisterNumberErrorText.setVisibility(View.VISIBLE);
                    }else{
                        mRegisterNumberHintText.setBackgroundResource(R.drawable.register_edit_selector_window);
                    }
                }
            }
        });

        mRegisterPassHintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passExtra(mRegisterPassHintText.getText().toString().trim());
            }
        });

        mRegisterButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRegisterNumberImg.getVisibility()==View.VISIBLE){
                    return;
                }
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mRegisterCaptionRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,ContentWedActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mRegisterExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRegisterRelative.getVisibility()==View.VISIBLE){
                    mRegisterExtraImg.startAnimation(mBottomAnim);
                    mRegisterRelative.setVisibility(View.GONE);
                }else{
                    mRegisterRelative.setVisibility(View.VISIBLE);
                    mRegisterExtraImg.startAnimation(mTopAnim);
                }
            }
        });

        mRegisterCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"cctalk",Toast.LENGTH_SHORT).show();
             }
        });

        mRegisterQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"qq",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initToolBar() {

        mRegisterOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });


    }

    public void passExtra(String str){
        if(str.length()==0){
            RegisterOneColor=getResources().getColor(R.color.register_powerful_color);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }else if(str.matches("^[0-9]+$")){
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }else if (str.matches ("^[a-z]+$"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        } else if (str.matches ("^[A-Z]+$"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Z0-9]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的小写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[a-z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的小写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[a-z0-9]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Za-z]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Za-z]{6,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符小于5个个密码为弱
        else if (str.matches ("^[A-Za-z0-9]{1,5}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_one);
            RegisterTwoColor=getResources().getColor(R.color.register_powerful_color);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于6个个密码为中
        else if (str.matches ("^[A-Za-z0-9]{6,8}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_two);
            RegisterTwoColor=getResources().getColor(R.color.register_two);
            RegisterThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches ("^[A-Za-z0-9]{9,16}"))
        {
            RegisterOneColor=getResources().getColor(R.color.register_three);
            RegisterTwoColor=getResources().getColor(R.color.register_three);
            RegisterThreeColor=getResources().getColor(R.color.register_three);
        }
        mRegisterOne.setBackgroundColor(RegisterOneColor);
        mRegisterTwo.setBackgroundColor(RegisterTwoColor);
        mRegisterThree.setBackgroundColor(RegisterThreeColor);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}





















