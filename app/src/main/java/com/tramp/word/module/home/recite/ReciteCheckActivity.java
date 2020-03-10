package com.tramp.word.module.home.recite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteCheckActivity extends RxBaseActivity {
    @BindView(R.id.recite_check_out)
    ImageView mReciteCheckOut;
    @BindView(R.id.recite_check_linear)
    LinearLayout mReciteCheckLinear;
    private Animation mReciteCheckAnim;
    @BindView(R.id.recite_check_view_pager)
    NoScrollViewPager mReciteCheckViewPager;

    private ReciteCheckAdapter mReciteCheckAdapter;
    private ArrayList<View> mViews=new ArrayList<View>(){};;
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_check;
    }
    @Override
    public void initView(Bundle save) {
        mReciteCheckAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.task_list_exit_anim);

        mReciteCheckLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReciteCheckLinear.startAnimation(mReciteCheckAnim);
                Utils.ShowToast(getBaseContext(),"点击了分享朋友圈!");
            }
        });
        mViews.add(LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recite_check_one,null));
        mViews.add(LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recite_check_two,null));
        mReciteCheckAdapter=new ReciteCheckAdapter(mViews);
        mReciteCheckViewPager.setPageMargin(20);
        mReciteCheckViewPager.setOffscreenPageLimit(2);
        mReciteCheckViewPager.setPageTransformer(true,new ScalePageTransformer());
        mReciteCheckViewPager.setAdapter(mReciteCheckAdapter);
        mReciteCheckViewPager.setCurrentItem(0);
    }

    @Override
    protected void initToolBar() {
        mReciteCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public class ScalePageTransformer implements ViewPager.PageTransformer{
        private static final float MIN_SCALE=0.8f;
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position >= -1 || position <= 1) {
                final float height = view.getHeight();
                final float width = view.getWidth();
                final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                final float vertMargin = height * (1 - scaleFactor) / 2;
                final float horzMargin = width * (1 - scaleFactor) / 2;
                view.setPivotY(0.5f * height);
                view.setPivotX(0.5f * width);
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }
    }



    public class ReciteCheckAdapter extends PagerAdapter{
        private ArrayList<View> mCheckViews;

        public ReciteCheckAdapter(ArrayList<View> checkview){
            mCheckViews=checkview;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mCheckViews.get(position));
            return mCheckViews.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView(mCheckViews.get(position));
        }

        @Override
        public int getCount() {
            return mCheckViews.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViews.clear();
    }
}

















