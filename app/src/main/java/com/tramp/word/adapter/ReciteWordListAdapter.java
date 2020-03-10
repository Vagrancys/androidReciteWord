package com.tramp.word.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tramp.word.module.home.check.HomeCheckFragment;
import com.tramp.word.module.home.find.HomeFindFragment;
import com.tramp.word.module.home.me.HomeMeFragment;
import com.tramp.word.module.home.recite.HomeReciteFragment;
import com.tramp.word.module.word.ReciteKnowFragment;
import com.tramp.word.module.word.ReciteRepeatFragment;
import com.tramp.word.module.word.ReciteStudyFragment;

/**
 * Created by Administrator on 2019/1/24.
 */

public class ReciteWordListAdapter  extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public ReciteWordListAdapter(FragmentManager fm, Context context){
        super(fm);
        fragments=new Fragment[3];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position]==null){
            switch (position){
                case 0:
                    fragments[position] = ReciteKnowFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = ReciteRepeatFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = ReciteStudyFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return 3;
    }

}