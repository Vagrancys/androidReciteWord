package com.tramp.word.module.word;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.tramp.word.R;
import com.tramp.word.adapter.section.ReciteKnowGateViewSection;
import com.tramp.word.adapter.section.ReciteRepeatListViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.module.home.recite.ReciteWordManageActivity;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/25.
 */

public class ReciteRepeatFragment extends RxLazyFragment {
    @BindView(R.id.recite_repeat_recycler)
    RecyclerView mReciteRepeatRecycler;
    @BindView(R.id.recite_item_manage)
    LinearLayout mReciteItemManage;
    @BindView(R.id.recite_list_lobby)
    ImageView mReciteListLobby;
    private SectionedRecyclerViewAdapter mAdapter;
    private PopupWindow mPopup;
    public static ReciteRepeatFragment newInstance(){
        return new ReciteRepeatFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recite_repeat;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }
        loadData();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void loadData() {
        mAdapter=new SectionedRecyclerViewAdapter();
        mAdapter.addSection(new ReciteRepeatListViewSection(getContext(),getActivity()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {

        mReciteRepeatRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mReciteRepeatRecycler.setAdapter(mAdapter);
        mReciteItemManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),ReciteWordManageActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mPopup=new PopupWindow(getContext());
        View mView=LayoutInflater.from(getContext()).inflate(R.layout.popup_recite_list_lobby,null);
        mPopup.setContentView(mView);
        mPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_recite_item_button));
        mPopup.setFocusable(true);
        mPopup.setAnimationStyle(R.style.popup_recite_lobby_style_anim);
        LinearLayout mPopupLinear=(LinearLayout) mView.findViewById(R.id.popup_recite_linear);
        mPopupLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();
            }
        });

        mReciteListLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.showAsDropDown(mReciteListLobby,0,20);
            }
        });
    }
}
















