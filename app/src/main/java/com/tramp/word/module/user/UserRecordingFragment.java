package com.tramp.word.module.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.user.UserDataInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.DateUtils;
import com.tramp.word.widget.dataview.MonthDateView;
import com.tramp.word.widget.dataview.MonthViewPagerListener;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/16.
 */

public class UserRecordingFragment extends RxLazyFragment{
    @BindView(R.id.date_title)
    TextView DateTitle;
    @BindView(R.id.date_left)
    ImageView mUserDateLeft;
    @BindView(R.id.date_right)
    ImageView mUserDateRight;
    @BindView(R.id.recording_linear)
    LinearLayout RecordingLinear;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.net_button)
    TextView NetButton;
    @BindView(R.id.recording_already)
    TextView RecordingAlready;
    @BindView(R.id.recording_last)
    TextView RecordingLast;
    @BindView(R.id.recording_win)
    TextView RecordingWin;
    @BindView(R.id.date_pager)
    ViewPager DatePager;
    @BindView(R.id.date_right_gray)
    ImageView DateRightGray;
    private int member_id;
    private int time_year;
    private int time_month;
    private UserDataInfo.Data Data;
    private ArrayList<UserDataInfo.Day> Day=new ArrayList<>();
    private MonthViewPager<MonthDateView> monthViewPager;
    private MonthViewPagerListener monthPagerListener;
    private MonthDateView[] monthDateViews;
    private Animation mScaleAnim;
    public static UserRecordingFragment newInstance(int member_id){
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.MEMBER_ID,member_id);
        UserRecordingFragment mUser=new UserRecordingFragment();
        mUser.setArguments(bundle);
        return mUser;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_recording;
    }

    @Override
    public void finishCreateView(Bundle state) {
        Bundle bundle=getArguments();
        if(bundle!=null){
            member_id=bundle.getInt(ConstantUtils.MEMBER_ID);
        }
        isPrepared=true;
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
        loadData();
    }

    @Override
    protected void initRecyclerView() {
        mScaleAnim= AnimationUtils.loadAnimation(getContext(),R.anim.default_button_scale_anim);
        monthDateViews= createMessMonthViews(getContext(),5);
        monthViewPager=new MonthViewPager<>(monthDateViews);
        DatePager.setAdapter(monthViewPager);
        DatePager.setCurrentItem(498);
        monthPagerListener=new MonthViewPagerListener(DatePager,monthViewPager,Day);
        DatePager.addOnPageChangeListener(monthPagerListener);

        mUserDateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_month--;

                if(time_month<=0){
                    time_month=12;
                    time_year--;
                }
                initNet(time_year,time_month);
                monthPagerListener.setDays(Day);
                DatePager.setCurrentItem(DatePager.getCurrentItem()-1);
                DateTitle.setText(time_year+"年"+time_month+"月");
                if(!DateUtils.isMonth(time_year,time_month)){
                    DateRightGray.setVisibility(View.GONE);
                    mUserDateRight.setVisibility(View.VISIBLE);
                    DatePager.setClickable(true);
                }
            }
        });

        mUserDateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_month++;
                if(time_month>=12){
                    time_month=1;
                    time_year++;
                }
                initNet(time_year,time_month);
                monthPagerListener.setDays(Day);
                DatePager.setCurrentItem(DatePager.getCurrentItem()+1);
                DateTitle.setText(time_year+"年"+time_month+"月");
                if(DateUtils.isMonth(time_year,time_month)){
                    DateRightGray.setVisibility(View.VISIBLE);
                    mUserDateRight.setVisibility(View.GONE);
                    DatePager.setClickable(false);
                }
            }
        });

        NetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetButton.startAnimation(mScaleAnim);
                loadData();
            }
        });
    }

    public void initNet(int user_year,int user_month){
        Day.clear();
        Retrofits.getUserAPI()
                .getWordDataInfo(member_id,user_year,user_month)
                .enqueue(new Callback<UserDataInfo>() {
                    @Override
                    public void onResponse(Call<UserDataInfo> call, Response<UserDataInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Day.addAll(response.body().getDay());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDataInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    @Override
    protected void loadData() {
        Retrofits.getUserAPI()
                .getUserDataInfo(member_id)
                .enqueue(new Callback<UserDataInfo>() {
                    @Override
                    public void onResponse(Call<UserDataInfo> call, Response<UserDataInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Data=response.body().getData();
                            Day.addAll(response.body().getDay());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDataInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void initEmpty(){
        Log.e("userrecording","user11");
        CommonEmpty.setVisibility(View.VISIBLE);
        RecordingLinear.setVisibility(View.GONE);
    }

    @Override
    protected void finishTask() {
        RecordingWin.setText(String.valueOf(Data.getData_pk()));
        RecordingAlready.setText(String.valueOf(Data.getData_word()));
        RecordingLast.setText(String.valueOf(Data.getData_day()));
        DateTitle.setText(Data.getDate_title());
        monthPagerListener.setDays(Day);
    }

    public class MonthViewPager<V extends View> extends PagerAdapter {
        private V[] views;
        public MonthViewPager(V[] views){
            super();
            this.views=views;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View childView =views[position%views.length];
            if(childView.getParent() !=null){
                ((ViewGroup)childView.getParent()).removeView(childView);
            }
            container.addView(childView,0);
            return childView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }

        public V[] getAllItems() {
            return views;
        }
    }

    public static MonthDateView[] createMessMonthViews(Context context, int count){
        MonthDateView[] monthDateViews=new MonthDateView[count];
        for (int i=0;i<count;i++){
            monthDateViews[i]=new MonthDateView(context);
        }
        return monthDateViews;
    }

}

















