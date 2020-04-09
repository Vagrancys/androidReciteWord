package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordBookKeepViewSection extends StatelessSection {
    private Context mContext;
    private WordBookPopInterFace mPop;
    private List<DefaultBookInfo> BookList;

    public WordBookKeepViewSection(Context context, List<DefaultBookInfo> arrayList){
        super(R.layout.item_word_book_keep_head,R.layout.item_word_book_now);
        mContext=context;
        BookList=arrayList;
        mPop=(WordBookPopInterFace) context;
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
        mHolder.ItemRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.ShowWordBookItemPop(BookList.get(position),0);
            }
        });

        Glide.with(mContext)
                .load(BookList.get(position).getBook_img())
                .placeholder(R.drawable.pic_bookcover_default)
                .into(mHolder.ItemBookImg);
        mHolder.ItemBookTitle.setText(BookList.get(position).getBook_name());
        mHolder.ItemBookNumber.setText((BookList.get(position).getNew_num()/BookList.get(position).getTotal_num()*100)+"%");
        mHolder.ItemBookTotal.setText("共"+BookList.get(position).getTotal_num()+"词");
        if(BookList.get(position).getGood()==0){
            mHolder.ItemBookGood.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemBookGood.setVisibility(View.GONE);
        }
    }

    @Override
    public int getContentItemsTotal() {
        return BookList.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_relative)
        RelativeLayout ItemRelative;
        @BindView(R.id.item_book_img)
        ImageView ItemBookImg;
        @BindView(R.id.item_book_title)
        TextView ItemBookTitle;
        @BindView(R.id.item_book_number)
        TextView ItemBookNumber;
        @BindView(R.id.item_book_total)
        TextView ItemBookTotal;
        @BindView(R.id.item_book_good)
        ImageView ItemBookGood;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







