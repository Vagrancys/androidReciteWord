package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.book.BookSearchInfo;
import com.tramp.word.port.BookSearchInterFace;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public class BookSearchSection extends StatelessSection {
    private ArrayList<BookSearchInfo.Language.Book> Books;
    private Context mContext;
    private Activity activity;
    private BookSearchInterFace mInterFace;
    public BookSearchSection(Activity activity,Context context, ArrayList<BookSearchInfo.Language.Book> books){
        super(R.layout.item_book_header,R.layout.item_book_footer,R.layout.item_book_view);
        Books=books;
        mContext=context;
        mInterFace=(BookSearchInterFace) activity;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.BookHeader.setText(String.valueOf(Books.size()));
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(mContext)
                .load(Books.get(position).getBook_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ViewImg);
        if(Books.get(position).getBook_select()==1){
            mHolder.ViewSelect.setVisibility(View.VISIBLE);
        }else{
            mHolder.ViewSelect.setVisibility(View.GONE);
        }
        mHolder.ViewTitle.setText(Books.get(position).getBook_name());
        mHolder.ViewWord.setText(String.valueOf(Books.get(position).getBook_total()+"词"));
        mHolder.ViewNumber.setText(String.valueOf(Books.get(position).getBook_number()+"人正在背诵"));
        if(Books.get(position).getBook_good()==1){
            mHolder.ViewGood.setVisibility(View.VISIBLE);
        }else{
            mHolder.ViewGood.setVisibility(View.GONE);
        }
        mHolder.ViewRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.ShowBookPop(Books.get(position));
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder mFooter=(FooterViewHolder) holder;
        if(Books.size()>=4){
            mFooter.BookFooter.setVisibility(View.GONE);
        }else{
            mFooter.BookFooter.setVisibility(View.VISIBLE);
        }

        mFooter.BookFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.SwipeSearch();
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return Books.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.book_header)
        TextView BookHeader;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.view_relative)
        RelativeLayout ViewRelative;
        @BindView(R.id.view_img)
        ImageView ViewImg;
        @BindView(R.id.view_select)
        ImageView ViewSelect;
        @BindView(R.id.view_title)
        TextView ViewTitle;
        @BindView(R.id.view_word)
        TextView ViewWord;
        @BindView(R.id.view_number)
        TextView ViewNumber;
        @BindView(R.id.view_good)
        ImageView ViewGood;

        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.book_footer)
        LinearLayout BookFooter;
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
