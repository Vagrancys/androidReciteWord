package com.tramp.word.module.book;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.book.BookInsertInfo;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.NoScrollViewPager;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/11.
 */

public class BookActivity extends RxBaseActivity implements WordBookPopInterFace{
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.word_book_sliding_tab)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.word_book_out)
    ImageView mWordBookOut;
    @BindView(R.id.word_book_search)
    ImageView mWordSearch;
    @BindView(R.id.book_background)
    RelativeLayout BookBackground;
    @BindView(R.id.word_book_pop)
    RelativeLayout mWordBookPop;
    @BindView(R.id.pop_title)
    TextView PopTitle;
    @BindView(R.id.pop_num_left)
    TextView PopTotal;
    @BindView(R.id.pop_num_right)
    TextView PopNumber;
    @BindView(R.id.pop_summary)
    TextView PopSummary;
    @BindView(R.id.pop_img)
    ImageView PopImg;
    @BindView(R.id.pop_perfect)
    ImageView PopPerfect;
    @BindView(R.id.pop_button)
    TextView PopButton;
    @BindView(R.id.pop_exit)
    ImageView PopExit;
    @BindView(R.id.pop_share)
    TextView PopShare;
    @BindView(R.id.pop_delete)
    ImageView PopDelete;

    private WordPagerAdapter mWordAdapter;
    private Animation PopEnterAnim;
    private Animation PopExitAnim;
    private Animation mScaleAnim;
    private Handler mHandler=new Handler();
    private int BookId;
    private int user_id;
    private int RESPONSE_CODE=10;
    private int REQUEST_CODE=20;
    private UserSqlHelper mUserSqlHelper;
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_book;
    }

    @Override
    public void initView(Bundle save) {
        mUserSqlHelper=new UserSqlHelper(getBaseContext());
        user_id=mUserSqlHelper.UserId();

        PopEnterAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_window_enter_anim);
        PopExitAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_window_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_button_scale_anim);

        mWordAdapter=new WordPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mWordAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);

        initPopItemView();
    }

    @Override
    protected void initToolBar() {
        mWordBookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(ConstantUtils.BOOK_DOWN_RESPONSE,1);
                setResult(RESPONSE_CODE,intent);
                finish();
            }
        });

        mWordSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchActivity.launch(BookActivity.this,REQUEST_CODE);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    private void initPopItemView(){
        PopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordBookPop.startAnimation(PopExitAnim);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BookBackground.setVisibility(View.GONE);
                        mWordBookPop.setVisibility(View.GONE);
                    }
                },250);

            }
        });

        PopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id=mUserSqlHelper.UserId();
                PopButton.startAnimation(mScaleAnim);
                mWordBookPop.startAnimation(PopEnterAnim);
                BookBackground.setVisibility(View.GONE);
                mWordBookPop.setVisibility(View.GONE);
                mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Retrofits.getBookAPI()
                                    .getBookInsertInfo(BookId,user_id)
                                    .enqueue(new Callback<BookInsertInfo>() {
                                        @Override
                                        public void onResponse(Call<BookInsertInfo> call, Response<BookInsertInfo> response) {
                                            if(response.body()!=null&&response.body().getCode()==200){
                                                if(response.body().getBook_status()==3){
                                                    mUserSqlHelper.insertBook(response.body().getBook());
                                                }
                                                Intent intent=new Intent();
                                                intent.putExtra(ConstantUtils.BOOK_DOWN_RESPONSE,2);
                                                intent.putExtra(ConstantUtils.BOOK_DOWN_ID,BookId);
                                                //1 当前不下载 2 非当前不下载 3 非当前下载
                                                intent.putExtra(ConstantUtils.BOOK_DOWN_STATUS,response.body().getBook_status());
                                                setResult(RESPONSE_CODE,intent);
                                                finish();
                                            }else{
                                                Utils.ShowToast(getBaseContext(),"添加失败!");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<BookInsertInfo> call, Throwable t) {
                                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                        }
                                    });
                        }
                    },250);
            }
        });

        PopShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.book_share_text));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BookBackground.setVisibility(View.GONE);
                        mWordBookPop.setVisibility(View.GONE);
                    }
                },250);
            }
        });

        PopDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getBookAPI().getBookDeleteInfo(mUserSqlHelper.UserId(),BookId)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    BookDelete();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });
            }
        });
    }

    private void BookDelete(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUserSqlHelper.DeleteBook(BookId,user_id);
                mUserSqlHelper.DeleteGate(BookId);
                mUserSqlHelper.DeleteWord(BookId);
                mHandler.post(()->{
                    BookBackground.setVisibility(View.GONE);
                    mWordBookPop.setVisibility(View.GONE);
                    mWordAdapter.notifyDataSetChanged();
                });
            }
        }).start();
    }

    @Override
    public void ShowWordBookPop(BookItemInfo.Book book) {
        mViewPager.setFocusable(false);
        initPop(book);
        BookBackground.setVisibility(View.VISIBLE);
        mWordBookPop.startAnimation(PopEnterAnim);
        mWordBookPop.setVisibility(View.VISIBLE);
    }

    @Override
    public void ShowWordBookItemPop(DefaultBookInfo book,int delete) {
        mViewPager.setFocusable(false);
        initItemPop(book,delete);
        BookBackground.setVisibility(View.VISIBLE);
        mWordBookPop.startAnimation(PopEnterAnim);
        mWordBookPop.setVisibility(View.VISIBLE);
    }

    private void initItemPop(DefaultBookInfo book,int delete){
        Glide.with(getBaseContext())
                .load(book.getBook_img())
                .placeholder(R.drawable.main_review_icon_no_word)
                .into(PopImg);
        PopNumber.setText(book.getNumber()+"人正在背诵");
        if(book.getGood()==1){
            PopPerfect.setVisibility(View.VISIBLE);
        }else{
            PopPerfect.setVisibility(View.GONE);
        }
        PopSummary.setText(book.getSummary());
        PopTitle.setText(book.getBook_name());
        PopTotal.setText("共"+book.getTotal_num()+"词");
        if(delete==1){
            PopDelete.setVisibility(View.GONE);
        }else{
            PopDelete.setVisibility(View.VISIBLE);
        }
        PopShare.setVisibility(View.VISIBLE);
        BookId=book.getBook_id();
    }

    private void initPop(BookItemInfo.Book book){
        Glide.with(getBaseContext())
                .load(book.getBook_img())
                .placeholder(R.drawable.main_review_icon_no_word)
                .into(PopImg);
        PopNumber.setText(book.getBook_number()+"人正在背诵");
        if(book.getBook_good()==1){
            PopPerfect.setVisibility(View.VISIBLE);
        }else{
            PopPerfect.setVisibility(View.GONE);
        }
        PopSummary.setText(book.getBook_summary());
        PopTitle.setText(book.getBook_name());
        PopTotal.setText("共"+book.getBook_total()+"词");
        PopDelete.setVisibility(View.GONE);
        PopShare.setVisibility(View.GONE);
        BookId=book.getBook_id();
    }

    private class WordPagerAdapter extends FragmentPagerAdapter{
        private String[] TITLE={"我的","全部"};
        private Fragment[] fragments;
        private WordPagerAdapter(FragmentManager fm){
            super(fm);
            fragments=new Fragment[TITLE.length];

        }
        @Override
        public Fragment getItem(int position) {
            if(fragments[position]==null){
                switch (position){
                    case 0:
                        fragments[position]= BookMyFragment.newInstance();
                        break;
                    case 1:
                        fragments[position]= BookWholeFragment.newInstance();
                        break;
                    default:
                        break;
                }
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RESPONSE_CODE_2=15;
        if(requestCode==REQUEST_CODE&&resultCode==RESPONSE_CODE_2&&data !=null){
            Intent intent=new Intent();
            intent.putExtra(ConstantUtils.BOOK_DOWN_RESPONSE,2);
            intent.putExtra(ConstantUtils.BOOK_DOWN_ID,data.getIntExtra(ConstantUtils.BOOK_DOWN_ID,0));
            //1 当前不下载 2 非当前不下载 3 非当前下载
            intent.putExtra(ConstantUtils.BOOK_DOWN_STATUS,data.getIntExtra(ConstantUtils.BOOK_DOWN_STATUS,1));
            setResult(RESPONSE_CODE,intent);
            finish();
        }
    }
}









