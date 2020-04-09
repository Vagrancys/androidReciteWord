package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupMedalViewAdapter;
import com.tramp.word.adapter.GroupMessageViewAdapter;
import com.tramp.word.adapter.GroupShareViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupMainInfo;
import com.tramp.word.module.pk.WordPkActivity;
import com.tramp.word.module.home.recite.ReciteWordActivity;
import com.tramp.word.module.revise.ReviseActivity;
import com.tramp.word.module.task.TaskListActivity;
import com.tramp.word.port.GroupMainInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.DefaultSeekBar;


import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/18
 * version:1.0
 */

public class GroupMainActivity extends RxBaseActivity implements GroupMainInterFace{
    @BindView(R.id.group_main_out)
    ImageView GroupMainOut;
    @BindView(R.id.group_main_day)
    ImageView GroupMainDay;
    @BindView(R.id.group_main_linear)
    LinearLayout GroupMainLinear;
    @BindView(R.id.group_main_recycler)
    RecyclerView GroupMainRecycler;
    @BindView(R.id.group_main_message)
    RecyclerView GroupMainMessage;
    @BindView(R.id.group_main_sliding)
    SlidingTabLayout GroupMainSliding;
    @BindView(R.id.group_main_pager)
    ViewPager GroupMainPager;
    @BindView(R.id.main_common_background)
    RelativeLayout MainCommonBackGround;
    @BindView(R.id.main_pop_skip)
    LinearLayout MainPopSkip;
    @BindView(R.id.main_skip_delete)
    TextView MainSkipDelete;
    @BindView(R.id.main_skip_rush)
    LinearLayout MainSkipRush;
    @BindView(R.id.main_skip_review)
    LinearLayout MainSkipReview;
    @BindView(R.id.main_skip_pk)
    LinearLayout MainSkipPk;
    @BindView(R.id.main_skip_task)
    LinearLayout MainSkipTask;
    @BindView(R.id.group_main_menu)
    ImageView GroupMainMenu;
    @BindView(R.id.group_main_swipe)
    SwipeRefreshLayout GroupMainSwipe;

    @BindView(R.id.main_share_recycler)
    RecyclerView MainShareRecycler;
    @BindView(R.id.main_pop_share)
    LinearLayout MainPopShare;
    @BindView(R.id.main_share_delete)
    TextView MainShareDelete;
    @BindView(R.id.group_main_img)
    ImageView GroupMainImg;
    @BindView(R.id.group_main_name)
    TextView GroupMainName;
    @BindView(R.id.group_main_level)
    ImageView GroupMainLevel;
    @BindView(R.id.group_main_padding)
    TextView GroupMainPadding;
    @BindView(R.id.group_main_seek)
    SeekBar GroupMainSeek;
    @BindView(R.id.group_main_number)
    TextView GroupMainNumber;
    @BindView(R.id.main_skip_title)
    TextView MainSkipTitle;
    @BindView(R.id.group_create_pop)
    RelativeLayout GroupCreatePop;
    @BindView(R.id.group_create_next)
    TextView GroupCreateNext;
    @BindView(R.id.group_create_exit)
    ImageView GroupCreateExit;
    @BindView(R.id.group_star_pop)
    RelativeLayout GroupStarPop;
    @BindView(R.id.group_star_exit)
    ImageView GroupStarExit;
    @BindView(R.id.group_star_linear)
    LinearLayout GroupStarLinear;
    @BindView(R.id.group_star_number)
    TextView GroupStarNumber;
    @BindView(R.id.group_star_num)
    TextView GroupStarNum;
    @BindView(R.id.group_star_seek)
    DefaultSeekBar GroupStarSeek;
    @BindView(R.id.group_star_next)
    TextView GroupStarNext;
    @BindView(R.id.group_main_pop)
    LinearLayout GroupMainPop;
    @BindView(R.id.main_come_name)
    TextView MainComeName;
    @BindView(R.id.main_come_content)
    TextView MainComeContent;
    @BindView(R.id.main_come_avatar)
    ImageView MainComeAvatar;
    @BindView(R.id.main_come_text)
    TextView MainComeText;
    @BindView(R.id.user_app_bar)
    AppBarLayout UserAppBar;

    @BindView(R.id.rank_relative)
    RelativeLayout RankRelative;
    @BindView(R.id.group_main_skip)
    LinearLayout GroupMainSkip;
    @BindView(R.id.group_main_rank)
    TextView GroupMainRank;
    @BindView(R.id.group_main_avatar)
    ImageView GroupMainAvatar;
    @BindView(R.id.group_main_title)
    TextView GroupMainTitle;
    @BindView(R.id.group_main_ranks)
    TextView GroupMainRanks;
    @BindView(R.id.group_rank_numbers)
    TextView GroupRankNumber;
    @BindView(R.id.group_main_skip_text)
    TextView GroupMainSkipText;

    @BindView(R.id.board_relative)
    RelativeLayout BoardRelative;
    @BindView(R.id.group_board_edit)
    EditText GroupBoardEdit;
    @BindView(R.id.group_board_send)
    TextView GroupBoardSend;
    @BindView(R.id.group_board_a)
    TextView GroupBoardA;
    @BindView(R.id.group_board_size)
    TextView GroupBoardSize;
    private boolean group_room;
    private GroupMessageViewAdapter mGroupMessageViewAdapter;
    private GroupMedalViewAdapter mGroupMedalViewAdapter;
    private GroupFragmentPager mGroupFragmentPager;
    private GroupShareViewAdapter mGroupShareViewAdapter;
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private Animation mScaleAnim;
    private PopupWindow mPop;
    private GroupMainInfo.Main Mains;
    private ArrayList<GroupMainInfo.medal> Medals=new ArrayList<>();
    private GroupMainInfo.Add Adds;
    private GroupMainInfo.Rank Ranks;
    //创建小组 1 加入小组2 进入小组0
    private int add_status=0;
    private int group_star=5;
    private int user_id;
    private boolean StarStatus;

    private TextView GroupStar;
    private TextView GroupMember;
    private TextView GroupManage;
    private int group_id;
    private float linear_scroll;
    private String member_name;
    private int member_id=0;
    private int member_status=0;
    private int REQUEST_CODE=202;
    private Handler mHandler=new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_main;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            add_status=intent.getIntExtra(ConstantUtils.ADD_STATUS,0);
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        lazyData();
        initData();
        initClickListener();
    }

    public void lazyData(){
       initRefreshLayout();
       initRecyclerView();
    }

    private void initRefreshLayout(){
        GroupMainSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        GroupMainSwipe.post(()->{
            GroupMainSwipe.setRefreshing(true);
            loadData();
        });
        GroupMainSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GroupMainSwipe.setRefreshing(true);
                clearData();
                loadData();
            }
        });
    }

    private void initRecyclerView(){
        initData();
        initClickListener();
        UserBoard();
    }

    public void clearData(){
        Medals.clear();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupMainInfo(group_id,user_id,add_status)
                .enqueue(new Callback<GroupMainInfo>() {
                    @Override
                    public void onResponse(Call<GroupMainInfo> call, Response<GroupMainInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Mains=response.body().getMains();
                            Medals.addAll(response.body().getMedal());
                            Adds=response.body().getAdd();
                            Ranks=response.body().getRank();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"没有数据!");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupMainInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void finishTask(){
        GroupMainSwipe.setRefreshing(false);
        Glide.with(getBaseContext())
                .load(Mains.getGroup_img())
                .placeholder(R.drawable.user_avater)
                .into(GroupMainImg);
        GroupMainName.setText(Mains.getGroup_name());
        GroupMainLevel.setImageResource(Utils.getGroupLevelImg(Mains.getGroup_level()));
        GroupMainSeek.setMax(Mains.getGroup_all_star());
        GroupMainSeek.setProgress(Mains.getGroup_star());
        GroupMainNumber.setText(Mains.getGroup_star()+"/"+Mains.getGroup_all_star());
        mGroupMedalViewAdapter.notifyDataSetChanged();
        mGroupFragmentPager.notifyDataSetChanged();
        member_status=Mains.getGroup_admin();
        if(member_status==1){
            GroupStar.setVisibility(View.VISIBLE);
            GroupMember.setVisibility(View.GONE);
            GroupManage.setVisibility(View.VISIBLE);
        }else{
            GroupStar.setVisibility(View.GONE);
            GroupMember.setVisibility(View.VISIBLE);
            GroupManage.setVisibility(View.GONE);
        }
        if(add_status==1){
            MainCommonBackGround.setVisibility(View.VISIBLE);
            GroupCreatePop.setVisibility(View.VISIBLE);
            GroupCreatePop.startAnimation(mTopEnterAnim);
        }else if(add_status==2){
            MainCommonBackGround.setVisibility(View.VISIBLE);
            GroupMainPop.setVisibility(View.VISIBLE);
            GroupMainPop.startAnimation(mTopEnterAnim);
            MainComeName.setText("Hi "+Adds.getUser_name());
            MainComeContent.setText("欢迎加入\""+Adds.getGroup_name()+"\"小组,成为第"+Adds.getGroup_rank()+"位组员.为了我们小组更好的" +
                    "成长和升级,希望你每日获得"+Adds.getGroup_star()+"颗词能量(背词.复习.pk.任务)。不要辜负组员的希望哦~");
            Glide.with(getBaseContext())
                    .load(Adds.getAdmin_img())
                    .placeholder(R.drawable.user_avater)
                    .into(MainComeAvatar);
            MainComeText.setText(Adds.getAdmin_name());
        }
        UserRank();

    }

    private void UserRank(){
        GroupMainRank.setText(Ranks.getUser_rank());
        GroupMainTitle.setText("我");
        Glide.with(getBaseContext())
                .load(Ranks.getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(GroupMainAvatar);
        GroupMainRanks.setText("第"+Ranks.getUser_rank()+"名");
        GroupRankNumber.setText(Ranks.getUser_star()+"/"+Ranks.getUser_star_up());
        if(Ranks.getUser_star()>Ranks.getUser_star_up()){
            GroupMainSkip.setBackground(getResources().getDrawable(R.drawable.btn_recite_anim_error));
            GroupMainSkipText.setText("非常棒");
            StarStatus=true;
        }else{
            GroupMainSkip.setBackground(getResources().getDrawable(R.drawable.btn_rank_error_bg));
            GroupMainSkipText.setText("未完成");
            StarStatus=false;
        }
    }

    private void UserBoard(){
        GroupBoardEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    GroupBoardEdit.setHeight(R.dimen.default_margin_size_9);
                    GroupBoardA.setVisibility(View.VISIBLE);
                    GroupBoardSize.setVisibility(View.VISIBLE);
                }else{
                    GroupBoardEdit.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    GroupBoardA.setVisibility(View.GONE);
                    GroupBoardSize.setVisibility(View.GONE);
                }
            }
        });

        GroupBoardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupBoardAActivity.launch(GroupMainActivity.this,group_id,REQUEST_CODE);
            }
        });

        GroupBoardEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                GroupBoardSize.setText(200-GroupBoardEdit.getText().toString().length()+"字");
            }
        });

        GroupBoardEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    GroupBoardEdit.setMinLines(3);
                    GroupBoardSize.setVisibility(View.VISIBLE);
                    GroupBoardA.setVisibility(View.VISIBLE);
                }else{
                    GroupBoardEdit.setMinLines(1);
                    GroupBoardSize.setVisibility(View.GONE);
                    GroupBoardA.setVisibility(View.GONE);
                }
            }
        });


        GroupBoardSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GroupBoardEdit.getText().toString().length()<=0){
                    return;
                }
                Retrofits.getGroupAPI()
                        .getSendFormInfo(user_id,
                                member_id,group_id,
                                GroupBoardEdit.getText().toString())
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    Utils.ShowToast(getBaseContext(),"留言成功!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });
            }
        });
    }

    public void initData(){
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        group_room= PreferencesUtils.getBoolean(ConstantUtils.GROUP_ROOM,false);
        if(group_room){
            GroupMainPadding.setVisibility(View.GONE);
        }else{
            GroupMainPadding.setVisibility(View.VISIBLE);
        }

        mPop=new PopupWindow(getBaseContext());
        View mView=LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_group_main,null);
        mPop.setContentView(mView);
        mPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_background_radius));
        mPop.setAnimationStyle(R.style.popup_recite_style_anim);
        mPop.setFocusable(true);
        GroupStar=(TextView) mView.findViewById(R.id.group_star);
        GroupManage=(TextView) mView.findViewById(R.id.group_manage);
        GroupMember=(TextView) mView.findViewById(R.id.group_member);

        GroupStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                MainCommonBackGround.setVisibility(View.VISIBLE);
                GroupStarPop.setVisibility(View.VISIBLE);
                GroupStarPop.startAnimation(mTopEnterAnim);
            }
        });
        GroupManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                GroupMemberActivity.launch(GroupMainActivity.this,group_id,1);
            }
        });

        TextView GroupLevel=(TextView) mView.findViewById(R.id.group_level);

        GroupLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                GroupLevelActivity.launch(GroupMainActivity.this,group_id);
            }
        });
        TextView GroupMedal=(TextView) mView.findViewById(R.id.group_medal);

        GroupMedal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                GroupMedalActivity.launch(GroupMainActivity.this,group_id,1);
            }
        });

        TextView GroupShare=(TextView) mView.findViewById(R.id.group_share);

        GroupShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainCommonBackGround.setVisibility(View.VISIBLE);
                MainPopShare.setVisibility(View.VISIBLE);
                MainPopShare.startAnimation(mTopEnterAnim);
                mPop.dismiss();
            }
        });
        TextView GroupDetails=(TextView) mView.findViewById(R.id.group_details);

        GroupDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(member_status==1){
                    GroupUpdateActivity.launch(GroupMainActivity.this,group_id);
                }else{
                    GroupDataActivity.launch(GroupMainActivity.this,group_id);
                }
                mPop.dismiss();
            }
        });

        GroupMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberActivity.launch(GroupMainActivity.this,group_id,0);
                mPop.dismiss();
            }
        });

        mGroupMedalViewAdapter=new GroupMedalViewAdapter(GroupMainRecycler,Medals);
        GroupMainRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupMainRecycler.setAdapter(mGroupMedalViewAdapter);

        mGroupMessageViewAdapter=new GroupMessageViewAdapter(GroupMainMessage);
        GroupMainMessage.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupMainMessage.setAdapter(mGroupMessageViewAdapter);

        mGroupShareViewAdapter=new GroupShareViewAdapter(MainShareRecycler);
        MainShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),4));
        MainShareRecycler.setAdapter(mGroupShareViewAdapter);

        mGroupShareViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"分享id"+position);
            }
        });

        GroupMainSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLayout(StarStatus);
            }
        });
        mGroupFragmentPager=new GroupFragmentPager(getSupportFragmentManager());
        GroupMainPager.setOffscreenPageLimit(2);
        GroupMainPager.setCurrentItem(0);
        GroupMainPager.setAdapter(mGroupFragmentPager);
        GroupMainSliding.setViewPager(GroupMainPager);

        GroupMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(linear_scroll<v){
                    RankRelative.setLeft(i1);
                    BoardRelative.setLeft(i1);
                }else{
                    RankRelative.setLeft(-i1);
                    BoardRelative.setLeft(-i1);
                }
                if(v==0.0&&i==0){
                    RankRelative.setVisibility(View.VISIBLE);
                    BoardRelative.setVisibility(View.GONE);
                }else if(v==0.0&&i==1){
                    RankRelative.setVisibility(View.GONE);
                    BoardRelative.setVisibility(View.VISIBLE);
                }else{
                    RankRelative.setVisibility(View.VISIBLE);
                    BoardRelative.setVisibility(View.VISIBLE);
                }
                linear_scroll=v;
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        UserAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(i>=0){
                    GroupMainSwipe.setEnabled(true);
                }else{
                    GroupMainSwipe.setEnabled(false);
                }
            }
        });
    }

    public void initClickListener(){
        GroupMainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putBoolean(ConstantUtils.GROUP_ROOM,true);
                startActivity(new Intent(GroupMainActivity.this,GroupContentActivity.class));
                Utils.StarActivityInAnim(GroupMainActivity.this);
            }
        });

        GroupMainRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMedalActivity.launch(GroupMainActivity.this,group_id,1);
            }
        });

        MainCommonBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainPopSkip.getVisibility()==View.VISIBLE){
                    MainPopSkip.startAnimation(mTopExitAnim);
                    MainPopSkip.setVisibility(View.GONE);
                }
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });

        MainSkipDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPopSkip.startAnimation(mTopExitAnim);
                MainPopSkip.setVisibility(View.GONE);
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });

        MainSkipRush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupMainActivity.this, ReciteWordActivity.class));
                Utils.StarActivityInAnim(GroupMainActivity.this);
            }
        });

        MainSkipReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupMainActivity.this, ReviseActivity.class));
                Utils.StarActivityInAnim(GroupMainActivity.this);
            }
        });

        MainSkipPk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupMainActivity.this, WordPkActivity.class));
                Utils.StarActivityInAnim(GroupMainActivity.this);
            }
        });

        MainSkipTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupMainActivity.this,TaskListActivity.class));
                Utils.StarActivityInAnim(GroupMainActivity.this);
            }
        });

        MainShareDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPopShare.startAnimation(mTopExitAnim);
                MainPopShare.setVisibility(View.GONE);
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });

        mGroupShareViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                MainPopShare.startAnimation(mTopExitAnim);
                MainPopShare.setVisibility(View.GONE);
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });

        GroupCreateNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainCommonBackGround.setVisibility(View.VISIBLE);
                MainPopShare.setVisibility(View.VISIBLE);
                MainPopShare.startAnimation(mTopEnterAnim);
            }
        });

        GroupCreateExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCreatePop.startAnimation(mTopExitAnim);
                GroupCreatePop.setVisibility(View.GONE);
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });
        GroupStarExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupStarPop.startAnimation(mTopExitAnim);
                GroupStarPop.setVisibility(View.GONE);
                MainCommonBackGround.setVisibility(View.GONE);
            }
        });

        GroupStarNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupStarNext.startAnimation(mScaleAnim);
                if(Mains.getGroup_star() !=group_star){
                    Retrofits.getGroupAPI()
                            .getGroupStarInfo(group_id,group_star)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body() !=null&& response.body().getCode()==200){
                                        GroupStarPop.startAnimation(mTopExitAnim);
                                        GroupStarPop.setVisibility(View.GONE);
                                        MainCommonBackGround.setVisibility(View.GONE);
                                    }else{
                                        Utils.ShowToast(getBaseContext(),"修改失败,重试吧!");
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                }
                            });
                }else{
                    GroupStarPop.startAnimation(mTopExitAnim);
                    GroupStarPop.setVisibility(View.GONE);
                    MainCommonBackGround.setVisibility(View.GONE);
                }

            }
        });

        GroupStarSeek.setOnProgressChangeListener(new DefaultSeekBar.OnProgressChangeListener() {
            @Override
            public void onChange(int selectProgress) {
                group_star=selectProgress;
                GroupStarNum.setText(selectProgress+"词能量x1名组员");
                GroupStarNumber.setText(selectProgress);
                GroupStarLinear.setScaleX(1.4f);
                GroupStarLinear.setScaleY(1.4f);
            }

            @Override
            public void onEnd(int selectProgress) {
                group_star=selectProgress;
                GroupStarNum.setText(selectProgress+"词能量x1名组员");
                GroupStarNumber.setText(selectProgress);
                GroupStarLinear.setScaleX(1f);
                GroupStarLinear.setScaleY(1f);
            }
        });
    }

    @Override
    public void ShowLayout(boolean status) {
        if(status){
            MainSkipTitle.setText(getResources().getString(R.string.main_skip_title));
        }else{
            MainSkipTitle.setText(getResources().getString(R.string.main_skip_title1));
        }
        MainCommonBackGround.setVisibility(View.VISIBLE);
        MainPopSkip.setVisibility(View.VISIBLE);
        MainPopSkip.startAnimation(mTopEnterAnim);
    }

    @Override
    public void ShowShareLayout() {
        MainCommonBackGround.setVisibility(View.VISIBLE);
        MainPopShare.setVisibility(View.VISIBLE);
        MainPopShare.startAnimation(mTopEnterAnim);
    }

    @Override
    protected void initToolBar() {
        GroupMainOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GroupMainDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDayActivity.launch(GroupMainActivity.this,group_id);
            }
        });

        GroupMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.showAsDropDown(GroupMainMenu,-50,0);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public class GroupFragmentPager extends FragmentPagerAdapter{
        private String[] mTitle={
                "排行榜","留言板"
        };
        private Fragment[] mFragment;

        public GroupFragmentPager(FragmentManager fm){
            super(fm);
            mFragment=new Fragment[mTitle.length];
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    mFragment[position]=GroupRankFragment.newInstance(group_id);
                    break;
                case 1:
                    mFragment[position]=GroupBoardFragment.newInstance(group_id);
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

    public static void launch(Activity activity,int create_status,int group_id){
        Intent intent=new Intent(activity,GroupMainActivity.class);
        intent.putExtra(ConstantUtils.ADD_STATUS,create_status);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE){
            if(resultCode==201){
                if(data !=null){
                    member_id=data.getIntExtra(ConstantUtils.MEMBER_ID,0);
                    member_name=data.getStringExtra(ConstantUtils.MEMBER_NAME);
                    mHandler.post(()->{
                        GroupBoardEdit.setText("@"+member_name);
                    });
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void AMember(String name, int id) {
        member_id=id;
        member_name=name;
        GroupBoardEdit.setText("@"+member_name);
    }
}







