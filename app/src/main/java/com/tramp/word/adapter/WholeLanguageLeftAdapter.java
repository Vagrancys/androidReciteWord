package com.tramp.word.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tramp.word.R;


/**
 * Created by Administrator on 2019/1/14.
 */

public class WholeLanguageLeftAdapter extends AbsRecyclerViewAdapter{
    private String[] title={
            "英语","日语","韩语",
            "法语","德语","西班牙语",
            "意大利语","俄语","泰语",
            "汉语"
    };
    private int[] language_img={
            R.drawable.pic_lang_en,R.drawable.pic_lang_jp,R.drawable.pic_lang_kr,
            R.drawable.pic_lang_fr,R.drawable.pic_lang_ge,R.drawable.pic_lang_sp,
            R.drawable.pic_lang_it,R.drawable.pic_lang_ru,R.drawable.pic_lang_th,
            R.drawable.pic_lang_cn
    };

    public WholeLanguageLeftAdapter(RecyclerView recyclerView){
        super(recyclerView);

    }

    @NonNull
    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_word_whole_language,parent,false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ItemViewHolder extends ClickableViewHolder{
        public ItemViewHolder(View itemView){
            super(itemView);
        }
    }

}
