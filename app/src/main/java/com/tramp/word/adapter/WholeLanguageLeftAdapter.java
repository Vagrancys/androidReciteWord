package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;

import java.util.HashMap;


/**
 * Created by Administrator on 2019/1/14.
 */

public class WholeLanguageLeftAdapter extends AbsRecyclerViewAdapter{
    private String[] mTitle;
    private int[] Language_img={
            R.drawable.pic_lang_en,R.drawable.pic_lang_jp,R.drawable.pic_lang_kr,
            R.drawable.pic_lang_fr,R.drawable.pic_lang_ge,R.drawable.pic_lang_sp,
            R.drawable.pic_lang_it,R.drawable.pic_lang_ru,R.drawable.pic_lang_th,
            R.drawable.pic_lang_cn
    };
    private int Language;
    private HashMap<String,Boolean> mCheck=new HashMap<>();
    public WholeLanguageLeftAdapter(RecyclerView recyclerView,String[] title){
        super(recyclerView);
        mTitle=title;
        Language= PreferencesUtils.getInt(ConstantUtils.LANGUAGE,0);
        initCheck();
    }
    private void initCheck(){
        if(mTitle !=null){
            for (int i=0;i<mTitle.length;i++){
                if(Language==i){
                    mCheck.put(mTitle[i],true);
                }else{
                    mCheck.put(mTitle[i],false);
                }
            }
        }
    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_language,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        ItemViewHolder mHolder=(ItemViewHolder) holder;
        mHolder.ItemLanguageImg.setImageResource(Language_img[position]);
        mHolder.ItemLanguageText.setText(mTitle[position]);
        if(mCheck.get(mTitle[position])){
            mHolder.ItemLanguageSelect.setVisibility(View.VISIBLE);
        }else{
            mHolder.ItemLanguageSelect.setVisibility(View.GONE);
        }
        mHolder.ItemLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHolder.ItemLanguageSelect.setVisibility(View.VISIBLE);
            }
        });
        super.onBindViewHolder(holder, position);
    }

    public HashMap<String, Boolean> getCheck() {
        return mCheck;
    }

    @Override
    public int getItemCount() {
        return mTitle.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        ImageView ItemLanguageImg;
        TextView ItemLanguageText;
        ImageView ItemLanguageSelect;
        LinearLayout ItemLanguage;
        public ItemViewHolder(View itemView){
            super(itemView);
            ItemLanguageImg=$(R.id.whole_language_img);
            ItemLanguageText=$(R.id.whole_language_text);
            ItemLanguageSelect=$(R.id.whole_language_img_select);
            ItemLanguage=$(R.id.item_language);
        }
    }

}
