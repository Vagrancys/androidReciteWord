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
import com.tramp.word.adapter.section.ReciteListViewSection;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/25.
 */

public class WordRepeatFragment extends RxLazyFragment {
    @BindView(R.id.repeat_recycler)
    RecyclerView RepeatRecycler;
    @BindView(R.id.manage_layout)
    LinearLayout ManageLayout;
    @BindView(R.id.repeat_lobby)
    ImageView RepeatLobby;
    @BindView(R.id.repeat_number)
    TextView RepeatNumber;
    @BindView(R.id.repeat_linear)
    LinearLayout RepeatLinear;
    @BindView(R.id.repeat_empty)
    LinearLayout RepeatEmpty;
    private Handler mHandler=new Handler();
    private SectionedRecyclerViewAdapter mAdapter;
    private PopupWindow mPopup;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private UserSqlHelper mUser;
    private int word_id;
    public static WordRepeatFragment newInstance(){
        return new WordRepeatFragment();
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
        mUser=new UserSqlHelper(getContext());
        word_id=mUser.WordId();
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
                Cursor cursor=mUser.WordContentAll(word_id,3,0);
                while (cursor.moveToNext()){
                    word=new DefaultWordInfo();
                    word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                    word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                    word.setWord_meaning(cursor.getString(cursor.getColumnIndex("word_meaning")));
                    word.setWord_time(cursor.getInt(cursor.getColumnIndex("word_time")));
                    words.add(word);
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
            RepeatNumber.setText(String.valueOf(words.size()));
            mAdapter.addSection(new ReciteListViewSection(getActivity(),words,3,mHandler));
            mAdapter.notifyDataSetChanged();
            RepeatEmpty.setVisibility(View.GONE);
            RepeatLinear.setVisibility(View.VISIBLE);
        }else{
            RepeatEmpty.setVisibility(View.VISIBLE);
            RepeatLinear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initRecyclerView() {
        mAdapter=new SectionedRecyclerViewAdapter();
        RepeatRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        RepeatRecycler.setAdapter(mAdapter);
        ManageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordManageActivity.launch(getSupportActivity(),0);
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

        RepeatLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.showAsDropDown(RepeatLobby,0,20);
            }
        });
    }
}
















