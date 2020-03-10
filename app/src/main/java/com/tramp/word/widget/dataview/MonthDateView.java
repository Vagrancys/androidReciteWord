package com.tramp.word.widget.dataview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultReciteEntity;
import com.tramp.word.module.home.me.UserRecordingFragment;
import com.tramp.word.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2019/2/17.
 */

public class MonthDateView extends View{
    private static final String LogTitle="MonthDateView";
    private static final int INVALID=-1;
    private static final int TOTAL_COL=7;
    public static final int TODAY=1;
    public static final int CURRENT_MONTH_DAY=2;
    public static final  int CLICK_DAY=3;
    public static final int OTHER_MONTH_DAY=4;
    private int interval=5;
    private int progress=100;
    private int TOTAL_ROW;
    private Paint mPaint;
    private int mCellSpaceX;
    private int mCellSpaceY;
    private UserRecordingFragment.Cell cells[][];
    private ArrayList<DefaultReciteEntity> recites;

    private RectF mRectF;
    private int touchSlop;
    private int defaultTextColor;
    private int defaultTextSize;
    private CustomDate mShowDate;

    public MonthDateView(Context context, AttributeSet attrs){

        this(context,attrs,0);
    }

    public MonthDateView(Context context){

        this(context,null);
    }

    public MonthDateView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        TOTAL_ROW=getTotalRow();
        init(context,attrs);
    }

    public void setRecites(ArrayList<DefaultReciteEntity> recites) {
        this.recites = recites;
    }

    /*private void fillMonthDate(){
        int lastMonthDays= DateUtils.getMonthDay(mShowDate.year,mShowDate.month-1);
        int currentMonthDays= DateUtils.getMonthDay(mShowDate.year,mShowDate.month);
        int firstDayWeek= DateUtils.getWeekDayFromDate(mShowDate.year,mShowDate.month);
        int day=0;
        for(int i=0;i<TOTAL_ROW;i++){
            if(cells[i]==null){
                cells[i]=new Cell[TOTAL_COL];
            }
            Log.d(LogTitle,"fillMonthDate||TOTAL_ROW||"+cells[i]);
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
                Log.d(LogTitle,"fillMonthDate||TOTAL_COL||"+cells[i][j]);
            }
        }
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(LogTitle,"onDraw||Top");
        mCellSpaceX=getWidth()/TOTAL_COL;
        mPaint.setTextSize(mCellSpaceX/3);
        Rect rect;
        UserRecordingFragment.Cell cell;
        for (int i=0;i<TOTAL_ROW;i++){
            if(cells[i]!=null){
                for (int j=0;j<TOTAL_COL;j++){
                    cell=cells[i][j];
                    if(cell !=null&&cell.getDate()!=null){
                        rect=cell.getRect(mCellSpaceX,mCellSpaceY);
                        int type=getDateType(cell);
                        onDrawBackground(canvas,type,cell,rect,mPaint);
                        mPaint.setTextSize(defaultTextSize);
                        onDrawBefore(canvas,type,rect,mPaint);
                        onDrawText(canvas,type,cell,rect,mPaint);
                    }
                }
            }
        }
        super.onDraw(canvas);
    }

    private void onDrawText(Canvas canvas, int type,
                            UserRecordingFragment.Cell cell, Rect rect, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        Log.d(LogTitle,"drawText:text="+getContent(cell,getDateType(cell))+
                "x="+rect.centerX()+
                "y="+getTextCenterY(rect.centerY()-interval,paint));
        int day=Integer.parseInt(getContent(cell,getDateType(cell)));


        if(day<20&&day>15&&type==CURRENT_MONTH_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.white));
            canvas.drawText(getContent(cell,getDateType(cell)),rect.centerX(),
                    getTextCenterY(rect.centerY(),paint),paint);
            paint.setColor(getContext().getResources().getColor(R.color.blue));
            canvas.drawText(recites.get(day).getNumber()+"",rect.centerX(),
                    getTextCenterY(rect.centerY()+30,paint),paint);
        }else{
            canvas.drawText(getContent(cell,getDateType(cell)),rect.centerX(),
                    getTextCenterY(rect.centerY(),paint),paint);
        }
    }

    public void setCells(UserRecordingFragment.Cell[][] cells) {
        this.cells = cells;
    }

    public boolean getReciteType(int day){
        if(recites.get(day).getNumber()==0){
            return false;
        }else{
            return true;
        }
    }

    public void onDrawBefore(Canvas canvas, int type, Rect rect, Paint paint) {
        if(type==CLICK_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.black));
        }else if(type==OTHER_MONTH_DAY){
            paint.setColor(getContext().getResources().getColor(R.color.black_1));
        }
    }

    public void onDrawBackground(Canvas canvas, int type,
                                 UserRecordingFragment.Cell cell, Rect rect, Paint paint) {
        int day=Integer.parseInt(getContent(cell,getDateType(cell)));
        if(day<20&&day>15&&type==TODAY){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
            int r=Math.min(rect.width(),rect.height())/2*progress/100;
            canvas.drawCircle(rect.centerX(),rect.centerY(),r-15,paint);
            canvas.drawArc(mRectF,rect.centerX(),rect.centerY(),false,paint);
        }
        if(day<20&&day>15&&type==CURRENT_MONTH_DAY){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(ContextCompat.getColor(getContext(), R.color.blue));
            int r=Math.min(rect.width(),rect.height())/2*progress/100;
            canvas.drawCircle(rect.centerX(),rect.centerY(),r-10,paint);
        }
    }

    public int getDateType(UserRecordingFragment.Cell cell) {
        CustomDate showDate=getShowDate();
        CustomDate compareDate=cell.getDate();
        if(showDate !=null&&compareDate.isSameDay(showDate)){
            return CLICK_DAY;
        }
        if(showDate.isSameMonth(compareDate)){
            return CURRENT_MONTH_DAY;
        }
        if(DateUtils.isToday(compareDate)){
            return TODAY;
        }
        return OTHER_MONTH_DAY;
    }

    public String getContent(UserRecordingFragment.Cell cell, int type){
        return cell.getDate().day+"";
    }

    private void init(Context context, AttributeSet attrs){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        if(attrs !=null){
            TypedArray a=getContext().obtainStyledAttributes(attrs, R.styleable.MonthDateView);
            defaultTextSize=(int) a.getDimension(R.styleable.MonthDateView_baseTextSize,35);
            defaultTextColor=a.getColor(R.styleable.MonthDateView_baseTextColor, Color.parseColor("#333333"));
            mCellSpaceY=(int) a.getDimension(R.styleable.MonthDateView_rowHeight,getResources().getDimensionPixelOffset(R.dimen.default_margin_size_5));
            a.recycle();
        }else{
            defaultTextColor=Color.parseColor("#333333");
            defaultTextSize=35;
        }
        mPaint.setColor(defaultTextColor);
        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
        //fillMonthDate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mViewWidth=w;
        mCellSpaceX=mViewWidth/TOTAL_COL;
        mPaint.setTextSize(mCellSpaceX/3);
    }

    /*public static class Cell{
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
    }*/

    public void setShowDate(CustomDate showDate) {
        this.mShowDate = showDate;
    }

    public CustomDate getShowDate() {
        return mShowDate;
    }

    public interface OnCalendarPageChanged{
        void onPageChanged(CustomDate showDate);
    }

    public int getTotalRow(){
        return 7;
    };

    public static float getTextCenterY(int centerY,Paint paint){
        return centerY-((paint.descent()+paint.ascent())/2);
    }

    public void NextMonthDate(){

    }
}















