package com.tramp.word.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupMemberInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/02
 * version:1.0
 */
public class GroupMemberAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<GroupMemberInfo.Member.list> Lists;
    private Activity mActivity;
    private HashMap<Integer,Boolean> mCheck=new HashMap<>();
    private int group_user;
    public GroupMemberAdapter(RecyclerView recyclerView, Activity activity, ArrayList<GroupMemberInfo.Member.list> lists){
        super(recyclerView);
        mActivity=activity;
        Lists=lists;
        initCheck();
        group_user=new UserSqlHelper(activity).UserId();
    }

    private void initCheck(){
        if(Lists !=null){
            for (GroupMemberInfo.Member.list list : Lists){
                mCheck.put(list.getUser_id(),false);
            }
        }
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        bindContext(viewGroup.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_member_item,viewGroup,false));
    }

    public void CheckAll(){
        Iterator iterator=mCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            mCheck.put((Integer) entry.getKey(),true);
        }
        notifyDataSetChanged();
    }

    public void cancelAll(){
        Iterator iterator=mCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            mCheck.put((Integer) entry.getKey(),false);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemNumber.setText(position+1+"");
        Glide.with(mActivity)
                .load(Lists.get(position).getUser_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemListAvatar);
        mHolder.ItemListName.setText(Lists.get(position).getUser_name());
        mHolder.ItemListPraise.setText(String.valueOf(Lists.get(position).getUser_praise()));
        mHolder.ItemListDone.setText(String.valueOf(Lists.get(position).getUser_done()));
        mHolder.ItemListStar.setText(String.valueOf(Lists.get(position).getUser_star()));
        if(Lists.get(position).getUser_id()!=group_user){
            if(mCheck.get(Lists.get(position).getUser_id())){
                mHolder.ItemDelete.setVisibility(View.VISIBLE);
            }else{
                mHolder.ItemDelete.setVisibility(View.GONE);
            }
        }

        mHolder.ItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getGroupAPI()
                        .getGroupDeleteInfo(Lists.get(position).getUser_id())
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body() !=null&& response.body().getCode()==200){
                                    Utils.ShowToast(mActivity,"删除成功!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(mActivity,mActivity.getResources().getString(R.string.forget_net_error));
                            }
                        });
            }
        });
        mHolder.ItemRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,Lists.get(position).getUser_id());
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Lists.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemNumber;
        ImageView ItemListAvatar;
        TextView ItemListName;
        TextView ItemListPraise;
        TextView ItemListDone;
        TextView ItemListStar;
        RelativeLayout ItemRelative;
        TextView ItemDelete;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemNumber=$(R.id.item_list_number);
            ItemListAvatar=$(R.id.item_list_avatar);
            ItemListName=$(R.id.item_list_name);
            ItemListPraise=$(R.id.item_list_praise);
            ItemListDone=$(R.id.item_list_done);
            ItemListStar=$(R.id.item_list_star);
            ItemRelative=$(R.id.item_list_relative);
            ItemDelete=$(R.id.item_delete);
        }
    }
}
