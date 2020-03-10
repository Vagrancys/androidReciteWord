package com.tramp.word.module.home.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.UserFriendAdapter;
import com.tramp.word.adapter.UserFriendAddAdapter;
import com.tramp.word.base.RxLazyFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/16.
 */

public class UserFriendFragment extends RxLazyFragment{
    @BindView(R.id.user_friend_add_recycler)
    RecyclerView mUserFriendAddRecycler;
    @BindView(R.id.user_friend_recycler)
    RecyclerView mUserFriendRecycler;
    @BindView(R.id.user_friend_add_more)
    TextView mUserFriendAddMore;
    private UserFriendAddAdapter mUserFriendAddAdapter;
    private UserFriendAdapter mUserFriendAdapter;
    public static UserFriendFragment newInstance(){
        return new UserFriendFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_friend;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mUserFriendAddAdapter=new UserFriendAddAdapter(mUserFriendAddRecycler);
        mUserFriendAddRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mUserFriendAddRecycler.setAdapter(mUserFriendAddAdapter);

        mUserFriendAdapter=new UserFriendAdapter(mUserFriendRecycler);
        mUserFriendRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mUserFriendRecycler.setAdapter(mUserFriendAdapter);

        mUserFriendAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                startActivity(new Intent(getActivity(),FriendDetailsActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mUserFriendAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FriendSearchActivity.class));
                getActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

    }

}
