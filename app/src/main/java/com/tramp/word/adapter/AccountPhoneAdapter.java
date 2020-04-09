package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;

/**
 * Created by Administrator on 2019/2/1.
 */

public class AccountPhoneAdapter extends AbsRecyclerViewAdapter {
    private String[] Title;
    private String[] Number;
    public AccountPhoneAdapter(RecyclerView recyclerView,String[] title,String[] number){
        super(recyclerView);
        Title=title;
        Number=number;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_account_phone_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemTitle.setText(Title[position]);
        mHolder.ItemNumber.setText("+"+Number[position]);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Title.length;
    }

    private class ItemViewHolder extends ClickableViewHolder{
        TextView ItemTitle;
        TextView ItemNumber;
        ItemViewHolder(View itemView){
            super(itemView);
            ItemTitle=$(R.id.phone_adapter_text);
            ItemNumber=$(R.id.phone_adapter_number);
        }
    }
}
