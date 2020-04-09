package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupFinialViewAdapter;
import com.tramp.word.adapter.GroupTagViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupFinialInfo;
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
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupFinialActivity extends RxBaseActivity{
    @BindView(R.id.group_finial_recycler)
    RecyclerView GroupFinialRecycler;
    @BindView(R.id.group_finial_recycler1)
    RecyclerView GroupFinialRecycler1;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_finial_swipe)
    SwipeRefreshLayout GroupFinialSwipe;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    private GroupFinialViewAdapter mGroupItemViewAdapter;
    private GroupTagViewAdapter mGroupTagViewAdapter;
    private int ItemStatus;
    private int ItemClass;
    private int GroupStatus=0;
    private ArrayList<GroupFinialInfo.Item> Lists=new ArrayList<>();
    private UserSqlHelper mUser;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_finial;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(ItemStatus==0){
            DefaultTitle.setText(getResources().getString(R.string.item_group_class1));
        }else{
            DefaultTitle.setText(getResources().getString(R.string.item_group_class2));
        }
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            ItemStatus=intent.getIntExtra(ConstantUtils.GROUP_ITEM_CLASS,1);
        }
        mUser=new UserSqlHelper(getBaseContext());
        ItemClass= PreferencesUtils.getInt(ConstantUtils.GROUP_CONTENT_CLASS,0);

        lazyData();
        loadData();

    }
    private void lazyData(){
        initRefreshLayout();
        initRecyclerView();
    }
    private void initRefreshLayout(){
        GroupFinialSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        GroupFinialSwipe.post(()->{
           GroupFinialSwipe.setRefreshing(true);
           loadData();
        });
        GroupFinialSwipe.setOnRefreshListener(()->{
            GroupFinialSwipe.setRefreshing(true);
            clearData();
            loadData();
        });
    }

    private void initRecyclerView(){
        mGroupTagViewAdapter=new GroupTagViewAdapter(GroupFinialRecycler);
        GroupFinialRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupFinialRecycler.setAdapter(mGroupTagViewAdapter);
        mGroupTagViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                PreferencesUtils.putInt(ConstantUtils.GROUP_CONTENT_CLASS,position);
                mGroupTagViewAdapter.setTagNumber(position);
                clearData();
                loadData();
                mGroupTagViewAdapter.notifyDataSetChanged();
            }
        });

        mGroupItemViewAdapter=new GroupFinialViewAdapter(GroupFinialRecycler1,this,Lists,GroupStatus,mUser.UserId());
        GroupFinialRecycler1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupFinialRecycler1.setAdapter(mGroupItemViewAdapter);
        mGroupItemViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupDetailsActivity.launch(GroupFinialActivity.this,Lists.get(position).getGroup_id());
            }
        });
    }

    public void clearData(){
        Lists.clear();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupFinialInfo(ItemStatus,ItemClass)
                .enqueue(new Callback<GroupFinialInfo>() {
                    @Override
                    public void onResponse(Call<GroupFinialInfo> call, Response<GroupFinialInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Lists.addAll(response.body().getLists());
                            GroupStatus=response.body().getGroup_status();
                            finishTask();
                        }else{
                            initEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupFinialInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    public void initEmpty(){
        GroupFinialSwipe.setRefreshing(false);
        CommonEmpty.setVisibility(View.VISIBLE);
        GroupFinialSwipe.setVisibility(View.GONE);
    }

    public void finishTask(){
        GroupFinialSwipe.setRefreshing(false);
        mGroupItemViewAdapter.notifyDataSetChanged();
        mGroupTagViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int itemClass){
        Intent intent=new Intent(activity,GroupFinialActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ITEM_CLASS,itemClass);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}





