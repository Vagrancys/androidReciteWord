package com.tramp.word.module.word;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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
import com.tramp.word.adapter.section.ReciteItemViewSection;
import com.tramp.word.adapter.section.ReciteListViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/24.
 */

public class WordKnowFragment extends RxLazyFragment {
    @BindView(R.id.select_layout)
    LinearLayout SelectLayout;
    @BindView(R.id.know_recycler)
    RecyclerView KnowRecycler;
    @BindView(R.id.manage_layout)
    LinearLayout ManageLayout;
    @BindView(R.id.select_img)
    ImageView SelectImg;
    @BindView(R.id.select_text)
    TextView SelectText;
    @BindView(R.id.know_empty)
    LinearLayout KnowEmpty;
    @BindView(R.id.know_linear)
    LinearLayout KnowLinear;
    @BindView(R.id.know_number)
    TextView KnowNumber;
    private Handler mHandler=new Handler();
    private SectionedRecyclerViewAdapter mAdapter;
    private int ListStatic=1;
    private PopupWindow mPopup;
    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    public static  WordKnowFragment newInstance(){
        return new WordKnowFragment();
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
        mUser=new UserSqlHelper(getContext());
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        mAdapter=new SectionedRecyclerViewAdapter();
        loadData();
    }

    @Override
    protected void loadData() {
        mAdapter.removeAllSections();
        words.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(ListStatic==0){
                    Cursor cursor=mUser.WordContentAll(mUser.WordId(),1,0);
                    int gate=1;
                    while (cursor.moveToNext()){
                        word=new DefaultWordInfo();
                        word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                        word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                        word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                        words.add(word);
                        if(gate!=cursor.getInt(cursor.getColumnIndex("word_gate"))){
                            mAdapter.addSection(new ReciteItemViewSection(getActivity(),words,gate,1));
                            gate++;
                        }
                    }
                    cursor.close();
                }else if(ListStatic==1){
                    Cursor cursor=mUser.WordContentAll(mUser.WordId(),2,0);
                    while (cursor.moveToNext()){
                        word=new DefaultWordInfo();
                        word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                        word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                        word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                        words.add(word);
                    }
                    mAdapter.addSection(new ReciteListViewSection(getActivity(),words,2,mHandler));
                    cursor.close();
                }
                mHandler.post(()->{
                   finishTask();
                });
            }
        }).start();
    }

    @Override
    protected void finishTask() {
        if(words.size()>0){
            KnowNumber.setText(String.valueOf(words.size()));
            mAdapter.notifyDataSetChanged();
            KnowEmpty.setVisibility(View.GONE);
            KnowLinear.setVisibility(View.VISIBLE);
        }else{
            KnowEmpty.setVisibility(View.VISIBLE);
            KnowLinear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initRecyclerView() {
        KnowRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        KnowRecycler.setAdapter(mAdapter);
        ManageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordManageActivity.launch(getSupportActivity(),1);
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
                SelectImg.setImageResource(R.drawable.icon_list_g_disable);
                SelectText.setText(R.string.recite_item_list);
                loadData();
                mPopup.dismiss();
            }
        });
        mPopupLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListStatic=2;
                SelectImg.setImageResource(R.drawable.icon_list_a_disable);
                SelectText.setText(R.string.recite_item_letter);
                loadData();
                mPopup.dismiss();
            }
        });

        SelectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPopup.isShowing()){
                    SelectLayout.setBackground(getResources().getDrawable(R.drawable.btn_recite_item_button_two));
                    mPopup.showAsDropDown(SelectLayout,0,20);
                }else{
                    mPopup.dismiss();
                    SelectLayout.setBackground(getResources().getDrawable(R.drawable.btn_recite_item_button));
                }
            }
        });
    }

}









