package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupDetailsTagAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupDetailsInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

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

public class GroupSummaryActivity extends RxBaseActivity{
    @BindView(R.id.group_summary_recycler)
    RecyclerView GroupSummaryRecycler;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.details_scroll_four_text)
    TextView DetailsText;
    private GroupDetailsInfo.Details detailses;
    private GroupDetailsTagAdapter mGroupDetailsTagAdapter;
    private int group_id;
    private int user_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_summary;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        loadData();
    }
    public void finishTask(){
        mGroupDetailsTagAdapter=new GroupDetailsTagAdapter(GroupSummaryRecycler,detailses.getTags());
        GroupSummaryRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupSummaryRecycler.setAdapter(mGroupDetailsTagAdapter);
        DetailsText.setText(detailses.getGroup_summary());
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupDetailsInfo(group_id,user_id)
                .enqueue(new Callback<GroupDetailsInfo>() {
                    @Override
                    public void onResponse(Call<GroupDetailsInfo> call, Response<GroupDetailsInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            detailses=response.body().getDetails();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据失效了");
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupDetailsInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.group_summary_title));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int group_id){
        Intent intent=new Intent(activity,GroupSummaryActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
