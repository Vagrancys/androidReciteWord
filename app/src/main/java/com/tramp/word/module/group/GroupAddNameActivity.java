package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupAddNameAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.GroupAvatarInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/16
 * version:1.0
 */

public class GroupAddNameActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_add_avatar)
    RelativeLayout GroupAddAvatar;
    @BindView(R.id.group_common_background)
    RelativeLayout GroupCommonBackground;
    @BindView(R.id.group_pop_avatar)
    RelativeLayout GroupPopAvatar;
    @BindView(R.id.group_avatar_delete)
    TextView GroupAvatarDelete;
    @BindView(R.id.group_add_edit)
    EditText GroupAddEdit;
    @BindView(R.id.group_add_next)
    TextView GroupAddNext;
    @BindView(R.id.group_avatar_recycler)
    RecyclerView GroupAvatarRecycler;
    @BindView(R.id.group_add_img)
    ImageView GroupAddImg;
    private GroupAddNameAdapter mAdapter;
    private Animation EnterAnim;
    private Animation ExitAnim;
    private int group_avatar;
    private ArrayList<GroupAvatarInfo.Item> Items=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_add_name;
    }

    @Override
    public void initView(Bundle save) {
        initNet();
        EnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        ExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        GroupAddAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCommonBackground.setVisibility(View.VISIBLE);
                GroupPopAvatar.setVisibility(View.VISIBLE);
                GroupPopAvatar.startAnimation(EnterAnim);
            }
        });
        GroupAvatarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupPopAvatar.startAnimation(ExitAnim);
                GroupPopAvatar.setVisibility(View.GONE);
                GroupCommonBackground.setVisibility(View.GONE);
            }
        });

        GroupAddEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(GroupAddEdit.getText().length()>4){
                    GroupAddNext.setBackground(getResources().getDrawable(R.drawable.btn_group_add_win));
                    GroupAddNext.setClickable(true);
                }else{
                    GroupAddNext.setBackground(getResources().getDrawable(R.drawable.btn_group_add_false));
                    GroupAddNext.setClickable(false);
                }
            }
        });

        GroupAddNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GroupAddEdit.getText().length()>4){
                    GroupAddTagActivity.launch(GroupAddNameActivity.this
                            ,GroupAddEdit.getText().toString()
                            ,group_avatar);
                }
            }
        });

        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                group_avatar=position;
                Glide.with(getBaseContext())
                    .load(Items.get(position).getAvatar_url())
                    .placeholder(R.drawable.pic_lang_it)
                    .into(GroupAddImg);
                PreferencesUtils.putInt(ConstantUtils.AVATAR_ID,position);
                mAdapter.notifyDataSetChanged();
                GroupPopAvatar.startAnimation(ExitAnim);
                GroupPopAvatar.setVisibility(View.GONE);
                GroupCommonBackground.setVisibility(View.GONE);
            }
        });
    }

    public void initNet(){
        Retrofits.getGroupAPI()
                .getGroupAvatarInfo()
                .enqueue(new Callback<GroupAvatarInfo>() {
                    @Override
                    public void onResponse(Call<GroupAvatarInfo> call, Response<GroupAvatarInfo> response) {
                        if(response.body() !=null&&response.body().getCode()==200){
                            Items.addAll(response.body().getItems());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupAvatarInfo> call, Throwable t) {

                    }
                });
    }
    public void finishTask(){
        mAdapter=new GroupAddNameAdapter(GroupAvatarRecycler,Items);
        GroupAvatarRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),4));
        GroupAvatarRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.group_add_title));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
