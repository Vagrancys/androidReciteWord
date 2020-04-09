package com.tramp.word.module.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
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

import java.util.Random;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetEmailActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.app_toolbar)
    AppBarLayout AppToolbar;
    @BindView(R.id.forget_email_linear)
    LinearLayout ForgetEmailLinear;
    @BindView(R.id.forget_email_name)
    EditText ForgetEmailName;
    @BindView(R.id.forget_email_img)
    ImageView ForgetEmailImg;
    @BindView(R.id.email_error_text)
    TextView EmailErrorText;
    @BindView(R.id.forget_code_text)
    TextView ForgetCodeText;
    @BindView(R.id.forget_code_name)
    EditText ForgetCodeName;
    @BindView(R.id.code_error_text)
    TextView CodeErrorText;
    @BindView(R.id.forget_email_start)
    TextView ForgetEmailStart;
    @BindView(R.id.forget_help)
    TextView ForgetHelp;
    @BindView(R.id.forget_email)
    RelativeLayout ForgetEmail;
    @BindView(R.id.forget_done)
    LinearLayout ForgetDone;
    @BindView(R.id.forget_email_done)
    TextView ForgetEmailDone;
    @BindView(R.id.forget_email_title)
    TextView ForgetEmailTitle;
    
    private Random mRandom=new Random();
    private int random_code;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_email;
    }

    @Override
    public void initView(Bundle save) {
        ForgetEmailName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ForgetEmailLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    ForgetEmailImg.setImageResource(R.drawable.icon_login_delate_normal);
                    ForgetEmailImg.setVisibility(View.VISIBLE);
                    EmailErrorText.setVisibility(View.GONE);
                }else{
                    if(ForgetEmailName.getText().length()<11){
                        ForgetEmailLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        ForgetEmailImg.setImageResource(R.drawable.image_seletor_picdelete);
                        ForgetEmailImg.setVisibility(View.VISIBLE);
                        EmailErrorText.setVisibility(View.VISIBLE);
                    }else{
                        ForgetEmailLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        ForgetEmailImg.setVisibility(View.GONE);
                        EmailErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });

        ForgetCodeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ForgetCodeName.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    CodeErrorText.setVisibility(View.GONE);
                }else{
                    if(ForgetCodeName.getText().length()==0){
                        ForgetCodeName.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        CodeErrorText.setVisibility(View.VISIBLE);
                    }else{
                        ForgetCodeName.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        CodeErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });


        ForgetCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random_code=mRandom.nextInt(10000);
                ForgetCodeText.setText(random_code);
            }
        });

        ForgetEmailStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
            }
        });
        ForgetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_help_text));
            }
        });
        ForgetEmailDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_email_done));
            }
        });
    }
    
    private void SendEmail(){
        if(ForgetCodeName.getText().length()==0&&ForgetEmailName.getText().length()==0){
            return;
        }
        if(!ForgetCodeName.getText().toString().equals(random_code+"")){
            return;
        }
        Retrofits.getUserAPI().getForgetEmailInfo(ForgetEmailName.getText().toString())
                .enqueue(new Callback<DefaultInfo>() {
                    @Override
                    public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            ForgetEmail.setVisibility(View.GONE);
                            ForgetEmailTitle.setText(ForgetEmailName.getText().toString());
                            ForgetDone.setVisibility(View.VISIBLE);
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_email_error));
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
        AppToolbar.setBackgroundColor(getResources().getColor(R.color.white));
        DefaultOut.setImageResource(R.drawable.common_back_normal);
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.email_title_text));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,ForgetEmailActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
