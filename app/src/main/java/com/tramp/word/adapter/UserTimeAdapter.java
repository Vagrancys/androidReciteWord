package com.tramp.word.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tramp.word.module.home.me.UserFriendFragment;
import com.tramp.word.module.home.me.UserRecordingFragment;

/**
 * Created by Administrator on 2019/2/21.
 */

public class UserTimeAdapter  extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] titles={"背词记录","好友列表"};
    public UserTimeAdapter(FragmentManager fm, Context context){
        super(fm);
        fragments=new Fragment[titles.length];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragments[position]= UserRecordingFragment.newInstance();
                break;
            case 1:
                fragments[position]= UserFriendFragment.newInstance();
                break;
            default:
                break;
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}