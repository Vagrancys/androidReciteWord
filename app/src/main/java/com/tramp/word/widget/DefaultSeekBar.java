package com.tramp.word.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/18
 * version:1.0
 */

public class DefaultSeekBar extends View{
    private Bitmap mCircleButtonImg;
    private int mCircleButtonWidth;
    private int mCircleButtonHeight;
    private int mBarClass;
    private int mTickBarHeight;
    private int mTickBarColor;
    private int mButtonTextColor;
    private float mButtonTextSize;
    private int mTickBarTextColor;
    private float mTickBarTextSize;
    private Paint mButtonTextPaint;
    private Paint mTickBarTextPaint;
    private Paint mTickBarPaint;
    private Paint mCircleButtonPaint;
    private RectF mTickBarRectF;
    private int mWidth;
    private int mHeight;
    private int mViewHeight;
    private int mViewWidth;
    private int mLeftPadding;
    private int mTopPadding;
    private int mButtonText;
    private int mSelectProgress;
    private int mMoveBitmap=0;
    private int mNumber;
    private Scroller mScroller;
    public DefaultSeekBar(Context context) {
        this(context, null);
    }

    public DefaultSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public interface OnProgressChangeListener{
        void onChange(int selectProgress);
        void onEnd(int selectProgress);
    }
    private OnProgressChangeListener mOnProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener){
        mOnProgressChangeListener=onProgressChangeListener;
    }

    public void init(Context context,AttributeSet attrs){
        TypedArray attr=context.obtainStyledAttributes(attrs, R.styleable.DefaultSeekBar);
        mTickBarHeight=attr.getDimensionPixelOffset(R.styleable.DefaultSeekBar_tickBarHeight,getDpValue(8));
        mTickBarColor=attr.getColor(R.styleable.DefaultSeekBar_tickBarColor,getResources()
                .getColor(R.color.black_2));

        mCircleButtonWidth=attr.getDimensionPixelOffset(R.styleable.DefaultSeekBar_circleButtonWidth,getDpValue(30));
        mCircleButtonHeight=attr.getDimensionPixelOffset(R.styleable.DefaultSeekBar_circleButtonHeight,getDpValue(20));
        mCircleButtonImg=zoomImg(BitmapFactory.decodeResource(getResources(),R.drawable.btn_knob),mCircleButtonWidth,mCircleButtonHeight) ;
        mLeftPadding=mCircleButtonImg.getWidth()/2;
        mBarClass=attr.getInt(R.styleable.DefaultSeekBar_BarClass,0);
        mButtonTextColor=attr.getColor(R.styleable.DefaultSeekBar_ButtonTextColor,getResources()
                .getColor(R.color.black_1));
        mButtonTextSize=attr.getDimension(R.styleable.DefaultSeekBar_ButtonTextSize,getDpValue(14));
        mTickBarTextColor=attr.getColor(R.styleable.DefaultSeekBar_TickBarTextColor,getResources()
                .getColor(R.color.white));
        mTickBarTextSize=attr.getDimension(R.styleable.DefaultSeekBar_TickBarTextSize,getDpValue(14));
        mTopPadding=(mCircleButtonHeight-mTickBarHeight)/2;
        initView();
        attr.recycle();
        mScroller=new Scroller(context);
    }

    public void initView(){
        mTickBarPaint=new Paint();
        mTickBarPaint.setColor(mTickBarColor);
        mTickBarPaint.setStyle(Paint.Style.FILL);
        mTickBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mTickBarPaint.setAntiAlias(true);

        mTickBarTextPaint=new Paint();
        mTickBarTextPaint.setTextAlign(Paint.Align.CENTER);
        mTickBarTextPaint.setColor(mTickBarTextColor);
        mTickBarTextPaint.setStyle(Paint.Style.FILL);
        mTickBarTextPaint.setTextSize(mTickBarTextSize);
        mTickBarTextPaint.setAntiAlias(true);

        mCircleButtonPaint=new Paint();
        mCircleButtonPaint.setStyle(Paint.Style.FILL);
        mCircleButtonPaint.setAntiAlias(true);

        mButtonTextPaint=new Paint();
        mButtonTextPaint.setTextAlign(Paint.Align.CENTER);
        mButtonTextPaint.setColor(mButtonTextColor);
        mButtonTextPaint.setStyle(Paint.Style.FILL);
        mButtonTextPaint.setTextSize(mButtonTextSize);
        mButtonTextPaint.setAntiAlias(true);

        mTickBarRectF=new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initValues(mWidth,mHeight);
        canvas.drawRoundRect(mTickBarRectF,10,10,mTickBarPaint);
        if(mBarClass==0){
            mNumber=1;
            for (int i=0;i<5;i++){
                int j=i+1;
                String Text=""+j;
                canvas.drawText(Text,mLeftPadding+10+i*(mWidth-mLeftPadding*2-20)/4,(mTopPadding+mHeight)/2,mTickBarTextPaint);
            }
        }else if(mBarClass==1){
            mNumber=10;
            for (int i=0;i<5;i++){
                int j=i*10+10;
                String Text=""+j;
                canvas.drawText(Text,mLeftPadding+10+i*(mWidth-mLeftPadding*2-20)/4,(mTopPadding+mHeight)/2,mTickBarTextPaint);
            }
        }else if(mBarClass==2){
            mNumber=5;
            for (int i=0;i<5;i++){
                int j=i*5+5;
                String Text=""+j;
                canvas.drawText(Text,mLeftPadding+10+i*(mWidth-mLeftPadding*2-20)/4,(mTopPadding+mHeight)/2,mTickBarTextPaint);
            }
        }


        canvas.drawBitmap(mCircleButtonImg,mMoveBitmap,-5,mCircleButtonPaint);

        mButtonText=mSelectProgress;
        canvas.drawText(String.valueOf(mButtonText),
                mCircleButtonImg.getWidth()/2+mMoveBitmap,mCircleButtonImg.getHeight()/2,mButtonTextPaint);
    }

    private void initValues(int width,int height){
        mViewWidth=width;
        mViewHeight=height;
        if(mTickBarHeight>mViewHeight){
            mTickBarHeight=mViewHeight;
        }
        mTickBarRectF.set(mLeftPadding,mTopPadding,mViewWidth-mLeftPadding,mViewHeight-mTopPadding);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isEnabled()){
            return false;
        }
        float x=event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                NowPosition(x);
                if(mOnProgressChangeListener !=null){
                    mOnProgressChangeListener.onChange(mSelectProgress);
                }
                return true;
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                MovePosition(x);
                if(mOnProgressChangeListener !=null){
                    mOnProgressChangeListener.onEnd(mSelectProgress);
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void NowPosition(float x){
        float end=mViewWidth-mLeftPadding;
        float start=mLeftPadding;
        int itemWidth=(mWidth-mLeftPadding*2-20)/8;
        if(x>=start&&x<end){
            if(x>=start&&x<mLeftPadding+10+itemWidth){
                mSelectProgress=1*mNumber;
            }else if(x>=mLeftPadding+10+itemWidth&&x<mLeftPadding+10+itemWidth*3){
                mSelectProgress=2*mNumber;
            }else if(x>=mLeftPadding+10+3*itemWidth&&x<mLeftPadding+10+itemWidth*5){
                mSelectProgress=3*mNumber;
            }else if(x>=mLeftPadding+10+5*itemWidth&&x<mLeftPadding+10+itemWidth*7){
                mSelectProgress=4*mNumber;
            }else if(x>=mLeftPadding+10+7*itemWidth&&x<end){
                mSelectProgress=5*mNumber;
            }
            mMoveBitmap=(int) x-mLeftPadding;
            invalidate();
        }
    }

    private void MovePosition(float x){
        int num;
        float end=mViewWidth-mLeftPadding;
        if(x<0){
            Log.e("1x","start"+(int)x+"end"+-(int) x);
            mScroller.startScroll((int)x,0,-(int) x,0,250);
        }else if(x>0&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8){
            Log.e("2x","start"+(int)x+"end"+-(int) x);
            mScroller.startScroll((int)x,0,-(int)x,0,250);
        }else if(x>=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*2){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*2;
            Log.e("3x","start"+(int)x+"end"+num);
            mScroller.startScroll((int)x,0,num-(int)x,0,250);
        }else if(x>=mLeftPadding+10+2*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*3){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*2;
            Log.e("4x","start"+(int)x+"end"+num);
            mScroller.startScroll((int)x,0,-(int)x+num,0,250);
        }else if(x>=mLeftPadding+10+3*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*4){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*4;
            mScroller.startScroll((int)x,0,num-(int)x,0,250);
        }else if(x>=mLeftPadding+10+4*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*5){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*4;
            Log.e("5x","start"+(int)x+"end"+num);
            mScroller.startScroll((int)x,0,-num-(int)x,0,250);
        }else if(x>=mLeftPadding+10+5*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*6){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*6;
            mScroller.startScroll((int)x,0,num-(int)x,0,250);
        }else if(x>=mLeftPadding+10+6*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*7){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*6;
            Log.e("7x","start"+(int)x+"end"+num);
            mScroller.startScroll((int)x,0,-num-(int)x,0,250);
        }else if(x>=mLeftPadding+10+7*(mWidth-mLeftPadding*2-20)/8&&x<mLeftPadding+10+8*(mWidth-mLeftPadding*2-20)/8){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*8;
            Log.e("8x","start"+(int)x+"end"+num);
            mScroller.startScroll((int)x,0,num-(int)x,0,250);
        }else if(x>end){
            num=mLeftPadding+10+(mWidth-mLeftPadding*2-20)/8*8;
            mScroller.startScroll((int)x,0,num-(int)x,0,250);
        }
        invalidate();
    }

    private int getDpValue(int val){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,val,getContext()
                .getResources().getDisplayMetrics());
    }

    private static Bitmap zoomImg(Bitmap bm,int newWidth,int newHeight){
        int width=bm.getWidth();
        int height=bm.getHeight();
        float scaleWidth=((float) newWidth)/width;
        float scaleHeight=((float) newHeight)/height;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        return Bitmap.createBitmap(bm,0,0,width,height,matrix,true);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        float end=mViewWidth-mLeftPadding*2;
        float start=0;
        if(mScroller.computeScrollOffset()&&mMoveBitmap>=start&&mMoveBitmap<end){
            if(mScroller.getStartX()>mScroller.getFinalX()){
                mMoveBitmap=mMoveBitmap-1;
            }else{
                mMoveBitmap=mMoveBitmap+1;
            }
            postInvalidate();
        }
    }
}


























