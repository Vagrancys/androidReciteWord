package com.tramp.word.entity.group;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class GroupSettingInfo {
    private int code;
    private Setting setting;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public class Setting{
        private int group_search;
        private int group_add;

        public int getGroup_add() {
            return group_add;
        }

        public void setGroup_add(int group_add) {
            this.group_add = group_add;
        }

        public int getGroup_search() {
            return group_search;
        }

        public void setGroup_search(int group_search) {
            this.group_search = group_search;
        }
    }
}
