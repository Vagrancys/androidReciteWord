package com.tramp.word.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tramp.word.module.home.check.HomeCheckFragment;
import com.tramp.word.module.home.find.HomeFindFragment;
import com.tramp.word.module.home.me.HomeMeFragment;
import com.tramp.word.module.home.recite.HomeReciteFragment;

/**
 * Created by Administrator on 2018/5/28.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context){
        super(fm);
        fragments=new Fragment[4];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position]==null){
            switch (position){
                case 0:
                    fragments[position] = HomeReciteFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = HomeCheckFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = HomeFindFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = HomeMeFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return 4;
    }

}




















