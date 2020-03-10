package com.tramp.word.module.home.recite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.WordManageViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.widget.WordManageDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/1/25.
 */

public class ReciteWordManageActivity extends RxBaseActivity {
    @BindView(R.id.recite_word_manage_out)
    ImageView mReciteWordManageOut;
    @BindView(R.id.recite_word_book_relative)
    RelativeLayout mReciteRelative;
    @BindView(R.id.recite_word_manage_img)
    ImageView mReciteWordManageImg;
    @BindView(R.id.view_manage_recycler)
    RecyclerView mManageRecycler;
    @BindView(R.id.recite_word_manage_start)
    TextView mReciteManageStart;
    @BindView(R.id.recite_manage_title)
    TextView mReciteManageTitle;
    private int ListStart=1;
    private WordManageViewAdapter mManageAdapter;
    private WordManageDialog mAlert;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word_manage;
    }

    @Override
    protected void initToolBar() {
        mReciteWordManageOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {

        mReciteRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListStart==1){
                    mReciteWordManageImg.setImageResource(R.drawable.icon_word_book_selected);
                    ListStart=2;
                    mReciteManageTitle.setText("已选 1 词");
                    mManageAdapter.notifyDataSetChanged();
                    mReciteManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start));
                }else{
                    mReciteWordManageImg.setImageResource(R.drawable.icon_word_book_unselect);
                    ListStart=1;
                    mReciteManageTitle.setText("单词管理");
                    mManageAdapter.notifyDataSetChanged();
                    mReciteManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start_un));
                }

            }
        });
        mManageAdapter=new WordManageViewAdapter(mManageRecycler,ListStart);
        mManageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mManageRecycler.setAdapter(mManageAdapter);

        mManageAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(mReciteManageStart.getBackground()==getResources().getDrawable(R.drawable.btn_word_manage_start_un)){
                    mReciteManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start));
                }
                mReciteManageTitle.setText("已选 1 词");
            }
        });

        mAlert=new WordManageDialog(this);
        mAlert.setCancelable(false);
        mAlert.setTitle(getResources().getString(R.string.word_manage_title));
        mAlert.setMessage(getResources().getString(R.string.word_manage_message));
        mAlert.setCancelOnClickListener("取消", new WordManageDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mAlert.dismiss();
            }
        });
        mAlert.setOkOnClickListener("确定", new WordManageDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                mAlert.dismiss();
                finish();
            }
        });
        mReciteManageStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlert.show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}







