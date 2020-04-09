package com.tramp.word.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.tramp.word.R;

/**
 * Created by capton on 2017/8/12.
 */

public class BookSeekBar extends View{

    private View NumberView;
    private Paint mBackgroundPaint;
    private Paint mTextNumberPaint;
    private Paint mBackgroundImgPaint;
    private Paint mBackgroundImgTextPaint;
    private Scroller mScroller;
    private RectF mRectF=new RectF();
    private Bitmap mBackgroundImg;
    private Boolean LastBoolean;

    private int x;
    private int LastX=5;
    private int TextLastX=40;
    private int TextNumber=1;
    private int Width;
    private int Height;
    private final int book_background_color= getResources().getColor(R.color.book_background_color);
    private final int book_background_text_color= getResources().getColor(R.color.book_background_text_color);
    private int TextNumberSize=30;
    private int TextImgSize=28;
    private int bookstrokeWidth=45;
    private int NumberWidth=100;
    private float TextNumberWidthLeft=40;
    private float BackgroundImgWidth=5;
    public BookSeekBar(Context context) {
        this(context, null);
    }

    public BookSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mBackgroundImg= BitmapFactory.decodeResource(getResources(),R.drawable.btn_knob);
        initPaint();
    }

    protected void initPaint(){
        mBackgroundPaint=new Paint();
        mBackgroundPaint.setColor(book_background_color);
        mBackgroundPaint.setStrokeWidth(bookstrokeWidth);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackgroundPaint.setAntiAlias(true);

        mTextNumberPaint=new Paint();
        mTextNumberPaint.setTextSize(TextNumberSize);
        mTextNumberPaint.setAntiAlias(true);
        mTextNumberPaint.setColor(book_background_text_color);

        mBackgroundImgPaint=new Paint();
        mBackgroundImgPaint.setAntiAlias(true);

        mBackgroundImgTextPaint=new Paint();
        mBackgroundImgTextPaint.setAntiAlias(true);
        mBackgroundImgTextPaint.setTextSize(TextImgSize);
        mBackgroundImgTextPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        Width = MeasureSpec.getSize(widthMeasureSpec);
        Height=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float paddingTop=40;
        float paddingLeft=60;
        float paddingRight=60;
        float paddingBottom=40;
        float NumberWidthLeft=50;
        String text;
        canvas.drawLine(paddingLeft,paddingTop,Width-paddingRight,paddingBottom,mBackgroundPaint);
        for (int i=0;i<5;i++){
            int j=i+1;
            String Text=""+j;
            canvas.drawText(Text,NumberWidthLeft+i*NumberWidth,Height/2+5,mTextNumberPaint);
        }

        canvas.drawBitmap(mBackgroundImg,BackgroundImgWidth,-14,mBackgroundPaint);
        text="闯"+TextNumber+"关";
        float TextWidth=(int) mTextNumberPaint.measureText(text,0,text.length());
        canvas.drawText(text,BackgroundImgWidth+TextNumberWidthLeft,Height/2+5,mBackgroundImgTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x=(int)event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if(x<35||x>440){
                    return true;
                }
                BackgroundImgWidth=x-50;
                if(x>35&&x<55){
                    TextNumber=1;
                }else if(x>85&&x<125){
                    TextNumber=2;
                }else if(x>185&&x<225){
                    TextNumber=3;
                }else if(x>285&&x<325){
                    TextNumber=4;
                }else if(x>385&&x<425){
                    TextNumber=5;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if(x<35||x>470){
                    return true;
                }
                if(BackgroundImgWidth<x){
                    LastBoolean=true;
                    mScroller.startScroll((int)BackgroundImgWidth,0,x,0,2000);
                }else{
                    LastBoolean=false;
                    mScroller.startScroll(-(int)BackgroundImgWidth,0,-x,0,2000);
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            if (BackgroundImgWidth < -10) {
                BackgroundImgWidth=-9;
                return;
            }
            if(BackgroundImgWidth>387){
                BackgroundImgWidth=386;
                return;
            }
            if(LastX>5&&LastX<45){
                TextNumber=1;
            }else if(LastX>55&&LastX<95){
                TextNumber=2;
            }else if(LastX>135&&LastX<195){
                TextNumber=3;
            }else if(LastX>255&&LastX<295){
                TextNumber=4;
            }else if(LastX>355&&LastX<395){
                TextNumber=5;
            }
            if(LastBoolean){
                BackgroundImgWidth=LastX+1;
                LastX=LastX+1;
                if(BackgroundImgWidth>390){
                    BackgroundImgWidth=390;
                    return;
                }
                if(BackgroundImgWidth>=x-50){
                    return;
                }
            }else{
                BackgroundImgWidth=LastX-1;
                LastX=LastX-1;
                if(BackgroundImgWidth<-10){
                    BackgroundImgWidth=-10;
                    return;
                }
                if(BackgroundImgWidth<=x-50){
                    return;
                }
            }
            postInvalidate();
        }

    }
}












































