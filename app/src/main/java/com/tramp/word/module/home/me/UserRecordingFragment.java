package com.tramp.word.module.home.me;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.DefaultReciteEntity;
import com.tramp.word.utils.DateUtils;
import com.tramp.word.widget.dataview.CustomDate;
import com.tramp.word.widget.dataview.MonthDateView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/16.
 */

public class UserRecordingFragment extends RxLazyFragment{
    @BindView(R.id.user_date_title)
    TextView mUserDateTitle;
    @BindView(R.id.user_date_left)
    ImageView mUserDateLeft;
    @BindView(R.id.user_date_right)
    ImageView mUserDateRight;
    @BindView(R.id.user_date_month)
    MonthDateView mUserDateMonth;
    private static final String LogTitle="UserRecordingFragment";
    private static final int INVALID=-1;
    private static final int TOTAL_COL=7;
    private static final int TOTAL_ROW=7;
    private CustomDate mShowDate;
    private ArrayList<DefaultReciteEntity> recites=new ArrayList<>();
    private DefaultReciteEntity mEntity=new DefaultReciteEntity();
    private Random mRandom=new Random();
    private Cell cells[][];
    private int month=30;
    private int year=0;
    private Calendar calendar;
    private Date mDate=new Date();
    public static UserRecordingFragment newInstance(){
        return new UserRecordingFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_recording;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mUserDateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month--;
                initData();
                mUserDateMonth.scrollTo(mUserDateMonth.getWidth(),0);
                mUserDateMonth.invalidate();
                mUserDateTitle.setText(mShowDate.year+"年"+mShowDate.month+"月");
            }
        });
        mUserDateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month++;
                initData();
                mUserDateMonth.scrollTo(-mUserDateMonth.getWidth(),0);
                mUserDateMonth.invalidate();
                mUserDateTitle.setText(mShowDate.year+"年"+mShowDate.month+"月");
            }
        });
        calendar= Calendar.getInstance();
        cells=new UserRecordingFragment.Cell[TOTAL_ROW][TOTAL_COL];
        calendar.setTime(mDate);
        initData();
    }

    private void initData(){
        calendar.clear();
        year=(30-month+calendar.get(Calendar.DAY_OF_MONTH))/12;
        mShowDate=new CustomDate(calendar.get(Calendar.DAY_OF_YEAR)-year,calendar.get(Calendar.DAY_OF_MONTH)+month-30,1);
        fillMonthDate();
        mUserDateMonth.setCells(cells);
        mUserDateMonth.setShowDate(mShowDate);
        mUserDateMonth.setRecites(recites);
    }

    private void fillMonthDate(){
        int lastMonthDays= DateUtils.getMonthDay(mShowDate.year,mShowDate.month-1);
        int currentMonthDays= DateUtils.getMonthDay(mShowDate.year,mShowDate.month);
        int firstDayWeek= DateUtils.getWeekDayFromDate(mShowDate.year,mShowDate.month);
        int day=0;
        for(int i=0;i<TOTAL_ROW;i++){
            if(cells[i]==null){
                cells[i]=new Cell[TOTAL_COL];
            }
            for (int j=0;j<TOTAL_COL;j++){
                int position=j+i*TOTAL_COL;
                int year=INVALID,month=INVALID,monthDay=INVALID;
                if(position>=firstDayWeek&&position<firstDayWeek+currentMonthDays){
                    day++;
                    year=mShowDate.year;
                    month=mShowDate.month;
                    monthDay=day;
                }else if(position<firstDayWeek){
                    year=mShowDate.year;
                    month=mShowDate.month-1;
                    monthDay=lastMonthDays-(firstDayWeek-position-1);
                }else if(position>=firstDayWeek+currentMonthDays){
                    year=mShowDate.year;
                    month=mShowDate.month+1;
                    monthDay=position-firstDayWeek-currentMonthDays+1;
                }
                Cell cell=cells[i][j];
                if (year==INVALID){
                    cell.update(null,i,j);
                }else{
                    if(cell!=null&&cell.getDate()!=null){
                        cell.getDate().update(year,month,monthDay,j);
                        cell.update(cell.getDate(),i,j);
                    }else{
                        CustomDate date=new CustomDate(year,month,monthDay,j);
                        cells[i][j]=new Cell(date,i,j);
                    }
                }
                if(day>15&&day<20){
                    mEntity.setDay(day);
                    mEntity.setNumber(mRandom.nextInt(100));
                }else{
                    mEntity.setDay(day);
                    mEntity.setNumber(0);
                }
                recites.add(mEntity);
            }
        }
    }

    public static class Cell{
        private CustomDate date;
        public int row;
        public int col;
        private Rect rect;
        Cell(CustomDate date,int row,int col){
            this.date=date;
            this.row=row;
            this.col=col;
            rect=new Rect();
        }

        void update(CustomDate date,int row,int col){
            this.date=date;
            this.col=col;
            this.row=row;
        }

        public CustomDate getDate() {
            return date;
        }

        public Rect getRect(int perW,int perH) {
            int left=perW*col;
            int top=row*perH;
            int right=left+perW;
            int bottom=top+perH;
            rect.set(left,top,right,bottom);
            return rect;
        }
    }
}





