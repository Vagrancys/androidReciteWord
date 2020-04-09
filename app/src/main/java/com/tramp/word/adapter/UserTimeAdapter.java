package com.tramp.word.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tramp.word.module.user.UserFriendFragment;
import com.tramp.word.module.user.UserRecordingFragment;

/**
 * Created by Administrator on 2019/2/21.
 */

public class UserTimeAdapter  extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] titles={"背词记录","好友列表"};
    private int Member_id;
    private Context mContext;
    public UserTimeAdapter(FragmentManager fm,Context context,int member_id){
        super(fm);
        fragments=new Fragment[titles.length];
        Member_id=member_id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragments[position]= UserRecordingFragment.newInstance(Member_id);
                break;
            case 1:
                fragments[position]= UserFriendFragment.newInstance(Member_id);
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