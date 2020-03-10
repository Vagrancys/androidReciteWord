package com.tramp.word.widget.forgetpoint;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2019/2/5.
 */

public class ForgetPointView extends View {
    public static final float RADIUS=50f;

    private ForgetPoint mForgetPoint;
    private Paint mPaint;
    public ForgetPointView(Context context, AttributeSet a){
        super(context,a);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mForgetPoint==null){
            mForgetPoint=new ForgetPoint(RADIUS,RADIUS);
            drawCircle(canvas);
            startAnimation();
        }else{
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas){
        float x=mForgetPoint.getX();
        float y=mForgetPoint.getY();
        canvas.drawCircle(x,y,RADIUS,mPaint);
    }

    private void startAnimation(){
        ForgetPoint startPoint=new ForgetPoint(RADIUS,RADIUS);
        ForgetPoint endPoint=new ForgetPoint(getWidth()-RADIUS,getHeight()-RADIUS);
        ObjectAnimator animColor= ObjectAnimator.ofObject(this,"color",new ColorEvaluator(),"#FF0000","#0000FF");
        ValueAnimator anim=ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mForgetPoint=(ForgetPoint) animation.getAnimatedValue();
                invalidate();
            }
        });

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(anim).with(animColor);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}






























