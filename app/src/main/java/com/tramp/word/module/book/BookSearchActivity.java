package com.tramp.word.module.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.BookHintViewAdapter;
import com.tramp.word.adapter.BookTagViewAdapter;
import com.tramp.word.adapter.section.BookSearchSection;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.BookHintInfo;
import com.tramp.word.entity.book.BookInsertInfo;
import com.tramp.word.entity.book.BookListInfo;
import com.tramp.word.entity.book.BookSearchInfo;
import com.tramp.word.entity.book.BookTagInfo;
import com.tramp.word.port.BookSearchInterFace;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.section.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/24
 * version:1.0
 */
public class BookSearchActivity extends RxBaseActivity implements BookSearchInterFace {
    private final String LOG="BookSearchActivity";
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_edit)
    EditText DefaultEdit;
    @BindView(R.id.default_delete)
    ImageView DefaultDelete;
    @BindView(R.id.default_search)
    ImageView DefaultSearch;

    @BindView(R.id.book_tag_recycler)
    RecyclerView BookTagRecycler;
    @BindView(R.id.book_back)
    LinearLayout BookBack;
    @BindView(R.id.book_hint)
    RecyclerView BookHint;
    @BindView(R.id.book_swipe)
    SwipeRefreshLayout BookSwipe;
    @BindView(R.id.book_section_recycler)
    RecyclerView BookSectionRecycler;
    @BindView(R.id.book_bottom_help)
    TextView BookBottomHelp;

    @BindView(R.id.book_background)
    RelativeLayout BookBackground;
    @BindView(R.id.book_search_pop)
    RelativeLayout BookSearchPop;
    @BindView(R.id.pop_title)
    TextView PopTitle;
    @BindView(R.id.pop_num_left)
    TextView PopTotal;
    @BindView(R.id.pop_num_right)
    TextView PopNumber;
    @BindView(R.id.pop_summary)
    TextView PopSummary;
    @BindView(R.id.pop_img)
    ImageView PopImg;
    @BindView(R.id.pop_perfect)
    ImageView PopPerfect;
    @BindView(R.id.pop_button)
    TextView PopButton;
    @BindView(R.id.pop_exit)
    ImageView PopExit;
    @BindView(R.id.pop_share)
    TextView PopShare;
    @BindView(R.id.book_frame)
    FrameLayout BookFrame;
    @BindView(R.id.common_empty)
    LinearLayout CommonEmpty;
    @BindView(R.id.net_button)
    TextView NetButton;

    private ArrayList<BookSearchInfo.Language> languages=new ArrayList<>();
    private ArrayList<BookTagInfo.Tag> tags=new ArrayList<>();
    private ArrayList<BookHintInfo.Hint> hints=new ArrayList<>();
    private BookTagViewAdapter mTagAdapter;
    private BookHintViewAdapter mHintAdapter;
    private SectionedRecyclerViewAdapter mSection;
    private int BookId;
    private int user_id;
    private Animation mScaleAnim;
    private Animation PopEnterAnim;
    private Animation PopExitAnim;
    private UserSqlHelper mUserSqlHelper;
    private Handler mHandler=new Handler();
    private int RESPONSE_CODE=15;
    private int edit_status=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_book_search;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DefaultDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookBack.setVisibility(View.GONE);
                BookHint.setVisibility(View.GONE);
                BookSwipe.setVisibility(View.GONE);
                DefaultDelete.setVisibility(View.GONE);
                DefaultEdit.setText("");
                edit_status=1;
            }
        });

        DefaultSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(LOG,"length="+DefaultEdit.getText().length());
                if(DefaultEdit.getText().length()>0){
                    BookBack.setVisibility(View.GONE);
                    BookHint.setVisibility(View.GONE);
                    loadData(DefaultEdit.getText().toString(),1);
                }
            }
        });
    }

    private void loadData(String search_name,int more_status){
        languages.clear();
        mSection.removeAllSections();
        BookSwipe.setVisibility(View.VISIBLE);
        BookSwipe.setRefreshing(true);
        Retrofits.getBookAPI().getBookSearchInfo(user_id,search_name,more_status)
                .enqueue(new Callback<BookSearchInfo>() {
                    @Override
                    public void onResponse(Call<BookSearchInfo> call, Response<BookSearchInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            languages.addAll(response.body().getLanguages());
                            finishTask();
                        }else{
                            initBookEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookSearchInfo> call, Throwable t) {
                        Log.e(LOG,t.getMessage());
                        initBookEmpty();
                    }
                });

    }

    private void finishTask(){
        BookSwipe.setRefreshing(false);
        for (BookSearchInfo.Language language:languages){
            mSection.addSection(new BookSearchSection(this,getBaseContext(),language.getBooks()));
        }
        mSection.notifyDataSetChanged();
    }

    private void initNetEmpty(){
        BookFrame.setVisibility(View.GONE);
        CommonEmpty.setVisibility(View.VISIBLE);
    }

    private void loadHint(String search_hint){
        hints.clear();
        Retrofits.getBookAPI().getBookHintInfo(search_hint)
                .enqueue(new Callback<BookHintInfo>() {
                    @Override
                    public void onResponse(Call<BookHintInfo> call, Response<BookHintInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            hints.addAll(response.body().getHints());
                            finishHint();
                        }else{
                            initHintEmpty();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookHintInfo> call, Throwable t) {
                        initHintEmpty();
                    }
                });

    }

    private void finishHint(){
        BookBack.setVisibility(View.VISIBLE);
        BookHint.setVisibility(View.VISIBLE);
        mHintAdapter.notifyDataSetChanged();
    }

    private void initBookEmpty(){
        BookSwipe.setRefreshing(false);
        BookSwipe.setVisibility(View.GONE);
    }

    private void initHintEmpty(){
        BookBack.setVisibility(View.GONE);
        BookHint.setVisibility(View.GONE);
    }

    @Override
    public void initView(Bundle save) {
        mUserSqlHelper=new UserSqlHelper(getBaseContext());
        user_id=mUserSqlHelper.UserId();
        PopEnterAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_window_enter_anim);
        PopExitAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_window_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.default_button_scale_anim);
        lazyData();
    }

    private void lazyData(){
        initRefresh();
        initRecycler();
        initPopView();
    }

    public void initPopView(){
        PopExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchPop.startAnimation(PopExitAnim);
                BookBackground.setVisibility(View.GONE);
                BookSearchPop.setVisibility(View.GONE);
            }
        });

        PopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int user_id=mUserSqlHelper.UserId();
                PopButton.startAnimation(mScaleAnim);
                BookSearchPop.startAnimation(PopEnterAnim);
                BookBackground.setVisibility(View.GONE);
                BookSearchPop.setVisibility(View.GONE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Call<BookInsertInfo> mCall =  Retrofits.getBookAPI()
                                .getBookInsertInfo(BookId,user_id);
                        addCall(mCall);
                        mCall.enqueue(new Callback<BookInsertInfo>() {
                            @Override
                            public void onResponse(Call<BookInsertInfo> call, Response<BookInsertInfo> response) {
                                if(response.body()!=null&&response.body().getCode()==200){
                                    if(response.body().getBook_status()==3){
                                        mUserSqlHelper.insertBook(response.body().getBook());
                                    }
                                    Intent intent=new Intent();
                                    intent.putExtra(ConstantUtils.BOOK_DOWN_RESPONSE,2);
                                    intent.putExtra(ConstantUtils.BOOK_DOWN_ID,BookId);
                                    //1 当前不下载 2 非当前不下载 3 非当前下载
                                    intent.putExtra(ConstantUtils.BOOK_DOWN_STATUS,response.body().getBook_status());
                                    setResult(RESPONSE_CODE,intent);
                                    finish();
                                }else{
                                    Utils.ShowToast(getBaseContext(),"添加失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<BookInsertInfo> call, Throwable t) {
                                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                            }
                        });
                    }
                },10);
            }
        });

        PopShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.ShowToast(getBaseContext(),getResources().getString(R.string.book_share_text));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BookBackground.setVisibility(View.GONE);
                        BookSearchPop.setVisibility(View.GONE);
                    }
                },250);
            }
        });
    }

    private void initRefresh(){
        Call<BookTagInfo> mCall = Retrofits.getBookAPI().getBookTagInfo();
        addCall(mCall);
        mCall.enqueue(new Callback<BookTagInfo>() {
            @Override
            public void onResponse(Call<BookTagInfo> call, Response<BookTagInfo> response) {
                if(response.body()!=null&&response.body().getCode()==200){
                    tags.addAll(response.body().getTags());
                    finishTag();
                }
            }

            @Override
            public void onFailure(Call<BookTagInfo> call, Throwable t) {
                initNetEmpty();
            }
        });
    }

    private void finishTag(){
        mTagAdapter.notifyDataSetChanged();
    }

    private void initRecycler(){
        mTagAdapter=new BookTagViewAdapter(BookTagRecycler,tags);
        GridLayoutManager grid=new GridLayoutManager(getBaseContext(),6);
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int length=tags.get(i).getTag_name().length();
                if(length<8&&length>=4){
                    return 2;
                }
                return 1;
            }
        });
        BookTagRecycler.setLayoutManager(grid);
        BookTagRecycler.setAdapter(mTagAdapter);

        mTagAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                edit_status=0;
                DefaultDelete.setVisibility(View.VISIBLE);
                DefaultEdit.setText(tags.get(position).getTag_name());
                loadData(tags.get(position).getTag_name(),1);
            }
        });

        DefaultEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(DefaultEdit.getText().length()>0&&edit_status==1){
                    DefaultDelete.setVisibility(View.VISIBLE);
                    loadHint(DefaultEdit.getText().toString());

                }else{
                    DefaultDelete.setVisibility(View.GONE);
                    BookBack.setVisibility(View.GONE);
                    BookHint.setVisibility(View.GONE);
                }
                edit_status=1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mHintAdapter=new BookHintViewAdapter(BookHint,hints);
        BookHint.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        BookHint.setAdapter(mHintAdapter);

        mHintAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                edit_status=0;
                DefaultDelete.setVisibility(View.VISIBLE);
                DefaultEdit.setText(hints.get(position).getHint_name());
                Log.e(LOG,"hints="+hints.size());
                loadData(hints.get(position).getHint_name(),1);
            }
        });

        mSection=new SectionedRecyclerViewAdapter();
        BookSectionRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        BookSectionRecycler.setAdapter(mSection);

        BookBottomHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookHelpActivity.launch(BookSearchActivity.this);
            }
        });

        BookSwipe.setOnRefreshListener(()->{
            BookSwipe.setRefreshing(true);
            loadData(DefaultEdit.getText().toString(),1);
        });

        NetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetButton.startAnimation(mScaleAnim);
                CommonEmpty.setVisibility(View.GONE);
                BookFrame.setVisibility(View.VISIBLE);
                initRefresh();
            }
        });
    }

    @Override
    public void ShowBookPop(BookSearchInfo.Language.Book book) {
        initPop(book);
        BookBackground.setVisibility(View.VISIBLE);
        BookSearchPop.startAnimation(PopEnterAnim);
        BookSearchPop.setVisibility(View.VISIBLE);
    }

    private void initPop(BookSearchInfo.Language.Book book){
        Glide.with(getBaseContext())
                .load(book.getBook_img())
                .placeholder(R.drawable.main_review_icon_no_word)
                .into(PopImg);
        PopNumber.setText(book.getBook_number()+"人正在背诵");
        if(book.getBook_good()==1){
            PopPerfect.setVisibility(View.VISIBLE);
        }else{
            PopPerfect.setVisibility(View.GONE);
        }
        PopSummary.setText(book.getBook_summary());
        PopTitle.setText(book.getBook_name());
        PopTotal.setText("共"+book.getBook_total()+"词");
        PopShare.setVisibility(View.VISIBLE);
        BookId=book.getBook_id();
    }

    @Override
    public void SwipeSearch() {
        BookSwipe.setRefreshing(true);
        loadData(DefaultEdit.getText().toString(),0);
    }

    public static void launch(Activity activity,int result_code){
        Intent intent=new Intent(activity,BookSearchActivity.class);
        activity.startActivityForResult(intent,result_code);
        Utils.StarActivityInAnim(activity);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}
