package com.tramp.word.module.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.module.common.LoginActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetResetActivity extends RxBaseActivity {
    @BindView(R.id.app_toolbar)
    AppBarLayout AppToolbar;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.forget_pass_name)
    EditText ForgetPassName;
    @BindView(R.id.forget_passed_name)
    EditText ForgetPassedName;
    @BindView(R.id.forget_powerful_one)
    TextView ForgetOne;
    @BindView(R.id.forget_powerful_two)
    TextView ForgetTwo;
    @BindView(R.id.forget_powerful_three)
    TextView ForgetThree;
    @BindView(R.id.forget_pass_start)
    TextView ForgetPassStart;

    private int OneColor;
    private int TwoColor;
    private int ThreeColor;
    private String user_phone;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_reset;
    }

    @Override
    protected void initToolBar() {
        AppToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        DefaultOut.setImageResource(R.drawable.common_back_normal);
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.forget_reset_title_text));
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            user_phone=intent.getStringExtra(ConstantUtils.PHONE_NAME);
        }
        ForgetPassName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passExtra(ForgetPassName.getText().toString().trim());
            }
        });

        ForgetPassStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePass();
            }
        });


    }

    private void UpdatePass(){
        if(ForgetPassName.getText().length()==0
                &&ForgetPassedName.getText().length()==0
                &&!ForgetPassName.getText().equals(ForgetPassedName.getText())){
            return;
        }
        Retrofits.getUserAPI().getForgetPassInfo(user_phone,ForgetPassName.getText().toString())
                .enqueue(new Callback<DefaultInfo>() {
                    @Override
                    public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                        if(response.body().getCode()==200){
                            startActivity(new Intent(ForgetResetActivity.this, LoginActivity.class));
                            finish();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_pass_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void passExtra(String str){
        if(str.length()==0){
            OneColor=getResources().getColor(R.color.white);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }else if(str.matches("^[0-9]+$")){
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }else if (str.matches ("^[a-z]+$"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        } else if (str.matches ("^[A-Z]+$"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Z0-9]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的小写字母和数字，输入的字符小于7个密码为弱
        else if (str.matches ("^[a-z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的小写字母和数字，输入的字符大于7个密码为中
        else if (str.matches ("^[a-z0-9]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和小写字母，输入的字符小于7个密码为弱
        else if (str.matches ("^[A-Za-z]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和小写字母，输入的字符大于7个密码为中
        else if (str.matches ("^[A-Za-z]{6,16}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和小写字母和数字，输入的字符小于5个个密码为弱
        else if (str.matches ("^[A-Za-z0-9]{1,5}"))
        {
            OneColor=getResources().getColor(R.color.register_one);
            TwoColor=getResources().getColor(R.color.white);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于6个个密码为中
        else if (str.matches ("^[A-Za-z0-9]{6,8}"))
        {
            OneColor=getResources().getColor(R.color.register_two);
            TwoColor=getResources().getColor(R.color.register_two);
            ThreeColor=getResources().getColor(R.color.white);
        }
        //输入的大写字母和小写字母和数字，输入的字符大于8个密码为强
        else if (str.matches ("^[A-Za-z0-9]{9,16}"))
        {
            OneColor=getResources().getColor(R.color.register_three);
            TwoColor=getResources().getColor(R.color.register_three);
            ThreeColor=getResources().getColor(R.color.register_three);
        }
        ForgetOne.setBackgroundColor(OneColor);
        ForgetTwo.setBackgroundColor(TwoColor);
        ForgetThree.setBackgroundColor(ThreeColor);
    }

    public static void launch(Activity activity,String user_phone){
        Intent intent=new Intent(activity,ForgetResetActivity.class);
        intent.putExtra(ConstantUtils.PHONE_NAME,user_phone);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
