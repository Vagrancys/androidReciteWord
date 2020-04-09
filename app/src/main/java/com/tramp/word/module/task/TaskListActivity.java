package com.tramp.word.module.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.TaskListAdvertiseSection;
import com.tramp.word.adapter.section.TaskListSelectSection;
import com.tramp.word.adapter.section.TaskListUnSelectSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.task.TaskInfo;
import com.tramp.word.entity.task.TaskStarInfo;
import com.tramp.word.port.TaskListInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.BookSeekBar;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/17.
 */

public class TaskListActivity extends RxBaseActivity implements TaskListInterFace{
    private static final int DEFAULT_TIME=550;
    private static final int TASK_WINDOW_CODE=0x1111;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_menu)
    ImageView DefaultMenu;
    @BindView(R.id.task_recycler)
    RecyclerView TaskRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.task_pop)
    RelativeLayout TaskPop;
    @BindView(R.id.task_background)
    RelativeLayout TaskBackground;
    @BindView(R.id.task_pop_exit)
    ImageView TaskPopExit;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.task_window)
    LinearLayout TaskWindow;
    @BindView(R.id.window_star)
    TextView WindowStar;
    @BindView(R.id.window_money)
    TextView WindowMoney;

    @BindView(R.id.task_pop_img)
    ImageView TaskPopImg;
    @BindView(R.id.task_pop_title)
    TextView TaskPopTitle;
    @BindView(R.id.task_pop_day)
    TextView TaskPopDay;
    @BindView(R.id.task_neck_star)
    TextView TaskNeckStar;
    @BindView(R.id.task_money)
    LinearLayout TaskMoney;
    @BindView(R.id.task_neck_money)
    TextView TaskNeckMoney;
    @BindView(R.id.task_pop_time)
    TextView TaskPopTime;
    @BindView(R.id.task_pop_summary)
    TextView TaskPopSummary;
    @BindView(R.id.task_seekBar)
    BookSeekBar TaskSeekBar;
    @BindView(R.id.task_pop_button)
    TextView TaskPopButton;
    @BindView(R.id.task_pop_delete)
    TextView TaskPopDelete;
    private SectionedRecyclerViewAdapter mSectionAdapter;
    private Animation ScaleAnim;
    private Animation EnterAnim;
    private Animation ExitAnim;
    private Handler mHandler=new Handler();
    private int[] taskName={
            R.string.task_list_title_4,R.string.task_list_title_1,
            R.string.task_list_title_2,R.string.task_list_title_3,
    };
    private int[] taskImg={
            R.drawable.icon_task,R.drawable.icon_word_list_unstudy,
            R.drawable.icon_word_list_review,R.drawable.icon_word_list_known,
    };
    private ArrayList<TaskInfo.Task> Accepts=new ArrayList<>();
    private ArrayList<TaskInfo.NoTask> NoAccepts=new ArrayList<>();
    private int Value=0;
    private int Status=0;
    private UserSqlHelper mUser;
    private int Star=10;
    private int Money=5;
    @Override
    public int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskListActivity.this,TaskDoneActivity.class));
                Utils.StarActivityInAnim(TaskListActivity.this);
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        initAnim();
        initRefreshLayout();
        initRecyclerView();
    }

    public void initAnim(){
        ScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        EnterAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        ExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);


    }

    public void initRefreshLayout(){
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        SwipeRefreshLayout.post(()->{
            SwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                loadData();
            }
        });
    }

    public void initRecyclerView(){
        mSectionAdapter=new SectionedRecyclerViewAdapter();
        TaskRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        TaskRecycler.setAdapter(mSectionAdapter);

        initClick();
    }

    private void initClick(){

        TaskPopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskPopExit.startAnimation(ScaleAnim);
                TaskPop.startAnimation(ExitAnim);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TaskPop.setVisibility(View.GONE);
                        TaskBackground.setVisibility(View.GONE);
                    }
                },DEFAULT_TIME);
            }
        });

        TaskPopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Status==1){
                    switch (Accepts.get(Value).getTask_status()){
                        case 1:
                            break;
                        case 2:
                            Retrofits.getTaskAPI()
                                    .getTaskStarInfo(mUser.UserId(),Accepts.get(Value).getTask_id(),1)
                                    .enqueue(new Callback<TaskStarInfo>() {
                                        @Override
                                        public void onResponse(Call<TaskStarInfo> call, Response<TaskStarInfo> response) {
                                            if(response.body()!=null&&response.body().getCode()==200){
                                                mSectionAdapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<TaskStarInfo> call, Throwable t) {
                                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                        }
                                    });
                            break;
                        case 3:
                            Retrofits.getTaskAPI()
                                    .getTaskDeleteInfo(Accepts.get(Value).getTask_id())
                                    .enqueue(new Callback<DefaultInfo>() {
                                        @Override
                                        public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                            if(response.body()!=null&&response.body().getCode()==200){
                                                mSectionAdapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                        }
                                    });
                            break;
                        case 4:
                            Retrofits.getTaskAPI()
                                    .getTaskUpdateInfo(Accepts.get(Value).getTask_id(),Star,Money)
                                    .enqueue(new Callback<DefaultInfo>() {
                                        @Override
                                        public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                            if(response.body()!=null&&response.body().getCode()==200){
                                                mSectionAdapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                        }
                                    });
                            break;
                    }
                }else{
                    Retrofits.getTaskAPI()
                            .getTaskInsertInfo(NoAccepts.get(Value).getTask_id())
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body()!=null&&response.body().getCode()==200){
                                        mSectionAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                                }
                            });
                }
                TaskPopExit.startAnimation(ScaleAnim);
                TaskPop.startAnimation(ExitAnim);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TaskPop.setVisibility(View.GONE);
                        TaskBackground.setVisibility(View.GONE);
                    }
                },DEFAULT_TIME);
            }
        });

        TaskPopDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id;
                if(Status==1){
                    id=Accepts.get(Value).getTask_id();
                }else{
                    id=NoAccepts.get(Value).getTask_id();
                }
                Retrofits.getTaskAPI()
                        .getTaskDeleteInfo(id)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    TaskPopExit.startAnimation(ScaleAnim);
                                    TaskPop.startAnimation(ExitAnim);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            TaskPop.setVisibility(View.GONE);
                                            TaskBackground.setVisibility(View.GONE);
                                        }
                                    },DEFAULT_TIME);
                                    mSectionAdapter.notifyDataSetChanged();
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

    public void loadData(){
        UserSqlHelper mUser=new UserSqlHelper(getBaseContext());
        int user=mUser.UserId();
        Call<TaskInfo> mCall = Retrofits.getTaskAPI().getTaskInfo(user);
        addCall(mCall);
        mCall.enqueue(new Callback<TaskInfo>() {
                    @Override
                    public void onResponse(Call<TaskInfo> call, Response<TaskInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Accepts.addAll(response.body().getAccept());
                            NoAccepts.addAll(response.body().getNoaccept());
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<TaskInfo> call, Throwable t) {
                        if(!call.isCanceled()){
                            initEmpty();
                        }
                    }
                });
    }

    public void initEmpty(){
        SwipeRefreshLayout.setRefreshing(false);
        CommonEmpty.setVisibility(View.VISIBLE);
        SwipeRefreshLayout.setVisibility(View.GONE);
    }

    public void clearData(){
        Accepts.clear();
        NoAccepts.clear();
        mSectionAdapter.removeAllSections();
    }

    public void finishTask(){
        SwipeRefreshLayout.setRefreshing(false);
        mSectionAdapter.addSection(new TaskListAdvertiseSection(getBaseContext()));
        mSectionAdapter.addSection(new TaskListSelectSection(this,Accepts,getBaseContext(),taskName,taskImg));
        mSectionAdapter.addSection(new TaskListUnSelectSection(this,NoAccepts,taskName,taskImg));
        mSectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowLayout(int status,int value) {
        TaskBackground.setVisibility(View.VISIBLE);
        TaskPop.setVisibility(View.VISIBLE);
        Value=value;
        Status=status;
        if(status==1){
            initPop(value);
        }else{
            initUnPop(value);
        }
        TaskPop.startAnimation(EnterAnim);
    }

    private void initPop(int value){
        TaskPopImg.setImageResource(taskImg[Accepts.get(value).getTask_class()-1]);
        TaskPopTitle.setText(taskName[Accepts.get(value).getTask_class()-1]);
        TaskPopDay.setText(String.valueOf(Accepts.get(value).getTask_day()));
        TaskNeckStar.setText(String.valueOf(Accepts.get(value).getTask_star()));
        if(Accepts.get(value).getTask_money()==0){
            TaskMoney.setVisibility(View.GONE);
        }else{
            TaskMoney.setVisibility(View.VISIBLE);
            TaskNeckMoney.setText(Accepts.get(value).getTask_money());
        }
        TaskPopTime.setText("进度 "+Accepts.get(value).getTask_no_number()+"/"+Accepts.get(value).getTask_number());
        switch (Accepts.get(value).getTask_status()){
            case 1:
                TaskPopButton.setText(getResources().getString(R.string.main_task_pop_button));
                TaskPopDelete.setVisibility(View.VISIBLE);
                break;
            case 2:
                TaskPopButton.setText(getResources().getString(R.string.task_button_two));
                TaskPopDelete.setVisibility(View.GONE);
                break;
            case 3:
                TaskPopButton.setText(getResources().getString(R.string.task_button_three));
                TaskPopDelete.setVisibility(View.GONE);
                break;
            case 4:
                TaskPopButton.setText(getResources().getString(R.string.task_button_four));
                TaskPopDelete.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initUnPop(int value){
        TaskPopImg.setImageResource(taskImg[NoAccepts.get(value).getTask_class()-1]);
        TaskPopTitle.setText(taskName[NoAccepts.get(value).getTask_class()-1]);
        TaskPopDay.setText(String.valueOf(NoAccepts.get(value).getTask_day()));
        TaskNeckStar.setText(String.valueOf(NoAccepts.get(value).getTask_star()));
        if(NoAccepts.get(value).getTask_money()==0){
            TaskMoney.setVisibility(View.GONE);
        }else{
            TaskMoney.setVisibility(View.VISIBLE);
            TaskNeckMoney.setText(String.valueOf(NoAccepts.get(value).getTask_money()));
        }
        TaskSeekBar.setVisibility(View.GONE);
        TaskPopSummary.setText(NoAccepts.get(value).getTask_summary());
        TaskPopButton.setText(getResources().getString(R.string.task_button_five));
        TaskPopDelete.setVisibility(View.GONE);
    }

    @Override
    public void HideLayout(int value) {
        TaskPop.startAnimation(ExitAnim);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TaskPop.setVisibility(View.GONE);
                TaskBackground.setVisibility(View.GONE);
            }
        },DEFAULT_TIME);
    }

    @Override
    public void ShowPopLayout(int star, int money) {
        TaskBackground.setVisibility(View.VISIBLE);
        TaskWindow.setVisibility(View.VISIBLE);
        WindowStar.setText(String.valueOf(star));
        WindowMoney.setText(String.valueOf(money));
        mHandler.post(()->{
            TaskBackground.setVisibility(View.GONE);
            TaskWindow.setVisibility(View.GONE);
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}









