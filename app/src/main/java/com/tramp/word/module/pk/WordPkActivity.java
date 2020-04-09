package com.tramp.word.module.pk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.user.FriendSearchActivity;
import com.tramp.word.port.WordPkInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.PkBookDialog;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class WordPkActivity extends RxBaseActivity implements WordPkInterFace{
    @BindView(R.id.word_pk_sliding)
    SlidingTabLayout WordPkSliding;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_add)
    ImageView DefaultAdd;
    @BindView(R.id.word_pk_viewPager)
    ViewPager WordPkPager;
    private WordPkViewPager mWordPkViewPager;
    private PkBookDialog mPkBookDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_pk;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WordPkActivity.this, FriendSearchActivity.class));
                Utils.StarActivityInAnim(WordPkActivity.this);
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mWordPkViewPager=new WordPkViewPager(getSupportFragmentManager());
        WordPkPager.setOffscreenPageLimit(2);
        WordPkPager.setAdapter(mWordPkViewPager);
        WordPkSliding.setViewPager(WordPkPager);
        WordPkSliding.setCurrentTab(0);

        mPkBookDialog=new PkBookDialog(getBaseContext());
        mPkBookDialog.setCancelable(false);

        mPkBookDialog.setCancelOnClickListener("算了", new PkBookDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mPkBookDialog.dismiss();
            }
        });

        mPkBookDialog.setOkOnClickListener("立即PK", new PkBookDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                PreferencesUtils.putBoolean(ConstantUtils.WORD_PK_CODE,true);
                mPkBookDialog.dismiss();
            }
        });

    }

    @Override
    public void ShowDialog() {
        mPkBookDialog.show();
    }

    public class WordPkViewPager extends FragmentPagerAdapter{

        public final String[] title={"PK","消息"};
        public final Fragment[] fragments;
        public WordPkViewPager(FragmentManager fm){
            super(fm);
            fragments=new Fragment[title.length];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    fragments[position]=WordPkFragment.newInstance();
                    break;
                case 1:
                    fragments[position]=WordNewsFragment.newInstance();
                    break;
            }
            return fragments[position];
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}







