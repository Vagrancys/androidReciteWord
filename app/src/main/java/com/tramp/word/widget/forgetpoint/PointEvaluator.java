package com.tramp.word.widget.forgetpoint;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * Created by Administrator on 2019/2/5.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        ForgetPoint startPoint=(ForgetPoint) startValue;
        ForgetPoint endPoint=(ForgetPoint) endValue;
        float x=startPoint.getX()+fraction*(endPoint.getX()-startPoint.getX());
        float y=startPoint.getY()+fraction*(endPoint.getY()-startPoint.getY());
        ForgetPoint point=new ForgetPoint(x,y);
        return point;
    }
}
