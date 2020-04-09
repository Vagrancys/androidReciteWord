package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.GroupDayViewAdapter;
import com.tramp.word.adapter.GroupPraiseViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.GroupDayInfo;
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

public class GroupDayActivity extends RxBaseActivity{
    @BindView(R.id.group_day_img)
    ImageView GroupDayImg;
    @BindView(R.id.group_day_static)
    TextView GroupDayStatic;
    @BindView(R.id.group_day_number)
    TextView GroupDayNumber;
    @BindView(R.id.group_day_progress)
    ProgressBar GroupDayProgress;
    @BindView(R.id.group_day_value)
    TextView GroupDayValue;
    @BindView(R.id.group_day_recycler)
    RecyclerView GroupDayRecycler;
    @BindView(R.id.group_day_done)
    TextView GroupDayDone;
    @BindView(R.id.group_day_not)
    TextView GroupDayNot;
    @BindView(R.id.group_day_target)
    TextView GroupDayTarget;
    @BindView(R.id.group_day_num)
    TextView GroupDayNum;
    @BindView(R.id.group_day_start)
    TextView GroupDayStart;
    @BindView(R.id.group_day_nums)
    TextView GroupDayNums;
    @BindView(R.id.group_day_praise)
    TextView GroupDayPraise;
    @BindView(R.id.group_day_praise_recycler)
    RecyclerView DayPraiseRecycler;
    @BindView(R.id.group_level_back)
    RelativeLayout GroupLevelBack;
    @BindView(R.id.group_level_img)
    ImageView GroupLevelImg;
    @BindView(R.id.group_level_exit)
    TextView GroupLevelExit;
    private Animation mScaleAnim;
    private int group_id;
    private GroupDayInfo.Day Days;
    private GroupDayViewAdapter mGroupDay;
    private GroupPraiseViewAdapter mGroupPraise;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_day;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        initClick();
        initNet();
    }
    public void initNet(){
        Retrofits.getGroupAPI()
                .getGroupDayInfo(group_id,new UserSqlHelper(getBaseContext()).UserId())
                .enqueue(new Callback<GroupDayInfo>() {
                    @Override
                    public void onResponse(Call<GroupDayInfo> call, Response<GroupDayInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Days=response.body().getDays();
                            finishTask();
                        }else{
                            Utils.ShowToast(getBaseContext(),getResources().getString(R.string.message_error));
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupDayInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    public void finishTask(){
        if(Days.getGroup_status()==1){
            GroupDayImg.setImageResource(R.drawable.group_daily_top_pic_succ);
            GroupDayStatic.setText(getResources().getString(R.string.group_day_static_win));
            GroupDayStatic.setTextColor(getResources().getColor(R.color.orange));
            GroupDayStart.setText(getResources().getString(R.string.group_day_start_win));
        }else{
            GroupDayImg.setImageResource(R.drawable.group_daily_top_pic_fail);
            GroupDayStatic.setText(getResources().getString(R.string.group_day_static_fail));
            GroupDayStatic.setTextColor(getResources().getColor(R.color.red));
            GroupDayStart.setText(getResources().getString(R.string.group_day_start_fail));
        }
        GroupDayNumber.setText(Days.getGroup_day_star());
        GroupDayProgress.setProgress(Days.getGroup_now_star());
        GroupDayProgress.setMax(Days.getGroup_all_star());
        GroupDayValue.setText(Days.getGroup_now_star()+"/"+Days.getGroup_all_star());
        mGroupDay=new GroupDayViewAdapter(GroupDayRecycler,Days.getLists());
        GroupDayRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        GroupDayRecycler.setAdapter(mGroupDay);
        GroupDayDone.setText(Days.getGroup_member()+"人");
        GroupDayNot.setText(Days.getGroup_not_member()+"人");
        if(Days.getUser_status()==1){
            GroupDayTarget.setText(getResources().getString(R.string.group_day_target_win));
            GroupDayTarget.setTextColor(getResources().getColor(R.color.orange));
        }else{
            GroupDayTarget.setText(getResources().getString(R.string.group_day_target_fail));
            GroupDayTarget.setTextColor(getResources().getColor(R.color.red));
        }
        GroupDayNum.setText(Days.getUser_star()+"/"+Days.getGroup_star());
        GroupDayNums.setText("("+Days.getUser_praise()+")");
        if(Days.getUser_praise()==0){
            GroupDayPraise.setVisibility(View.VISIBLE);
            DayPraiseRecycler.setVisibility(View.GONE);
        }else{
            GroupDayPraise.setVisibility(View.GONE);
            DayPraiseRecycler.setVisibility(View.VISIBLE);
        }
        mGroupPraise=new GroupPraiseViewAdapter(DayPraiseRecycler,Days.getPraises());
        DayPraiseRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        DayPraiseRecycler.setAdapter(mGroupPraise);

        if(Days.getGroup_level_status()==1){
            GroupLevelBack.setVisibility(View.VISIBLE);
            GroupLevelImg.setImageResource(Utils.getGroupLevelImg(Days.getGroup_level()));
        }
    }

    public void initClick(){
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        GroupDayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupDayStart.startAnimation(mScaleAnim);
                finish();
            }
        });

        GroupLevelExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupLevelExit.startAnimation(mScaleAnim);
                GroupLevelBack.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initToolBar() {
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int group_id){
        Intent intent=new Intent(activity,GroupDayActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
