package com.tramp.word.module.home.recite;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.main.HomeReciteInfo;
import com.tramp.word.module.book.BookActivity;
import com.tramp.word.module.group.GroupContentActivity;
import com.tramp.word.module.group.GroupMainActivity;
import com.tramp.word.module.pk.WordPkActivity;
import com.tramp.word.module.home.recite.ReciteWordActivity;
import com.tramp.word.module.revise.ReviseActivity;
import com.tramp.word.module.task.TaskListActivity;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.CircleProgressView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/01/08
 * version:1.0
 */

public class HomeReciteFragment extends RxLazyFragment implements View.OnClickListener{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.default_book)
    ImageView DefaultBook;
    @BindView(R.id.recite_star_layout)
    LinearLayout ReciteStarLayout;
    @BindView(R.id.recite_star_hint)
    TextView ReciteStarHint;
    @BindView(R.id.recite_task_img)
    RelativeLayout ReciteTaskImg;
    @BindView(R.id.recite_task_layout)
    LinearLayout ReciteTaskLinear;
    @BindView(R.id.recite_start)
    LinearLayout ReciteStart;
    @BindView(R.id.recite_down_audio)
    ImageView ReciteDownAudio;
    @BindView(R.id.recite_sign)
    TextView ReciteSign;
    @BindView(R.id.recite_revise)
    LinearLayout ReciteRevise;
    @BindView(R.id.recite_pk)
    LinearLayout RecitePk;
    @BindView(R.id.recite_group)
    LinearLayout ReciteGroup;
    @BindView(R.id.recite_title)
    TextView ReciteHeadTitle;
    @BindView(R.id.recite_star_number)
    TextView ReciteStarNumber;
    @BindView(R.id.recite_gate_linear)
    LinearLayout ReciteGateLinear;
    @BindView(R.id.circle_progress)
    CircleProgressView CircleProgress;
    @BindView(R.id.recite_gate_text)
    TextView ReciteGateText;
    @BindView(R.id.recite_talent)
    TextView ReciteTalent;
    @BindView(R.id.group_number)
    TextView GroupNumber;
    @BindView(R.id.pk_number)
    TextView PkNumber;
    @BindView(R.id.revise_img)
    ImageView ReviseImg;
    @BindView(R.id.revise_number)
    TextView ReviseNumber;
    private Animation mShowStarImgAnim;
    private Animation mHideStarImgAnim;
    private Animation mReciteStartRush;
    private ObjectAnimator mTaskIconTranslationAnim;
    private ObjectAnimator mTaskIconScaleAnim;
    private AnimatorSet mTaskIconAnimSet;
    private int AnimStatic=1;
    private MainAnimInterFace mInterFace;
    private UserSqlHelper mSqlHelper;
    private final int HomeReciteCode=105;
    private final int HomeTaskCode=106;
    private HomeReciteInfo.main reciteInfo;
    private Calendar mCalendar;
    private int word_id;
    private int Study_no_count;
    private int Study_count;
    private int group_status=0;
    public static HomeReciteFragment newInstance(){
        return new HomeReciteFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_recite;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        mInterFace=(MainAnimInterFace) getActivity();
        mSqlHelper=new UserSqlHelper(getContext());
        mCalendar=Calendar.getInstance();
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        mInterFace.ShowAnimLayout();
        mInterFace.StartAnim();
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }

    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mSwipeRefreshLayout.post(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(()->{
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    protected void loadData() {
        Retrofits.getMainAPI().getHomeReciteInfo(new UserSqlHelper(getContext()).UserId())
            .enqueue(new Callback<HomeReciteInfo>() {
                @Override
                public void onResponse(Call<HomeReciteInfo> call, Response<HomeReciteInfo> response) {
                    if(response.body()!=null&&response.body().getCode()==200){
                        reciteInfo=response.body().getMains();
                        finishTask();
                    }else{
                        Utils.ShowToast(getContext(),getResources().getString(R.string.home_recite_error));
                        HideSwipeLayout();
                    }
                }

                @Override
                public void onFailure(Call<HomeReciteInfo> call, Throwable t) {
                    HideSwipeLayout();
                }
            });
    }

    @Override
    protected void finishTask() {
        HideSwipeLayout();
        if(reciteInfo.getGroup_news()>0&&reciteInfo.getGroup_status()==1){
            GroupNumber.setText(String.valueOf(reciteInfo.getGroup_news()));
            GroupNumber.setVisibility(View.VISIBLE);
        }else{
            GroupNumber.setVisibility(View.GONE);
        }
        if(reciteInfo.getPk_number()>0){
            PkNumber.setText(String.valueOf(reciteInfo.getPk_number()));
            PkNumber.setVisibility(View.VISIBLE);
        }else{
            PkNumber.setVisibility(View.GONE);
        }
        int number;
        Cursor cursor=mSqlHelper.UserQuery();
        word_id=cursor.getInt(cursor.getColumnIndex("recited_book"));
        Study_count=mSqlHelper.WordStudyCount(word_id,mCalendar.getTime().getTime(),1);
        Study_no_count=mSqlHelper.WordStudyCount(word_id,mCalendar.getTime().getTime(),0);
        if((number=mSqlHelper.WordAllCount(word_id, Calendar.getInstance().getTime().getTime()))>0){
            if(Study_no_count==0){
                ReviseNumber.setText(getNumber(number));
                ReviseNumber.setVisibility(View.VISIBLE);
                ReviseImg.setImageResource(R.drawable.main_review_icon_no_review);
            }else if(Study_no_count<Study_count){
                ReviseNumber.setVisibility(View.GONE);
                ReviseImg.setImageResource(R.drawable.main_review_icon_reviewing);
            }else if(number==Study_count){
                ReviseNumber.setVisibility(View.GONE);
                ReviseImg.setImageResource(R.drawable.main_review_icon_finished);
            }
        }else{
            ReviseImg.setImageResource(R.drawable.main_review_icon_no_word);
            ReviseNumber.setVisibility(View.GONE);
        }
        group_status=reciteInfo.getGroup_status();
    }

    private void HideSwipeLayout(){
        mInterFace.HideAnimLayout();
        mInterFace.StopAnim();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private String getNumber(int number){
        String str;
        if(number>99){
            str=number+"+";
        }else{
           str=number+"";
        }
        return str;
    }

    @Override
    protected void initRecyclerView() {
        initData();
        initAnim();
        initClick();
    }

    public void initData(){
        if(mSqlHelper.isExistBook()){
            Cursor cursor=mSqlHelper.NewBook();
            if(cursor.getCount()>0){
                ReciteHeadTitle.setText(cursor.getString(cursor.getColumnIndex("book_name")));
                ReciteStarNumber.setText(cursor.getString(cursor.getColumnIndex("all_star")));
                if(cursor.getInt(cursor.getColumnIndex("finished"))==1){
                    ReciteGateText.setText(getResources().getString(R.string.task_finished_text));
                    ReciteTalent.setVisibility(View.VISIBLE);
                    CircleProgress.setFinished(true);
                }else{
                    if(PreferencesUtils.getBoolean(ConstantUtils.RECITE_SIGN,false)){
                        ReciteSign.setVisibility(View.VISIBLE);
                    }else{
                        ReciteSign.setVisibility(View.GONE);
                    }
                    CircleProgress.setProgress(cursor.getInt(cursor.getColumnIndex("now_gate")));
                    CircleProgress.setMax(cursor.getInt(cursor.getColumnIndex("finish_gate")));
                }
                cursor.close();
            }
            ReciteStart.setClickable(true);
        }else{
            ReciteHeadTitle.setText(getResources().getString(R.string.recite_head_title));
            ReciteStarNumber.setText("--");
            ReciteGateLinear.setVisibility(View.GONE);
            ReciteDownAudio.setVisibility(View.GONE);
            CircleProgress.setInitStatus(true);
            ReciteStart.setClickable(false);
        }
    }

    public void initClick(){
        if(AnimStatic==1){
            mTaskIconAnimSet.start();
        }

        ReciteGateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.ShowTaskLayout();
            }
        });

        ReciteStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ReciteStarHint.getVisibility()==View.VISIBLE){
                    ReciteStarHint.startAnimation(mHideStarImgAnim);
                    ReciteStarHint.setVisibility(View.GONE);
                }else{
                    ReciteStarHint.startAnimation(mShowStarImgAnim);
                    ReciteStarHint.setVisibility(View.VISIBLE);
                }
            }
        });

        ReciteTaskLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimStatic=2;
                mTaskIconAnimSet.pause();
                startActivity(new Intent(getSupportActivity(), TaskListActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        ReciteStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReciteStart.startAnimation(mReciteStartRush);
                startActivity(new Intent(getSupportActivity(), ReciteWordActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        ReciteTalent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), ReciteOpenActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        ReciteSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), ReciteCheckActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        ReciteDownAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterFace.ShowMusicDownLayout();
            }
        });

        ReciteRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviseNumber.setVisibility(View.GONE);
                startActivity(new Intent(getSupportActivity(), ReviseActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        RecitePk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PkNumber.setVisibility(View.GONE);
                startActivity(new Intent(getSupportActivity(),WordPkActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        ReciteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupNumber.setVisibility(View.GONE);
                if(group_status==0&&Utils.getNetWork(getSupportActivity())){
                    startActivity(new Intent(getSupportActivity(),GroupContentActivity.class));
                    Utils.StarActivityInAnim(getSupportActivity());
                }else if(group_status==1&&Utils.getNetWork(getSupportActivity())){
                    GroupMainActivity.launch(getSupportActivity(),0,reciteInfo.getGroup_id());
                }
            }
        });

        DefaultBook.setOnClickListener(this);
    }

    public void initAnim(){
        mReciteStartRush= AnimationUtils.loadAnimation(getContext(),R.anim.default_button_scale_anim);
        mShowStarImgAnim = AnimationUtils.loadAnimation(getContext(),R.anim.recite_star_hint_show_anim);
        mHideStarImgAnim =AnimationUtils.loadAnimation(getContext(),R.anim.recite_star_hint_hide_anim);
        mTaskIconAnimSet=new AnimatorSet();

        mTaskIconTranslationAnim=ObjectAnimator.ofFloat(ReciteTaskImg,"translationY",0,-15,0);
        mTaskIconTranslationAnim.setDuration(500);
        mTaskIconScaleAnim=ObjectAnimator.ofFloat(ReciteTaskImg,"scaleY",1f,1.3f,1f);
        mTaskIconTranslationAnim.setRepeatCount(ValueAnimator.INFINITE);
        mTaskIconScaleAnim.setDuration(500);
        mTaskIconScaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        mTaskIconAnimSet.setDuration(800);
        mTaskIconAnimSet.play(mTaskIconTranslationAnim).with(mTaskIconScaleAnim);
    }

    @OnClick({R.id.default_book})
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.default_book){
            Intent intent=new Intent(getActivity(), BookActivity.class);
            getSupportActivity().startActivityForResult(intent,HomeReciteCode);
            Utils.StarActivityInAnim(getSupportActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case HomeReciteCode:
                break;
            case HomeTaskCode:
                break;
        }
    }

}
















