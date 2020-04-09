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
import com.tramp.word.entity.group.GroupFinialInfo;
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

public class GroupFinialViewAdapter extends AbsRecyclerViewAdapter{
    private Activity mActivity;
    private GroupAddInterFace mFace;
    private int Add_Status;
    private ArrayList<GroupFinialInfo.Item> Commons;
    private int User_id;
    public GroupFinialViewAdapter(RecyclerView recyclerView, Activity activity,ArrayList<GroupFinialInfo.Item> commons,int add_status,int user_id){
        super(recyclerView);
        mActivity=activity;
        mFace=(GroupAddInterFace) getContext();
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
        mHolder.ItemLinear.setText(Commons.get(position).getGroup_summary());
        if(Add_Status==0){
            mHolder.ItemAdd.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemAdd.setVisibility(View.GONE);
        }
        mHolder.ItemAdd.setOnClickListener(new View.OnClickListener() {
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
        super.onBindViewHolder(holder, position);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_view,parent,false));
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemAvatar;
        TextView ItemMember;
        TextView ItemName;
        TextView ItemDone;
        TextView ItemTag;
        TextView ItemStar;
        TextView ItemLinear;
        TextView ItemAdd;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemAdd=$(R.id.item_view_add);
            ItemLinear=$(R.id.item_view_linear);
            ItemAvatar=$(R.id.item_view_avatar);
            ItemMember=$(R.id.item_view_member);
            ItemName=$(R.id.item_view_name);
            ItemDone=$(R.id.item_view_done);
            ItemTag=$(R.id.item_view_tag);
            ItemStar=$(R.id.item_view_star);
        }
    }
}
