package com.tramp.word.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.tramp.word.R;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2019/3/2.
 */

public class TimeNumberPicker extends NumberPicker{
    public TimeNumberPicker(Context context){
        super(context);
    }
    public TimeNumberPicker(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public TimeNumberPicker(Context context, AttributeSet attrs,int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        this.addView(child,null);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        this.addView(child,-1, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        setNumberPicker(child);
    }

    public void setNumberPicker(View view){
        if(view instanceof EditText){
            ((EditText) view).setTextColor(getResources().getColor(R.color.blue));
            ((EditText) view).setTextSize(18);
        }
    }


    @SuppressWarnings("unused")
    public void setNumberPickerDividerColor(NumberPicker numberPicker){
        Field[] pickerFields=NumberPicker.class.getDeclaredFields();
        for (Field pf:pickerFields){
            if(pf.getName().equals("mSelectionDivider")){
                pf.setAccessible(true);
                try {
                    pf.set(numberPicker,new ColorDrawable(getResources().getColor(R.color.black_2)));
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}





















































