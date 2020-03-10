package com.tramp.word.module.home.find;

import android.os.Bundle;

import com.tramp.word.R;
import com.tramp.word.base.RxLazyFragment;

/**
 * Created by Administrator on 2019/1/9.
 */

public class HomeFindFragment extends RxLazyFragment {
    public static HomeFindFragment newInstance(){
        return new HomeFindFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_find;
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
