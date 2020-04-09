package com.tramp.word.module.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.AutoMaticAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/3/2.
 */

public class SettingAutoMaticActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.automatic_recycler)
    RecyclerView AutoMaticRecycler;
    private String[] title={
            "关闭","一次","两次","三次"
    };
    private int AUTOMATIC_RESULT=20;
    private AutoMaticAdapter mAutoMaticAdapter;
    private int status;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_automatic;
    }

    @Override
    public void initView(Bundle save) {
        status=PreferencesUtils.getInt(ConstantUtils.SETTING_AUTO_CODE,0);
        mAutoMaticAdapter=new AutoMaticAdapter(AutoMaticRecycler,title,status);
        AutoMaticRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        AutoMaticRecycler.setAdapter(mAutoMaticAdapter);
        mAutoMaticAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                status=position;
                mAutoMaticAdapter.notifyDataSetChanged();
                PreferencesUtils.putInt(ConstantUtils.SETTING_AUTO_CODE,position);
                initResult();
            }
        });
    }

    private void initResult(){
        Intent intent=new Intent();
        intent.putExtra(ConstantUtils.SETTING_AUTO_CODE,status);
        setResult(AUTOMATIC_RESULT,intent);
        finish();
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initResult();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.automatic_text));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}










