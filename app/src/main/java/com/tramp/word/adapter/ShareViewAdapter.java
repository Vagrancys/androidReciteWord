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
 * time  : 2019/05/17
 * version:1.0
 */
public class ShareViewAdapter extends AbsRecyclerViewAdapter{
    public String[] mTitle;
    private int[] mImg;
    private int[] mUmImg;
    public ShareViewAdapter(RecyclerView recyclerView,String[] title,int[] img,int[] Unimg){
        super(recyclerView);
        mImg=img;
        mTitle=title;
        mUmImg=Unimg;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        bindContext(viewGroup.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_share_layout,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ShareImg.setImageResource(mImg[position]);
        mHolder.ShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ShareImg.setImageResource(mUmImg[position]);
            }
        });
        mHolder.ShareText.setText(mTitle[position]);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ShareImg;
        TextView ShareText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ShareImg=$(R.id.share_img);
            ShareText=$(R.id.share_text);
        }
    }
}
