package com.tramp.word.module.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.UserFriendAddSection;
import com.tramp.word.adapter.section.UserFriendSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.UserFriendInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/16.
 */

public class UserFriendFragment extends RxLazyFragment{
    @BindView(R.id.friend_recycler)
    RecyclerView FriendRecycler;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.net_button)
    TextView NetButton;
    private UserFriendInfo.Data Data;
    private int member_id;
    private int user_status;
    private SectionedRecyclerViewAdapter mSection;
    private Animation mScaleAnim;
    public static UserFriendFragment newInstance(int member_id){
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.MEMBER_ID,member_id);
        UserFriendFragment mUser=new UserFriendFragment();
        mUser.setArguments(bundle);
        return mUser;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_friend;
    }

    @Override
    public void finishCreateView(Bundle state) {
        Bundle bundle=getArguments();
        if(bundle !=null){
            member_id=bundle.getInt(ConstantUtils.MEMBER_ID);
        }
        if(member_id==new UserSqlHelper(getContext()).UserId()){
            user_status=1;
        }else{
            user_status=0;
        }
        isPrepared=true;
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
        loadData();
    }

    @Override
    protected void finishTask() {
        if(user_status==0&&Data.getAdds().size()!=0){
                mSection.addSection(new UserFriendAddSection(getActivity(),getContext(),Data.getAdds()));
        }
        mSection.addSection(new UserFriendSection(getActivity(),getContext(),Data.getFriends(),user_status));
        mSection.notifyDataSetChanged();
    }

    @Override
    protected void loadData() {
        Retrofits.getUserAPI().getUserFriendInfo(member_id)
                .enqueue(new Callback<UserFriendInfo>() {
                    @Override
                    public void onResponse(Call<UserFriendInfo> call, Response<UserFriendInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Data=response.body().getData();
                            finishTask();
                        }else {
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserFriendInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    private void initEmpty(){
        CommonEmpty.setVisibility(View.VISIBLE);
        FriendRecycler.setVisibility(View.GONE);
    }

    @Override
    protected void initRecyclerView() {
        mScaleAnim= AnimationUtils.loadAnimation(getContext(),R.anim.default_button_scale_anim);
        mSection=new SectionedRecyclerViewAdapter();
        FriendRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        FriendRecycler.setAdapter(mSection);
        NetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetButton.startAnimation(mScaleAnim);
                loadData();
            }
        });
    }
}
