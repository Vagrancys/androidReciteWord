package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tramp.word.R;
import com.tramp.word.adapter.section.GroupWordDoneSection;
import com.tramp.word.adapter.section.GroupWordNotSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupMainRankInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupRankFragment extends RxLazyFragment{
    @BindView(R.id.group_rank_recycler)
    RecyclerView GroupRankRecycler;
    private SectionedRecyclerViewAdapter mSection;
    private GroupMainRankInfo.Rank Ranks;
    private int User_Id;
    private int group_id;
    public static GroupRankFragment newInstance(int group_id){
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.GROUP_ID,group_id);
        GroupRankFragment mFragment=new GroupRankFragment();
        mFragment.setArguments(bundle);
        return mFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_ranks;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        User_Id=new UserSqlHelper(getContext()).UserId();
        Bundle bundle=getArguments();
        if(bundle !=null){
            group_id=bundle.getInt(ConstantUtils.GROUP_ID);
        }
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isVisible||!isPrepared){
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
    protected void loadData() {
        Log.e("groupRank","group_id="+group_id+"user_id="+User_Id);
        Retrofits.getGroupAPI()
                .getGroupMainRankInfo(group_id,User_Id)
                .enqueue(new Callback<GroupMainRankInfo>() {
                    @Override
                    public void onResponse(Call<GroupMainRankInfo> call, Response<GroupMainRankInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Ranks=response.body().getRank();
                            finishTask();
                        }else{
                            Utils.ShowToast(getSupportActivity(),"数据失效!");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupMainRankInfo> call, Throwable t) {
                        Utils.ShowToast(getSupportActivity(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    @Override
    protected void finishTask() {
        mSection.addSection(new GroupWordDoneSection(getContext(),getSupportActivity(),Ranks.getItems()));
        mSection.addSection(new GroupWordNotSection(getContext(),getSupportActivity(),Ranks.getNotitems()));
        mSection.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mSection=new SectionedRecyclerViewAdapter();
        GroupRankRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        GroupRankRecycler.setAdapter(mSection);

    }

}
