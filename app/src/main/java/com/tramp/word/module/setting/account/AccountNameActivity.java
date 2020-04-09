package com.tramp.word.module.setting.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/28.
 */

public class AccountNameActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;

    @BindView(R.id.account_name_edit)
    EditText AccountNameEdit;
    @BindView(R.id.account_name_delete)
    ImageView AccountNameDelete;
    @BindView(R.id.account_name_start)
    TextView AccountNameStart;
    private String title;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_name;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initResult();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.account_name_edit_text));
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            title=intent.getStringExtra(ConstantUtils.ACCOUNT_NAME);
            AccountNameEdit.setText(title);
            AccountNameStart.setEnabled(false);
            AccountNameDelete.setVisibility(View.VISIBLE);
        }else{
            AccountNameEdit.setText("");
        }
        AccountNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!AccountNameEdit.getText().toString().equals(title)){
                    AccountNameStart.setEnabled(true);
                }else{
                    AccountNameStart.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AccountNameDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountNameDelete.setVisibility(View.GONE);
                AccountNameEdit.setText("");
            }
        });

        AccountNameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AccountNameEdit.getText().toString().equals(title)){
                    Retrofits.getUserAPI().getUpdateNameInfo(new UserSqlHelper(getBaseContext()).UserId(),AccountNameEdit.getText().toString())
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body()!=null&&response.body().getCode()==200){
                                        Utils.ShowToast(AccountNameActivity.this,"修改用户名成功！");
                                        initResult();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),"网络失效了!");
                                }
                            });
                }
            }
        });
    }

    private void initResult(){
        int NAME_CODE=31;
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.ACCOUNT_NAME,AccountNameEdit.getText().toString());
        setResult(NAME_CODE,intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,String user_name,int code){
        Intent intent=new Intent(activity,AccountNameActivity.class);
        intent.putExtra(ConstantUtils.ACCOUNT_NAME,user_name);
        activity.startActivityForResult(intent,code);
        Utils.StarActivityInAnim(activity);
    }
}







