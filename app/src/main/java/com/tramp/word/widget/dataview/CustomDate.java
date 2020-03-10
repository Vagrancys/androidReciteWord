package com.tramp.word.widget.dataview;

import android.support.annotation.NonNull;

import com.tramp.word.utils.DateUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/17.
 */

public class CustomDate implements Serializable,Comparable<CustomDate> {
    private static final long serialVersionUID=1L;
    public int year;
    public int month;
    public int day;
    public int week;
    public CustomDate(int year,int month,int day,int week){
        if(month>12){
            month=1;
            year++;
        }else if(month<1){
            month=12;
            year--;
        }
        this.year=year;
        this.month=month;
        this.day=day;
        this.week=week;
    }

    public void update(int year,int month,int day,int week){
        if(month>12){
            month=1;
            year++;
        }else if(month<1){
            month=12;
            year--;
        }
        this.year=year;
        this.month=month;
        this.day=day;
        this.week=week;
    }

    public CustomDate(int year,int month,int day){
        if(month>12){
            month=1;
            year++;
        }else if(month<1){
            month=12;
            year--;
        }
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public CustomDate(){
        this.year= DateUtils.getYear();
        this.month= DateUtils.getMonth();
        this.day= DateUtils.getCurrentMonthDay();
        this.week= DateUtils.getWeekDay();
    }

    @Override
    public int compareTo(@NonNull CustomDate another) {
        if(another!=null){
            if(this.year>another.year){
                return 1;
            }else if(this.year==another.year&&this.month>another.month){
                return 1;
            }else if(this.year==another.year&&this.month==another.month&&this.day>another.day){
                return 1;
            }else if(this.year==another.year&&this.month==another.month&&this.day==another.day){
                return 0;
            }
            return -1;
        }
        return -1;
    }

    public boolean isSameDay(CustomDate customDate){
        return year==customDate.year&&customDate.month==month&&day==customDate.day;
    }

    public boolean isSameMonth(CustomDate customDate){
        return year==customDate.year&&customDate.month==month;
    }

    public boolean isSameYear(CustomDate customDate){
        return year==customDate.year;
    }
}





















