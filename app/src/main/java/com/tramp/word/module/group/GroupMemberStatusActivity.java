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
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.MemberStatusViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.MemberStatusInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
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
 * time  : 2019/04/15
 * version:1.0
 */

public class GroupMemberStatusActivity extends RxBaseActivity{
    @BindView(R.id.member_status_recycler)
    RecyclerView MemberStatusRecycler;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    private int group_id;
    private int group_status;
    private ArrayList<MemberStatusInfo.member> Members=new ArrayList<>();
    private MemberStatusViewAdapter mStatus;
    @Override
    public int getLayoutId() {
        return R.layout.activity_member_status;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(group_status==1){
            DefaultTitle.setText(getResources().getString(R.string.member_status_1));
        }else{
            DefaultTitle.setText(getResources().getString(R.string.member_status_2));
        }
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent != null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
            group_status=intent.getIntExtra(ConstantUtils.GROUP_STATUS,0);
        }
        loadData();
        initData();
    }
    public void loadData(){
        Retrofits.getGroupAPI()
                .getMemberStatusInfo(group_id,group_status)
                .enqueue(new Callback<MemberStatusInfo>() {
                    @Override
                    public void onResponse(Call<MemberStatusInfo> call, Response<MemberStatusInfo> response) {
                        if(response.body() !=null && response.body().getCode()==200){
                            Members.addAll(response.body().getMembers());
                            initData();
                        }else{
                            Utils.ShowToast(getBaseContext(),"数据失效了");
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberStatusInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void initData(){
        mStatus=new MemberStatusViewAdapter(MemberStatusRecycler,Members,getBaseContext());
        MemberStatusRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        MemberStatusRecycler.setAdapter(mStatus);
        mStatus.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                FriendDetailsActivity.launch(GroupMemberStatusActivity.this,Members.get(position).getMember_id());
            }
        });
    }

    public static void launch(Activity activity,int group_id,int group_status){
        Intent intent=new Intent(activity,GroupMemberStatusActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        intent.putExtra(ConstantUtils.GROUP_STATUS,group_status);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
