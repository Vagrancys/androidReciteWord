package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupAddTagAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.DefaultTagInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/16
 * version:1.0
 */

public class GroupAddTagActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.group_add_tag_recycler)
    RecyclerView GroupTagRecycler;
    @BindView(R.id.group_tag_wx)
    ImageView TagWx;
    @BindView(R.id.group_tag_wx_close)
    ImageView TagWxClose;
    @BindView(R.id.group_tag_qq)
    ImageView TagQq;
    @BindView(R.id.group_tag_qq_close)
    ImageView TagQqClose;
    @BindView(R.id.group_tag_wb)
    ImageView TagWb;
    @BindView(R.id.group_tag_wb_close)
    ImageView TagWbClose;
    @BindView(R.id.group_tag_edit)
    EditText GroupTagEdit;
    @BindView(R.id.group_tag_next)
    TextView GroupTagNext;
    @BindView(R.id.group_add_edit)
    EditText GroupAddEdit;
    private GroupAddTagAdapter mAdapter;
    private int group_avatar;
    private String group_name;
    private String[] Title={
            "四级","六级","考研",
            "高考","中考","小学",
            "日语","韩语","小语种",
            "留学","职场","兴趣"
    };
    private DefaultTagInfo tags;
    private ArrayList<DefaultTagInfo> Lists=new ArrayList<>();
    private List<Integer> mInteger=new ArrayList<>();
    private int group_phone;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_add_tag;
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
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent !=null){
            group_name=intent.getStringExtra(ConstantUtils.GROUP_NAME);
            group_avatar=intent.getIntExtra(ConstantUtils.GROUP_AVATAR,0);
        }
        initData();
    }

    public void initData(){
        for (int i=0;i<Title.length;i++){
            tags=new DefaultTagInfo();
            tags.setTag_status(0);
            tags.setTag_title(Title[i]);
            tags.setTag_id(i);
            Lists.add(tags);
        }
        mAdapter=new GroupAddTagAdapter(GroupTagRecycler,Lists,mInteger);
        GroupTagRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        GroupTagRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(mInteger.size()<3&&Lists.get(position).getTag_status()==0){
                    Lists.get(position).setTag_status(1);
                    mInteger.add(Lists.get(position).getTag_id());
                }else if(Lists.get(position).getTag_status()==1){
                    Lists.get(position).setTag_status(0);
                    for(int j=0;j<mInteger.size();j++){
                        if(mInteger.get(j)==Lists.get(position).getTag_id()){
                            mInteger.remove(j);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        TagWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagWx.setVisibility(View.GONE);
                TagWxClose.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.GONE);
                group_phone=0;
            }
        });
        TagWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagWb.setVisibility(View.GONE);
                TagWbClose.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.GONE);
                group_phone=0;
            }
        });
        TagQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagQq.setVisibility(View.GONE);
                TagQqClose.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.GONE);
                group_phone=0;
            }
        });

        TagWxClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagWxClose.setVisibility(View.GONE);
                TagWx.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.VISIBLE);
                GroupTagEdit.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_wx));
                group_phone=1;
            }
        });

        TagQqClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagQqClose.setVisibility(View.GONE);
                TagQq.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.VISIBLE);
                GroupTagEdit.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_qq));
                group_phone=2;
            }
        });

        TagWbClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagWbClose.setVisibility(View.GONE);
                TagWb.setVisibility(View.VISIBLE);
                GroupTagEdit.setVisibility(View.VISIBLE);
                GroupTagEdit.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_wb));
                group_phone=3;
            }
        });

        GroupTagNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text="";
                for (int z=0;z<mInteger.size();z++){
                    if(z==mInteger.size()){
                        text=text+Title[mInteger.get(z)]+",";
                    }else{
                        text=text+Title[mInteger.get(z)];
                    }
                }

                GroupAddStarActivity.launch(GroupAddTagActivity.this,
                        group_name,group_avatar,
                        text,GroupAddEdit.getText().toString(),
                        group_phone,GroupTagEdit.getText().toString());
            }
        });
    }
    public void initHide(){
        TagWxClose.setVisibility(View.VISIBLE);
        TagWx.setVisibility(View.GONE);
        TagQqClose.setVisibility(View.VISIBLE);
        TagQq.setVisibility(View.GONE);
        TagWbClose.setVisibility(View.VISIBLE);
        TagWb.setVisibility(View.GONE);
    }

    public static void launch(Activity activity,String group_name,int group_avatar){
        Intent intent=new Intent(activity,GroupAddTagActivity.class);
        intent.putExtra(ConstantUtils.GROUP_NAME,group_name);
        intent.putExtra(ConstantUtils.GROUP_AVATAR,group_avatar);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
