package com.tramp.word.widget.dataview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/2/17.
 */

public class WeekDayView extends View {
    private static int mBackgroundColor=Color.parseColor("#CCE4F2");
    private static int mWeekSize=16;
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString=new String[]{"日","一","二","三","四","五","六"};
    private Paint mBackPaint=new Paint();
    private Paint mTextPaint=new Paint();
    private static int columnWidth;
    private static int width;
    private static int height;
    private RectF rectF=new RectF();

    public WeekDayView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public WeekDayView(Context context){
        this(context,null);
    }

    public WeekDayView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        mDisplayMetrics=getResources().getDisplayMetrics();
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width,height);
    }
    public void initPaint(){
        mBackPaint.setStrokeWidth(30);
        mBackPaint.setColor(getContext().getResources().getColor(R.color.black_1));
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackPaint.setStyle(Paint.Style.FILL);

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mWeekSize*mDisplayMetrics.scaledDensity);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        columnWidth=getWidth()/7;
        height=getHeight();
        rectF.left=0;
        rectF.top=0;
        rectF.bottom=height;
        rectF.right=getWidth();
        canvas.drawRoundRect(rectF,5,5,mBackPaint);
        for (int i=0;i<weekString.length;i++){
            String text=weekString[i];
            int fontWidth=(int) mTextPaint.measureText(text);
            int startX=columnWidth*i+(columnWidth-fontWidth)/2;
            int startY=(int) (height-fontWidth)/2;
            canvas.drawText(text,startX,startY,mTextPaint);
        }
        super.onDraw(canvas);
    }
}


























