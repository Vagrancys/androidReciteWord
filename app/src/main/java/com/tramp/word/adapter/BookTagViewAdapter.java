package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.BookTagInfo;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public class BookTagViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<BookTagInfo.Tag> Tags;
    public BookTagViewAdapter(RecyclerView recyclerView, ArrayList<BookTagInfo.Tag> tags){
        super(recyclerView);
        Tags=tags;
    }

    @Override
    public int getItemCount() {
        return Tags.size();
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_book_tag,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemName.setText(Tags.get(position).getTag_name());
        super.onBindViewHolder(holder, position);
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemName;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemName=$(R.id.book_tag);
        }
    }

}
