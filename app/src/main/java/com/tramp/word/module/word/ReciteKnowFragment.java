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
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.ReciteKnowGateViewSection;
import com.tramp.word.adapter.section.ReciteKnowLetterViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.module.home.recite.ReciteWordManageActivity;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/24.
 */

public class ReciteKnowFragment extends RxLazyFragment {
    @BindView(R.id.recite_item_list)
    LinearLayout mReciteItemList;
    @BindView(R.id.recite_know_recycler)
    RecyclerView mReciteKnowRecycler;
    @BindView(R.id.recite_item_manage)
    LinearLayout mReciteItemMangage;
    @BindView(R.id.recite_item_list_img)
    ImageView mReciteItemListImg;
    @BindView(R.id.recite_item_list_text)
    TextView mReciteItemListText;
    private SectionedRecyclerViewAdapter mAdapter;
    private int ListStatic=1;
    private PopupWindow mPopup;

    public static  ReciteKnowFragment newInstance(){
        return new ReciteKnowFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recite_know;
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
    }

    @Override
    protected void loadData() {
        mAdapter=new SectionedRecyclerViewAdapter();
        if(ListStatic==1){
            mAdapter.addSection(new ReciteKnowGateViewSection(getActivity(),getActivity()));
        }else{
            for (int i=0;i<5;i++){
                mAdapter.addSection(new ReciteKnowLetterViewSection(getActivity(),getActivity()));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mReciteKnowRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mReciteKnowRecycler.setAdapter(mAdapter);
        mReciteItemMangage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),ReciteWordManageActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mPopup=new PopupWindow(getContext());
        View mView= LayoutInflater.from(getContext()).inflate(R.layout.popup_recite_list,null);
        mPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopup.setFocusable(true);
        mPopup.setContentView(mView);
        mPopup.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_recite_item_button));
        mPopup.setAnimationStyle(R.style.popup_recite_style_anim);
        LinearLayout mPopupGate=(LinearLayout) mView.findViewById(R.id.popup_recite_gate);
        LinearLayout mPopupLetter=(LinearLayout) mView.findViewById(R.id.popup_recite_letter);
        mPopupGate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListStatic=1;
                mReciteItemListImg.setImageResource(R.drawable.icon_list_g_disable);
                mReciteItemListText.setText(R.string.recite_item_list);
                loadData();
                mPopup.dismiss();
            }
        });
        mPopupLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListStatic=2;
                mReciteItemListImg.setImageResource(R.drawable.icon_list_a_disable);
                mReciteItemListText.setText(R.string.recite_item_letter);
                loadData();
                mPopup.dismiss();
            }
        });

        mReciteItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPopup.isShowing()){
                    mReciteItemList.setBackground(getResources().getDrawable(R.drawable.btn_recite_item_button_two));
                    mPopup.showAsDropDown(mReciteItemList,0,20);
                }else{
                    mPopup.dismiss();
                    mReciteItemList.setBackground(getResources().getDrawable(R.drawable.btn_recite_item_button));
                }
            }
        });
    }

}









