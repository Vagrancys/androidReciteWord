package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/1/21.
 */

public class UserFriendAddAdapter extends AbsRecyclerViewAdapter {

    public UserFriendAddAdapter(RecyclerView recyclerView){
        super(recyclerView);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_user_friend_add,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
        }
    }
}
