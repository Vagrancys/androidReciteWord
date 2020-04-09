package com.tramp.word.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordSearchActivity extends RxBaseActivity {
    @BindView(R.id.word_search_out)
    ImageView WordSearchOut;
    @BindView(R.id.word_search_delete)
    ImageView WordSearchDelete;
    @BindView(R.id.word_search_edit)
    EditText WordSearchEdit;
    @BindView(R.id.word_search_to)
    ImageView WordSearchTo;
    @BindView(R.id.word_search_viewpager)
    ViewPager WordSearchViewPager;
    @BindView(R.id.word_search_sliding)
    SlidingTabLayout WordSearchSliding;
    private WordSearchPager mWordSearchViewPager;
    @Override
    public int getLayoutId() {
        return R.layout.activity_word_search;
    }

    @Override
    public void initView(Bundle save) {
        mWordSearchViewPager=new WordSearchPager(getSupportFragmentManager());
        WordSearchViewPager.setOffscreenPageLimit(3);
        WordSearchViewPager.setAdapter(mWordSearchViewPager);
        WordSearchViewPager.setCurrentItem(0);
        WordSearchSliding.setViewPager(WordSearchViewPager);
        WordSearchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordSearchEdit.setText("");
                WordSearchDelete.setVisibility(View.GONE);
            }
        });
        WordSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //mSearchFragment.DataChange(WordSearchEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(WordSearchEdit.getText().length()>0){
                    WordSearchDelete.setVisibility(View.VISIBLE);
                }else{
                    WordSearchDelete.setVisibility(View.GONE);
                }
            }
        });
        WordSearchTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WordSearchActivity.this,WordSearchDetailsActivity.class));
                Utils.StarActivityInAnim(WordSearchActivity.this);
            }
        });
    }

    @Override
    protected void initToolBar() {
        WordSearchOut.setOnClickListener(new View.OnClickListener() {
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

    public class WordSearchPager extends FragmentPagerAdapter{
        private Fragment[] mFragment;
        private String[] mTitle={"英->中","日->中","韩->中"};
        public WordSearchPager(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[mTitle.length];
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=SearchEnglishFragment.newInstance();
                    break;
                case 1:
                    mFragment[position]=SearchEnglishFragment.newInstance();
                    break;
                case 2:
                    mFragment[position]=SearchEnglishFragment.newInstance();
                    break;
            }
            return mFragment[position];
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }
}
