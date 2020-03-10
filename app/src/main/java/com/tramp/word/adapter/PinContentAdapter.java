package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.DefaultLetterEntity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/1.
 */

public class PinContentAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<DefaultLetterEntity> mArray;
    public PinContentAdapter(RecyclerView recyclerView, ArrayList<DefaultLetterEntity> array){
        super(recyclerView);
        mArray=array;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pin_anim_content,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;

        mHolder.mItemPinContent.setText(mArray.get(position).getTitle());

        mHolder.mItemPinLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mArray.get(position).getStatus()){
                    mHolder.mItemPinContent.setTextColor(Color.WHITE);
                    mHolder.mItemPinLinear.setBackground(getContext().getResources().getDrawable(R.drawable.btn_anim_pin_back_nu));
                    mArray.get(position).setStatus(false);
                }else{
                    mHolder.mItemPinContent.setTextColor(Color.BLUE);
                    mHolder.mItemPinLinear.setBackground(getContext().getResources().getDrawable(R.drawable.btn_anim_pin_back));
                    mArray.get(position).setStatus(true);
                }
            }
        });

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mItemPinContent;
        RelativeLayout mItemPinLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            mItemPinContent=$(R.id.item_pin_anim_content);
            mItemPinLinear=$(R.id.item_pin_anim_linear);
        }
    }
}
