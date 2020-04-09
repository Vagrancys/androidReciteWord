package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.user.UserFriendInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/24
 * version:1.0
 */
public class UserFriendAddSection extends StatelessSection {
    private ArrayList<UserFriendInfo.Data.Add> Add;
    private Context mContext;
    private Activity mActivity;
    public UserFriendAddSection(Activity activity,Context context, ArrayList<UserFriendInfo.Data.Add> add){
        super(R.layout.item_friend_add_header,R.layout.item_friend_add);
        Add=add;
        mContext=context;
        mActivity=activity;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder mHeader=(HeaderViewHolder) holder;
        mHeader.FriendAddTitle.setText(String.valueOf(Add.size()));
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
       ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.FriendName.setText(Add.get(position).getAdd_name());
        Glide.with(mContext)
                .load(Add.get(position).getAdd_avatar())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.FriendImg);
        mHolder.FriendDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getUserAPI()
                        .getFriendUpdateInfo(Add.get(position).getItem_id(),0)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    Utils.ShowToast(mContext,"忽略成功!");
                                }else{
                                    Utils.ShowToast(mContext,"忽略失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {

                            }
                        });
            }
        });

        mHolder.FriendAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getUserAPI()
                        .getFriendUpdateInfo(Add.get(position).getItem_id(),1)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    Utils.ShowToast(mContext,"接受成功!");
                                }else{
                                    Utils.ShowToast(mContext,"接受失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(mContext,"网络失效!");
                            }
                        });
            }
        });

        mHolder.FriendAddRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity,Add.get(position).getAdd_id());
            }
        });
    }

    @Override
    public int getContentItemsTotal() {
        return Add.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.friend_add_title)
        TextView FriendAddTitle;
        public HeaderViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.friend_add_relative)
        RelativeLayout FriendAddRelative;
        @BindView(R.id.friend_add_img)
        ImageView FriendImg;
        @BindView(R.id.friend_add_name)
        TextView FriendName;
        @BindView(R.id.friend_add_delete)
        TextView FriendDelete;
        @BindView(R.id.friend_add_add)
        TextView FriendAdd;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
