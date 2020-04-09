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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/12.
 */

public class WordBookNowViewSection extends StatelessSection {
    private Context mContext;
    private WordBookPopInterFace mPop;
    private DefaultBookInfo NewBook;

    public WordBookNowViewSection(Context context,DefaultBookInfo myInfo){
        super(R.layout.item_word_book_now_head,R.layout.item_word_book_now);
        mContext=context;
        NewBook=myInfo;
        mPop=(WordBookPopInterFace) context;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
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
                    mPop.ShowWordBookItemPop(NewBook,1);
                }
            });
            Glide.with(mContext)
                    .load(NewBook.getBook_img())
                    .placeholder(R.drawable.pic_bookcover_default)
                    .into(mHolder.ItemBookImg);
            mHolder.ItemBookTitle.setText(NewBook.getBook_name());
            mHolder.ItemBookNumber.setText((NewBook.getNew_num()/NewBook.getTotal_num()*100)+"%");
            mHolder.ItemBookTotal.setText("共"+NewBook.getTotal_num()+"词");
            if(NewBook.getGood()==0){
                mHolder.ItemBookGood.setVisibility(View.VISIBLE);
            }else{
                mHolder.ItemBookGood.setVisibility(View.GONE);
            }
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
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
        ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







