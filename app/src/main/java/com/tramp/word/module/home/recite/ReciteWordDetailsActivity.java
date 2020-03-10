package com.tramp.word.module.home.recite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.WordDetailsViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/23.
 */

public class ReciteWordDetailsActivity extends RxBaseActivity {
    @BindView(R.id.word_details_back)
    RelativeLayout mWordDetailsBack;
    @BindView(R.id.word_details_out)
    ImageView mWordDetailsOut;
    private Bitmap mDetailsBack;
    @BindView(R.id.word_details_select)
    TextView mWordDetailsSelect;
    @BindView(R.id.word_details_recycler)
    RecyclerView mWordDetailsRecycler;
    @BindView(R.id.word_details_start)
    ImageView mWordDetailsStart;
    private WordDetailsViewAdapter mWordDetailsAdapter;
    private int selectStatus=1;
    private Animation mStartAnim;
    private String Status="Status";
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_details;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        mStartAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme2/bg_street@2x.png");
        mDetailsBack= BitmapFactory.decodeStream(back);
        mWordDetailsBack.setBackground(new BitmapDrawable(mDetailsBack));

        mWordDetailsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWordDetailsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWordDetailsSelect.getText().equals(R.string.word_details_unselect)){
                    mWordDetailsSelect.setText(R.string.word_details_select);
                    mWordDetailsOut.setVisibility(View.GONE);
                    selectStatus=2;
                }else{
                    mWordDetailsSelect.setText(R.string.word_details_unselect);
                    mWordDetailsOut.setVisibility(View.VISIBLE);
                    selectStatus=1;
                }
            }
        });

        mWordDetailsAdapter=new WordDetailsViewAdapter(mWordDetailsRecycler);

        mWordDetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        mWordDetailsRecycler.setAdapter(mWordDetailsAdapter);

        mWordDetailsAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(selectStatus==1){
                    Intent intent=new Intent(getBaseContext(),ReciteWordContextActivity.class);
                    intent.putExtra(Status,"details");
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
                }
            }
        });

        mWordDetailsStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDetailsStart.startAnimation(mStartAnim);
                startActivity(new Intent(getBaseContext(),ReciteAnimActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}










