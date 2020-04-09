package com.tramp.word.module.group;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupSearchAdapter;
import com.tramp.word.adapter.GroupSearchTagAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.entity.group.SearchListInfo;
import com.tramp.word.entity.group.SearchTagInfo;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupSearchActivity extends RxBaseActivity{
    @BindView(R.id.group_search_out)
    ImageView GroupSearchOut;
    @BindView(R.id.group_search_delete)
    ImageView GroupSearchDelete;
    @BindView(R.id.group_search_edit)
    EditText GroupSearchEdit;
    @BindView(R.id.group_search_swipe)
    SwipeRefreshLayout GroupSearchSwipe;
    @BindView(R.id.group_search_relative)
    RelativeLayout GroupSearchRelative;
    @BindView(R.id.group_search_img)
    ImageView GroupSearchImg;
    @BindView(R.id.group_search_text)
    TextView GroupSearchText;
    @BindView(R.id.group_search_recycler1)
    RecyclerView GroupSearchRecycler1;
    @BindView(R.id.group_search_recycler2)
    RecyclerView GroupSearchRecycler2;
    private GroupSearchTagAdapter mGroupSearchTagAdapter;
    private GroupSearchAdapter mGroupSearchAdapter;
    private ArrayList<SearchTagInfo.Tag> Tags=new ArrayList<>();
    private ArrayList<SearchListInfo.List> Lists=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_search;
    }

    @Override
    protected void initToolBar() {
        GroupSearchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GroupSearchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupSearchEdit.setText("");
                GroupSearchRelative.setVisibility(View.VISIBLE);
                GroupSearchSwipe.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void initView(Bundle save) {
        initNet();
        initData();
    }

    public void initNet(){
        Retrofits.getGroupAPI()
                .getSearchTagInfo()
                .enqueue(new Callback<SearchTagInfo>() {
                    @Override
                    public void onResponse(Call<SearchTagInfo> call, Response<SearchTagInfo> response) {
                        if(response.body().getCode()==200){
                            Tags.clear();
                            Tags.addAll(response.body().getTags());
                            mGroupSearchTagAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchTagInfo> call, Throwable t) {
                        GroupSearchText.setVisibility(View.GONE);
                        GroupSearchRecycler1.setVisibility(View.GONE);
                    }
                });
    }

    public void initData(){
        GroupSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(GroupSearchEdit.getText().length()>0){
                    GroupSearchDelete.setVisibility(View.VISIBLE);
                }else{
                    GroupSearchDelete.setVisibility(View.GONE);
                }
            }
        });

        GroupSearchSwipe.setColorSchemeColors(getResources().getColor(R.color.blue));
        GroupSearchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupSearchRelative.setVisibility(View.GONE);
                GroupSearchSwipe.setVisibility(View.VISIBLE);
                GroupSearchSwipe.setRefreshing(true);
                loadData();
            }
        });

        mGroupSearchTagAdapter=new GroupSearchTagAdapter(GroupSearchRecycler1,Tags);
        GroupSearchRecycler1.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
        GroupSearchRecycler1.setAdapter(mGroupSearchTagAdapter);
        mGroupSearchTagAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupSearchEdit.setText(Tags.get(position).getTag_name());
                GroupSearchRelative.setVisibility(View.GONE);
                GroupSearchSwipe.setVisibility(View.VISIBLE);
                GroupSearchSwipe.setRefreshing(true);
                loadData();
            }
        });

        mGroupSearchAdapter=new GroupSearchAdapter(GroupSearchRecycler2,Lists);
        GroupSearchRecycler2.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        GroupSearchRecycler2.setAdapter(mGroupSearchAdapter);
        mGroupSearchAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                GroupDetailsActivity.launch(GroupSearchActivity.this,Lists.get(position).getGroup_id());
            }
        });
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getSearchListInfo(GroupSearchEdit.getText().toString())
                .enqueue(new Callback<SearchListInfo>() {
            @Override
            public void onResponse(Call<SearchListInfo> call, Response<SearchListInfo> response) {
                if(response.body() !=null&&response.body().getCode()==200){
                    Lists.clear();
                    Lists.addAll(response.body().getLists());
                    finishTask();
                }else{
                    GroupSearchSwipe.setRefreshing(false);
                    GroupSearchRecycler2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchListInfo> call, Throwable t) {
                GroupSearchSwipe.setRefreshing(false);
                GroupSearchRecycler2.setVisibility(View.GONE);
            }
        });
    }

    public void finishTask(){
        GroupSearchSwipe.setRefreshing(false);
        mGroupSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
