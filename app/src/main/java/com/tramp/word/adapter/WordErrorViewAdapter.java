package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.widget.WordErrorDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/06
 * version:1.0
 */
public class WordErrorViewAdapter extends AbsRecyclerViewAdapter{
    private String[] mTitle;
    private Map<Integer,Integer> mMap=new HashMap<>();
    private String ErrorText;
    private WordErrorDialog.HintClickListener hintClickListener;
    public WordErrorViewAdapter(RecyclerView recyclerView, String[] title, WordErrorDialog.HintClickListener hintClickListener){
        super(recyclerView);
        mTitle=title;
        initCheckMap();
        this.hintClickListener=hintClickListener;
    }

    private void initCheckMap(){
        if(mTitle !=null){
            for (int i=0;i<mTitle.length;i++){
                mMap.put(i,0);
            }
        }
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        bindContext(viewGroup.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_error_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ErrorTitle.setText(mTitle[position]);
        mHolder.ErrorCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked&&position==getItemCount()){
                    mMap.put(position,1);
                    mHolder.ErrorHint.setVisibility(View.GONE);
                    mHolder.ErrorEdit.setVisibility(View.VISIBLE);
                }else if(isChecked&&position==getItemCount()){
                    mMap.put(position,0);
                    mHolder.ErrorHint.setVisibility(View.VISIBLE);
                    mHolder.ErrorEdit.setVisibility(View.GONE);
                }else if(isChecked){
                    mMap.put(position,1);
                }else{
                    mMap.put(position,0);
                }
                if(hintClickListener !=null){
                    hintClickListener.onHintClick(getNumber());
                }
            }
        });
        mHolder.ErrorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ErrorText=mHolder.ErrorEdit.getText().toString();
            }
        });
        if(position==getItemCount()){
            mHolder.ErrorView.setVisibility(View.GONE);
        }
        super.onBindViewHolder(holder, position);
    }

    public List<Integer> getCheck(){
        List<Integer> id=new ArrayList<>();
        Iterator iterator=mMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            if((Integer) entry.getValue()==1){
                id.add((Integer) entry.getKey());
            }
        }
        return id;
    }

    public Integer getNumber(){
        int num=0;
        Iterator iterator=mMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry=(Map.Entry) iterator.next();
            if((Integer) entry.getValue()==1){
                num++;
            }
        }
        return num;
    }

    public String getEdit(){
        return ErrorText;
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        TextView ErrorTitle;
        TextView ErrorHint;
        CheckBox ErrorCheck;
        EditText ErrorEdit;
        View ErrorView;
        public ItemViewHolder(View itemView){
            super(itemView);
            ErrorTitle=$(R.id.error_title);
            ErrorHint=$(R.id.error_hint);
            ErrorCheck=$(R.id.error_check);
            ErrorEdit=$(R.id.error_edit);
            ErrorView=$(R.id.error_view);
        }
    }
}
