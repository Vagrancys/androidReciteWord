package com.tramp.word.adapter.section;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.task.TaskInfo;
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

public class TaskListUnSelectSection extends StatelessSection{
    private Activity mActivity;
    private TaskListInterFace mTaskList;
    private ArrayList<TaskInfo.NoTask> NoAccepts;
    private int[] TaskName;
    private int[] TaskImg;
    public TaskListUnSelectSection(Activity activity,ArrayList<TaskInfo.NoTask> noAccepts,int[] taskName,int[] taskImg){
        super(R.layout.item_task_un_select_header,R.layout.item_task_un_select_item);
        mActivity=activity;
        NoAccepts=noAccepts;
        TaskName=taskName;
        TaskImg=taskImg;
        mTaskList=(TaskListInterFace) activity;
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
        mHolder.TaskImg.setImageResource(TaskImg[NoAccepts.get(position).getTask_class()-1]);
        mHolder.TaskTitle.setText(TaskName[NoAccepts.get(position).getTask_class()-1]);
        mHolder.TaskNeckSummary.setVisibility(View.GONE);
        mHolder.TaskMoney.setVisibility(View.GONE);
        switch (NoAccepts.get(position).getTask_class()){
            case 3:
                mHolder.TaskSummary.setText(R.string.item_task_class_3);
                mHolder.TaskNeckSummary.setVisibility(View.VISIBLE);
                break;
            case 4:
                mHolder.TaskSummary.setText(R.string.item_task_class_4);
                mHolder.TaskMoney.setVisibility(View.VISIBLE);
                mHolder.TaskNeckSummary.setVisibility(View.VISIBLE);
                break;
        }
        mHolder.TaskNeckStar.setText("x"+NoAccepts.get(position).getTask_star());
        mHolder.TaskNeckMoney.setText("x"+NoAccepts.get(position).getTask_money());
        mHolder.TaskLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskList.ShowLayout(2,position);
            }
        });
        mHolder.TaskButtonNeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getTaskAPI()
                        .getTaskInsertInfo(NoAccepts.get(position).getTask_id())
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    mHolder.TaskLinear.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(mActivity,mActivity.getResources().getString(R.string.forget_net_error));
                            }
                        });
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return NoAccepts.size();
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
        @BindView(R.id.task_summary)
        TextView TaskSummary;
        @BindView(R.id.task_neck_star)
        TextView TaskNeckStar;
        @BindView(R.id.task_money)
        LinearLayout TaskMoney;
        @BindView(R.id.task_neck_money)
        TextView TaskNeckMoney;
        @BindView(R.id.task_neck_summary)
        TextView TaskNeckSummary;
        @BindView(R.id.task_button_neck)
        TextView TaskButtonNeck;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
