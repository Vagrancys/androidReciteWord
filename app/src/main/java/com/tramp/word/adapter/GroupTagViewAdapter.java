package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.HashMap;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/17
 * version:1.0
 */

public class GroupTagViewAdapter extends AbsRecyclerViewAdapter{
    public int mTagNum;
    private String[] mTag={
            "全部","四级","六级","考研",
            "高考","中考","小学","日语",
            "韩语","小语种","留学","职场",
            "兴趣"
    };
    private HashMap<Integer,Boolean> mCheck=new HashMap<>();
    public GroupTagViewAdapter(RecyclerView recyclerView){
        super(recyclerView);
        mTagNum= PreferencesUtils.getInt(ConstantUtils.GROUP_CONTENT_CLASS,0);
        initCheckMap();
    }
    private void initCheckMap(){
        for (int i=0;i<mTag.length;i++){
            if(mTagNum==i){
                mCheck.put(i,true);
            }else{
                mCheck.put(i,false);
            }

        }
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.GroupTagText.setText(mTag[position]);
        if(mCheck.get(position)){
            mHolder.GroupTagText.setTextColor(getContext().getResources().getColor(R.color.black));
        }else{
            mHolder.GroupTagText.setTextColor(getContext().getResources().getColor(R.color.black_1));
        }
        super.onBindViewHolder(holder, position);
    }

    public HashMap<Integer, Boolean> getCheck() {
        return mCheck;
    }

    public void setCheck(HashMap<Integer, Boolean> mCheck) {
        this.mCheck = mCheck;
    }

    public void setTagNumber(int Number){
        mCheck.put(mTagNum,false);
        mCheck.put(Number,true);
        mTagNum=Number;
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_group_tag,parent,false));
    }

    @Override
    public int getItemCount() {
        return mTag.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView GroupTagText;
        public ItemViewHolder(View itemView){
            super(itemView);
            GroupTagText=$(R.id.item_group_tag_text);
        }
    }
}
