package com.tramp.word.module.user;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.MeShareAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.UserShareInfo;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/25.
 */

public class MeShareActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.me_share_recycler)
    RecyclerView mMeShareRecycler;
    @BindView(R.id.me_share_number)
    TextView MeShareNumber;
    @BindView(R.id.me_share_time)
    TextView MeShareTime;
    private MeShareAdapter mMeShareAdapter;
    private UserShareInfo.Share share;
    private int user_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_me_share;
    }

    @Override
    public void initView(Bundle save) {
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        initNet();
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setImageResource(R.drawable.icon_close);
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.me_share_title));
    }

    public void initNet(){
        Retrofits.getUserAPI()
                .getUserShareInfo(user_id)
                .enqueue(new Callback<UserShareInfo>() {
                    @Override
                    public void onResponse(Call<UserShareInfo> call, Response<UserShareInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            share=response.body().getShare();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据错误!");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserShareInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络失效!");
                    }
                });
    }
    public void finishTask(){
        MeShareNumber.setText(share.getShare_date());
        MeShareTime.setText(share.getShare_year()+"年"+share.getShare_month()+"月");
        mMeShareAdapter=new MeShareAdapter(mMeShareRecycler,share.getDays());
        mMeShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),7));
        mMeShareRecycler.setAdapter(mMeShareAdapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
