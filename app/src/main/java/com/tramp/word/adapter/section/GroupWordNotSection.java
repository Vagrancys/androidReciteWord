package com.tramp.word.adapter.section;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.entity.group.GroupMainRankInfo;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.port.GroupMainInterFace;
import com.tramp.word.widget.section.StatelessSection;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/20
 * version:1.0
 */

public class GroupWordNotSection extends StatelessSection{
    private Context mContext;
    private Activity mActivity;
    private ArrayList<GroupMainRankInfo.Rank.Item> Ranks;
    private GroupMainInterFace mFace;
    public GroupWordNotSection(Context context,Activity activity,ArrayList<GroupMainRankInfo.Rank.Item> ranks){
        super(R.layout.item_empty,R.layout.item_rank_not_foot,R.layout.item_group_rank_item);
        mContext=context;
        mActivity=activity;
        Ranks=ranks;
        mFace=(GroupMainInterFace) activity;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.MemberPraise.setText(String.valueOf(Ranks.get(position).getUser_praise()));
        mHolder.MemberName.setText(Ranks.get(position).getUser_name());

        Glide.with(mContext)
                .load(Ranks.get(position).getUser_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.MemberAvatar);
        mHolder.MemberNumber.setVisibility(View.VISIBLE);
        mHolder.MemberImg.setVisibility(View.GONE);
        if(Ranks.get(position).getUser_star()<=0){
            mHolder.MemberNumber.setText("");
            mHolder.MemberName.setTextColor(mContext.getResources().getColor(R.color.black_1));
            mHolder.MemberStar.setTextColor(mContext.getResources().getColor(R.color.black_1));
            mHolder.MemberStar.setText(Ranks.get(position).getUser_star()+"(昨天)");
            mHolder.MemberMind.setImageResource(R.drawable.icon_new);
            mHolder.MemberView.setAlpha(0.5f);
        }else{
            mHolder.MemberNumber.setText(String.valueOf(position));
            mHolder.MemberName.setTextColor(mContext.getResources().getColor(R.color.black));
            mHolder.MemberStar.setTextColor(mContext.getResources().getColor(R.color.black));
            mHolder.MemberStar.setText(String.valueOf(Ranks.get(position).getUser_star()));
            mHolder.MemberMind.setImageResource(R.drawable.icon_group_member_heart);
            mHolder.MemberView.setAlpha(1f);
        }
        mHolder.GroupRankRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendDetailsActivity.launch(mActivity, Ranks.get(position).getUser_id());
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        FooterViewHolder mFooter=(FooterViewHolder) holder;
        if(Ranks.size()==1){
            mFooter.GroupRankShare.setVisibility(View.VISIBLE);
        }else{
            mFooter.GroupRankShare.setVisibility(View.GONE);
        }
        mFooter.GroupRankShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFace.ShowShareLayout();
            }
        });
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.group_rank_relative)
        RelativeLayout GroupRankRelative;
        @BindView(R.id.item_group_member_number)
        TextView MemberNumber;
        @BindView(R.id.item_group_member_img)
        ImageView MemberImg;
        @BindView(R.id.item_group_member_avatar)
        ImageView MemberAvatar;
        @BindView(R.id.item_group_member_mind)
        TextView MemberStar;
        @BindView(R.id.item_group_member_name)
        TextView MemberName;
        @BindView(R.id.item_group_member_star)
        TextView MemberPraise;
        @BindView(R.id.mind)
        ImageView MemberMind;
        @BindView(R.id.imageView)
        ImageView MemberView;
        public ItemViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.group_rank_share)
        LinearLayout GroupRankShare;
        public FooterViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getContentItemsTotal() {
        return Ranks.size();
    }
}
