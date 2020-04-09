package com.tramp.word.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tramp.word.R;
import com.tramp.word.entity.recite.DefaultGateInfo;
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2019/1/24.
 */

public class ReciteBookViewAdapter extends AbsRecyclerViewAdapter{
    private Bitmap mMissionImg;
    private Bitmap mDefaultAvatarImg;
    private Bitmap mUserGoalImg;
    private Bitmap mOneItemImg;
    private Bitmap mTwoItemImg;
    private Bitmap mThereItemImg;
    private Bitmap mFourItemImg;
    private Bitmap mFiveItemImg;
    private Bitmap mAvatarImg;
    private Matrix mMatrix=new Matrix();
    private Activity mActivity;
    private int ItemGate;
    private int Gate;
    private ArrayList<DefaultGateInfo> Gates;
    public ReciteBookViewAdapter(RecyclerView recyclerView, Activity activity, int item_gate, ArrayList<DefaultGateInfo> gates,int gate){
        super(recyclerView);
        mActivity=activity;
        ItemGate=item_gate;
        Gates=gates;

        Gate=gate;
        InputStream is= AssetsUtils.getAssetsOpen(mActivity,"mission_assets@2x.png");
        mMissionImg= BitmapFactory.decodeStream(is);
        InputStream avatar= AssetsUtils.getAssetsOpen(mActivity,"pic_default_avtar@2x.png");
        mDefaultAvatarImg= BitmapFactory.decodeStream(avatar);
        mMatrix.setScale(0.2f,0.2f);
        mDefaultAvatarImg= Bitmap.createBitmap(mDefaultAvatarImg,0,0,176,176,mMatrix,false);
        InputStream user= AssetsUtils.getAssetsOpen(mActivity,"user_goal_icon.png");
        mUserGoalImg= BitmapFactory.decodeStream(user);
        mMatrix.setRotate(-90);
        mAvatarImg=Bitmap.createBitmap(mMissionImg,440,0,70,80,mMatrix,false);
        mOneItemImg=Bitmap.createBitmap(mMissionImg,360,445,100,90,mMatrix,false);
        mTwoItemImg=Bitmap.createBitmap(mMissionImg,260,430,100,95,mMatrix,false);
        mThereItemImg=Bitmap.createBitmap(mMissionImg,0,480,100,90,mMatrix,false);
        mFourItemImg=Bitmap.createBitmap(mMissionImg,100,430,100,90,mMatrix,false);
        mFiveItemImg=Bitmap.createBitmap(mMissionImg,0,385,100,90,mMatrix,false);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_recite_book_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
            if(position==0){
                mHolder.mLineHead.setVisibility(View.GONE);
            }else{
                mHolder.mLineHead.setVisibility(View.VISIBLE);
            }
            if(Gate==position+ItemGate*position){
                mHolder.mUserGoalImg.setImageBitmap(mUserGoalImg);
                mHolder.mUserAvatarLinear.setBackground(new BitmapDrawable(mAvatarImg));
                mHolder.mUserAvatarImg1.setImageBitmap(mDefaultAvatarImg);
            }else{
                mHolder.mUserAvatarImg1.setVisibility(View.GONE);
                mHolder.mUserAvatarLinear.setVisibility(View.GONE);
                mHolder.mUserGoalImg.setVisibility(View.GONE);
            }
            switch (Gates.get(position).getGate_star()){
                case 0:
                    mHolder.mReciteBookItem.setImageBitmap(mTwoItemImg);
                    break;
                case 1:
                    mHolder.mReciteBookItem.setImageBitmap(mThereItemImg);
                    break;
                case 2:
                    mHolder.mReciteBookItem.setImageBitmap(mFourItemImg);
                    break;
                case 3:
                    mHolder.mReciteBookItem.setImageBitmap(mFiveItemImg);
                    break;
                default:
                    mHolder.mReciteBookItem.setImageBitmap(mOneItemImg);
                    break;
            }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Gates.size();
    }

    public static class ItemViewHolder extends ClickableViewHolder{
        ImageView mUserGoalImg;
        ImageView mReciteBookItem;
        LinearLayout mUserAvatarLinear;
        ImageView mUserAvatarImg1;
        View mLineHead;
        public ItemViewHolder(View itemView){
            super(itemView);
            mReciteBookItem=$(R.id.book_item);
            mUserGoalImg=$(R.id.user_goal_img);
            mUserAvatarLinear=$(R.id.user_avatar_img);
            mUserAvatarImg1=$(R.id.user_avatar_img_1);
            mLineHead=$(R.id.line_head);
        }
    }
}
