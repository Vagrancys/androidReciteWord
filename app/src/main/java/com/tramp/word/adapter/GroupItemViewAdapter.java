package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.group.GroupContextInfo;
import com.tramp.word.module.group.GroupMainActivity;
import com.tramp.word.port.GroupAddInterFace;
import com.tramp.word.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupItemViewAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    private ArrayList<GroupContextInfo.Item> Lists;
    private int Add_Status;
    private GroupAddInterFace mFace;
    private int mUser_id;
    public GroupItemViewAdapter(RecyclerView recyclerView, Activity activity,ArrayList<GroupContextInfo.Item> lists,int add_status,int user_id){
        super(recyclerView);
        mActivity=activity;
        Lists=lists;
        Add_Status=add_status;
        mFace=(GroupAddInterFace) activity;
        mUser_id=user_id;
    }
    @Override
    public int getItemCount() {
        return Lists.size();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Lists.get(position).getGroup_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemAvatar);
        mHolder.ItemName.setText(Lists.get(position).getGroup_name());
        mHolder.ItemMember.setText(Lists.get(position).getGroup_member()+"/"+Lists.get(position).getGroup_all_member());
        mHolder.ItemDone.setText("完成率"+Lists.get(position).getGroup_done()+"%");
        mHolder.ItemTag.setText(Lists.get(position).getGroup_tag());
        mHolder.ItemStar.setText(Lists.get(position).getGroup_star());
        mHolder.ItemSummary.setText(Lists.get(position).getGroup_summary());
        if(Add_Status!=0){
            mHolder.ItemViewAdd.setVisibility(View.GONE);
        }else{
            mHolder.ItemViewAdd.setVisibility(View.VISIBLE);
        }
        mHolder.ItemViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Add_Status==0){
                    Retrofits.getGroupAPI()
                            .getGroupAddInfo(Lists.get(position).getGroup_id(),mUser_id)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body() !=null&&response.body().getCode()==200){
                                        mActivity.startActivity(new Intent(mActivity, GroupMainActivity.class));
                                        Utils.StarActivityInAnim(mActivity);
                                    }else if(response.body().getCode()==201){
                                        mFace.ShowDialog(Lists.get(position).getGroup_id(),Lists.get(position).getGroup_name());
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                    Utils.ShowToast(getContext(),"网络失效!");
                                }
                            });
                }
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_view,parent,false));
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ItemViewAdd;
        ImageView ItemAvatar;
        TextView ItemMember;
        TextView ItemDone;
        TextView ItemTag;
        TextView ItemName;
        TextView ItemStar;
        TextView ItemSummary;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemViewAdd=$(R.id.item_view_add);
            ItemStar=$(R.id.item_view_star);
            ItemAvatar=$(R.id.item_view_avatar);
            ItemMember=$(R.id.item_view_member);
            ItemDone=$(R.id.item_view_done);
            ItemTag=$(R.id.item_view_tag);
            ItemName=$(R.id.item_view_name);
            ItemSummary=$(R.id.item_view_linear);
        }
    }
}
