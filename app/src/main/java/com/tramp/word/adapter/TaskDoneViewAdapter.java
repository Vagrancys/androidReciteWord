package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tramp.word.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/21.
 */

public class TaskDoneViewAdapter extends AbsRecyclerViewAdapter {

    public TaskDoneViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
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
            if(position==0){
                mHolder.mTaskDoneHead.setVisibility(View.VISIBLE);
            }else{
                mHolder.mTaskDoneHead.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout mTaskDoneHead;
        public ItemViewHolder(View itemView){
            super(itemView);
            mTaskDoneHead=$(R.id.task_done_head);
        }
    }
}
