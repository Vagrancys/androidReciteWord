package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupLevelViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.GroupLevelInfo;
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
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupLevelActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_level_recycler)
    RecyclerView GroupLevelRecycler;
    @BindView(R.id.group_level_large)
    ImageView GroupLevelLarge;
    @BindView(R.id.group_level_number)
    TextView GroupLevelNumber;
    @BindView(R.id.group_level_up)
    TextView GroupLevelUp;
    @BindView(R.id.group_level_down)
    TextView GroupLevelDown;
    @BindView(R.id.group_level_seekBar)
    ProgressBar GroupLevelSeekBar;
    @BindView(R.id.group_level_leave)
    TextView GroupLevelLeave;
    @BindView(R.id.group_level_text)
    TextView GroupLevelText;
    private GroupLevelViewAdapter mGroupLevel;
    private int group_id;
    private GroupLevelInfo.Level Levels;
    private static final int level_number=1;
    private ArrayList<GroupLevelInfo.Level.Item> Items=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_level;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
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
        mGroupLevel=new GroupLevelViewAdapter(GroupLevelRecycler,Items);
        GroupLevelRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupLevelRecycler.setAdapter(mGroupLevel);
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupLevelInfo(group_id)
                .enqueue(new Callback<GroupLevelInfo>() {
                    @Override
                    public void onResponse(Call<GroupLevelInfo> call, Response<GroupLevelInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Levels=response.body().getLevel();
                            Items.addAll(response.body().getLevel().getItems());
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.intent_error_message));
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupLevelInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }
    public void finishTask(){
        DefaultTitle.setText(Levels.getGroup_name());
        GroupLevelLarge.setImageResource(GroupAvatar(Levels.getGroup_level()));
        GroupLevelNumber.setText("词能量:"+ Levels.getGroup_all_star());
        GroupLevelUp.setText("Lv"+Levels.getGroup_level());
        GroupLevelDown.setText("Lv"+Levels.getGroup_level()+1);
        GroupLevelSeekBar.setMax(Levels.getGroup_level_all_star());
        GroupLevelSeekBar.setProgress(Levels.getGroup_level_star());
        GroupLevelLeave.setText(String.valueOf(Levels.getGroup_level_all_star()-Levels.getGroup_level_star()));
        GroupLevelText.setText("词能量:"+Levels.getGroup_now_star());
        mGroupLevel.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int group_id){
        Intent intent=new Intent(activity,GroupLevelActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    public int GroupAvatar(int level){
        switch (level){
            case 1:
                return R.drawable.icon_group_level_1_large;
            case 2:
                return R.drawable.icon_group_level_2_large;
            case 3:
                return R.drawable.icon_group_level_3_large;
            case 4:
                return R.drawable.icon_group_level_4_large;
            case 5:
                return R.drawable.icon_group_level_5_large;
            case 6:
                return R.drawable.icon_group_level_6_large;
            case 7:
                return R.drawable.icon_group_level_7_large;
            case 8:
                return R.drawable.icon_group_level_8_large;
            case 9:
                return R.drawable.icon_group_level_9_large;
            case 10:
                return R.drawable.icon_group_level_10_large;
            case 11:
                return R.drawable.icon_group_level_11_large;
            case 12:
                return R.drawable.icon_group_level_12_large;
            case 13:
                return R.drawable.icon_group_level_13_large;
            case 14:
                return R.drawable.icon_group_level_14_large;
            case 15:
                return R.drawable.icon_group_level_15_large;
            case 16:
                return R.drawable.icon_group_level_16_large;
            case 17:
                return R.drawable.icon_group_level_17_large;
            case 18:
                return R.drawable.icon_group_level_18_large;
        }
        return R.drawable.icon_group_level_1_large;
    }
}
