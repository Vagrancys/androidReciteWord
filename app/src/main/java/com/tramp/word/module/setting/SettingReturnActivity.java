package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.UserMoneyInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingReturnActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.setting_return)
    LinearLayout SettingReturn;
    @BindView(R.id.return_swipe_refresh)
    SwipeRefreshLayout ReturnSwipeRefresh;
    @BindView(R.id.return_help)
    TextView ReturnHelp;
    @BindView(R.id.return_money)
    TextView ReturnMoney;
    private UserMoneyInfo.Money money;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_return;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultTitle.setText(getResources().getString(R.string.return_default_title));
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        initData();
    }

    private void initData(){
        ReturnSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.blue));
        ReturnSwipeRefresh.post(()->{
            ReturnSwipeRefresh.setRefreshing(true);
            loadData();
        });
        ReturnSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ReturnSwipeRefresh.setRefreshing(true);
                loadData();
            }
        });

        ReturnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingReturnActivity.this, WedCommonActivity.class));
                Utils.StarActivityInAnim(SettingReturnActivity.this);
            }
        });
    }

    private void loadData(){
        Retrofits.getUserAPI()
                .getUserMoneyInfo(new UserSqlHelper(getBaseContext()).UserId())
                .enqueue(new Callback<UserMoneyInfo>() {
                    @Override
                    public void onResponse(Call<UserMoneyInfo> call, Response<UserMoneyInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            money=response.body().getMoney();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserMoneyInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    private void initEmpty(){
        CommonEmpty.setVisibility(View.VISIBLE);
        SettingReturn.setVisibility(View.GONE);
    }

    private void finishTask(){
        ReturnSwipeRefresh.setRefreshing(false);
        ReturnMoney.setText("￥"+money.getMoney_number());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}













