package com.tramp.word.module.home.check;

import android.os.Bundle;

import com.tramp.word.R;
import com.tramp.word.base.RxLazyFragment;

/**
 * Created by Administrator on 2019/1/9.
 */

public class HomeCheckFragment extends RxLazyFragment {

    public static HomeCheckFragment newInstance(){
        return new HomeCheckFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_check;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}





