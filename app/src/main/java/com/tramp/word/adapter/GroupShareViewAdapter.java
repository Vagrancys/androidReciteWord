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
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupShareViewAdapter extends AbsRecyclerViewAdapter{
    private int[] mImg={
            R.drawable.icon_wechat,R.drawable.icon_qq2,R.drawable.icon_wx_timeline,
            R.drawable.icon_qqzone,R.drawable.icon_weibo,R.drawable.icon_weibo
    };
    private String[] mText={
            "微信","QQ","朋友圈","QQ空间","新浪微博","生成海报"
    };

    public GroupShareViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemGroupShareImg.setImageResource(mImg[position]);
        mHolder.ItemGroupShareText.setText(mText[position]);
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

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemGroupShareImg;
        TextView ItemGroupShareText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemGroupShareImg=$(R.id.item_group_share_img);
            ItemGroupShareText=$(R.id.item_group_share_text);
        }
    }
}
