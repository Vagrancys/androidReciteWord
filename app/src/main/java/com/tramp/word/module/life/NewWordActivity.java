package com.tramp.word.module.life;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.NewWordBookViewAdapter;
import com.tramp.word.adapter.NewWordViewAdapter;
import com.tramp.word.base.RxBaseActivity;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/13
 * version:1.0
 */

public class NewWordActivity extends RxBaseActivity{
    @BindView(R.id.new_word_out)
    ImageView NewWordOut;
    @BindView(R.id.new_word_back)
    LinearLayout NewWordBack;
    @BindView(R.id.new_word_linear)
    LinearLayout NewWordLinear;
    @BindView(R.id.new_word_relative_2)
    RelativeLayout NewWordRelative2;
    @BindView(R.id.new_word_title)
    TextView NewWordTitle;
    @BindView(R.id.new_word_down)
    ImageView NewWordDown;
    @BindView(R.id.new_word_select)
    ImageView NewWordSelect;
    @BindView(R.id.new_word_menu)
    ImageView NewWordMenu;
    @BindView(R.id.new_word_relative)
    RelativeLayout NewWordRelative;

    @BindView(R.id.new_word_default)
    TextView NewWordDefault;
    @BindView(R.id.new_word_time)
    TextView NewWordTime;
    @BindView(R.id.new_word_english)
    TextView NewWordEnglish;
    @BindView(R.id.new_word_impression)
    TextView NewWordImpression;
    @BindView(R.id.new_word_skilled)
    TextView NewWordSkilled;
    @BindView(R.id.new_word_strange)
    TextView NewWordStrange;
    @BindView(R.id.new_word_reset)
    TextView NewWordReset;
    @BindView(R.id.new_word_definite)
    TextView NewWordDefinite;

    @BindView(R.id.new_word_recycler)
    RecyclerView NewWordRecycler;
    @BindView(R.id.new_word_recycler_1)
    RecyclerView NewWordRecycler1;
    private Animation mScaleTopAnim;
    private Animation mScaleBottomAnim;
    private Animation mRotateAnim;
    private PopupWindow mPopup;
    private NewWordViewAdapter mNewWordViewAdapter;
    private NewWordBookViewAdapter mNewWordBookViewAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_new_word;
    }

    @Override
    public void initView(Bundle save) {
        initAnim();
        initClick();

        mNewWordViewAdapter=new NewWordViewAdapter(NewWordRecycler,this);
        NewWordRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        NewWordRecycler.setAdapter(mNewWordViewAdapter);

        mNewWordBookViewAdapter=new NewWordBookViewAdapter(NewWordRecycler1);
        NewWordRecycler1.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        NewWordRecycler1.setAdapter(mNewWordBookViewAdapter);
        mNewWordBookViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {

            }
        });
    }

    private void initClick(){
        mPopup=new PopupWindow(this);
        mPopup.setAnimationStyle(R.style.popup_recite_style_anim);
        View Pup= LayoutInflater.from(getBaseContext()).inflate(R.layout.popup_new_word,null);
        mPopup.setContentView(Pup);
        mPopup.setFocusable(true);

        TextView PopProcess=Pup.findViewById(R.id.pop_process);
        PopProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.dismiss();
            }
        });
        TextView PopRandom=Pup.findViewById(R.id.pop_random);
        PopRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewWordActivity.this,NewRandomAnimActivity.class));
                mPopup.dismiss();
            }
        });
        TextView PopGroup=Pup.findViewById(R.id.pop_group);
        PopGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewWordActivity.this,NewGroupDetailsActivity.class));
                mPopup.dismiss();
            }
        });

        NewWordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewWordLinear.getVisibility()==View.VISIBLE){
                    NewWordLinear.startAnimation(mScaleBottomAnim);
                    NewWordLinear.setVisibility(View.GONE);
                    NewWordDown.startAnimation(mRotateAnim);
                }
                if(NewWordRelative2.getVisibility()==View.VISIBLE){
                    NewWordRelative2.startAnimation(mScaleBottomAnim);
                    NewWordRelative2.setVisibility(View.GONE);
                }
                NewWordBack.setVisibility(View.GONE);
            }
        });

        NewWordTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordLinear.setVisibility(View.VISIBLE);
                NewWordLinear.startAnimation(mScaleTopAnim);
                NewWordDown.startAnimation(mRotateAnim);
                NewWordBack.setVisibility(View.VISIBLE);
            }
        });

        NewWordSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordRelative2.setVisibility(View.VISIBLE);
                NewWordRelative2.startAnimation(mScaleTopAnim);
                NewWordBack.setVisibility(View.VISIBLE);
            }
        });

        NewWordMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopup.showAsDropDown(NewWordMenu,0,0, Gravity.END);
            }
        });

        NewWordRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordRelative2.startAnimation(mScaleBottomAnim);
                NewWordRelative2.setVisibility(View.GONE);
                NewWordLinear.setVisibility(View.GONE);
                startActivity(new Intent(NewWordActivity.this,NewManageActivity.class));
                overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        NewWordDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordDefault.setTextColor(getResources().getColor(R.color.white));
                NewWordDefault.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                NewWordTime.setTextColor(getResources().getColor(R.color.black_1));
                NewWordTime.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
            }
        });

        NewWordTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordTime.setTextColor(getResources().getColor(R.color.white));
                NewWordTime.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                NewWordDefault.setTextColor(getResources().getColor(R.color.black_1));
                NewWordDefault.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
            }
        });

        NewWordEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewWordEnglish.getBackground().equals(getResources().getDrawable(R.drawable.btn_new_button_bg))){
                    NewWordEnglish.setTextColor(getResources().getColor(R.color.black_1));
                    NewWordEnglish.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                }else{
                    NewWordEnglish.setTextColor(getResources().getColor(R.color.white));
                    NewWordEnglish.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                }
            }
        });

        NewWordStrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewWordStrange.getBackground().equals(getResources().getDrawable(R.drawable.btn_new_button_bg))){
                    NewWordStrange.setTextColor(getResources().getColor(R.color.black_1));
                    NewWordStrange.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                }else{
                    NewWordStrange.setTextColor(getResources().getColor(R.color.white));
                    NewWordStrange.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                }
            }
        });

        NewWordImpression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewWordImpression.getBackground().equals(getResources().getDrawable(R.drawable.btn_new_button_bg))){
                    NewWordImpression.setTextColor(getResources().getColor(R.color.black_1));
                    NewWordImpression.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                }else{
                    NewWordImpression.setTextColor(getResources().getColor(R.color.white));
                    NewWordImpression.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                }
            }
        });

        NewWordSkilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NewWordSkilled.getBackground().equals(getResources().getDrawable(R.drawable.btn_new_button_bg))){
                    NewWordSkilled.setTextColor(getResources().getColor(R.color.black_1));
                    NewWordSkilled.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                }else{
                    NewWordSkilled.setTextColor(getResources().getColor(R.color.white));
                    NewWordSkilled.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                }
            }
        });

        NewWordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordDefault.setTextColor(getResources().getColor(R.color.white));
                NewWordDefault.setBackground(getResources().getDrawable(R.drawable.btn_new_button_bg));
                NewWordTime.setTextColor(getResources().getColor(R.color.black_1));
                NewWordTime.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                NewWordEnglish.setTextColor(getResources().getColor(R.color.black_1));
                NewWordEnglish.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                NewWordStrange.setTextColor(getResources().getColor(R.color.black_1));
                NewWordStrange.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                NewWordImpression.setTextColor(getResources().getColor(R.color.black_1));
                NewWordImpression.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
                NewWordSkilled.setTextColor(getResources().getColor(R.color.black_1));
                NewWordSkilled.setBackground(getResources().getDrawable(R.drawable.btn_new_button_un_bg));
            }
        });

        NewWordDefinite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWordSelect.setImageResource(R.drawable.rwb_wg_icon_filter_active);
                NewWordRelative2.startAnimation(mScaleBottomAnim);
                NewWordRelative2.setVisibility(View.GONE);
                NewWordLinear.setVisibility(View.GONE);
            }
        });


    }

    private void initAnim(){
        mScaleTopAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.scale_y_top_anim);
        mScaleBottomAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.scale_y_bottom_anim);
        mRotateAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_rotate_anim);
    }

    @Override
    protected void initToolBar() {
        NewWordOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
