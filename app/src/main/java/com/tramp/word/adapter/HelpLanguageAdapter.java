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

import java.util.HashMap;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/12
 * version:1.0
 */
public class HelpLanguageAdapter extends AbsRecyclerViewAdapter{
    private String[] mTitle;
    private int[] Language_img={
            R.drawable.pic_lang_en,R.drawable.pic_lang_jp,R.drawable.pic_lang_kr,
            R.drawable.pic_lang_fr,R.drawable.pic_lang_ge,R.drawable.pic_lang_sp,
            R.drawable.pic_lang_it,R.drawable.pic_lang_ru,R.drawable.pic_lang_th,
            R.drawable.pic_lang_cn
    };
    private int Language;
    private HashMap<String,Boolean> mCheck=new HashMap<>();
    public HelpLanguageAdapter(RecyclerView recyclerView, String[] title,int language){
        super(recyclerView);
        mTitle=title;
        Language= language;
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
        WholeLanguageLeftAdapter.ItemViewHolder mHolder=(WholeLanguageLeftAdapter.ItemViewHolder) holder;
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

