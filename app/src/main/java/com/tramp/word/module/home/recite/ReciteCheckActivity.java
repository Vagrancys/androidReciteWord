package com.tramp.word.module.home.recite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.main.ReciteCheckInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/2/1.
 */

public class ReciteCheckActivity extends RxBaseActivity {
    @BindView(R.id.check_out)
    ImageView CheckOut;
    @BindView(R.id.check_linear)
    LinearLayout CheckLinear;
    @BindView(R.id.check_help)
    ImageView CheckHelp;
    @BindView(R.id.check_weixin)
    ImageView CheckWeixin;
    @BindView(R.id.check_weibo)
    ImageView CheckWeibo;
    @BindView(R.id.check_qq)
    ImageView CheckQq;
    @BindView(R.id.check_down)
    ImageView CheckDown;
    @BindView(R.id.check_view_pager)
    NoScrollViewPager CheckViewPager;

    private Animation mScaleAnim;
    private ReciteCheckAdapter CheckAdapter;
    private ArrayList<View> mViews=new ArrayList<View>(){};
    private UserSqlHelper mUser;
    private ReciteCheckInfo.Check check;
    private Time mTime=new Time();
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_check;
    }
    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        mScaleAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
        loadData();
        initClick();
    }
    private void initClick(){
        CheckLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLinear.startAnimation(mScaleAnim);
                Utils.ShowToast(getBaseContext(),"点击了分享朋友圈!");
            }
        });
        CheckHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteCheckActivity.this, WedCommonActivity.class));
                Utils.StarActivityInAnim(ReciteCheckActivity.this);
            }
        });
        CheckWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享微信!");
            }
        });
        CheckWeibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享微博!");
            }
        });
        CheckQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享QQ!");
            }
        });
        CheckDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),"点击了分享下载!");
            }
        });
    }

    private void loadData(){
        Retrofits.getMainAPI().getReciteCheckInfo(mUser.UserId())
                .enqueue(new Callback<ReciteCheckInfo>() {
                    @Override
                    public void onResponse(Call<ReciteCheckInfo> call, Response<ReciteCheckInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            check=response.body().getCheck();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReciteCheckInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    private void finishTask(){
        mTime.setToNow();
        mTime.month=6;
        mTime.monthDay=15;
        mTime.weekDay=6;
        OneView();
        TwoView();
        CheckAdapter=new ReciteCheckAdapter(mViews);
        CheckViewPager.setPageMargin(R.dimen.default_margin_size_2);
        CheckViewPager.setOffscreenPageLimit(2);
        CheckViewPager.setPageTransformer(true,new ScalePageTransformer());
        CheckViewPager.setAdapter(CheckAdapter);
        CheckViewPager.setCurrentItem(0);
    }

    private void OneView(){
        View OneCheckView=LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recite_check_one,null);
        TextView mOneMonth=(TextView) OneCheckView.findViewById(R.id.check_day_month);
        mOneMonth.setText(String.valueOf(mTime.month));
        TextView mOneDay=(TextView) OneCheckView.findViewById(R.id.check_day);
        mOneDay.setText(String.valueOf(mTime.monthDay));
        TextView mOneTime=(TextView) OneCheckView.findViewById(R.id.check_time);
        mOneTime.setText(String.valueOf(check.getCheck_time()));
        TextView mOneGate=(TextView) OneCheckView.findViewById(R.id.check_gate);
        mOneGate.setText(String.valueOf(check.getCheck_gate()));
        TextView mOneBook=(TextView) OneCheckView.findViewById(R.id.check_book);
        mOneBook.setText(String.valueOf(check.getCheck_book()));
        TextView mOneStar=(TextView) OneCheckView.findViewById(R.id.check_star);
        mOneStar.setText(String.valueOf(check.getCheck_star()));
        TextView mOneRank=(TextView) OneCheckView.findViewById(R.id.check_rank);
        mOneRank.setText(String.valueOf(check.getCheck_rank()));
        ImageView mOneAvatar=(ImageView) OneCheckView.findViewById(R.id.check_avatar);
        Glide.with(getBaseContext())
                .load(check.getCheck_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mOneAvatar);
        TextView mOneName=(TextView) OneCheckView.findViewById(R.id.check_name);
        mOneName.setText(check.getCheck_name());
        mViews.add(OneCheckView);
    }

    private void TwoView(){
        View TwoCheckView=LayoutInflater.from(getBaseContext()).inflate(R.layout.item_recite_check_two,null);
        TextView mTwoMonth=(TextView) TwoCheckView.findViewById(R.id.check_month);
        mTwoMonth.setText(String.valueOf(mTime.month));
        TextView mTwoDay=(TextView) TwoCheckView.findViewById(R.id.check_day);
        mTwoDay.setText(String.valueOf(mTime.monthDay));
        TextView mTwoWeek=(TextView) TwoCheckView.findViewById(R.id.check_week);
        mTwoWeek.setText(String.valueOf(mTime.weekDay));
        TextView mTwoTime=(TextView) TwoCheckView.findViewById(R.id.check_time);
        mTwoTime.setText(String.valueOf(check.getCheck_time()));
        TextView mTwoGate=(TextView) TwoCheckView.findViewById(R.id.check_gate);
        mTwoGate.setText(String.valueOf(check.getCheck_gate()));
        TextView mTwoBook=(TextView) TwoCheckView.findViewById(R.id.check_book);
        mTwoBook.setText(String.valueOf(check.getCheck_book()));
        TextView mTwoStar=(TextView) TwoCheckView.findViewById(R.id.check_star);
        mTwoStar.setText(String.valueOf(check.getCheck_star()));
        TextView mTwoRank=(TextView) TwoCheckView.findViewById(R.id.check_rank);
        mTwoRank.setText(String.valueOf(check.getCheck_rank()));
        ImageView mTwoAvatar=(ImageView) TwoCheckView.findViewById(R.id.check_avatar);
        Glide.with(getBaseContext())
                .load(check.getCheck_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mTwoAvatar);
        mViews.add(TwoCheckView);
    }

    @Override
    protected void initToolBar() {
        CheckOut.setOnClickListener(new View.OnClickListener() {
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

    public class ScalePageTransformer implements ViewPager.PageTransformer{
        private static final float MIN_SCALE=0.8f;
        @Override
        public void transformPage(@NonNull View view, float position) {
            if (position >= -1 || position <= 1) {
                final float height = view.getHeight();
                final float width = view.getWidth();
                final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                final float vertMargin = height * (1 - scaleFactor) / 2;
                final float horzMargin = width * (1 - scaleFactor) / 2;
                view.setPivotY(0.5f * height);
                view.setPivotX(0.5f * width);
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }
    }

    public class ReciteCheckAdapter extends PagerAdapter{
        private ArrayList<View> mCheckViews;

        public ReciteCheckAdapter(ArrayList<View> checkview){
            mCheckViews=checkview;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mCheckViews.get(position));
            return mCheckViews.get(position);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView(mCheckViews.get(position));
        }

        @Override
        public int getCount() {
            return mCheckViews.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViews.clear();
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,ReciteCheckActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }
}

















