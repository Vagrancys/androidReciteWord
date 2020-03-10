package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tramp.word.R;
import com.tramp.word.port.TaskListInterFace;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/21.
 */

public class TaskListUnSelectSection extends StatelessSection{
    private Activity mActivity;
    private TaskListInterFace mTaskList;
    public TaskListUnSelectSection(Activity activity){
        super(R.layout.item_task_list_un_select_header,R.layout.item_task_list_un_select_item);
        mActivity=activity;
        mTaskList=(TaskListInterFace) activity;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {

    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.task_list_button_neck){

                }else{
                    mTaskList.ShowLayout(position);
                }
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return 2;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.task_list_linear)
        LinearLayout mLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
