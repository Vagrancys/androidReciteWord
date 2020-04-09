package com.tramp.word.module.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupTagViewAdapter;
import com.tramp.word.adapter.section.GroupActiveViewSection;
import com.tramp.word.adapter.section.GroupItemViewSection;
import com.tramp.word.adapter.section.GroupLearnViewSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupContextInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.port.GroupAddInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.GroupAddDialog;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/21.
 */

public class GroupContentActivity extends RxBaseActivity implements GroupAddInterFace{
    @BindView(R.id.group_content_swipe)
    SwipeRefreshLayout GroupContentSwipe;
    @BindView(R.id.group_content_out)
    ImageView GroupContentOut;
    @BindView(R.id.group_content_search)
    ImageView GroupContentSearch;
    @BindView(R.id.group_content_add)
    ImageView GroupContentAdd;
    @BindView(R.id.group_content_banner)
    Banner GroupContentBanner;
    @BindView(R.id.group_content_viewPager)
    ViewPager GroupContentViewPager;
    @BindView(R.id.group_content_sliding)
    SlidingTabLayout GroupContentSliding;
    @BindView(R.id.group_content_number)
    TextView GroupContentNumber;
    @BindView(R.id.group_content_more)
    TextView GroupContentMore;
    @BindView(R.id.group_content_recycler1)
    RecyclerView GroupContentRecycler1;
    @BindView(R.id.group_content_recycler2)
    RecyclerView GroupContentRecycler2;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.group_content_linear)
    LinearLayout GroupContentLinear;
    private GroupViewPager mGroupViewPager;
    private GroupTagViewAdapter mGroupTagViewAdapter;
    private SectionedRecyclerViewAdapter mSection;
    private GroupAddDialog mGroupAddDialog;
    private ArrayList<Integer> mImage=new ArrayList<>();
    private ArrayList<GroupContextInfo.Item> Learns=new ArrayList<>();
    private ArrayList<GroupContextInfo.Item> Actives=new ArrayList<>();
    private ArrayList<GroupContextInfo.Item> Lists=new ArrayList<>();
    private PopupWindow mPop;
    private int ItemClass;
    private GroupContextInfo.group Groups;
    private int add_group;
    private int user_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_content;
    }

    @Override
    protected void initToolBar() {
        GroupContentOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GroupContentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupContentActivity.this,GroupSearchActivity.class));
                Utils.StarActivityInAnim(GroupContentActivity.this);
            }
        });

        GroupContentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Groups.getGroup_status()==0&&Groups.getGroup_money()>200){
                    startActivity(new Intent(GroupContentActivity.this,GroupAddNameActivity.class));
                    Utils.StarActivityInAnim(GroupContentActivity.this);
                }else{
                    mGroupAddDialog.show();
                }
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        lazyData();
        initData();
        initClick();
    }

    public void lazyData(){
        ItemClass=PreferencesUtils.getInt(ConstantUtils.GROUP_CONTENT_CLASS,0);
        GroupContentSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        GroupContentSwipe.post(()->{
            GroupContentSwipe.setRefreshing(true);
            loadData();
        });
        GroupContentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                loadData();
            }
        });
    }

    public void clearData(){
        Learns.clear();
        Actives.clear();
        Lists.clear();
        mSection.removeAllSections();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupContextInfo(ItemClass,user_id)
                .enqueue(new Callback<GroupContextInfo>() {
                    @Override
                    public void onResponse(Call<GroupContextInfo> call, Response<GroupContextInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Groups=response.body().getGroups();
                            Lists.addAll(response.body().getLists());
                            Actives.addAll(response.body().getActive());
                            Learns.addAll(response.body().getLearns());
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupContextInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void initEmpty(){
        GroupContentSwipe.setVisibility(View.GONE);
        CommonEmpty.setVisibility(View.VISIBLE);
    }

    public void finishTask(){
        if(Groups.getGroup_status()==1){
            GroupContentAdd.setVisibility(View.GONE);
        }else{
            GroupContentAdd.setVisibility(View.VISIBLE);
        }
        GroupContentSwipe.setRefreshing(false);
        mSection.addSection(new GroupLearnViewSection(getBaseContext(),this,0,Learns,Groups.getGroup_status(),user_id));
        mSection.addSection(new GroupActiveViewSection(getBaseContext(),this,1,Actives,Groups.getGroup_status(),user_id));
        mSection.addSection(new GroupItemViewSection(getBaseContext(),this,Lists,Groups.getGroup_status(),user_id));
        mSection.notifyDataSetChanged();
    }

    public void initData(){
        mImage.add(R.drawable.checkin_img_x_5);
        mImage.add(R.drawable.checkin_img_x_3);
        mImage.add(R.drawable.checkin_img_x_6);
        mImage.add(R.drawable.checkin_img_x_8);
        mImage.add(R.drawable.checkin_img_x_7);
        GroupContentBanner.setDelayTime(2000)
                .setImageLoader(new LoadingImage())
                .setImages(mImage)
                .start();

        mGroupViewPager=new GroupViewPager(getSupportFragmentManager());

        GroupContentViewPager.setOffscreenPageLimit(3);
        GroupContentViewPager.setAdapter(mGroupViewPager);

        GroupContentSliding.setViewPager(GroupContentViewPager);
        GroupContentSliding.setCurrentTab(0);

        mGroupTagViewAdapter=new GroupTagViewAdapter(GroupContentRecycler1);
        GroupContentRecycler1.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupContentRecycler1.setAdapter(mGroupTagViewAdapter);

        mGroupTagViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                PreferencesUtils.putInt(ConstantUtils.GROUP_CONTENT_CLASS,position);
                mGroupTagViewAdapter.setTagNumber(position);
                clearData();
                loadData();
                mGroupTagViewAdapter.notifyDataSetChanged();
            }
        });
        mGroupAddDialog=new GroupAddDialog(this);
        mGroupAddDialog.setCancelOnClickListener("取消", new GroupAddDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mGroupAddDialog.dismiss();
            }
        });
        mGroupAddDialog.setOkOnClickListener("确定", new GroupAddDialog.OkOnClickListener() {
            @Override
            public void onOkClick(String text) {
                Retrofits.getGroupAPI()
                        .getAddTextInfo(add_group,mGroupAddDialog.getEditText(),user_id)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body() !=null&&response.body().getCode()==200){
                                    Utils.ShowToast(getBaseContext(),"等待审核中!");
                                    mGroupAddDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });

            }
        });

        mSection=new SectionedRecyclerViewAdapter();
        GroupContentRecycler2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupContentRecycler2.setAdapter(mSection);

        initPop();
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
                GroupContentNumber.setText("5");
            }
        });
        TextView StarTwo=(TextView) StarPop.findViewById(R.id.pop_star_two);
        StarTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,10);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupContentNumber.setText("10");
            }
        });

        TextView StarThree=(TextView) StarPop.findViewById(R.id.pop_star_three);
        StarThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,15);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupContentNumber.setText("15");
            }
        });

        TextView StarFour=(TextView) StarPop.findViewById(R.id.pop_star_four);
        StarFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,20);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupContentNumber.setText("20");
            }
        });

        TextView StarFive=(TextView) StarPop.findViewById(R.id.pop_star_five);
        StarFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putInt(ConstantUtils.ITEM_STAR,25);
                mGroupViewPager.notifyDataSetChanged();
                mPop.dismiss();
                GroupContentNumber.setText("25");
            }
        });
    }

    public void initClick(){
        GroupContentBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                startActivity(new Intent(GroupContentActivity.this, WedCommonActivity.class));
                Utils.StarActivityInAnim(GroupContentActivity.this);
            }
        });
        GroupContentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.showAsDropDown(GroupContentLinear,0,0);
            }
        });

        GroupContentMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupRankActivity.launch(GroupContentActivity.this, GroupContentSliding.getCurrentTab());
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    private class GroupViewPager extends FragmentPagerAdapter{
        public String[] mTitle={
                "新晋榜","奋进榜","总榜"
        };
        private Fragment[] mFragment;
        private int ItemNumber=3;
        public GroupViewPager(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[mTitle.length];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=GroupItemViewFragment.newInstance(position,ItemNumber);
                    break;
                case 1:
                    mFragment[position]=GroupItemViewFragment.newInstance(position,ItemNumber);
                    break;
                case 2:
                    mFragment[position]=GroupItemViewFragment.newInstance(position,ItemNumber);
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

    @Override
    public void ShowDialog(int id,String text) {
        mGroupAddDialog.setMessage("申请加入"+text+"小组");
        add_group=id;
        mGroupAddDialog.show();
    }

    public class LoadingImage extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.drawable.user_avater).into(imageView);
        }
    }
}






