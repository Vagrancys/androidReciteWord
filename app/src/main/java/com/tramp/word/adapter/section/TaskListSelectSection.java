package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.task.TaskInfo;
import com.tramp.word.entity.task.TaskStarInfo;
import com.tramp.word.module.home.recite.ReciteWordActivity;
import com.tramp.word.module.revise.ReviseActivity;
import com.tramp.word.port.TaskListInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/21.
 */

public class TaskListSelectSection extends StatelessSection {
    private Activity mActivity;
    private TaskListInterFace mTaskList;
    private ArrayList<TaskInfo.Task> mAccept;
    private int[] TaskName;
    private int[] TaskImg;
    private int GateDay=10;
    private int FinishDay=4;
    private Animation mTaskScaleAnim;
    private Animation mScaleAnim;
    private int UserId;
    public TaskListSelectSection(Activity activity,ArrayList<TaskInfo.Task> accept,Context context,int[] taskName,int[] taskImg){
        super(R.layout.item_task_select_header,R.layout.item_task_select_item);
        mActivity=activity;
        mAccept=accept;
        TaskName=taskName;
        TaskImg=taskImg;
        mTaskList=(TaskListInterFace) mActivity;
        mScaleAnim= AnimationUtils.loadAnimation(mActivity,R.anim.default_button_scale_anim);
        mTaskScaleAnim= AnimationUtils.loadAnimation(mActivity,R.anim.task_scale_anim);
        UserId= new UserSqlHelper(context).UserId();
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.TaskImg.setImageResource(TaskImg[mAccept.get(position).getTask_class()-1]);
        mHolder.TaskTitle.setText(TaskName[mAccept.get(position).getTask_class()-1]);
        mHolder.TaskMoney.setVisibility(View.GONE);
        mHolder.TaskNeckSummary.setVisibility(View.GONE);
        mHolder.TaskAccept.setVisibility(View.GONE);
        switch (mAccept.get(position).getTask_class()){
            case 1:
                mHolder.TaskDay.setText("第"+mAccept.get(position).getTask_day()+"天");
                mHolder.TaskButtonNeck.setText(R.string.item_task_button_2);
                break;
            case 2:
                mHolder.TaskDay.setText("第"+mAccept.get(position).getTask_day()+"天-还剩"+GateDay+"天背完");
                mHolder.TaskButtonNeck.setText(R.string.item_task_button_1);
                break;
            case 3:
                mHolder.TaskDay.setText(R.string.item_task_class_3);
                mHolder.TaskMoney.setVisibility(View.VISIBLE);
                mHolder.TaskNeckSummary.setVisibility(View.VISIBLE);
                mHolder.TaskButtonNeck.setText(R.string.item_task_button_2);
                break;
            case 4:
                mHolder.TaskDay.setText(R.string.item_task_class_4);
                mHolder.TaskNeckSummary.setVisibility(View.VISIBLE);
                mHolder.TaskButtonNeck.setText(R.string.item_task_button_4);
                break;
        }
        if(mAccept.get(position).getTask_no_star()>0){
            mHolder.TaskAccept.setVisibility(View.VISIBLE);
        }else{
            mHolder.TaskAccept.setVisibility(View.GONE);
        }
        mHolder.TaskStatus.setVisibility(View.VISIBLE);
        mHolder.TaskProgressLayout.setVisibility(View.GONE);
        mHolder.TaskNeck.setVisibility(View.GONE);
        switch (mAccept.get(position).getTask_status()){
            case 1:
                mHolder.TaskStatus.setVisibility(View.GONE);
                mHolder.TaskButtonNeck.setBackgroundResource(R.drawable.btn_register_button_start);
                break;
            case 2:
                mHolder.TaskStatus.setImageResource(R.drawable.icon_task_complete);
                mHolder.TaskButtonNeck.setText(R.string.item_task_button_3);
                mHolder.TaskButtonNeck.setBackgroundResource(R.drawable.btn_task_list_button_neck);
                mHolder.TaskProgressLayout.setVisibility(View.VISIBLE);
                mHolder.TaskNeck.setVisibility(View.VISIBLE);
                break;
            case 3:
                mHolder.TaskStatus.setImageResource(R.drawable.img_task_fail);
                mHolder.TaskButtonNeck.setText("失败");
                break;
            case 4:
                mHolder.TaskStatus.setImageResource(R.drawable.img_task_accept);
                break;
        }
        mHolder.TaskStatus.startAnimation(mTaskScaleAnim);
        mHolder.TaskProgress.setProgress(mAccept.get(position).getTask_no_number());
        mHolder.TaskProgress.setMax(mAccept.get(position).getTask_number());
        mHolder.TaskProgressText.setText("进度 "+mAccept.get(position).getTask_no_number()+"/"+mAccept.get(position).getTask_number());
        mHolder.TaskNeckStar.setText("x"+mAccept.get(position).getTask_star());
        mHolder.TaskNeckMoney.setText("x"+mAccept.get(position).getTask_money());
        mHolder.TaskNeckSummary.setText("距活动结束"+FinishDay+"天");
        mHolder.TaskAcceptStar.setText("x"+mAccept.get(position).getTask_no_star());
        mHolder.TaskLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskList.ShowLayout(1,position);
            }
        });
        mHolder.TaskButtonNeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.TaskButtonNeck.startAnimation(mScaleAnim);
                switch (mAccept.get(position).getTask_status()){
                    case 1:
                        switch (mAccept.get(position).getTask_class()){
                            case 1:
                                mActivity.startActivity(new Intent(mActivity, ReviseActivity.class));
                                mActivity.finish();
                                break;
                            case 2:
                                mActivity.startActivity(new Intent(mActivity, ReciteWordActivity.class));
                                mActivity.finish();
                                break;
                            case 3:
                                mActivity.startActivity(new Intent(mActivity, ReciteWordActivity.class));
                                mActivity.finish();
                                break;
                            case 4:
                                break;
                        }
                        break;
                    case 2:
                        Retrofits.getTaskAPI()
                                .getTaskStarInfo(UserId,mAccept.get(position).getTask_id(),1)
                                .enqueue(new Callback<TaskStarInfo>() {
                                    @Override
                                    public void onResponse(Call<TaskStarInfo> call, Response<TaskStarInfo> response) {
                                        if(response.body()!=null&&response.body().getCode()==200){
                                            mTaskList.ShowPopLayout(mAccept.get(position).getTask_star(),mAccept.get(position).getTask_money());
                                            mHolder.TaskProgressLayout.setVisibility(View.GONE);
                                            mHolder.TaskNeck.setVisibility(View.GONE);
                                            mHolder.TaskStatus.setImageResource(R.drawable.img_task_accept);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<TaskStarInfo> call, Throwable t) {
                                        Utils.ShowToast(mActivity,mActivity.getResources().getString(R.string.forget_net_error));
                                    }
                                });
                        break;
                }
            }
        });
        mHolder.TaskAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getTaskAPI()
                        .getTaskStarInfo(UserId,mAccept.get(position).getTask_id(),2)
                        .enqueue(new Callback<TaskStarInfo>() {
                            @Override
                            public void onResponse(Call<TaskStarInfo> call, Response<TaskStarInfo> response) {
                                if(response.body().getCode()==200){
                                    Utils.ShowToast(mActivity,"领取历史奖励成功!");
                                    mHolder.TaskAccept.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<TaskStarInfo> call, Throwable t) {

                            }
                        });
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return mAccept.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.task_linear)
        LinearLayout TaskLinear;
        @BindView(R.id.task_img)
        ImageView TaskImg;
        @BindView(R.id.task_title)
        TextView TaskTitle;
        @BindView(R.id.task_day)
        TextView TaskDay;
        @BindView(R.id.task_status)
        ImageView TaskStatus;
        @BindView(R.id.task_progress_layout)
        LinearLayout TaskProgressLayout;
        @BindView(R.id.task_progress)
        ProgressBar TaskProgress;
        @BindView(R.id.task_progress_text)
        TextView TaskProgressText;
        @BindView(R.id.task_neck_star)
        TextView TaskNeckStar;
        @BindView(R.id.task_neck_money)
        TextView TaskNeckMoney;
        @BindView(R.id.task_money)
        LinearLayout TaskMoney;
        @BindView(R.id.task_neck_summary)
        TextView TaskNeckSummary;
        @BindView(R.id.task_button_neck)
        TextView TaskButtonNeck;
        @BindView(R.id.task_accept)
        LinearLayout TaskAccept;
        @BindView(R.id.task_accept_button)
        TextView TaskAcceptButton;
        @BindView(R.id.task_accept_star)
        TextView TaskAcceptStar;
        @BindView(R.id.task_neck)
        RelativeLayout TaskNeck;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}










