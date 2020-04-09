package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.port.GroupRankInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupRankActivity extends RxBaseActivity implements GroupRankInterFace{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;

    @BindView(R.id.group_rank_sliding)
    SlidingTabLayout GroupRankSliding;
    @BindView(R.id.group_rank_viewPager)
    ViewPager GroupRankViewPager;
    @BindView(R.id.group_rank_linear)
    LinearLayout GroupRankLinear;
    @BindView(R.id.group_rank_number)
    TextView GroupRankNumber;
    @BindView(R.id.pop_rank_background)
    RelativeLayout PopRankBackground;
    @BindView(R.id.pop_rank_pop)
    RelativeLayout PopRankPop;
    @BindView(R.id.pop_rank_exit)
    ImageView PopRankExit;
    @BindView(R.id.pop_rank_button)
    TextView PopRankButton;
    private PopupWindow mPop;
    private GroupViewPager mGroupViewPager;
    private int Tab;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_rank;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            Tab=intent.getIntExtra(ConstantUtils.GROUP_RANK_TAB,0);
        }
        initPop();
        mGroupViewPager=new GroupViewPager(getSupportFragmentManager());
        GroupRankViewPager.setOffscreenPageLimit(3);
        GroupRankViewPager.setAdapter(mGroupViewPager);
        GroupRankSliding.setViewPager(GroupRankViewPager);
        GroupRankSliding.setCurrentTab(Tab);
        GroupRankLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.showAsDropDown(GroupRankLinear,0,0);
            }
        });

        PopRankExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopRankBackground.setVisibility(View.GONE);
                PopRankPop.setVisibility(View.GONE);
            }
        });

        PopRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopRankBackground.setVisibility(View.GONE);
                PopRankPop.setVisibility(View.GONE);
            }
        });
    }

    public void initPop(){
        View StarPop= LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_content_star,null);
        mPop=new PopupWindow(getBaseContext());
        mPop.setContentView(StarPop);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setAnimationStyle(R.style.popup_recite_style_anim);
        mPop.setFocusable(true);
        TextView StarOne=(TextView) StarPop.findViewById(R.id.pop_star_one);
        StarOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,5);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupRankNumber.setText("5");
            }
        });

        TextView StarTwo=(TextView) StarPop.findViewById(R.id.pop_star_two);
        StarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,10);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupRankNumber.setText("10");
            }
        });

        TextView StarThree=(TextView) StarPop.findViewById(R.id.pop_star_three);
        StarThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,15);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupRankNumber.setText("15");
            }
        });

        TextView StarFour=(TextView) StarPop.findViewById(R.id.pop_star_four);
        StarFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,20);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupRankNumber.setText("20");
            }
        });

        TextView StarFive=(TextView) StarPop.findViewById(R.id.pop_star_five);
        StarFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,25);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupRankNumber.setText("25");
            }
        });
    }

    @Override
    public void ShowPopHelp() {
        PopRankBackground.setVisibility(View.VISIBLE);
        PopRankPop.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText("小组排行榜");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    private class GroupViewPager extends FragmentPagerAdapter {
        public String[] mTitle={
                "新晋榜","奋进榜","总榜"
        };
        private Fragment[] mFragment;
        private int ItemNumber=100;
        public GroupViewPager(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[mTitle.length];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=GroupRankViewFragment.newInstance(position,ItemNumber);
                    break;
                case 1:
                    mFragment[position]=GroupRankViewFragment.newInstance(position,ItemNumber);
                    break;
                case 2:
                    mFragment[position]=GroupRankViewFragment.newInstance(position,ItemNumber);
                    break;
            }
            return mFragment[position];
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }
    }

    public static void launch(Activity activity,int tab){
        Intent intent=new Intent(activity,GroupRankActivity.class);
        intent.putExtra(ConstantUtils.GROUP_RANK_TAB,tab);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
