package com.tramp.word.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/1/8.
 */

public abstract class RxLazyFragment extends RxFragment {
    private View parentView;
    private FragmentActivity activity;
    protected boolean isVisible;
    protected boolean isPrepared;
    private Unbinder bind;

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        parentView=inflater.inflate(getLayoutId(),container,false);
        activity=getSupportActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind= ButterKnife.bind(this,view);
        finishCreateView(savedInstanceState);
    }

    public abstract void finishCreateView(Bundle state);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity=null;
    }

    public FragmentActivity getSupportActivity(){
        super.getActivity();
        return activity;
    }

    protected void lazyLoad(){

    }

    protected void initRefreshLayout(){

    }

    protected void loadData(){

    }

    protected void initRecyclerView(){
    }

    protected void finishTask(){

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else{
            isVisible=false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected void onInvisible(){

    }
}













