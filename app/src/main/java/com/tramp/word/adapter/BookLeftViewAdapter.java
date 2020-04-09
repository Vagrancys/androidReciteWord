package com.tramp.word.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.BookListInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/14.
 */

public class BookLeftViewAdapter extends AbsRecyclerViewAdapter{
    private List<BookListInfo.One> mLeftList;
    public BookLeftViewAdapter(RecyclerView recyclerView, List<BookListInfo.One> leftList){
        super(recyclerView);
        mLeftList=leftList;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_left,parent,false));
    }



    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mTextView.setText(mLeftList.get(position).getOne_name());
            if(mLeftList.get(position).getStatus()==1){
                mHolder.mLinear.setBackgroundColor(Color.WHITE);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mLeftList.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        LinearLayout mLinear;
        public ItemViewHolder(View itemView){
            super(itemView);
            mLinear=$(R.id.word_book_left_layout);
            mTextView=$(R.id.word_whole_left_text);
        }
    }

}
