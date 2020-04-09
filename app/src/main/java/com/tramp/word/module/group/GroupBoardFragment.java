package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupBoardViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupBoardInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/21
 * version:1.0
 */

public class GroupBoardFragment extends RxLazyFragment {
    @BindView(R.id.group_board_recycler)
    RecyclerView GroupBoardRecycler;
    @BindView(R.id.group_board_empty)
    LinearLayout GroupBoardEmpty;
    private GroupBoardViewAdapter mGroupBoardViewAdapter;
    private int user_id;
    private int group_id;

    private ArrayList<GroupBoardInfo.Board> Boards=new ArrayList<>();

    public static GroupBoardFragment newInstance(int group_id){
        Bundle bundle=new Bundle();
        bundle.putInt(ConstantUtils.GROUP_ID,group_id);
        GroupBoardFragment mFragment=new GroupBoardFragment();
        mFragment.setArguments(bundle);
        return mFragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_group_board;
    }

    @Override
    public void finishCreateView(Bundle state) {
        Bundle bundle=getArguments();
        if(bundle !=null){
            group_id=bundle.getInt(ConstantUtils.GROUP_ID);
        }
        isPrepared=true;
        user_id=new UserSqlHelper(getContext()).UserId();
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
        Retrofits.getGroupAPI()
                .getGroupBoardInfo(group_id,user_id)
                .enqueue(new Callback<GroupBoardInfo>() {
                    @Override
                    public void onResponse(Call<GroupBoardInfo> call, Response<GroupBoardInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Boards.addAll(response.body().getBoards());
                            finishTask();
                        }else{
                            Utils.ShowToast(getSupportActivity(),"数据失效!");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupBoardInfo> call, Throwable t) {
                        Utils.ShowToast(getSupportActivity(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    @Override
    protected void finishTask() {
        if(Boards.size()<=0){
            GroupBoardEmpty.setVisibility(View.VISIBLE);
            GroupBoardRecycler.setVisibility(View.GONE);
        }else{
            GroupBoardEmpty.setVisibility(View.GONE);
            GroupBoardRecycler.setVisibility(View.VISIBLE);
        }
        mGroupBoardViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initRecyclerView() {
        mGroupBoardViewAdapter=new GroupBoardViewAdapter(GroupBoardRecycler,getSupportActivity(),Boards);
        GroupBoardRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        GroupBoardRecycler.setAdapter(mGroupBoardViewAdapter);
    }

}


