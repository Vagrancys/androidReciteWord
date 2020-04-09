package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupRankViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.entity.group.GroupRankInfo;
import com.tramp.word.port.GroupRankInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/18
 * version:1.0
 */

public class GroupRankViewFragment extends RxLazyFragment{
    @BindView(R.id.group_rank_recycler)
    RecyclerView GroupRankRecycler;
    @BindView(R.id.group_rank_help)
    ImageView GroupRankHelp;

    private GroupRankViewAdapter mGroupRankViewAdapter;
    private int ItemStatus;
    private int ItemStar;
    private int ItemNumber;
    private ArrayList<GroupRankInfo.Rank> Ranks=new ArrayList<>();
    private GroupRankInterFace mFace;
    public static GroupRankViewFragment newInstance(int itemStatus,int itemNumber){
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.GROUP_ITEM_STATUS,itemStatus);
        bundle.putInt(ConstantUtils.GROUP_ITEM_NUMBER,itemNumber);
        GroupRankViewFragment fragment=new GroupRankViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_rank;
    }

    @Override
    public void finishCreateView(Bundle state) {
        Bundle bundle=getArguments();
        if(bundle !=null){
            ItemStatus=bundle.getInt(ConstantUtils.GROUP_ITEM_STATUS);
            ItemNumber=bundle.getInt(ConstantUtils.GROUP_ITEM_NUMBER);
        }
        ItemStar= PreferencesUtils.getInt(ConstantUtils.ITEM_STAR,5);
        isPrepared=true;
        mFace=(GroupRankInterFace)getActivity();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }

        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    protected void initRefreshLayout() {
        loadData();
    }

    @Override
    protected void loadData() {
        Retrofits.getGroupAPI().getGroupRankInfo(ItemStatus,ItemStar,ItemNumber)
                .enqueue(new Callback<GroupRankInfo>() {
                    @Override
                    public void onResponse(Call<GroupRankInfo> call, Response<GroupRankInfo> response) {
                        if(response.body().getCode()==200){
                            Ranks.clear();
                            Ranks.addAll(response.body().getRanks());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupRankInfo> call, Throwable t) {
                        Utils.ShowToast(getContext(),"网络出错了!");
                    }
                });
    }

    @Override
    protected void finishTask() {
        mGroupRankViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mGroupRankViewAdapter=new GroupRankViewAdapter(GroupRankRecycler,ItemStatus,Ranks);
        GroupRankRecycler.setLayoutManager(new LinearLayoutManager(getSupportActivity()));
        GroupRankRecycler.setAdapter(mGroupRankViewAdapter);
        mGroupRankViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupDetailsActivity.launch(getSupportActivity(),Ranks.get(position).getGroup_id());
            }
        });

        GroupRankHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFace.ShowPopHelp();
            }
        });
    }
}
