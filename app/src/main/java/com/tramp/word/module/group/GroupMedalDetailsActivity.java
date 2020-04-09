package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.MedalDetailsInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/23.
 */

public class GroupMedalDetailsActivity extends RxBaseActivity{
    @BindView(R.id.group_medal_out)
    ImageView mGroupMedalOut;
    @BindView(R.id.medal_linear)
    LinearLayout MedalLinear;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.group_medal_details_img)
    ImageView MedalImg;
    @BindView(R.id.group_medal_details_title)
    TextView MedalTitle;
    @BindView(R.id.group_medal_details_task)
    TextView MedalTask;
    @BindView(R.id.group_medal_details_time)
    TextView MedalTime;
    @BindView(R.id.group_medal_details_number)
    TextView MedalNumber;
    private int Medal_id;
    private int group_id;
    private Animation mScaleAnim;
    private MedalDetailsInfo.Details Details;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_medal_details;
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
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            Medal_id=intent.getIntExtra(ConstantUtils.MEDAL_ID,0);
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        loadData();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getMedalDetailsInfo(Medal_id,group_id)
                .enqueue(new Callback<MedalDetailsInfo>() {
                    @Override
                    public void onResponse(Call<MedalDetailsInfo> call, Response<MedalDetailsInfo> response) {
                        if(response.body().getCode()==200){
                            Details=response.body().getDetails();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<MedalDetailsInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }
    public void initEmpty(){
        MedalLinear.setVisibility(View.GONE);
        CommonEmpty.setVisibility(View.VISIBLE);
    }

    public void finishTask(){
        Glide.with(getBaseContext())
                .load(Details.getMedal_img())
                .placeholder(R.drawable.user_avater)
                .into(MedalImg);
        MedalImg.startAnimation(mScaleAnim);
        MedalTitle.setText(Details.getMedal_name());
        MedalTask.setText(Details.getMedal_task());
        MedalTime.setText(String.valueOf(Details.getMedal_time()));
        MedalNumber.setText("共有"+Details.getMedal_number()+"个小组获得此勋章");
    }

    public static void launch(Activity activity,int group_id,int medal_id){
        Intent intent=new Intent(activity,GroupMedalDetailsActivity.class);
        intent.putExtra(ConstantUtils.MEDAL_ID,medal_id);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
