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

public abstract class AbsRecyclerViewAdapter extends RecyclerView.Adapter<AbsRecyclerViewAdapter.ClickableViewHolder> {
    private Context context;
    protected RecyclerView mRecycleView;
    //滚动监听器
    private List<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();
    public AbsRecyclerViewAdapter(RecyclerView recyclerView){
        this.mRecycleView=recyclerView;
        //添加滚动监听器
        this.mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //循环为每个item添加监听器
            @Override
            //在滚轴之上的状态发生改变
            public void onScrollStateChanged(RecyclerView rv, int newState) {
                for (RecyclerView.OnScrollListener listener:mListeners){
                    listener.onScrollStateChanged(rv,newState);
                }
            }

            @Override
            //在滚轴上
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                for (RecyclerView.OnScrollListener listener:mListeners){
                    listener.onScrolled(rv,dx,dy);
                }
            }
        });
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mListeners.add(listener);
    }

    //自定义点击回调
    public interface OnItemClickListener {
        void onItemClick(int position, ClickableViewHolder holder);
    }

    //自定义长点击回调
    interface OnItemLongClickListener {
        boolean onItemLongClick(int position, ClickableViewHolder holder);
    }

    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    //设置点击鉴听器
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }


    //设置上下文
    public void bindContext(Context context){
        this.context=context;
    }

    //得到上下文
    public Context getContext(){return this.context;}

    //作用于捆绑数据或者监听器
    @Override
    public void onBindViewHolder(final  ClickableViewHolder holder,final int position) {
        holder.getParentView().setOnClickListener(v->{
            if(itemClickListener !=null){
                itemClickListener.onItemClick(position,holder);
            }
        });
        holder.getParentView().setOnLongClickListener(v->
                itemLongClickListener!=null&&itemLongClickListener.onItemLongClick(position, holder));
    }

    //作用于提供视图
    public static class ClickableViewHolder extends  RecyclerView.ViewHolder{
        private View parentView;
        public ClickableViewHolder(View itemView){
            super(itemView);
            this.parentView=itemView;
        }
        View getParentView(){return parentView;}

        //t 表示泛型例子t=TextView 翻译等于public <TextView extends View> TextView $(@IdRes int id){return (TextView) parentView.findViewById(id)}
        @SuppressWarnings("unchecked")
        public <T extends View>T $(@IdRes int id){return (T) parentView.findViewById(id);}
    }
}
