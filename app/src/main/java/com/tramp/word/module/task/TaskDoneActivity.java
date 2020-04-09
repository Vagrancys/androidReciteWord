package com.tramp.word.module.task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupTagAdapter;
import com.tramp.word.adapter.TaskDoneViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.task.TaskListInfo;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/19.
 */

public class TaskDoneActivity extends RxBaseActivity {
    @BindView(R.id.task_done_out)
    ImageView mTaskDoneOut;
    @BindView(R.id.task_done_recycler)
    RecyclerView mTaskDoneRecycler;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.task_done_linear)
    LinearLayout TaskDoneLinear;
    @BindView(R.id.task_done_number)
    TextView TaskDoneNumber;
    private Call<TaskListInfo> mCall;
    private ArrayList<TaskListInfo.List> lists=new ArrayList<>();
    private TaskDoneViewAdapter mTaskDoneViewAdapter;

    private int[] taskName={
            R.string.task_list_title_4,R.string.task_list_title_1,
            R.string.task_list_title_2,R.string.task_list_title_3,
    };
    private int[] taskImg={
            R.drawable.icon_task,R.drawable.icon_word_list_unstudy,
            R.drawable.icon_word_list_review,R.drawable.icon_word_list_known,
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_done;
    }

    @Override
    protected void initToolBar() {
        mTaskDoneOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        initRefreshLayout();
        initRecyclerView();
    }

    public void initRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearData();
                loadData();
            }
        });
    }

    public void initRecyclerView(){
        mTaskDoneViewAdapter=new TaskDoneViewAdapter(mTaskDoneRecycler,lists,taskImg,taskName);
        mTaskDoneRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mTaskDoneRecycler.setAdapter(mTaskDoneViewAdapter);
    }

    public void loadData(){
        int user= new UserSqlHelper(getBaseContext()).UserId();
        mCall = Retrofits.getTaskAPI().getTaskListInfo(user);
        addCall(mCall);
        mCall.enqueue(new Callback<TaskListInfo>() {
            @Override
            public void onResponse(Call<TaskListInfo> call, Response<TaskListInfo> response) {
                if(response.body().getCode()==200){
                    lists.addAll(response.body().getLists());
                    finishTask();
                }else{
                    initEmpty();
                }
            }

            @Override
            public void onFailure(Call<TaskListInfo> call, Throwable t) {
                if(!call.isCanceled()){
                    initEmpty();
                }
            }
        });

    }

    public void initDataEmpty(){
        TaskDoneLinear.setVisibility(View.GONE);
    }

    public void initEmpty(){
        Log.e("测试","空指针 =" +mSwipeRefreshLayout.getVisibility());
        mSwipeRefreshLayout.setRefreshing(false);
        CommonEmpty.setVisibility(View.VISIBLE);
        TaskDoneLinear.setVisibility(View.GONE);
    }

    public void clearData(){
        lists.clear();
    }

    public void finishTask(){
        mSwipeRefreshLayout.setRefreshing(false);
        mTaskDoneViewAdapter.notifyDataSetChanged();
        TaskDoneNumber.setText("共完成"+lists.size()+"项任务");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}






