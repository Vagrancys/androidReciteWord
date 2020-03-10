package com.tramp.word.module.setting.screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.ScreenBackAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

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

    private ScreenBackAdapter mScreenBackAdapter;
    private int number=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_back;
    }

    @Override
    public void initView(Bundle save) {
        mScreenBackAdapter=new ScreenBackAdapter(ScreenBackRecycler,number);
        ScreenBackRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        ScreenBackRecycler.setAdapter(mScreenBackAdapter);
        mScreenBackAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Intent intent=new Intent(SettingScreenBackActivity.this,SettingScreenImgActivity.class);
                intent.putExtra("number",position);
                startActivity(intent);
                Utils.StarActivityInAnim(SettingScreenBackActivity.this);
                number=position;
                mScreenBackAdapter.notifyDataSetChanged();
            }
        });
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
}
