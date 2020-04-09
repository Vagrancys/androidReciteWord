package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.entity.pk.PkKnowInfo;
import com.tramp.word.entity.pk.PkModernInfo;
import com.tramp.word.port.PkKnowInterFace;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/10
 * version:1.0
 */

public class WordModernViewAdapter extends AbsRecyclerViewAdapter{
    private ArrayList<PkModernInfo.Modern> Datas;
    private PkKnowInterFace mKnow;
    public WordModernViewAdapter(RecyclerView recyclerView, ArrayList<PkModernInfo.Modern> datas,Fragment fragment){
        super(recyclerView);
        Datas=datas;
        mKnow=(PkKnowInterFace) fragment;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_pk_modern,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        Glide.with(getContext())
                .load(Datas.get(position).getModern_img())
                .placeholder(R.drawable.user_avater)
                .into(mHolder.ItemImg);
        mHolder.ItemName.setText(Datas.get(position).getModern_name());
        mHolder.ItemTime.setText(String.valueOf(Datas.get(position).getModern_time()));
        mHolder.ItemWord.setText(Datas.get(position).getModern_word());
        mHolder.ItemKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofits.getPkAPI().getPkKnowInfo(Datas.get(position).getModern_id())
                        .enqueue(new Callback<PkKnowInfo>() {
                            @Override
                            public void onResponse(Call<PkKnowInfo> call, Response<PkKnowInfo> response) {
                                if(response.body().getCode()==200){
                                    mKnow.SwipeLayout();
                                }
                            }

                            @Override
                            public void onFailure(Call<PkKnowInfo> call, Throwable t) {

                            }
                        });
            }
        });
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return Datas.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemImg;
        TextView ItemName;
        TextView ItemWord;
        TextView ItemTime;
        TextView ItemKnow;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemImg=$(R.id.pk_modern_avatar);
            ItemName=$(R.id.pk_modern_name);
            ItemWord=$(R.id.pk_modern_word);
            ItemTime=$(R.id.pk_modern_time);
            ItemKnow=$(R.id.pk_modern_know);
        }
    }
}
