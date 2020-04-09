package com.tramp.word.adapter;

import android.app.Activity;
import android.content.Intent;
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
 * time  : 2019/03/19
 * version:1.0
 */

public class GroupViewAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    private GroupAddInterFace mFace;
    private int mStatus;
    private ArrayList<GroupContextInfo.Item> Commons;
    private int Add_Status;
    private int User_id;
    public GroupViewAdapter(RecyclerView recyclerView, Activity activity,int status,ArrayList<GroupContextInfo.Item> commons,int add_status,int user_id){
        super(recyclerView);
        mActivity=activity;
        mStatus=status;
        mFace=(GroupAddInterFace) activity;
        Commons=commons;
        Add_Status=add_status;
        User_id=user_id;
    }
    @Override
    public int getItemCount() {
        return Commons.size();
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Commons.get(position).getGroup_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemAvatar);
        mHolder.ItemMember.setText(Commons.get(position).getGroup_member()+"/"+Commons.get(position).getGroup_all_member());
        mHolder.ItemName.setText(Commons.get(position).getGroup_name());
        mHolder.ItemDone.setText(Commons.get(position).getGroup_done()+"%");
        mHolder.ItemStar.setText(Commons.get(position).getGroup_star());
        mHolder.ItemTag.setText(Commons.get(position).getGroup_tag());
        if(Add_Status!=0){
            mHolder.ItemGroupAdd.setVisibility(View.GONE);
        }else{
            mHolder.ItemGroupAdd.setVisibility(View.VISIBLE);
        }
        mHolder.ItemGroupAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Add_Status==0){
                    Retrofits.getGroupAPI()
                            .getGroupAddInfo(Commons.get(position).getGroup_id(),User_id)
                            .enqueue(new Callback<DefaultInfo>() {
                                @Override
                                public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                    if(response.body() !=null&&response.body().getCode()==200){
                                        mActivity.startActivity(new Intent(mActivity, GroupMainActivity.class));
                                        Utils.StarActivityInAnim(mActivity);
                                    }else if(response.body().getCode()==201){
                                        mFace.ShowDialog(Commons.get(position).getGroup_id(),Commons.get(position).getGroup_name());
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
        if(mStatus==0){
            mHolder.ItemGroupItem.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_item_bg));
            mHolder.ItemGroupAdd.setTextColor(getContext().getResources().getColor(R.color.blue_1));
            mHolder.ItemGroupAdd.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_add_bg));
        }else{
            mHolder.ItemGroupItem.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_items_bg));
            mHolder.ItemGroupAdd.setTextColor(getContext().getResources().getColor(R.color.red_1));
            mHolder.ItemGroupAdd.setBackground(getContext().getResources().getDrawable(R.drawable.btn_group_adds_bg));
        }
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_item,parent,false));
    }

    public class ItemViewHolder extends ClickableViewHolder{
        RelativeLayout ItemGroupItem;
        TextView ItemGroupAdd;
        ImageView ItemAvatar;
        TextView ItemMember;
        TextView ItemName;
        TextView ItemDone;
        TextView ItemTag;
        TextView ItemStar;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemGroupAdd=$(R.id.item_group_add);
            ItemGroupItem=$(R.id.item_group_item);
            ItemAvatar=$(R.id.item_group_avatar);
            ItemMember=$(R.id.item_group_member);
            ItemName=$(R.id.item_group_name);
            ItemDone=$(R.id.item_group_learn_done);
            ItemTag=$(R.id.item_group_learn_tag);
            ItemStar=$(R.id.item_group_learn_number);
        }
    }
}
