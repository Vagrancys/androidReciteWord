package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupAddTagAdapter;
import com.tramp.word.adapter.GroupTagAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.DefaultSummaryInfo;
import com.tramp.word.entity.DefaultTagInfo;
import com.tramp.word.entity.group.GroupSummaryInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/19
 * version:1.0
 */

public class GroupUpdateActivity extends RxBaseActivity{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_up)
    TextView DefaultUp;
    @BindView(R.id.update_relative)
    RelativeLayout UpdateRelative;
    @BindView(R.id.update_text)
    TextView UpdateText;
    @BindView(R.id.update_recycler)
    RecyclerView UpdateRecycler;
    @BindView(R.id.update_edit)
    EditText UpdateEdit;
    @BindView(R.id.update_size)
    TextView UpdateSize;
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
    @BindView(R.id.update_delete)
    ImageView UpdateDelete;
    @BindView(R.id.update_link)
    EditText UpdateLink;
    @BindView(R.id.update_relative2)
    RelativeLayout UpdateRelative2;
    @BindView(R.id.pop_tag)
    LinearLayout PopTag;
    @BindView(R.id.background)
    RelativeLayout BackGround;
    @BindView(R.id.pop_tag_delete)
    TextView PopTagDelete;
    @BindView(R.id.pop_tag_recycler)
    RecyclerView PopTagRecycler;
    @BindView(R.id.pop_tag_start)
    TextView PopTagStart;

    private int group_id;
    private int user_id;
    private int group_link;
    private String[] Title={
            "四级","六级","考研",
            "高考","中考","小学",
            "日语","韩语","小语种",
            "留学","职场","兴趣"
    };
    private GroupAddTagAdapter mAdapter;
    private DefaultTagInfo Tag=new DefaultTagInfo();
    private DefaultSummaryInfo summary=new DefaultSummaryInfo();
    private GroupSummaryInfo.Summary Summary;
    private ArrayList<String> Tags=new ArrayList<>();
    private ArrayList<Integer> Tags_id=new ArrayList<>();
    private ArrayList<DefaultTagInfo> TagLists=new ArrayList<>();
    private List<Integer> mInteger=new ArrayList<>();
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private GroupTagAdapter mTagAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_update;
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent.getExtras() !=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        for (int i=0;i<Title.length;i++){
            Tag=new DefaultTagInfo();
            Tag.setTag_status(0);
            Tag.setTag_title(Title[i]);
            Tag.setTag_id(i);
            TagLists.add(Tag);
        }
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
                            Tags_id.addAll(response.body().getSummary().getTags_id());
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
        UpdateEdit.setText(Summary.getGroup_summary());
        UpdateSize.setText(200-Summary.getGroup_summary().length()+"字");
        switch (Summary.getGroup_link()){
            case 0:
                break;
            case 1:
                TagWxClose.setVisibility(View.GONE);
                TagWx.setVisibility(View.VISIBLE);
                UpdateLink.setVisibility(View.VISIBLE);
                UpdateLink.setText(Summary.getGroup_links());
                group_link=1;
                break;
            case 2:
                TagQqClose.setVisibility(View.GONE);
                TagQq.setVisibility(View.VISIBLE);
                UpdateLink.setVisibility(View.VISIBLE);
                UpdateLink.setText(Summary.getGroup_links());
                group_link=2;
                break;
            case 3:
                TagWbClose.setVisibility(View.GONE);
                TagWb.setVisibility(View.VISIBLE);
                UpdateLink.setVisibility(View.VISIBLE);
                UpdateLink.setText(Summary.getGroup_links());
                group_link=3;
                break;
        }
        if(Tags.size()>0){
            UpdateText.setVisibility(View.GONE);
            UpdateRecycler.setVisibility(View.VISIBLE);
            mTagAdapter.notifyDataSetChanged();
            for (int i=0;i<Tags_id.size();i++){
                TagLists.get(Tags_id.get(i)-1).setTag_status(1);
                mInteger.add(Tags_id.get(i)-1);
            }
            mAdapter.notifyDataSetChanged();
        }else{
            UpdateRecycler.setVisibility(View.GONE);
            UpdateText.setVisibility(View.VISIBLE);
        }
    }

    public void initData(){
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);

        mTagAdapter=new GroupTagAdapter(UpdateRecycler,Tags);
        UpdateRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        UpdateRecycler.setAdapter(mTagAdapter);

        mAdapter=new GroupAddTagAdapter(PopTagRecycler,TagLists,mInteger);

        PopTagRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));

        PopTagRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(TagLists.get(position).getTag_status()==0){
                    TagLists.get(position).setTag_status(1);
                    mInteger.add(TagLists.get(position).getTag_id());
                }else if(TagLists.get(position).getTag_status()==1){
                    TagLists.get(position).setTag_status(0);
                    for(int j=0;j<mInteger.size();j++){
                        if(mInteger.get(j)==TagLists.get(position).getTag_id()){
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
                UpdateLink.setVisibility(View.GONE);
                group_link=0;
            }
        });

        TagWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagWb.setVisibility(View.GONE);
                TagWbClose.setVisibility(View.VISIBLE);
                UpdateLink.setVisibility(View.GONE);
                group_link=0;
            }
        });

        TagQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagQq.setVisibility(View.GONE);
                TagQqClose.setVisibility(View.VISIBLE);
                UpdateLink.setVisibility(View.GONE);
                group_link=0;
            }
        });

        TagWxClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagWxClose.setVisibility(View.GONE);
                TagWx.setVisibility(View.VISIBLE);
                UpdateRelative2.setVisibility(View.VISIBLE);
                UpdateLink.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_wx));
                group_link=1;
            }
        });

        TagQqClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagQqClose.setVisibility(View.GONE);
                TagQq.setVisibility(View.VISIBLE);
                UpdateRelative2.setVisibility(View.VISIBLE);
                UpdateLink.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_qq));
                group_link=2;
            }
        });

        TagWbClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHide();
                TagWbClose.setVisibility(View.GONE);
                TagWb.setVisibility(View.VISIBLE);
                UpdateRelative2.setVisibility(View.VISIBLE);
                UpdateLink.setHint(getBaseContext().getResources().getString(R.string.group_tag_edit_wb));
                group_link=3;
            }
        });

        UpdateRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackGround.setVisibility(View.VISIBLE);
                PopTag.setVisibility(View.VISIBLE);
                PopTag.startAnimation(mTopEnterAnim);
            }
        });

        PopTagDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopTag.startAnimation(mTopExitAnim);
                PopTag.setVisibility(View.GONE);
                BackGround.setVisibility(View.GONE);
            }
        });

        PopTagStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mInteger.size()>0){
                    UpdateRecycler.setVisibility(View.VISIBLE);
                    UpdateText.setVisibility(View.GONE);
                    mTagAdapter.notifyDataSetChanged();
                }else{
                    UpdateText.setVisibility(View.VISIBLE);
                    UpdateRecycler.setVisibility(View.GONE);
                }
                PopTag.startAnimation(mTopExitAnim);
                PopTag.setVisibility(View.GONE);
                BackGround.setVisibility(View.GONE);
            }
        });

        UpdateDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateLink.setText("'");
                UpdateDelete.setVisibility(View.GONE);
            }
        });

        UpdateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                UpdateSize.setText(200-UpdateEdit.getText().length()+"字");
            }
        });

    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text="";
                String text_id="";
                for (int z=0;z<mInteger.size();z++){
                    if(z==mInteger.size()){
                        text=text+Title[mInteger.get(z)]+",";
                        text_id=text_id+mInteger.get(z)+",";
                    }else{
                        text=text+Title[mInteger.get(z)];
                        text_id=text_id+mInteger.get(z);
                    }
                }
                summary.setGroup_id(group_id);
                summary.setGroup_summary(UpdateEdit.getText().toString());
                summary.setGroup_tag(text);
                summary.setGroup_tag_id(text_id);
                summary.setGroup_link(group_link);
                summary.setGroup_links(UpdateLink.getText().toString());
                Retrofits.getGroupAPI()
                        .getSummaryAddInfo(summary)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body() !=null&& response.body().getCode()==200){
                                    finish();
                                }else{
                                    Utils.ShowToast(getBaseContext(),"修改失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });
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

    public static void launch(Activity activity,int group_id){
        Intent intent=new Intent(activity,GroupUpdateActivity.class);
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
