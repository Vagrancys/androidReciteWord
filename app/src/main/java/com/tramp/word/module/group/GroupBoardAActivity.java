package com.tramp.word.module.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.GroupListViewAdapter;
import com.tramp.word.adapter.GroupSearchViewAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.group.BoardSearchInfo;
import com.tramp.word.entity.group.GroupListInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/11
 * version:1.0
 */

public class GroupBoardAActivity extends RxBaseActivity{
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.group_board_edit)
    EditText GroupBoardEdit;
    @BindView(R.id.group_board_delete)
    ImageView GroupBoardDelete;
    @BindView(R.id.board_list)
    RecyclerView BoardList;
    @BindView(R.id.search_list)
    RecyclerView SearchList;
    @BindView(R.id.search_empty)
    LinearLayout SearchEmpty;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    private int user_id;
    private int BOARD_CODE=201;
    private ArrayList<GroupListInfo.List> Lists=new ArrayList<>();
    private ArrayList<BoardSearchInfo.List> Searchs=new ArrayList<>();
    private GroupListViewAdapter mGroupList;
    private GroupSearchViewAdapter mGroupSearch;
    private int group_id;
    @Override
    public int getLayoutId() {
        return R.layout.activity_group_board;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultTitle.setText(getResources().getString(R.string.group_board_title));
    }

    @Override
    public void initView(Bundle save) {
        user_id=new UserSqlHelper(getBaseContext()).UserId();
        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            group_id=intent.getIntExtra(ConstantUtils.GROUP_ID,0);
        }
        loadData();
        initData();
    }

    public void loadData(){
        Retrofits.getGroupAPI()
                .getGroupListInfo(group_id)
                .enqueue(new Callback<GroupListInfo>() {
                    @Override
                    public void onResponse(Call<GroupListInfo> call, Response<GroupListInfo> response) {
                        if (response.body()!=null&&response.body().getCode()==200){
                            Lists.addAll(response.body().getLists());
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<GroupListInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }
    public void initEmpty(){
        CommonEmpty.setVisibility(View.VISIBLE);
        SearchList.setVisibility(View.GONE);
        BoardList.setVisibility(View.GONE);
        SearchEmpty.setVisibility(View.GONE);
    }

    public void finishTask(){
        mGroupList.notifyDataSetChanged();
    }

    public void initData(){
        mGroupList=new GroupListViewAdapter(BoardList,Lists);
        BoardList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        BoardList.setAdapter(mGroupList);
        mGroupList.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Intent intent=new Intent();
                intent.putExtra(ConstantUtils.MEMBER_ID,Lists.get(position).getUser_id());
                intent.putExtra(ConstantUtils.MEMBER_NAME,Lists.get(position).getUser_name());
                setResult(BOARD_CODE,intent);
                finish();
            }
        });

        mGroupSearch=new GroupSearchViewAdapter(SearchList,Searchs);
        SearchList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        SearchList.setAdapter(mGroupSearch);
        mGroupSearch.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                Intent intent=new Intent();
                intent.putExtra(ConstantUtils.MEMBER_ID,Searchs.get(position).getUser_id());
                intent.putExtra(ConstantUtils.MEMBER_NAME,Searchs.get(position).getUser_name());
                setResult(BOARD_CODE,intent);
                finish();
            }
        });

        GroupBoardEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(GroupBoardEdit.getText().length()>0){
                    GroupBoardDelete.setVisibility(View.VISIBLE);
                }else{
                    GroupBoardDelete.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSearch();
            }
        });

        GroupBoardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupBoardEdit.setText("");
                GroupBoardDelete.setVisibility(View.GONE);
                BoardList.setVisibility(View.VISIBLE);
                SearchList.setVisibility(View.GONE);
                SearchEmpty.setVisibility(View.GONE);
            }
        });
    }

    public void loadSearch(){
        Retrofits.getGroupAPI()
                .getBoardSearchInfo(group_id,GroupBoardEdit.getText().toString())
                .enqueue(new Callback<BoardSearchInfo>() {
                    @Override
                    public void onResponse(Call<BoardSearchInfo> call, Response<BoardSearchInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            Searchs.addAll(response.body().getLists());
                            if(Searchs.size()<=0){
                                SearchList.setVisibility(View.GONE);
                                BoardList.setVisibility(View.GONE);
                                SearchEmpty.setVisibility(View.VISIBLE);
                            }else{
                                SearchList.setVisibility(View.VISIBLE);
                                BoardList.setVisibility(View.GONE);
                            }
                            finishSearchTask();
                        }else{
                            SearchList.setVisibility(View.GONE);
                            BoardList.setVisibility(View.GONE);
                            SearchEmpty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<BoardSearchInfo> call, Throwable t) {
                        initEmpty();
                    }
                });
    }

    private void finishSearchTask(){
        mGroupSearch.notifyDataSetChanged();
    }

    public static void launch(Activity activity,int group_id,int code){
        Intent intent=new Intent(activity,GroupBoardAActivity.class);
        intent.putExtra(ConstantUtils.GROUP_ID,group_id);
        activity.startActivityForResult(intent,code);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
