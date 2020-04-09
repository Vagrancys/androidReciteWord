package com.tramp.word.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/1/14.
 */

public abstract class AbsRecyclerViewAdapter extends
        RecyclerView.Adapter<AbsRecyclerViewAdapter.ClickableViewHolder> {

    private Context context;
    private RecyclerView mRecyclerView;
    private List<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();


    public AbsRecyclerViewAdapter(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrollStateChanged(rv, newState);
                }
            }


            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                for (RecyclerView.OnScrollListener listener : mListeners) {
                    listener.onScrolled(rv, dx, dy);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(int position, ClickableViewHolder holder);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void bindContext(Context context) {
        this.context = context;
    }


    public Context getContext() {
        return this.context;
    }


    @Override
    public void onBindViewHolder(final ClickableViewHolder holder, final int position) {
        holder.getParentView().setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position, holder);
            }
        });
    }


    public static class ClickableViewHolder extends RecyclerView.ViewHolder {
        private View parentView;

        public ClickableViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;
        }


        View getParentView() {
            return parentView;
        }


        @SuppressWarnings("unchecked")
        public <T extends View> T $(@IdRes int id) {
            return (T) parentView.findViewById(id);
        }
    }
}




















