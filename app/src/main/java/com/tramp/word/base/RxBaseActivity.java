package com.tramp.word.base;

import android.os.Bundle;

import com.tramp.word.entity.task.TaskListInfo;
import com.tramp.word.utils.AppManagerUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/1/7.
 */

public abstract class RxBaseActivity extends RxAppCompatActivity {
    private String LOG="RxBaseActivity";
    private Unbinder bind;
    private List<Call> mCalls;

    public abstract int getLayoutId();

    protected abstract void initToolBar();

    public abstract void initView(Bundle save);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        AppManagerUtils.getAppManager().addActivity(this);
        bind= ButterKnife.bind(this);
        initView(savedInstanceState);
        initToolBar();
    }

    public void addCall(Call call){
        if(mCalls == null){
            mCalls = new ArrayList<>();
        }
        mCalls.add(call);
    }

    public void callCancel(){
        if(mCalls !=null && mCalls.size() >0){
            for(Call call: mCalls){
                if(!call.isCanceled()){
                    call.cancel();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        callCancel();
        super.onDestroy();
        AppManagerUtils.getAppManager().finishActivity(this);
        bind.unbind();
    }

    public void ExitActivity(){
        AppManagerUtils.getAppManager().finishAllActivity();
    }
}


















