package com.tramp.word.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2019/1/11.
 */

public class MainTabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;
    public MainTabEntity(String title,int selectedIcon,int unSelectedIcon){
        this.title=title;
        this.selectedIcon=selectedIcon;
        this.unSelectedIcon=unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}















