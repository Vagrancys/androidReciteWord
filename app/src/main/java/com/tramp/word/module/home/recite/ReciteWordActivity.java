package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.ReciteBookViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/21.
 */

public class ReciteWordActivity extends RxBaseActivity {
    @BindView(R.id.recite_book_out)
    ImageView mReciteBookOut;
    @BindView(R.id.recite_book_img)
    ImageView mReciteBookImg;
    @BindView(R.id.recite_book_recycler)
    RecyclerView mReciteBookRecycler;
    @BindView(R.id.recite_star_img)
    ImageView mReciteStarImg;

    private ReciteBookViewAdapter mReciteBookViewAdapter;
    private Matrix mMatrix=new Matrix();
    private Bitmap mBackgroundImg;
    private Bitmap mMissionImg;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word;
    }

    @Override
    protected void initToolBar() {

        mReciteBookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme0/bg_desert@2x.png");
        mBackgroundImg= BitmapFactory.decodeStream(back);
        InputStream is= AssetsUtils.getAssetsOpen(getBaseContext(),"mission_assets@2x.png");
        mMissionImg= BitmapFactory.decodeStream(is);
        mReciteBookRecycler.setBackground(new BitmapDrawable(mBackgroundImg));

        mReciteBookImg.setImageBitmap(Bitmap.createBitmap(mMissionImg,100,520,95,95,mMatrix,false));

        mMatrix.setRotate(-90);
        mReciteBookOut.setImageBitmap(Bitmap.createBitmap(mMissionImg,108,285,40,25,mMatrix,false));

        mReciteStarImg.setImageBitmap(Bitmap.createBitmap(mMissionImg,105,315,45,45));

        mReciteBookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteWordActivity.this,ReciteWordListActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mReciteBookViewAdapter=new ReciteBookViewAdapter(mReciteBookRecycler,this);
        mReciteBookRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mReciteBookRecycler.setAdapter(mReciteBookViewAdapter);
        mReciteBookRecycler.scrollToPosition(mReciteBookViewAdapter.getItemCount()-1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}












