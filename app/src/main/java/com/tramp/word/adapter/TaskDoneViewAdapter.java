package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.task.TaskListInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/1/21.
 */

public class TaskDoneViewAdapter extends AbsRecyclerViewAdapter {
    private int[] TaskImg;
    private int[] TaskName;
    private ArrayList<TaskListInfo.List> Lists;
    public TaskDoneViewAdapter(RecyclerView recyclerView,ArrayList<TaskListInfo.List> lists,int[] taskImg,int[] taskName){
        super(recyclerView);
        Lists=lists;
        TaskImg=taskImg;
        TaskName=taskName;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_task_done,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.TaskDoneImg.setImageResource(TaskImg[Lists.get(position).getTask_class()-1]);
            mHolder.TaskDoneTitle.setText(TaskName[Lists.get(position).getTask_class()-1]);
            mHolder.TaskDoneDay.setText("第"+Lists.get(position).getTask_day()+"天");
            switch (Lists.get(position).getTask_status()){
                case 2:
                   mHolder.TaskDoneStatus.setImageResource(R.drawable.icon_task_complete);
                    break;
                case 3:
                    mHolder.TaskDoneStatus.setImageResource(R.drawable.img_task_fail);
                    break;
            }
            mHolder.TaskDoneStar.setText(Lists.get(position).getTask_star());
            mHolder.TaskDoneMoney.setText(Lists.get(position).getTask_money());
            mHolder.TaskDoneTime.setText(Lists.get(position).getTask_time());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView TaskDoneImg;
        TextView TaskDoneTitle;
        TextView TaskDoneDay;
        ImageView TaskDoneStatus;
        TextView TaskDoneStar;
        TextView TaskDoneMoney;
        TextView TaskDoneTime;
        public ItemViewHolder(View itemView){
            super(itemView);
            TaskDoneImg=$(R.id.task_done_img);
            TaskDoneTitle=$(R.id.task_done_title);
            TaskDoneDay=$(R.id.task_done_summary);
            TaskDoneStatus=$(R.id.task_done_status);
            TaskDoneStar=$(R.id.task_done_neck_star);
            TaskDoneMoney=$(R.id.task_done_neck_money);
            TaskDoneTime=$(R.id.task_done_neck_time);
        }
    }
}
