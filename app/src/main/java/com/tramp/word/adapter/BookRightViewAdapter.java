package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class BookRightViewAdapter extends AbsRecyclerViewAdapter{
    private List<BookListInfo.Two> mRightList;
    public BookRightViewAdapter(RecyclerView recyclerView, List<BookListInfo.Two> rightList){
        super(recyclerView);
        mRightList=rightList;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_right,parent,false));
    }



    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mTextView.setText(mRightList.get(position).getTwo_name());
            if(mRightList.get(position).getStatus()==1){
                mHolder.mTextView.setTextColor(getContext().getResources().getColor(R.color.blue));
                mHolder.mImageView.setVisibility(View.VISIBLE);
            }else{
                mHolder.mTextView.setTextColor(getContext().getResources().getColor(R.color.black));
                mHolder.mImageView.setVisibility(View.GONE);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mRightList.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView mTextView;
        ImageView mImageView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mTextView=$(R.id.word_whole_right_text);
            mImageView=$(R.id.word_whole_right_img);
        }
    }

}



