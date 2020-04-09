package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;

import java.util.List;

/**
 * Created by Administrator on 2019/2/1.
 */

public class GroupDetailsTagAdapter extends AbsRecyclerViewAdapter {
    private List<String> Tags;
    public GroupDetailsTagAdapter(RecyclerView recyclerView,List<String> tags){
        super(recyclerView);
        Tags=tags;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_details_tag,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemText.setText(Tags.get(position));
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Tags.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemText=$(R.id.group_details_tag);
        }
    }
}
