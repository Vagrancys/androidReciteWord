package com.tramp.word.module.pk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.WordPkViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.friend.FriendInfo;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class WordPkFragment extends RxLazyFragment{
    @BindView(R.id.word_pk_swipe)
    SwipeRefreshLayout WordPkSwipe;
    @BindView(R.id.word_pk_random)
    ImageView WordPkRandom;
    @BindView(R.id.word_pk_recycler)
    RecyclerView WordPkRecycler;
    @BindView(R.id.word_pk_qq)
    ImageView WordPkQq;
    @BindView(R.id.word_pk_weixin)
    ImageView WordPkWeiXin;
    private WordPkViewAdapter mWordPkViewAdapter;
    private ArrayList<FriendInfo.Friend> friends=new ArrayList<>();
    private UserSqlHelper mUser;
    public static WordPkFragment newInstance(){
        return new WordPkFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_pk;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        mUser=new UserSqlHelper(getSupportActivity());
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        WordPkSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        WordPkSwipe.post(()->{
            WordPkSwipe.setRefreshing(true);
            loadData();
        });
        WordPkSwipe.setOnRefreshListener(()->{
            WordPkSwipe.setRefreshing(true);
            clearData();
            loadData();
        });
    }

    @Override
    protected void initRecyclerView() {
        WordPkRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),PkSearchActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });
        WordPkQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),PkSearchActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });
        WordPkWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),PkSearchActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });
        mWordPkViewAdapter=new WordPkViewAdapter(WordPkRecycler,getSupportActivity(),friends);
        WordPkRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        WordPkRecycler.setAdapter(mWordPkViewAdapter);
    }

    @Override
    protected void loadData() {
        Retrofits.getFriendAPI()
                .getFriendInfo(mUser.UserId())
                .enqueue(new Callback<FriendInfo>() {
                    @Override
                    public void onResponse(Call<FriendInfo> call, Response<FriendInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            friends.addAll(response.body().getFriends());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<FriendInfo> call, Throwable t) {
                        Utils.ShowToast(getContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
        finishTask();
    }

    @Override
    protected void finishTask() {
        WordPkSwipe.setRefreshing(false);
        mWordPkViewAdapter.notifyDataSetChanged();
    }

    public void clearData(){
        friends.clear();
    }
}
