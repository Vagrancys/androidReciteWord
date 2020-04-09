package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.section.GroupMedalViewSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.GroupMedalInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/21.
 */

public class GroupMedalActivity extends RxBaseActivity {
    @BindView(R.id.group_medal_out)
    ImageView mGroupMedalOut;
    @BindView(R.id.group_medal_recycler)
    RecyclerView mGroupMedalRecycler;
    @BindView(R.id.group_medal_number)
    TextView GroupMedalNumber;
    @BindView(R.id.group_medal_rank)
    TextView GroupMedalRank;
    private int group_id;
    private int medal_status;
    private GroupMedalInfo.Medal Medal;
    private SectionedRecyclerViewAdapter mSection;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_medal;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
            medal_status=intent.getIntExtra(ConstantUtils.MEDAL_STATUS,0);
        }
        lazyData();
    }

    private void lazyData(){
        initRefreshLayout();
        initRecyclerView();
    }
    private void initRefreshLayout(){
        loadData();
    }
    private void initRecyclerView(){
        mSection=new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayout=new GridLayoutManager(getBaseContext(),3);
        mGridLayout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                switch (mSection.getSectionItemViewType(i)){
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mGroupMedalRecycler.setLayoutManager(mGridLayout);

        mGroupMedalRecycler.setAdapter(mSection);
    }
    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupMedalInfo(group_id,medal_status)
                .enqueue(new Callback<GroupMedalInfo>() {
                    @Override
                    public void onResponse(Call<GroupMedalInfo> call, Response<GroupMedalInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Medal=response.body().getMedal();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.intent_error_message));
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupMedalInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void finishTask(){
        GroupMedalNumber.setText("共获"+Medal.getMedal_number()+"枚");
        GroupMedalRank.setText("已超越"+Medal.getMedal_rank()+"%小组");
        mSection.addSection(new GroupMedalViewSection(getBaseContext(),this,Medal.getOnes(),1,group_id));
        mSection.addSection(new GroupMedalViewSection(getBaseContext(),this,Medal.getTwos(),2,group_id));
        mSection.addSection(new GroupMedalViewSection(getBaseContext(),this,Medal.getThrees(),3,group_id));
        mSection.notifyDataSetChanged();
    }

    @Override
    protected void initToolBar() {
        mGroupMedalOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int group_id,int medal_status){
        Intent intent=new Intent(activity,GroupMedalActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        intent.putExtra(ConstantUtils.MEDAL_STATUS,medal_status);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}







