package com.tramp.word.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tramp.word.R;

import com.tramp.word.utils.Utils;

/**
 * Created by Administrator on 2019/1/9.
 */

public class CircleProgressView extends View {
    private Paint mArcPaint;
    private Paint mTextPaint;

    private RectF mRectF=new RectF();

    private boolean InitStatus=false;
    private boolean Finished=false;
    private int HeadInt;
    private int NowNumber=0;
    private String HeadNowInt="0";
    private String FooterText;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float strokeWidth;
    private float arcAngle;
    private static final int START=0x1111;
    private int max;
    private final int min_size;
    private float progress=0;
    private float NowProgress=0;
    private final int mTextStrokeWidth=2;
    private final int mTextNewStrokeWidth=1;
    private final float default_arc_angle=360*0.8f;
    private float default_stroke_width;
    private long speed=120;
    private final  int default_max=100;
    private final int default_finished_color = Color.WHITE;
    private final int default_unfinished_color= Color.rgb(72,106,176);
    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_stroke_width= Utils.dp2px(getResources(),20);
        min_size = (int) Utils.dp2px(getResources(), 100);
        TypedArray array=context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircleProgressView,defStyleAttr,0);
        initArray(array);
        initPaint();
    }

    public void setFinished(boolean finished) {
        Finished = finished;
        invalidate();
    }

    private void initArray(TypedArray array){
        strokeWidth=array.getDimension(R.styleable.CircleProgressView_circle_stroke_width,default_stroke_width);
        arcAngle=array.getFloat(R.styleable.CircleProgressView_circle_arc_angle,default_arc_angle);
        unfinishedStrokeColor=array.getColor(R.styleable.CircleProgressView_circle_un_finish_color,default_unfinished_color);
        finishedStrokeColor=array.getColor(R.styleable.CircleProgressView_circle_finish_color,default_finished_color);
        setMax(array.getInt(R.styleable.CircleProgressView_circle_arc_max,default_max));
    }

    public int getHeadInt() {
        return HeadInt;
    }

    public void setHeadInt(int headInt) {
        HeadInt = headInt;
    }

    protected void initPaint(){
        mArcPaint=new Paint();
        mArcPaint.setColor(default_unfinished_color);
        mArcPaint.setStrokeWidth(strokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setAntiAlias(true);

        mTextPaint=new Paint();
    }

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==START){
                if(NowProgress>=progress){
                    return false;
                }else {
                    NowProgress=NowProgress+1;
                    if(!(NowNumber==HeadInt)){
                        Log.d("HeadNotInt",HeadNowInt+"");
                        NowNumber=NowNumber+1;
                    }
                    invalidate();
                    handler.sendEmptyMessageDelayed(START,speed);
                }
            }
            return false;
        }
    });

    public float getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if(this.progress>getMax()){
            this.progress %=getMax();
        }
        handler.removeMessages(START);
        handler.sendEmptyMessageDelayed(START,speed);
    }

    public float getArcAngle() {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle) {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public void setInitStatus(boolean initStatus) {
        this.InitStatus = initStatus;
        this.invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        float paddingTop=50;
        float paddingLeft=30;
        float paddingRight=30;
        float paddingBottom=20;
        mRectF.set(strokeWidth / 2f + paddingLeft,
                strokeWidth / 2f + paddingTop,
                width - strokeWidth / 2f - paddingRight,
                MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f - paddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=this.getWidth();
        int height=this.getHeight();
        String text;
        String text1;
        String content;
        int textHeight;
        int textWidth;
        if(InitStatus){
            content="--";
        }else{
            content=""+NowNumber;
        }
        if(!Finished){
            text="第 "+content+" 关";
            text1="共 "+content+" 关";
        }else{
            text=getResources().getString(R.string.circle_progress_head);
            text1=getResources().getString(R.string.circle_progress_foot);
            NowProgress=progress;
        }
        float startAngle = 270 - arcAngle / 2f;
        float finishedSweepAngle = NowProgress / (float) getMax() * arcAngle;
        float finishedStartAngle = startAngle;
        if (NowProgress == 0) finishedStartAngle = 0.01f;
        mArcPaint.setColor(unfinishedStrokeColor);
        canvas.drawArc(mRectF, startAngle, arcAngle, false, mArcPaint);
        mArcPaint.setColor(finishedStrokeColor);
        canvas.drawArc(mRectF,finishedStartAngle,finishedSweepAngle,false,mArcPaint);
        mTextPaint.setStrokeWidth(mTextStrokeWidth);
        textHeight=height/8;
        mTextPaint.setTextSize(textHeight);
        mTextPaint.setColor(Color.WHITE);
        textWidth=(int) mTextPaint.measureText(text,0,text.length());
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,width/2-textWidth/2,height/2+textHeight/3-40,mTextPaint);
        mTextPaint.setStrokeWidth(mTextNewStrokeWidth);

        textHeight=height/16;
        mTextPaint.setTextSize(textHeight);
        mTextPaint.setColor(Color.rgb(136, 203, 230));
        textWidth=(int) mTextPaint.measureText(text1,0,text1.length());
        mTextPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text1,width/2-textWidth/2,height/2+textHeight/3+40,mTextPaint);
    }

}



















