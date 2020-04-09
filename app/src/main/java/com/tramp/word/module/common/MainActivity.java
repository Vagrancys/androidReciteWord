package com.tramp.word.module.common;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.tramp.word.R;
import com.tramp.word.adapter.HomePagerAdapter;
import com.tramp.word.adapter.MainSignAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.MainTabEntity;
import com.tramp.word.entity.book.BookInfo;
import com.tramp.word.entity.book.WordQueryInfo;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.DownUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.MainProgressDialog;
import com.tramp.word.widget.NoScrollViewPager;
import com.tramp.word.port.DownProgressListener;
import com.tramp.word.widget.down.FileDownLoad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends RxBaseActivity implements MainAnimInterFace{
    private static final String LOG="MainActivity";
    @BindView(R.id.common_tab)
    CommonTabLayout mCommon;
    @BindView(R.id.view_pager)
    NoScrollViewPager mPager;
    @BindView(R.id.view_anim)
    LinearLayout mAnim;
    @BindView(R.id.img_anim)
    ImageView mImgAnim;
    @BindView(R.id.main_common_background)
    RelativeLayout mMainCommonBackground;
    @BindView(R.id.main_task_pop_exit)
    ImageView mMainTaskPopExit;
    @BindView(R.id.main_task_pop)
    RelativeLayout mMainTaskPop;
    @BindView(R.id.main_music_pop)
    RelativeLayout mMusicPop;
    @BindView(R.id.main_music_down)
    TextView mMainMusicDown;
    @BindView(R.id.main_music_progress)
    ProgressBar mMainMusicProgress;
    @BindView(R.id.main_music_num)
    TextView mMainMusicNum;

    @BindView(R.id.main_sign_pop_recycler)
    RecyclerView mMainSignPopRecycler;
    @BindView(R.id.main_sign_pop_exit)
    ImageView mMainSignPopExit;
    @BindView(R.id.main_sign_pop)
    RelativeLayout mMainSignPop;
    @BindView(R.id.main_sign_pop_start)
    TextView mMainSignPopStart;

    @BindView(R.id.insert_book)
    LinearLayout InsertBook;

    private AnimationDrawable mAnimation;
    private Animation mAnimIcon;
    private ObjectAnimator mTaskTopOneAnim;
    private ObjectAnimator mTaskTopTwoAnim;
    private ObjectAnimator mSignPopUpAnim;
    private ObjectAnimator mSignPopDownAnim;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0x1120:
                    mMainTaskPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                    break;
                case 0x1130:
                    mMainSignPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                    break;
            }
            return false;
        }
    });
    private Animation mMusicAnim;
    private HomePagerAdapter mHomeAdapter;
    private String[] mTitles={"背词","备考","发现","我的"};
    private int[] mIconUnSelectIds={
            R.drawable.icon_tab_home,R.drawable.icon_word_list_review,
            R.drawable.icon_tab_discover_oval,R.drawable.icon_tab_me
    };
    private int[] mIconSelectIds={
            R.drawable.icon_tab_home_selected,R.drawable.icon_word_review,
            R.drawable.icon_tab_discover_oval_selected,R.drawable.icon_tab_me_selected
    };

    private ArrayList<CustomTabEntity> mTabEntities=new ArrayList<>();
    private MainProgressDialog MainProgress;
    private MainSignAdapter mMainSignAdapter;
    private UserSqlHelper mUserHelper;
    private DownProgressListener progressListener;
    private ArrayList<WordQueryInfo.Word> wordQuery=new ArrayList<>();
    private ArrayList<DefaultBookInfo> books=new ArrayList<>();
    public int MainProgressNumber;
    public int MainProgressNow;
    public long MainProgressTotal;
    public int book_status=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            book_status=intent.getIntExtra(ConstantUtils.BOOK_STATUS,0);
        }
        mUserHelper=new UserSqlHelper(getBaseContext());
        initAnim();
        initProgress();
        mHomeAdapter=new HomePagerAdapter(getSupportFragmentManager(),getApplicationContext());
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mHomeAdapter);
        mPager.setCurrentItem(0);
        initFragment();
        mCommon.setTabData(mTabEntities);
        mCommon.setCurrentTab(0);

        mCommon.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPager.setCurrentItem(position);
                mCommon.getIconView(position).startAnimation(mAnimIcon);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCommon.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMainTaskPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskTopTwoAnim.start();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMainTaskPop.setVisibility(View.GONE);
                        mMainCommonBackground.setVisibility(View.GONE);
                    }
                },550);
            }
        });

        mMainCommonBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMusicPop.getVisibility()==View.VISIBLE){
                    mMusicPop.setVisibility(View.GONE);
                    mMainCommonBackground.setVisibility(View.GONE);
                }
            }
        });

        progressListener=new DownProgressListener() {

            @Override
            public void onStartProgress() {
                MainProgress.setDown("下载....");
                MainProgress.setMax(100);
                MainProgress.show();
                Log.e(LOG,"onStartProgress");
            }

            @Override
            public void onEndProgress(int book_id,String path,long total) {
                MainProgress.setDown("待解压");
                MainProgress.setProgress(1);
                MainProgress.setDrawable(getResources().getDrawable(R.drawable.main_anim_progress_one_back));
                mMainMusicDown.setEnabled(false);
                DownUtils.RarDp(book_id,path,
                        Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator,progressListener,mHandler);
                Log.e(LOG,"onEndProgress");
            }

            @Override
            public void onErrorProgress(String msg) {
                MainProgress.dismiss();
                Log.e(LOG,"onErrorProgress");
            }

            @Override
            public void onProgress(int p,long now,long total) {
                MainProgress.setProgress(p);
                MainProgress.setNumber( Formatter.formatFileSize(getBaseContext(),now)+"/"+Formatter.formatFileSize(getBaseContext(),total));
                Log.e(LOG,"onProgress");
            }

            @Override
            public void onStartSolve() {
                MainProgress.setDown("解压中");
                Log.e(LOG,"onStartSolve");
            }

            @Override
            public void onProgressSolve(int progress) {
                MainProgress.setProgress(progress);
                Log.e(LOG,"onProgressSolve");
            }

            @Override
            public void onEndSolve(int book_id) {
                MainProgress.setDown("数据写入....");
                File mFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+book_id+".rar");
                if(mFile.isDirectory()){
                    mFile.delete();
                }
                BookQuery(book_id);
                Log.e(LOG,"onEndSolve");
            }

            @Override
            public void onErrorSolve(String msg) {
                Log.e(LOG,"onErrorSolve="+msg);
                MainProgress.dismiss();
            }

            @Override
            public void onStartRead(int total) {
                MainProgress.setDown("数据写入中....");
                MainProgress.setMax(total);
                MainProgress.setProgress(1);
                Log.e(LOG,"onStartRead");
            }

            @Override
            public void onProgressRead(int now) {
                MainProgress.setProgress(now);
                Log.e(LOG,"onProgressRead");
            }

            @Override
            public void onEndRead(int book_id) {
                MainProgress.setDown("完成");
                File mFile=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+book_id+".json");
                if(mFile.isDirectory()){
                    mFile.delete();
                }
                MainProgress.dismiss();
                Log.e(LOG,"onEndRead");
            }
        };

        initBook(book_status);
        initWord();
        initSign();
    }

    private void initWord(){
        if(mUserHelper.isWordEmpty(mUserHelper.WordId())){
            BookDown(mUserHelper.WordId());
        }
        mHomeAdapter.notifyDataSetChanged();
    }

    private void initBook(int book_status){
        Log.e(LOG,"size="+mUserHelper.BookSize(mUserHelper.UserId()));
        if(book_status==1&&mUserHelper.BookSize(mUserHelper.UserId())<=0){
            mMainCommonBackground.setVisibility(View.VISIBLE);
            InsertBook.setVisibility(View.VISIBLE);
            Retrofits.getBookAPI().getBookInfo(mUserHelper.UserId())
                    .enqueue(new Callback<BookInfo>() {
                @Override
                public void onResponse(Call<BookInfo> call, Response<BookInfo> response) {
                    if(response.body()!=null&&response.body().getCode()==200){
                        books=response.body().getBookList();
                        finishBook();
                    }else{
                        mMainCommonBackground.setVisibility(View.GONE);
                        InsertBook.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<BookInfo> call, Throwable t) {
                    mMainCommonBackground.setVisibility(View.GONE);
                    InsertBook.setVisibility(View.GONE);
                }
            });
        }
    }

    private void finishBook(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (DefaultBookInfo Book:books){
                    if(!mUserHelper.isBook(Book.getBook_id())){
                        mUserHelper.insertBook(Book);
                        Log.e(LOG,"for="+Book.getBook_id());
                    }
                }
                mHandler.post(()->{
                    mMainCommonBackground.setVisibility(View.GONE);
                    InsertBook.setVisibility(View.GONE);
                });
            }
        }).start();
    }

    private void BookQuery(int url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.just(readJson(url))
                        .compose(bindToLifecycle())
                        .map(s->new Gson().fromJson(s, WordQueryInfo.class))
                        .map(WordQueryInfo::getWord)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(dataBeans->{
                            Log.e(LOG,"dataBeans="+dataBeans.size());
                            wordQuery.addAll(dataBeans);
                            finishQuery(url);
                        },throwable -> {
                            Log.e(LOG,"msg="+throwable.getMessage());
                        });
            }
        }).start();
    }

    public void finishQuery(int book_id){
        mHandler.post(()->{
            progressListener.onStartRead(wordQuery.size());
        });
        Log.e(LOG,"wordQuery="+wordQuery.size());
        mUserHelper.DeleteWord(book_id);
        for (int i=0;i<wordQuery.size();i++){
            Log.e(LOG,"progress="+i);
            mUserHelper.insertWord(wordQuery.get(i));
            final int now=i;
            mHandler.post(()->{
                progressListener.onProgressRead(now);
            });
        }
        mHandler.post(()->{
            progressListener.onEndRead(book_id);
        });
    }

    private String readJson(int url){
        try {
            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+url+".json");
            Log.e(LOG,"path="+Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+url+".json");
            InputStream is=new FileInputStream(file);
            BufferedReader buffered=new BufferedReader(new InputStreamReader(is));
            StringBuffer string=new StringBuffer();
            String str;
            while ((str =buffered.readLine())!=null){
                string.append(str);
            }
            return string.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private void initSign(){
        mMainSignAdapter=new MainSignAdapter(mMainSignPopRecycler,5);
        mMainSignPopRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        mMainSignPopRecycler.setAdapter(mMainSignAdapter);

        mMainSignPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignPopDownAnim.start();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMainSignPop.setVisibility(View.GONE);
                        mMainCommonBackground.setVisibility(View.GONE);
                    }
                },550);
            }
        });

        mMainSignPopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"签到了");
            }
        });
    }

    private void initProgress(){
        MainProgress=new MainProgressDialog(this);

        mMainMusicProgress.setProgress(0);
        mMainMusicProgress.setSecondaryProgress(0);
        mMainMusicProgress.setMax(400);
        mMainMusicDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMainMusicDown.getText().toString().equals("下载")){
                    mMainMusicProgress.setVisibility(View.VISIBLE);
                    mMainMusicDown.setBackgroundColor(getResources().getColor(R.color.black_2));
                    mMainMusicDown.setTextColor(getResources().getColor(R.color.black_1));
                    mMainMusicDown.setText("待下载");
                    mMainMusicDown.setEnabled(false);
                }else if(mMainMusicDown.getText().toString().equals("暂停")){
                    mMainMusicDown.setEnabled(true);
                    mMainMusicDown.setText("继续");
                }else if(mMainMusicDown.getText().toString().equals("继续")){
                    mMainMusicDown.setText("暂停");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==105&&resultCode==10&&data.getExtras()!=null){
            if(data.getIntExtra(ConstantUtils.BOOK_DOWN_RESPONSE,1)==2){
                int book_id=data.getIntExtra(ConstantUtils.BOOK_DOWN_ID,1);
                switch (data.getIntExtra(ConstantUtils.BOOK_DOWN_STATUS,1)){
                    case 1:
                        if(mUserHelper.isWordEmpty(book_id)){
                            BookDown(book_id);
                        }
                        mHomeAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        ContentValues values=new ContentValues();
                        values.put("recited_book",book_id);
                        mUserHelper.update(values);
                        if(mUserHelper.isWordEmpty(book_id)){
                            BookDown(book_id);
                        }
                        mHomeAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        BookDown(book_id);
                        break;
                }
            }
        }

    }

    private void BookDown(int book_id){
        String path=book_id+".rar";
        Retrofits.getDownAPI().getDownLoad(path)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        new Thread(new FileDownLoad(response,book_id,path,
                                progressListener,
                                mHandler,MainProgressNumber,
                                MainProgressNow,MainProgressTotal)).start();
                        Log.e(LOG,"onResponse");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(LOG,"onFailure");
                    }
                });
    }

    public void initAnim(){
        mImgAnim.setImageResource(R.drawable.default_main_anim);
        mAnimation=(AnimationDrawable) mImgAnim.getDrawable();
        mAnimIcon= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        mMusicAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mAnimIcon.setRepeatCount(3);

        DisplayMetrics metric=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float MaxHeight=metric.widthPixels;
        float curTranslationY=mMainTaskPop.getTranslationY();

        mTaskTopOneAnim= ObjectAnimator.ofFloat(mMainTaskPop,"translationY",MaxHeight,curTranslationY-70,curTranslationY);
        mTaskTopOneAnim.setDuration(500);
        mTaskTopTwoAnim= ObjectAnimator.ofFloat(mMainTaskPop,"translationY",curTranslationY,curTranslationY-70,MaxHeight+350);
        mTaskTopTwoAnim.setDuration(500);

        mSignPopUpAnim= ObjectAnimator.ofFloat(mMainSignPop,"translationY",MaxHeight,curTranslationY-70,curTranslationY);
        mSignPopUpAnim.setDuration(500);
        mSignPopDownAnim= ObjectAnimator.ofFloat(mMainSignPop,"translationY",curTranslationY,curTranslationY-70,MaxHeight+350);
        mSignPopDownAnim.setDuration(500);
    }

    public void initFragment(){
        for (int j=0;j<mTitles.length;j++){
            mTabEntities.add(new MainTabEntity(mTitles[j],mIconSelectIds[j],mIconUnSelectIds[j]));
        }
    }

    @Override
    public void StartAnim() {
        mAnimation.start();
    }

    @Override
    public void StopAnim() {
        mAnimation.stop();
    }

    @Override
    public void ShowAnimLayout() {
        mAnim.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideAnimLayout() {
        mAnim.setVisibility(View.GONE);
    }

    @Override
    public void ShowTaskLayout() {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMainTaskPop.setVisibility(View.VISIBLE);
        mTaskTopOneAnim.start();
    }

    @Override
    public void HideTaskLayout() {
        mTaskTopTwoAnim.start();
        mHandler.sendEmptyMessageDelayed(0x1120,500);
    }

    @Override
    public void ShowSignLayout(int status) {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMainSignPop.setVisibility(View.VISIBLE);
        mSignPopUpAnim.start();
    }

    @Override
    public void HideSignLayout() {
        mSignPopDownAnim.start();
        mHandler.sendEmptyMessageDelayed(0x1130,500);
    }

    @Override
    public void ShowMusicDownLayout() {
        mMainCommonBackground.setVisibility(View.VISIBLE);
        mMusicPop.setVisibility(View.VISIBLE);
        mMusicPop.startAnimation(mMusicAnim);
    }

    @Override
    public void HideMusicDownLayout() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimation.stop();
    }

    public static void launch(Activity activity,int book_status){
        Intent intent=new Intent(activity,MainActivity.class);
        intent.putExtra(ConstantUtils.BOOK_STATUS,book_status);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}








