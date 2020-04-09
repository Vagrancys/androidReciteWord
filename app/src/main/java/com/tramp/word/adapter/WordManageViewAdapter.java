package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.entity.book.DefaultWordInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/1/21.
 */

public class WordManageViewAdapter extends AbsRecyclerViewAdapter {
    private HashMap<Integer,Boolean> checkMap=new HashMap<>();
    private ArrayList<DefaultWordInfo> Words;
    private CheckedChangeListener mCheckedChangeListener;
    public WordManageViewAdapter(RecyclerView recyclerView,ArrayList<DefaultWordInfo> words){
        super(recyclerView);
        Words=words;
        initCheckMap();
    }

    public void initCheckMap(){
        if(Words !=null){
            for (DefaultWordInfo word :Words){
                checkMap.put(word.getWord_id(),false);
            }
        }
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_manage,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        if(checkMap.get(Words.get(position).getWord_id())!=null){
            mHolder.ManageCheck.setChecked(checkMap.get(Words.get(position).getWord_id()));
        }
        mHolder.ManageText.setText(Words.get(position).getWord_name());
        mHolder.ManageCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkMap.put(Words.get(position).getWord_id(),isChecked);
                if(mCheckedChangeListener !=null){
                    mCheckedChangeListener.onCheckedChanged(position,buttonView,isChecked);
                }
            }
        });
        super.onBindViewHolder(holder, position);
    }

    public void checkAll(){
        Iterator iterator=checkMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            checkMap.put((Integer) entry.getKey(),true);
        }
        notifyDataSetChanged();
    }

    public void cancelAll(){
        Iterator iterator=checkMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            checkMap.put((Integer) entry.getKey(),false);
        }
        notifyDataSetChanged();
    }

    public int getCheckNum(){
        int num=0;
        Iterator iterator=checkMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            if((Boolean) entry.getValue()){
                num++;
            }
        }
        return num;
    }

    public List<Integer> getCheckId(){
        List<Integer> id=new ArrayList<>();
        Iterator iterator=checkMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            if((Boolean) entry.getValue()){
                id.add((Integer) entry.getKey());
            }
        }
        return id;
    }

    @Override
    public int getItemCount() {
        return Words.size();
    }

    public class ItemViewHolder extends ClickableViewHolder{
        CheckBox ManageCheck;
        TextView ManageText;
        public ItemViewHolder(View itemView){
            super(itemView);
            ManageText=$(R.id.manage_text);
            ManageCheck=$(R.id.manage_check);
        }
    }

    public void setCheckedChangeListener(CheckedChangeListener checkedChangeListener){
        mCheckedChangeListener = checkedChangeListener;
    }

    public interface CheckedChangeListener{
        void onCheckedChanged(int position,CompoundButton buttonView, boolean isChecked);
    }
}









