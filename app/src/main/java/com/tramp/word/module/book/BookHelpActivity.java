package com.tramp.word.module.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.HelpLanguageAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.book.BookFormInfo;
import com.tramp.word.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/06/09
 * version:1.0
 */
public class BookHelpActivity extends RxBaseActivity {
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.help_language_relative)
    RelativeLayout HelpLanguageRelative;
    @BindView(R.id.language_img)
    ImageView LanguageImg;
    @BindView(R.id.language_title)
    TextView LanguageTitle;
    @BindView(R.id.help_name_edit)
    EditText HelpNameEdit;
    @BindView(R.id.help_group_edit)
    EditText HelpGroupEdit;
    @BindView(R.id.help_note_edit)
    EditText HelpNoteEdit;
    @BindView(R.id.help_link_edit)
    EditText HelpLinkEdit;
    @BindView(R.id.help_start)
    TextView HelpStart;
    @BindView(R.id.language_back)
    LinearLayout LanguageBack;
    @BindView(R.id.language_recycler)
    RecyclerView LanguageRecycler;

    private int[] Language_img={
            R.drawable.pic_lang_en,R.drawable.pic_lang_jp,R.drawable.pic_lang_kr,
            R.drawable.pic_lang_fr,R.drawable.pic_lang_ge,R.drawable.pic_lang_sp,
            R.drawable.pic_lang_it,R.drawable.pic_lang_ru,R.drawable.pic_lang_th,
            R.drawable.pic_lang_cn
    };

    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private Animation mScaleAnim;
    private HelpLanguageAdapter mLanguageAdapter;
    private String[] languages;
    private int user_id;
    private BookFormInfo bookFormInfo=new BookFormInfo();
    private int language_number=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_book_help;
    }

    @Override
    protected void initToolBar() {
        DefaultTitle.setText(getResources().getString(R.string.book_help_title));

        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);

        user_id=new UserSqlHelper(getBaseContext()).UserId();

        languages=getResources().getStringArray(R.array.language_array);
        LanguageRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
        mLanguageAdapter=new HelpLanguageAdapter(LanguageRecycler,languages,language_number);
        LanguageRecycler.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                mLanguageAdapter.getCheck().put(languages[language_number],false);
                mLanguageAdapter.getCheck().put(languages[position],true);
                language_number=position;
                LanguageImg.setImageResource(Language_img[position]);
                LanguageTitle.setText(languages[position]);
                LanguageRecycler.startAnimation(mTopExitAnim);
                LanguageBack.setVisibility(View.GONE);
                mLanguageAdapter.notifyDataSetChanged();
                LanguageRecycler.setVisibility(View.GONE);
            }
        });

        HelpLanguageRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageRecycler.setVisibility(View.VISIBLE);
                LanguageRecycler.startAnimation(mTopEnterAnim);
                LanguageBack.setVisibility(View.VISIBLE);
            }
        });

        LanguageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageRecycler.startAnimation(mTopExitAnim);
                LanguageRecycler.setVisibility(View.GONE);
                LanguageBack.setVisibility(View.GONE);
            }
        });

        HelpStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpStart.startAnimation(mScaleAnim);
                initForm();
            }
        });
    }

    private void initForm(){
        if(HelpNameEdit.getText().length()<=0){
            Utils.ShowToast(getBaseContext(),"名称未填写!");
            return;
        }
        if(HelpLinkEdit.getText().length()<=0){
            Utils.ShowToast(getBaseContext(),"联系方式未填写!");
            return;
        }
        bookFormInfo.setForm_language(language_number);
        bookFormInfo.setForm_name(HelpNameEdit.getText().toString());
        bookFormInfo.setForm_group(HelpGroupEdit.getText().length()==0?HelpGroupEdit.getText().toString():"");
        bookFormInfo.setForm_note(HelpNoteEdit.getText().length()==0?HelpNoteEdit.getText().toString():"");
        bookFormInfo.setForm_link(HelpLinkEdit.getText().toString());
        Retrofits.getBookAPI().getBookFormInfo(user_id,bookFormInfo).enqueue(new Callback<DefaultInfo>() {
            @Override
            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                if(response.body()!=null&&response.body().getCode()==200){
                    Utils.ShowToast(getBaseContext(),getResources().getString(R.string.help_toast));
                }
            }

            @Override
            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
            }
        });
    }

    public static void launch(Activity activity){
        Intent intent=new Intent(activity,BookHelpActivity.class);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
