package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tramp.word.R;
import com.tramp.word.widget.section.StatelessSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/19.
 */

public class TaskListAdvertiseSection extends StatelessSection {
    private Context mContext;
    public TaskListAdvertiseSection(Context context){
        super(R.layout.item_task_list_advertise_item);
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.mTaskAdvertiseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"广告",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.task_advertise_img)
        ImageView mTaskAdvertiseImg;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}











