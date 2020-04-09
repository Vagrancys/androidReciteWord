package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/24
 * version:1.0
 */
public class AboutShareViewAdapter extends AbsRecyclerViewAdapter{
    private final int[] mImg={
            R.drawable.btn_share_wechat_normal,R.drawable.btn_share_pyq_normal,
            R.drawable.btn_share_sina_normal,R.drawable.btn_share_qq_normal,
            R.drawable.btn_share_qzone_normal
    };
    private final int[] mImgNu={
            R.drawable.btn_share_wechat_pressed,R.drawable.btn_share_pyq_pressed,
            R.drawable.btn_share_sina_pressed,R.drawable.btn_share_qq_pressed,
            R.drawable.btn_share_qzone_pressed
    };
    private final String[] mText={
            "微信好友","朋友圈","新浪微博","QQ好友","QQ空间"
    };

    public AboutShareViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemShareImg.setImageResource(mImg[position]);
        mHolder.ItemShareText.setText(mText[position]);
        mHolder.ItemShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemShareImg.setImageResource(mImgNu[position]);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_share,parent,false));
    }

    @Override
    public int getItemCount() {
        return mText.length;
    }

    private class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemShareImg;
        TextView ItemShareText;
        ItemViewHolder(View itemView){
            super(itemView);
            ItemShareImg=$(R.id.item_group_share_img);
            ItemShareText=$(R.id.item_group_share_text);
        }
    }
}
