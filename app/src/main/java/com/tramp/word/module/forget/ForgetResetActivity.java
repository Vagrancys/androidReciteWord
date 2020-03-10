package com.tramp.word.module.forget;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.LoginActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetResetActivity extends RxBaseActivity {
    @BindView(R.id.forget_out)
    ImageView mForgetOut;
    @BindView(R.id.forget_pass_hint_text)
    EditText mForgetPassHintText;
    @BindView(R.id.forget_passed_hint_text)
    EditText mForgetPassedHintText;
    @BindView(R.id.forget_powerful_one)
    TextView mForgetOne;
    @BindView(R.id.forget_powerful_two)
    TextView mForgetTwo;
    @BindView(R.id.forget_powerful_three)
    TextView mForgetThree;
    @BindView(R.id.forget_button_pass_start)
    TextView mForgetButtonPassStart;

    private int OneColor;
    private int TwoColor;
    private int ThreeColor;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_reset;
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
        mForgetPassHintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passExtra(mForgetPassHintText.getText().toString().trim());
            }
        });

        mForgetButtonPassStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                win();
            }
        });


    }

    private void win(){
        if(mForgetPassHintText.getText().length()==0
                &&mForgetPassedHintText.getText().length()==0
                &&!mForgetPassHintText.getText().equals(mForgetPassedHintText.getText())){
            return;
        }

        startActivity(new Intent(ForgetResetActivity.this, LoginActivity.class));
        finish();
    }

    public void passExtra(String str){
        if(str.length()==0){
            OneColor=getResources().getColor(R.color.register_powerful_color);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }else if(str.matches("^[0-9]+$")){
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }else if (str.matches ("^[a-z]+$"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        } else if (str.matches ("^[A-Z]+$"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Z0-9]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的小写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[a-z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的小写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[a-z0-9]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Za-z]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Za-z]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符小于5个个密码为弱
        else if (str.matches ("^[A-Za-z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.register_powerful_color);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于6个个密码为中
        else if (str.matches ("^[A-Za-z0-9]{6,8}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.register_powerful_color);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches ("^[A-Za-z0-9]{9,16}"))
        {
            OneColor=getResources().getColor(R.color.register_three);
            TwoColor=getResources().getColor(R.color.register_three);
            ThreeColor=getResources().getColor(R.color.register_three);
        }
        mForgetOne.setBackgroundColor(OneColor);
        mForgetTwo.setBackgroundColor(TwoColor);
        mForgetThree.setBackgroundColor(ThreeColor);
    }

}
