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
import com.tramp.word.entity.friend.FriendInfo;
import com.tramp.word.module.pk.PkSearchActivity;
import com.tramp.word.module.user.FriendDetailsActivity;
import com.tramp.word.port.WordPkInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.ArrayList;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/06
 * version:1.0
 */

public class WordPkViewAdapter extends AbsRecyclerViewAdapter{
    private String[] title={
            "Yugyseqwuiag","xxfyd"
    };
    private String[] text={
            "仁爱版七年级下册","BR L4"
    };

    private WordPkInterFace mWordPk;
    private Activity mActivity;
    private ArrayList<FriendInfo.Friend> mFriends;
    public WordPkViewAdapter(RecyclerView recyclerView, Activity activity, ArrayList<FriendInfo.Friend> friends){
        super(recyclerView);
        mActivity=activity;
        mFriends=friends;
        mWordPk=(WordPkInterFace) getContext();
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_pk,parent,false));
    }



    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            ItemViewHolder mHolder=(ItemViewHolder) holder;
            mHolder.mTitleView.setText(mFriends.get(position).getFriend_name());
            mHolder.mTextView.setText(mFriends.get(position).getFriend_summary());
            Glide.with(mActivity)
                    .load(mFriends.get(position).getFriend_img())
                    .placeholder(R.drawable.user_avater)
                    .into(mHolder.mAvatarView);
            mHolder.mAvatarView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendDetailsActivity.launch(mActivity,mFriends.get(position).getFriend_id());
                }
            });
            mHolder.mPkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(PreferencesUtils.getBoolean(ConstantUtils.WORD_PK_CODE,false)){
                        mActivity.startActivity(new Intent(mActivity, PkSearchActivity.class));
                    }else{
                        mWordPk.ShowDialog();
                    }

                }
            });
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView mAvatarView;
        TextView mTitleView;
        TextView mTextView;
        ImageView mPkView;
        public ItemViewHolder(View itemView){
            super(itemView);
            mAvatarView=$(R.id.item_word_pk_img);
            mTitleView=$(R.id.item_word_pk_title);
            mTextView=$(R.id.item_word_pk_text);
            mPkView=$(R.id.item_word_pk_out);
        }
    }

}



