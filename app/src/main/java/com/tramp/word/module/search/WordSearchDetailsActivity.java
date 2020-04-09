package com.tramp.word.module.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/12
 * version:1.0
 */

public class WordSearchDetailsActivity extends RxBaseActivity{
    @BindView(R.id.search_details_out)
    ImageView SearchDetailsOut;
    @BindView(R.id.search_details_relative)
    RelativeLayout SearchDetailsRelative;
    @BindView(R.id.search_details_img)
    ImageView SearchDetailsImg;
    @BindView(R.id.search_details_text)
    TextView SearchDetailsText;
    private int WordState=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_search_details;
    }

    @Override
    protected void initToolBar() {
        SearchDetailsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        SearchDetailsRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WordState==0){
                    SearchDetailsImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_toolbar_scb_highlight));
                    SearchDetailsText.setText(getResources().getString(R.string.search_details_texts));
                    WordState=1;
                }else{
                    SearchDetailsImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_toolbar_scb_normal));
                    SearchDetailsText.setText(getResources().getString(R.string.search_details_text));
                    WordState=0;
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
