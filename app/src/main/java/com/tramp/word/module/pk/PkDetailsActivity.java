package com.tramp.word.module.pk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.PkDetailsViewAdapter;
import com.tramp.word.adapter.ShareViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.pk.PkDetailsInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/09
 * version:1.0
 */

public class PkDetailsActivity extends RxBaseActivity{
    @BindView(R.id.details_one_avatar)
    ImageView DetailsOneAvatar;
    @BindView(R.id.details_two_avatar)
    ImageView DetailsTwoAvatar;
    @BindView(R.id.details_one_number)
    TextView DetailsOneNumber;
    @BindView(R.id.details_two_number)
    TextView DetailsTwoNumber;
    @BindView(R.id.details_recycler)
    RecyclerView DetailsRecycler;
    @BindView(R.id.details_number)
    LinearLayout DetailsNumber;
    @BindView(R.id.details_change)
    ImageView DetailsChange;
    @BindView(R.id.details_share)
    ImageView DetailsShare;
    @BindView(R.id.details_out)
    ImageView DetailsOut;
    @BindView(R.id.share_back)
    LinearLayout ShareBack;
    @BindView(R.id.share_recycler)
    RecyclerView ShareRecycler;
    @BindView(R.id.share_linear)
    LinearLayout ShareLinear;
    @BindView(R.id.share_delete)
    TextView ShareDelete;

    private Bitmap PkScoreBg;
    private Animation mEnterAnim;
    private Animation mExitAnim;
    private PkDetailsViewAdapter mDetailsAdapter;
    private ShareViewAdapter mShareAdapter;
    private PkDetailsInfo.Details details;
    private ArrayList<PkDetailsInfo.Details.Item> Items=new ArrayList<>();
    private int details_id;
    private String[] mTitle={
            "微信好友","朋友圈","新浪微博","QQ好友","QQ空间"
    };
    private int[] mImg={
            R.drawable.btn_share_wechat_normal,
            R.drawable.btn_share_pyq_normal,
            R.drawable.btn_share_sina_normal,
            R.drawable.btn_share_qq_normal,
            R.drawable.btn_share_qzone_normal
    };
    private int[] mUnImg={
            R.drawable.btn_share_wechat_pressed,
            R.drawable.btn_share_pyq_pressed,
            R.drawable.btn_share_sina_pressed,
            R.drawable.btn_share_qq_pressed,
            R.drawable.btn_share_qzone_pressed
    };
    @Override
    public int getLayoutId() {
        return R.layout.activity_pk_details;
    }

    @Override
    protected void initToolBar() {
        DetailsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            details_id=intent.getIntExtra(ConstantUtils.DETAILS_ID,1);
        }
        loadData();
        initData();
        initClick();
    }
    private void initData(){
        mEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"pk_score_assets@2x.png");
        PkScoreBg= BitmapFactory.decodeStream(back);

        DetailsNumber.setBackground(getDrawable(PkScoreBg,830,539,122,95));

        DetailsChange.setBackground(getDrawable(PkScoreBg,1702,282,275,106));

        DetailsShare.setBackground(getDrawable(PkScoreBg,1542,529,275,106));

        mDetailsAdapter=new PkDetailsViewAdapter(DetailsRecycler,getBaseContext(),Items);
        DetailsRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        DetailsRecycler.setAdapter(mDetailsAdapter);

        mShareAdapter=new ShareViewAdapter(ShareRecycler,mTitle,mImg,mUnImg);
        ShareRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        ShareRecycler.setAdapter(mShareAdapter);
        mShareAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Utils.ShowToast(getBaseContext(),"选中了"+position);
            }
        });
    }
    private void initClick(){
        DetailsChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkSearchActivity.launch(PkDetailsActivity.this);
                finish();
            }
        });
        DetailsShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareBack.setVisibility(View.VISIBLE);
                ShareLinear.setVisibility(View.VISIBLE);
                ShareLinear.startAnimation(mEnterAnim);
            }
        });
        ShareDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinear.startAnimation(mExitAnim);
                ShareLinear.setVisibility(View.GONE);
                ShareBack.setVisibility(View.GONE);
            }
        });
        ShareBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinear.startAnimation(mExitAnim);
                ShareLinear.setVisibility(View.GONE);
                ShareBack.setVisibility(View.GONE);
            }
        });

        DetailsTwoAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(PkDetailsActivity.this,details.getDetails_two_id());
            }
        });
    }

    private void loadData(){
        Retrofits.getPkAPI().getPkDetailsInfo(details_id)
                .enqueue(new Callback<PkDetailsInfo>() {
                    @Override
                    public void onResponse(Call<PkDetailsInfo> call, Response<PkDetailsInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            details=response.body().getDetails();
                            Items.addAll(response.body().getDetails().getItems());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<PkDetailsInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),"网络失效了!");
                    }
                });
    }

    private void finishTask(){
        Glide.with(getBaseContext())
                .load(details.getDetails_one_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsOneAvatar);
        DetailsOneNumber.setText(details.getDetails_one_number());
        Glide.with(getBaseContext())
                .load(details.getDetails_two_avatar())
                .placeholder(R.drawable.user_avater)
                .into(DetailsTwoAvatar);
        DetailsTwoNumber.setText(details.getDetails_two_number());
        mDetailsAdapter.notifyDataSetChanged();
    }

    public Drawable getDrawable(Bitmap bitmap, int left, int top, int width, int height){
        return new BitmapDrawable(Bitmap.createBitmap(bitmap,left,top,width,height));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }

    public static void launch(Activity activity,int details_id){
        Intent intent=new Intent(activity,PkDetailsActivity.class);
        intent.putExtra(ConstantUtils.DETAILS_ID,details_id);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}
