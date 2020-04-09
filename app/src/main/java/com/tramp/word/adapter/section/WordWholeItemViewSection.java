package com.tramp.word.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.book.BookItemInfo;
import com.tramp.word.port.WordBookPopInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/1/13.
 */

public class WordWholeItemViewSection extends StatelessSection {
    private Context mContext;
    public int FooterInt=0;
    private WordBookPopInterFace mPop;
    private List<BookItemInfo.Book> mBook;

    public WordWholeItemViewSection(Context context,List<BookItemInfo.Book> bookItemInfo){
        super(R.layout.item_empty,R.layout.item_word_whole_item_footer,R.layout.item_word_whole_item);
        mContext=context;
        mBook=bookItemInfo;
        mPop=(WordBookPopInterFace) context;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            Glide.with(mContext)
                    .load(mBook.get(position).getBook_img())
                    .placeholder(R.drawable.award_icon_common)
                    .into(mHolder.ItemWordImg);
            mHolder.ItemWordTitle.setText(mBook.get(position).getBook_name());
            mHolder.ItemWordNumber.setText(""+mBook.get(position).getBook_total());
            mHolder.ItemWordNum.setText(""+mBook.get(position).getBook_number());

            if(mBook.get(position).getBook_good()==1){
                mHolder.ItemWordPerfect.setVisibility(View.VISIBLE);
            }else{
                mHolder.ItemWordPerfect.setVisibility(View.GONE);
            }

            FooterInt=position;
            mHolder.ItemWordLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPop.ShowWordBookPop(mBook.get(position));
                }
            });
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder mFooter=(FooterViewHolder) holder;
        if(FooterInt==getContentItemsTotal()-1){
            mFooter.mLayout.setVisibility(View.VISIBLE);
        }else{
            mFooter.mLayout.setVisibility(View.INVISIBLE);
        }
        mFooter.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(mContext,mContext.getResources().getString(R.string.book_help_message));
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public int getContentItemsTotal() {
        return mBook.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView){
            super(itemView);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_word_book_layout)
        RelativeLayout ItemWordLayout;
        @BindView(R.id.item_word_item_img)
        ImageView ItemWordImg;
        @BindView(R.id.item_word_book_head_text)
        TextView ItemWordTitle;
        @BindView(R.id.item_word_book_item_text)
        TextView ItemWordNumber;
        @BindView(R.id.item_word_book_num_text)
        TextView ItemWordNum;
        @BindView(R.id.item_word_book_perfect)
        ImageView ItemWordPerfect;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.whole_item_footer_layout)
        LinearLayout mLayout;
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}







