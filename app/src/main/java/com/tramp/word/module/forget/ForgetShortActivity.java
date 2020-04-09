package com.tramp.word.module.forget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Administrator on 2019/2/5.
 */

public class ForgetShortActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.app_toolbar)
    AppBarLayout AppToolbar;
    @BindView(R.id.forget_short_linear)
    LinearLayout ForgetShortLinear;
    @BindView(R.id.forget_short_phone)
    EditText ForgetShortPhone;
    @BindView(R.id.forget_short_img)
    ImageView ForgetShortImg;
    @BindView(R.id.short_error_text)
    TextView ShortErrorText;
    @BindView(R.id.forget_code_text)
    TextView ForgetCodeText;
    @BindView(R.id.forget_code_name)
    EditText ForgetCodeName;
    @BindView(R.id.code_error_text)
    TextView CodeErrorText;
    @BindView(R.id.forget_short_start)
    TextView ForgetShortStart;
    @BindView(R.id.forget_help)
    TextView ForgetHelp;

    private CountDownTimer mCountTimer;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_short;
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
        DefaultTitle.setText(getResources().getString(R.string.short_title_text));
    }

    @Override
    public void initView(Bundle save) {
        ForgetShortPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_focused));
                    ForgetShortImg.setImageResource(R.drawable.icon_login_delate_normal);
                    ForgetShortImg.setVisibility(View.VISIBLE);
                    ShortErrorText.setVisibility(View.GONE);
                }else{
                    if(ForgetShortPhone.getText().length()<11){
                        ForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_error));
                        ForgetShortImg.setImageResource(R.drawable.image_seletor_picdelete);
                        ForgetShortImg.setVisibility(View.VISIBLE);
                        ShortErrorText.setVisibility(View.VISIBLE);
                    }else{
                        ForgetShortLinear.setBackground(getResources().getDrawable(R.drawable.register_edit_selector_window));
                        ForgetShortImg.setVisibility(View.GONE);
                        ShortErrorText.setVisibility(View.GONE);
                    }
                }
            }
        });

        mCountTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ForgetCodeText.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                ForgetCodeText.setText(getResources().getString(R.string.login_code_text));
            }
        };

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
                mCountTimer.start();
            }
        });

        ForgetShortStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });

        ForgetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_help_text));
            }
        });
    }

    private void Next(){
        if(ForgetShortPhone.getText().length()==0&&ForgetCodeName.getText().length()==0){
            return;
        }
        Retrofits.getUserAPI().getForgetShortInfo(ForgetShortPhone.getText().toString(),
                ForgetCodeName.getText().toString()).enqueue(new Callback<DefaultInfo>() {
            @Override
            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                if(response.body()!=null &&response.body().getCode()==200){
                    ForgetResetActivity.launch(ForgetShortActivity.this,ForgetShortPhone.getText().toString());
                }else{
                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_short_error));
                }
            }

            @Override
            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
            }
        });
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

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,ForgetShortActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
