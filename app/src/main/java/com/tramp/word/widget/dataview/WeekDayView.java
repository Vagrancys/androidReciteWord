package com.tramp.word.widget.dataview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Administrator on 2019/2/17.
 */

public class WeekDayView extends View {
    private static int mBackgroundColor=Color.parseColor("#CCE4F2");
    private static int mWeekdayColor=Color.parseColor("#666666");
    private static int mWeekSize=16;
    private static Paint mPaint;
    private static RectF mRectF=new RectF();
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString=new String[]{"日","一","二","三","四","五","六"};
    public WeekDayView(Context context, AttributeSet attrs){
        super(context,attrs);
        mDisplayMetrics=getResources().getDisplayMetrics();
        mPaint=new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST){
            heightSize=mDisplayMetrics.densityDpi*30;
        }
        if(widthMode==MeasureSpec.AT_MOST){
            widthSize=mDisplayMetrics.densityDpi*300;
        }
        mRectF.top=0;
        mRectF.left=0;mRectF.bottom=0;
        mRectF.right=0;
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=getWidth();
        int height=getHeight();
        mPaint.setStrokeWidth(height);
        mPaint.setColor(mBackgroundColor);
        mPaint.setAntiAlias(true);
        canvas.drawRect(0,0,width,height,mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mWeekSize*mDisplayMetrics.scaledDensity);
        int columnWidth=width/7;
        for (int i=0;i<weekString.length;i++){
            String text=weekString[i];
            int fontWidth=(int) mPaint.measureText(text);
            int startX=columnWidth*i+(columnWidth-fontWidth)/2;
            int startY=(int) (height/2-(mPaint.ascent()+mPaint.descent())/2);
            canvas.drawText(text,startX,startY,mPaint);
        }
        super.onDraw(canvas);
    }
}


























