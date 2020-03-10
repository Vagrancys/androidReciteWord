package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/1/21.
 */

public class WordManageViewAdapter extends AbsRecyclerViewAdapter {
    private int ListStart;
    public WordManageViewAdapter(RecyclerView recyclerView,int list){
        super(recyclerView);
        ListStart=list;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_manage,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            if(ListStart==1){
                mHolder.mWordBookImageTwo.setVisibility(View.GONE);
                mHolder.mWordBookImageOne.setVisibility(View.VISIBLE);
            }else{
                mHolder.mWordBookImageTwo.setVisibility(View.VISIBLE);
                mHolder.mWordBookImageOne.setVisibility(View.GONE);
            }
            mHolder.mRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mHolder.mWordBookImageOne.getVisibility()==View.GONE){
                        mHolder.mWordBookImageTwo.setVisibility(View.GONE);
                        mHolder.mWordBookImageOne.setVisibility(View.VISIBLE);
                    }else{
                        mHolder.mWordBookImageTwo.setVisibility(View.VISIBLE);
                        mHolder.mWordBookImageOne.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout mRelative;
        ImageView mWordBookImageOne;
        ImageView mWordBookImageTwo;
        TextView mWordBookText;
        public ItemViewHolder(View itemView){
            super(itemView);
            mRelative=$(R.id.relative);
            mWordBookText=$(R.id.recite_word_manage_text);
            mWordBookImageOne=$(R.id.recite_word_manage_img_one);
            mWordBookImageTwo=$(R.id.recite_word_manage_img_two);
        }
    }
}









