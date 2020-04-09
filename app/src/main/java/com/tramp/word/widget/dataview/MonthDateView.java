package com.tramp.word.widget.dataview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.tramp.word.R;
import com.tramp.word.entity.user.Day;
import com.tramp.word.entity.user.UserDataInfo;
import com.tramp.word.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/02/17
 * version:1.0
 */

public class MonthDateView extends View{
    private static final String LOG="MonthDateView";
    private static final int INVALID=-1;
    private static final int TOTAL_COL=7;
    public static final int TODAY=1;
    public static final int CURRENT_MONTH_DAY=2;
    public static final  int CLICK_DAY=3;
    public static final int OTHER_MONTH_DAY=4;
    private int TOTAL_ROW;
    private Paint mPaint;
    private int ItemWidth;
    private int ItemHeight;
    private Cell cells[][];
    private int defaultTextColor;
    private int defaultTextSize;
    private CustomDate mShowDate;
    private Day day=new Day();
    private ArrayList<UserDataInfo.Day> Days=new ArrayList<>();
    private ArrayList<Day> ListDays=new ArrayList<>();
    private Paint mArcPaint=new Paint();

    public MonthDateView(Context context, AttributeSet attrs){
        super(context,attrs);
        initData(context);
    }

    public MonthDateView(Context context){
        super(context);
        initData(context);
    }

    public MonthDateView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        initData(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect;
        Cell cell;
        int num=0;
        for (int i=0;i<TOTAL_ROW;i++){
            if(cells[i]!=null){
                for (int j=0;j<TOTAL_COL;j++){
                    cell=cells[i][j];
                    if(cell !=null&&cell.getDate()!=null){
                        rect=cell.getRect(ItemWidth,ItemHeight);
                        int type=getDateType(cell);
                        if(num<Days.size()){
                            ListDays.get(num).setDay_number(Days.get(num).getDay_number());
                            ListDays.get(num).setDay_status(Days.get(num).getDay_status());
                        }else{
                            ListDays.get(num).setDay_number(0);
                            ListDays.get(num).setDay_status(1);
                        }
                        onDrawBackground(canvas,type,rect,mPaint,ListDays.get(num));
                        mPaint.setTextSize(defaultTextSize);
                        onDrawBefore(type,mPaint);
                        onDrawText(canvas,type,cell,rect,mPaint,ListDays.get(num));
                        num++;
                    }
                }
            }
        }
        super.onDraw(canvas);
    }

    private void onDrawText(Canvas canvas, int type,
                            Cell cell, Rect rect, Paint paint,Day days){
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        if(days.getDay_status()==2&&type==CURRENT_MONTH_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.white));
            canvas.drawText(cell.getDate().day+"",rect.centerX(),
                    getTextCenterY(rect.centerY(),paint),paint);
            if(days.getDay_number()>0){
                paint.setColor(getContext().getResources().getColor(R.color.blue));
                canvas.drawText(days.getDay_number()+"",rect.centerX(),
                        getTextCenterY(rect.centerY()+35,paint),paint);
            }

        }else if(days.getDay_status()==2&&type==TODAY){
            paint.setColor(getContext().getResources().getColor(R.color.white));
            canvas.drawText(cell.getDate().day+"",rect.centerX(),
                    getTextCenterY(rect.centerY(),paint),paint);
            if(days.getDay_number()>0){
                paint.setColor(getContext().getResources().getColor(R.color.blue));
                canvas.drawText(days.getDay_number()+"",rect.centerX(),
                        getTextCenterY(rect.centerY()+35,paint),paint);
            }

        }else{
            canvas.drawText(cell.getDate().day+"",rect.centerX(),
                    getTextCenterY(rect.centerY(),paint),paint);
        }
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
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

    public void onDrawBefore( int type, Paint paint) {
        if(type==CURRENT_MONTH_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.black));
        }else if(type==OTHER_MONTH_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.black_1));
        }
    }

    public void onDrawBackground(Canvas canvas, int type,
                                 Rect rect, Paint paint,Day day) {
        if(day.getDay_number()>0&&type==TODAY){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
            canvas.drawCircle(rect.centerX(),rect.centerY(),18,paint);
            canvas.drawCircle(rect.centerX(),rect.centerY(),20,mArcPaint);
        }else if(day.getDay_number()>0&&type==CURRENT_MONTH_DAY){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
            canvas.drawCircle(rect.centerX(),rect.centerY(),20,paint);
        }
    }

    public int getDateType(Cell cell) {
        CustomDate showDate=getShowDate();
        CustomDate compareDate=cell.getDate();
        if(DateUtils.isToday(compareDate)){
            return TODAY;
        }
        if(showDate.isSameMonth(compareDate)){
            return CURRENT_MONTH_DAY;
        }
        return OTHER_MONTH_DAY;
    }

    public String getContent(Cell cell, int type){
        return cell.getDate().day+"";
    }

    private void initData(Context context){
        ItemHeight=getResources().getDimensionPixelOffset(R.dimen.default_margin_size_7);
        defaultTextColor=Color.parseColor("#333333");
        defaultTextSize=35;
        TOTAL_ROW=getTotalRow();
        ItemWidth=getWidth()/TOTAL_COL;
        cells=new Cell[TOTAL_ROW][TOTAL_COL];
        mShowDate=new CustomDate();
        for (int j=0;j<TOTAL_COL*TOTAL_ROW;j++){
            day.setDay_status(1);
            day.setDay_number(0);
            ListDays.add(day);
        }
        initPaint();
        fillMonthDate();
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(ItemWidth/3);
        mPaint.setColor(defaultTextColor);
        mArcPaint.setColor(Color.BLUE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setTextSize(2);
        mArcPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ItemWidth=w/TOTAL_COL;
        mPaint.setTextSize(ItemWidth/3);
    }
    public void fillDate(List<UserDataInfo.Day> data){
        Days.clear();
        Days.addAll(data);
        fillMonthDate();
    }

    public void update(List<UserDataInfo.Day> data){
        fillDate(data);
        invalidate();
    }

    public void rightScroll(List<UserDataInfo.Day> data,CustomDate date){
        setShowDate(date);
        update(data);
    }

    //向左滑动
    public void leftScroll(List<UserDataInfo.Day> data,CustomDate date){
        setShowDate(date);
        update(data);
    }


    public void setShowDate(CustomDate showDate) {
        this.mShowDate = showDate;
    }

    public CustomDate getShowDate() {
        return mShowDate;
    }

    public int getTotalRow(){
        return 7;
    }

    public static float getTextCenterY(int centerY,Paint paint){
        return centerY-((paint.descent()+paint.ascent())/2);
    }
}















