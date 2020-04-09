package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/30.
 */

public class WordDetailsViewAdapter extends AbsRecyclerViewAdapter {
    private ArrayList<DefaultWordInfo> Words;
    private HashMap<Integer,Boolean> mCheck=new HashMap<>();
    private HashMap<Integer,Boolean> mSelectCheck=new HashMap<>();
    private CheckedChangeListener mCheckedChangeListener;
    public WordDetailsViewAdapter(RecyclerView recyclerView,ArrayList<DefaultWordInfo> words){
        super(recyclerView);
        Words=words;
        initCheckMap();
    }

    public void initCheckMap(){
        if(Words!=null){
            int position=0;
            for (DefaultWordInfo word:Words){
                mCheck.put(position,true);
                mSelectCheck.put(position,false);
                position++;
            }
        }
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_details,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.DetailsTitle.setText(Words.get(position).getWord_name());
        mHolder.DetailsMusic.setText(Words.get(position).getWord_music());
        mHolder.DetailsMeaning.setText(Words.get(position).getWord_meaning());
        if(Words.get(position).getWord_error()==1){
            mHolder.DetailsError.setVisibility(View.VISIBLE);
            mHolder.DetailsErrorImg.setVisibility(View.VISIBLE);
        }else{
            mHolder.DetailsError.setVisibility(View.GONE);
            mHolder.DetailsErrorImg.setVisibility(View.GONE);
        }
        if(mSelectCheck.get(position)){
            mHolder.DetailsCheck.setVisibility(View.VISIBLE);
            if(mCheck.get(position)!=null){
                mHolder.DetailsCheck.setChecked(mCheck.get(position));
            }
        }else{
            mHolder.DetailsCheck.setVisibility(View.GONE);
        }
        mHolder.DetailsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheck.put(position,isChecked);
                if(isChecked){
                    mHolder.DetailsTitle.setTextColor(getContext().getResources().getColor(R.color.black));
                    mHolder.DetailsMeaning.setTextColor(getContext().getResources().getColor(R.color.black));
                }else{
                    mHolder.DetailsTitle.setTextColor(getContext().getResources().getColor(R.color.black_1));
                    mHolder.DetailsMeaning.setTextColor(getContext().getResources().getColor(R.color.black_1));
                }
                if(mCheckedChangeListener !=null){
                    mCheckedChangeListener.onCheckedChanged(position,getCheckNum(),buttonView,isChecked);
                }
            }
        });
        super.onBindViewHolder(holder, position);
    }

    public HashMap<Integer, Boolean> getCheck() {
        return mCheck;
    }

    public int getCheckNum(){
        int num=0;
        Iterator iterator=mCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            if((Boolean) entry.getValue()){
                num++;
            }
        }
        return num;
    }

    public void checkAll(){
        Iterator iterator=mSelectCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            mSelectCheck.put((Integer) entry.getKey(),true);
        }
        notifyDataSetChanged();
    }

    public void cancelAll(){
        Iterator iterator=mSelectCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            mSelectCheck.put((Integer) entry.getKey(),false);
        }
        Iterator iterator1=mCheck.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator1.next();
            mCheck.put((Integer) entry.getKey(),true);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Words.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        View DetailsError;
        ImageView DetailsErrorImg;
        CheckBox DetailsCheck;
        TextView DetailsTitle;
        TextView DetailsMeaning;
        TextView DetailsMusic;

        public ItemViewHolder(View itemView){
            super(itemView);
            DetailsError=$(R.id.details_error);
            DetailsErrorImg=$(R.id.details_error_img);
            DetailsCheck=$(R.id.details_check);
            DetailsTitle=$(R.id.details_title);
            DetailsMeaning=$(R.id.details_meaning);
            DetailsMusic=$(R.id.details_music);
        }
    }

    public void setCheckedChangeListener(CheckedChangeListener checkedChangeListener){
        mCheckedChangeListener = checkedChangeListener;
    }

    public interface CheckedChangeListener{
        void onCheckedChanged(int position,int number,CompoundButton buttonView, boolean isChecked);
    }
}



