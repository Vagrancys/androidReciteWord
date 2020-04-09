package com.tramp.word.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.pk.PkDetailsInfo;
import com.tramp.word.utils.AssetsUtils;

import java.io.InputStream;
import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/09
 * version:1.0
 */

public class PkDetailsViewAdapter extends AbsRecyclerViewAdapter {
    private Bitmap PkScoreBg;
    private Bitmap BtnTrue;
    private Bitmap BtnFalse;
    private List<PkDetailsInfo.Details.Item> Details;
    public PkDetailsViewAdapter(RecyclerView recyclerView, Context context, List<PkDetailsInfo.Details.Item> details){
        super(recyclerView);
        Details=details;
        InputStream pkBg= AssetsUtils.getAssetsOpen(context,"pk_score_assets@2x.png");
        PkScoreBg= BitmapFactory.decodeStream(pkBg);
        BtnFalse=Bitmap.createBitmap(PkScoreBg,446,974,31,31);
        BtnTrue=Bitmap.createBitmap(PkScoreBg,4,990,37,26);
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pk_details,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.DetailsHead.setText(Details.get(position).getItem_word());
        if(position>=Details.size()-3){
            if(position==Details.size()){
                mHolder.DetailsOneTotal.setVisibility(View.GONE);
                mHolder.DetailsTwoTotal.setVisibility(View.GONE);
            }else{
                mHolder.DetailsOneTotal.setText(Details.get(position).getOne_total_number());
                mHolder.DetailsTwoTotal.setText(Details.get(position).getTwo_total_number());
                mHolder.DetailsOneTotal.setVisibility(View.VISIBLE);
                mHolder.DetailsTwoTotal.setVisibility(View.VISIBLE);
            }
            mHolder.DetailsHead.setTextColor(getContext().getResources().getColor(R.color.black_1));
            mHolder.DetailsOneNow.setText(Details.get(position).getOne_now_number());
            mHolder.DetailsTwoNow.setText(Details.get(position).getTwo_now_number());
            mHolder.DetailsImg.setVisibility(View.GONE);
            mHolder.DetailsTwo.setVisibility(View.VISIBLE);
            mHolder.DetailsOne.setVisibility(View.VISIBLE);
        }else{
            mHolder.DetailsImg.setVisibility(View.VISIBLE);
            mHolder.DetailsOne.setVisibility(View.GONE);
            mHolder.DetailsTwo.setVisibility(View.GONE);
            mHolder.DetailsHead.setTextColor(getContext().getResources().getColor(R.color.black));
            if(Details.get(position).getItem_one_number()==1){
                mHolder.DetailsOneImg.setImageBitmap(BtnTrue);
            }else{
                mHolder.DetailsOneImg.setImageBitmap(BtnFalse);
            }
            if(Details.get(position).getItem_two_number()==1){
                mHolder.DetailsTwoImg.setImageBitmap(BtnTrue);
            }else{
                mHolder.DetailsTwoImg.setImageBitmap(BtnFalse);
            }
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Details.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView DetailsHead;
        LinearLayout DetailsImg;
        ImageView DetailsOneImg;
        ImageView DetailsTwoImg;
        LinearLayout DetailsOne;
        TextView DetailsOneNow;
        TextView DetailsOneTotal;
        LinearLayout DetailsTwo;
        TextView DetailsTwoNow;
        TextView DetailsTwoTotal;
        public ItemViewHolder(View itemView){
            super(itemView);
            DetailsHead=$(R.id.details_head);
            DetailsImg=$(R.id.details_img);
            DetailsOne=$(R.id.details_one);
            DetailsOneNow=$(R.id.details_one_now);
            DetailsOneTotal=$(R.id.details_one_total);
            DetailsOneImg=$(R.id.details_one_img);
            DetailsTwo=$(R.id.details_two);
            DetailsTwoNow=$(R.id.details_two_now);
            DetailsTwoTotal=$(R.id.details_two_total);
            DetailsTwoImg=$(R.id.details_two_img);
        }
    }
}