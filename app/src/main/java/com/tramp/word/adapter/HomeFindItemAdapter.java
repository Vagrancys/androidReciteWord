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
 * time  : 2019/03/10
 * version:1.0
 */

public class HomeFindItemAdapter extends AbsRecyclerViewAdapter{
    private int[] mImg={
            R.drawable.icon_discover_nonet_qiu,R.drawable.pic_cup,R.drawable.icon_discover_nonet_ni,R.drawable.icon_discover_nonet_search,
            R.drawable.icon_discover_nonet_scb,R.drawable.icon_gift,R.drawable.icon_discover_nonet_kuai,R.drawable.icon_discover_nonet_lian
    };
    private String[] mTitle={
            "词汇量测试","排行榜","词汇社","查询",
            "生词本","词场商城","阅读","备考专栏"
    };
    public HomeFindItemAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_find_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemHomeFindImg.setImageResource(mImg[position]);
        mHolder.ItemHomeFindText.setText(mTitle[position]);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mImg.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemHomeFindImg;
        TextView ItemHomeFindText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemHomeFindImg=$(R.id.item_home_find_img);
            ItemHomeFindText=$(R.id.item_home_find_text);
        }
    }
}
