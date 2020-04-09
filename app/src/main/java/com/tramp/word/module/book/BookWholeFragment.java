package com.tramp.word.module.book;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.BookLeftViewAdapter;
import com.tramp.word.adapter.BookRightViewAdapter;
import com.tramp.word.adapter.WholeLanguageLeftAdapter;
import com.tramp.word.adapter.section.WordWholeItemViewSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.entity.book.BookListInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/12.
 */

public class BookWholeFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.view_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.word_whole_left_layout)
    LinearLayout mWordWholeLeftLayout;
    @BindView(R.id.word_whole_right_layout)
    LinearLayout mWordWholeRightLayout;
    @BindView(R.id.word_book_menu_left_text)
    TextView mWordBookMenuLeftText;
    @BindView(R.id.word_book_menu_left_img)
    ImageView mWordBookMenuLeftImg;
    @BindView(R.id.word_language_recycler)
    RecyclerView mWordLanguageRecycler;
    @BindView(R.id.word_book_right_head)
    LinearLayout mWordBookRightHead;
    @BindView(R.id.word_book_right_common)
    LinearLayout mWordBookRightCommon;
    @BindView(R.id.word_book_menu_right_text)
    TextView mWordBookMenuRightText;
    @BindView(R.id.word_book_menu_right_img)
    ImageView mWordBookMenuRightImg;
    @BindView(R.id.book_left_recycler)
    RecyclerView mBookLeftRecycler;
    @BindView(R.id.book_right_recycler)
    RecyclerView mBookRightRecycler;
    @BindView(R.id.word_whole_empty)
    LinearLayout WordWholeEmpty;
    @BindView(R.id.word_whole_item_empty)
    LinearLayout WordWholeItemEmpty;
    private int Language=0;
    private int OneNumber;
    private int TwoNumber;
    private Animation mRotateAnim;
    private SectionedRecyclerViewAdapter mSectionViewAdapter;
    private WholeLanguageLeftAdapter mWholeLanguageAdapter;
    private BookLeftViewAdapter mBookLeftViewAdapter;
    private BookRightViewAdapter mBookRightViewAdapter;
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private ArrayList<BookItemInfo.Book> Items=new ArrayList<>();
    public ArrayList<BookListInfo.One> mOnes=new ArrayList<>();
    public ArrayList<BookListInfo.Two> mTwos=new ArrayList<>();
    private String[] mTitle={
            "英语","日语","韩语",
            "法语","德语","西班牙语",
            "意大利语","俄语","泰语",
            "汉语"
    };

    public static BookWholeFragment newInstance(){
        return new BookWholeFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_whole;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mRotateAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.default_rotate_anim);
        mTopEnterAnim= AnimationUtils.loadAnimation(getActivity(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getActivity(),R.anim.default_top_exit_anim);
        isPrepared=true;
        Language=PreferencesUtils.getInt(ConstantUtils.LANGUAGE,0);
        OneNumber=PreferencesUtils.getInt(ConstantUtils.ONE_NUMBER,0);
        TwoNumber=PreferencesUtils.getInt(ConstantUtils.TWO_NUMBER,0);
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        loadBookList();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(()->{
            clearData();
            loadData();
        });
    }

    @Override
    protected void loadData() {
        ShowDataEmpty();
        Retrofits.getBookAPI().getBookItemInfo(Language,OneNumber,TwoNumber)
                .enqueue(new Callback<BookItemInfo>() {
                    @Override
                    public void onResponse(Call<BookItemInfo> call, Response<BookItemInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Items.clear();
                            Items.addAll(response.body().getItems());
                            if(Items.size()>0){
                                finishTask();
                            }else{
                                HideDataEmpty();
                            }
                        }else{
                            HideDataEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookItemInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void loadBookList(){
        Retrofits.getBookAPI().getBookListInfo(Language,OneNumber)
                .enqueue(new Callback<BookListInfo>() {
                    @Override
                    public void onResponse(Call<BookListInfo> call, Response<BookListInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            mOnes.clear();
                            mTwos.clear();
                            mOnes.addAll(response.body().getOnes());
                            mTwos.addAll(response.body().getTwos());
                            String text;
                            if(mOnes.size()>0&&Language==0){
                                text=mOnes.get(OneNumber).getOne_name()+".";
                                mBookLeftRecycler.setVisibility(View.VISIBLE);
                                mOnes.get(OneNumber).setStatus(1);
                            }else{
                                text="";
                                mBookLeftRecycler.setVisibility(View.GONE);
                            }
                            if(mTwos.size()>0){
                                text=text+mTwos.get(TwoNumber).getTwo_name();
                                mTwos.get(TwoNumber).setStatus(1);
                            }
                            mWordBookMenuRightText.setText(text);
                            mBookLeftViewAdapter.notifyDataSetChanged();
                            mBookRightViewAdapter.notifyDataSetChanged();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookListInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    private void ShowDataEmpty(){
        WordWholeItemEmpty.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
    }

    public void HideDataEmpty(){
        mSwipeRefreshLayout.setRefreshing(false);
        WordWholeItemEmpty.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
    }

    public void initEmpty(){
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        WordWholeEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void finishTask() {
        WordWholeEmpty.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        mSectionViewAdapter.addSection(new WordWholeItemViewSection(getActivity(),Items));
        mSectionViewAdapter.notifyDataSetChanged();
    }

    public void clearData(){
        mSectionViewAdapter.removeAllSections();
    }

    @Override
    protected void initRecyclerView() {
        mSectionViewAdapter=new SectionedRecyclerViewAdapter();
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setAdapter(mSectionViewAdapter);
        initWordLanguage();
        initWordBookNumber();
    }

    public void initWordLanguage(){
        mWordLanguageRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mWholeLanguageAdapter=new WholeLanguageLeftAdapter(mWordLanguageRecycler,mTitle);
        mWordLanguageRecycler.setAdapter(mWholeLanguageAdapter);
        mWholeLanguageAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Language=position;
                OneNumber=0;
                TwoNumber=0;
                PreferencesUtils.putInt(ConstantUtils.LANGUAGE,Language);
                PreferencesUtils.putInt(ConstantUtils.ONE_NUMBER,OneNumber);
                PreferencesUtils.putInt(ConstantUtils.TWO_NUMBER,TwoNumber);
                mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                mWordBookMenuLeftText.setText(mTitle[position]);
                mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu);
                mSwipeRefreshLayout.setEnabled(true);
                mWordBookMenuLeftImg.startAnimation(mRotateAnim);
                mWordLanguageRecycler.startAnimation(mTopExitAnim);
                mWordLanguageRecycler.setVisibility(View.GONE);
                loadBookList();
                clearData();
                loadData();
                mWholeLanguageAdapter.notifyDataSetChanged();
            }
        });
        mWordWholeLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWordBookRightCommon.getVisibility()!=View.GONE){
                    mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordBookRightCommon.startAnimation(mTopExitAnim);
                    mWordBookRightCommon.setVisibility(View.GONE);
                    mWordBookRightHead.setVisibility(View.GONE);
                    return;
                }

                if(mWordLanguageRecycler.getVisibility()==View.GONE){
                    mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.blue));
                    mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu_select);
                    mSwipeRefreshLayout.setEnabled(false);
                    mWordBookMenuLeftImg.startAnimation(mRotateAnim);
                    mWordLanguageRecycler.startAnimation(mTopEnterAnim);
                    mWordLanguageRecycler.setVisibility(View.VISIBLE);
                }else{
                    mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordBookMenuLeftImg.startAnimation(mRotateAnim);
                    mWordLanguageRecycler.startAnimation(mTopExitAnim);
                    mWordLanguageRecycler.setVisibility(View.GONE);
                }

            }
        });
    }

    public void initWordBookNumber(){
        mBookLeftRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBookLeftViewAdapter=new BookLeftViewAdapter(mBookLeftRecycler,mOnes);
        mBookLeftRecycler.setAdapter(mBookLeftViewAdapter);
        mBookLeftViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                OneNumber=mOnes.get(position).getOne_number();
                TwoNumber=0;
                PreferencesUtils.putInt(ConstantUtils.ONE_NUMBER,OneNumber);
                PreferencesUtils.putInt(ConstantUtils.TWO_NUMBER,TwoNumber);
                loadBookList();
                clearData();
                loadData();
            }
        });

        mBookRightRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBookRightViewAdapter=new BookRightViewAdapter(mBookRightRecycler,mTwos);
        mBookRightRecycler.setAdapter(mBookRightViewAdapter);
        mBookRightViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                TwoNumber=mTwos.get(position).getTwo_number();
                PreferencesUtils.putInt(ConstantUtils.TWO_NUMBER,TwoNumber);
                mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu);
                mSwipeRefreshLayout.setEnabled(true);
                mWordBookMenuRightImg.startAnimation(mRotateAnim);
                mWordBookRightCommon.startAnimation(mTopExitAnim);
                mWordBookRightCommon.setVisibility(View.GONE);
                mWordBookRightHead.setVisibility(View.GONE);
                loadBookList();
                clearData();
                loadData();
            }
        });

        mWordWholeRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWordLanguageRecycler.getVisibility()!=View.GONE){
                    mWordBookMenuLeftText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuLeftImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordLanguageRecycler.startAnimation(mTopExitAnim);
                    mWordLanguageRecycler.setVisibility(View.GONE);
                    return;
                }

                if(mWordBookRightCommon.getVisibility()==View.GONE){
                    mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.blue));
                    mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu_select);
                    mSwipeRefreshLayout.setEnabled(false);
                    mWordBookMenuRightImg.startAnimation(mRotateAnim);
                    mWordBookRightCommon.startAnimation(mTopEnterAnim);
                    mWordBookRightCommon.setVisibility(View.VISIBLE);
                    mWordBookRightHead.setVisibility(View.VISIBLE);
                }else{
                    mWordBookMenuRightText.setTextColor(getResources().getColor(R.color.word_book_menu_text_color));
                    mWordBookMenuRightImg.setImageResource(R.drawable.icon_submenu);
                    mSwipeRefreshLayout.setEnabled(true);
                    mWordBookMenuRightImg.startAnimation(mRotateAnim);
                    mWordBookRightCommon.startAnimation(mTopExitAnim);
                    mWordBookRightCommon.setVisibility(View.GONE);
                    mWordBookRightHead.setVisibility(View.GONE);
                }
            }
        });
    }

}














