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
import com.tramp.word.adapter.GroupTagAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupSummaryInfo;
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
 * time  : 2019/06/04
 * version:1.0
 */
public class GroupDataActivity extends RxBaseActivity {
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.data_tag_text)
    TextView DataTagText;
    @BindView(R.id.data_tag_recycler)
    RecyclerView DataTagRecycler;
    @BindView(R.id.data_summary)
    TextView DataSummary;
    @BindView(R.id.data_link_linear)
    TextView DataLinkLinear;
    @BindView(R.id.data_link_img)
    ImageView DataLinkImg;
    @BindView(R.id.data_link_summary)
    TextView DataLinkSummary;
    private int group_id;
    private int user_id;

    private GroupSummaryInfo.Summary Summary;
    private ArrayList<String> Tags=new ArrayList<>();
    private GroupTagAdapter mTagAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_data;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        loadData();
        initData();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupSummaryInfo(user_id,group_id)
                .enqueue(new Callback<GroupSummaryInfo>() {
                    @Override
                    public void onResponse(Call<GroupSummaryInfo> call, Response<GroupSummaryInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Summary=response.body().getSummary();
                            Tags.addAll(response.body().getSummary().getTags());
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.message_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupSummaryInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void finishTask(){
        DataSummary.setText(Summary.getGroup_summary());
        if(Summary.getGroup_link()==0){
            DataLinkLinear.setVisibility(View.GONE);
        }else{
            DataLinkLinear.setVisibility(View.VISIBLE);
            switch (Summary.getGroup_link()){
                case 1:
                    DataLinkImg.setImageResource(R.drawable.icon_wechat);
                    break;
                case 2:
                    DataLinkImg.setImageResource(R.drawable.icon_qq_list);
                    break;
                case 3:
                    DataLinkImg.setImageResource(R.drawable.icon_weibo);
                    break;
            }
            DataLinkSummary.setText(Summary.getGroup_links());
        }
        if(Tags.size()>0){
            DataTagText.setVisibility(View.GONE);
            DataTagRecycler.setVisibility(View.VISIBLE);
            mTagAdapter.notifyDataSetChanged();
        }else{
            DataTagRecycler.setVisibility(View.GONE);
            DataTagText.setVisibility(View.VISIBLE);
        }
    }

    public void initData(){
        mTagAdapter=new GroupTagAdapter(DataTagRecycler,Tags);
        DataTagRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        DataTagRecycler.setAdapter(mTagAdapter);
    }

    public static void launch(Activity activity, int group_id){
        Intent intent=new Intent(activity,GroupUpdateActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    protected void initToolBar() {
        DefaultTitle.setText(getBaseContext().getString(R.string.update_title));

        DefaultOut.setOnClickListener(new View.OnClickListener() {
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
}
