package com.tramp.word.module.setting.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.ScreenBackAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.user.UserPhotoInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/3/4.
 */

public class SettingScreenBackActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.screen_back_recycler)
    RecyclerView ScreenBackRecycler;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;

    private ScreenBackAdapter mScreenBackAdapter;
    private int number=0;
    private List<UserPhotoInfo.Photo> list=new ArrayList<>();
    private int SCREENBACK_CODE=40;
    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_back;
    }

    @Override
    public void initView(Bundle save) {
        number= PreferencesUtils.getInt(ConstantUtils.SCREEN_NUMBER,0);
        initNet();
        initClick();

    }
    private void initClick(){
        mScreenBackAdapter=new ScreenBackAdapter(ScreenBackRecycler,number,list);
        ScreenBackRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        ScreenBackRecycler.setAdapter(mScreenBackAdapter);
        mScreenBackAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                SettingScreenImgActivity.launch(SettingScreenBackActivity.this,list.get(position).getPhoto_url()
                        ,SCREENBACK_CODE,list.get(position).getPhoto_id());
                number=list.get(position).getPhoto_id();
                mScreenBackAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initNet(){
        Retrofits.getUserAPI()
                .getUserPhotoInfo()
                .enqueue(new Callback<UserPhotoInfo>() {
                    @Override
                    public void onResponse(Call<UserPhotoInfo> call, Response<UserPhotoInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            list=response.body().getPhotos();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据加载失败!");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserPhotoInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    private void finishTask(){
        mScreenBackAdapter.notifyDataSetChanged();
    }

    private void initEmpty(){
        CommonEmpty.setVisibility(View.VISIBLE);
        ScreenBackRecycler.setVisibility(View.GONE);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.screen_back_title));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SCREENBACK_CODE&&resultCode==41&&data!=null){
            if(data.getBooleanExtra("img_status",false)){
                PreferencesUtils.putInt(ConstantUtils.SCREEN_NUMBER,data.getIntExtra("number",0));
            }
        }
    }
}
