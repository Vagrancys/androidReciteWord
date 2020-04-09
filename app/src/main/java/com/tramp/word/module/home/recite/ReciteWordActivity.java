package com.tramp.word.module.home.recite;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.AbsRecyclerViewAdapter;
import com.tramp.word.adapter.ReciteBookViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultBookInfo;
import com.tramp.word.entity.recite.DefaultGateInfo;
import com.tramp.word.module.home.recite.ReciteDetailsActivity;
import com.tramp.word.module.word.WordListActivity;
import com.tramp.word.utils.AssetsUtils;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.PullGateRefreshLayout;
import com.tramp.word.widget.ValueDialog;

import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/21.
 */

public class ReciteWordActivity extends RxBaseActivity {
    @BindView(R.id.refresh_view)
    PullGateRefreshLayout RefreshView;
    @BindView(R.id.book_out)
    ImageView BookOut;
    @BindView(R.id.book_img)
    ImageView BookImg;
    @BindView(R.id.book_recycler)
    RecyclerView BookRecycler;
    @BindView(R.id.star_img)
    ImageView StarImg;
    @BindView(R.id.book_left_number)
    TextView BookLeftNumber;
    @BindView(R.id.book_right_number)
    TextView BookRightNumber;
    @BindView(R.id.book_left_star)
    TextView BookLeftStar;
    @BindView(R.id.book_right_star)
    TextView BookRightStar;

    @BindView(R.id.save_frame)
    FrameLayout SaveFrame;
    @BindView(R.id.save_relative)
    RelativeLayout SaveRelative;
    @BindView(R.id.save_close)
    ImageView SaveClose;
    @BindView(R.id.save_message)
    TextView SaveMessage;
    @BindView(R.id.save_cancel)
    TextView SaveCancel;
    @BindView(R.id.save_ok)
    TextView SaveOk;

    private DefaultGateInfo gate;
    private DefaultBookInfo book=new DefaultBookInfo();
    private ArrayList<DefaultGateInfo> gates=new ArrayList<>();
    private ArrayList<DefaultGateInfo> Gates=new ArrayList<>();
    private ReciteBookViewAdapter mViewAdapter;
    private Matrix mMatrix=new Matrix();
    private Bitmap mBackgroundImg;
    private Bitmap mMissionImg;
    private int word_id;
    private int word_gate;
    private UserSqlHelper mUser;
    private static final int ItemGate=20;
    private int NowGate=0;
    private int Gate;
    private int book_gate;
    private Animation mTopEnterAnim;
    private Animation mTopExitAnim;
    private Animation mScaleAnim;
    private ValueDialog mDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word;
    }

    @Override
    protected void initToolBar() {
        BookOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle save) {
        mUser=new UserSqlHelper(getBaseContext());
        word_id=mUser.WordId();
        initAnim();
        initDialog();
        initBack();
        initClick();
        initData();
    }

    private void initDialog(){
        mDialog=new ValueDialog(getBaseContext());
    }

    private void initAnim(){
        mTopEnterAnim= AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_enter_anim);
        mTopExitAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_top_exit_anim);
        mScaleAnim=AnimationUtils.loadAnimation(getBaseContext(),R.anim.default_button_scale_anim);
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=mUser.BookQuery(word_id);
                if(!(cursor.getCount()>0)){
                    return;
                }
                book=new DefaultBookInfo();
                book.setBook_id(cursor.getInt(cursor.getColumnIndex("book_id")));
                book.setTotal_num(cursor.getInt(cursor.getColumnIndex("total_num")));
                book.setNew_num(cursor.getInt(cursor.getColumnIndex("new_num")));
                book.setLevel_star(cursor.getInt(cursor.getColumnIndex("level_star")));
                book.setStar(cursor.getInt(cursor.getColumnIndex("star")));
                book.setNow_gate(cursor.getInt(cursor.getColumnIndex("now_gate")));
                book.setFinish_gate(cursor.getInt(cursor.getColumnIndex("finish_gate")));
                if(book!=null&&book.getNow_gate()>=0){
                    word_gate=book.getNow_gate();
                    Gate=3;
                    book_gate=50;
                    initGate();
                }
                cursor.close();
                finishTask();
            }
        }).start();
    }

    public void initGate(){
        Cursor cursor=mUser.ItemGateAll(word_id);
        while (cursor.moveToNext()){
            gate=new DefaultGateInfo();
            gate.setGate_id(cursor.getInt(cursor.getColumnIndex("gate_id")));
            gate.setGate_uid(cursor.getInt(cursor.getColumnIndex("gate_uid")));
            gate.setGate_star(cursor.getInt(cursor.getColumnIndex("gate_star")));
            gates.add(gate);
        }
        /*book.getFinish_gate()-book.getNow_gate()*/
        for (int i=0;i<50;i++){
            gate=new DefaultGateInfo();
            gate.setGate_star(4);
            gate.setGate_id(0);
            gate.setGate_uid(word_id);
            gates.add(gate);
        }
        Log.e("recite","gates="+gates.size());
        RefreshView.setGate(Gate);
        cursor.close();
        updateGate();
        mViewAdapter.notifyDataSetChanged();
    }

    public void finishTask(){
        BookLeftNumber.setText(String.valueOf(book.getNew_num()));
        BookRightNumber.setText(String.valueOf(book.getTotal_num()));
        BookLeftStar.setText(String.valueOf(book.getStar()));
        BookRightStar.setText(String.valueOf(book.getLevel_star()));
    }

    private void initClick(){
        BookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReciteWordActivity.this, WordListActivity.class));
                Utils.StarActivityInAnim(ReciteWordActivity.this);
            }
        });

        RefreshView.setRefreshListener(new PullGateRefreshLayout.OnRefreshListener() {
            @Override
            public void refreshFinished() {
                NowGate++;
                if(NowGate<Gate){
                    Log.e("refreshUp","NowGate="+NowGate);
                    updateGate();
                }else{
                    NowGate=Gate-1;
                }
                mViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void loadMoreFinished() {
                NowGate--;
                if(NowGate>=0){
                    updateGate();
                    Log.e("refreshDown","NowGate="+NowGate);
                }else{
                    NowGate=0;
                }

                mViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void HeaderLayout(int y) {

            }

            @Override
            public void FooterLayout(int y) {

            }
        });

        SaveClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveRelative.startAnimation(mTopExitAnim);
                SaveRelative.setVisibility(View.GONE);
                SaveFrame.setVisibility(View.GONE);
            }
        });

        SaveCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveCancel.startAnimation(mScaleAnim);
                SaveRelative.startAnimation(mTopExitAnim);
                SaveRelative.setVisibility(View.GONE);
                SaveFrame.setVisibility(View.GONE);
                PreferencesUtils.putBoolean(ConstantUtils.SAVE_STATUS,false);
            }
        });

        SaveOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveOk.startAnimation(mScaleAnim);
                SaveRelative.startAnimation(mTopExitAnim);
                SaveRelative.setVisibility(View.GONE);
                SaveFrame.setVisibility(View.GONE);
                PreferencesUtils.putBoolean(ConstantUtils.SAVE_STATUS,false);
                ReciteDetailsActivity.launch(ReciteWordActivity.this,word_gate);
            }
        });

        mDialog.setOkClickListener(new ValueDialog.OkClickListener() {
            @Override
            public void onOkClick() {
                PreferencesUtils.putBoolean(ConstantUtils.SHARE_VALUE,false);
                Utils.ShowToast(getBaseContext(),"跳转开始了!");
                mDialog.dismiss();
            }
        });

        mDialog.setCancelClickListener(new ValueDialog.CancelClickListener() {
            @Override
            public void onCancelClick() {
                PreferencesUtils.putBoolean(ConstantUtils.SHARE_VALUE,false);
                Utils.ShowToast(getBaseContext(),"知道了,等你回复哦!");
                mDialog.dismiss();
            }
        });

        mDialog.setCloseClickListener(new ValueDialog.CloseClickListener() {
            @Override
            public void onCloseClick() {
                PreferencesUtils.putBoolean(ConstantUtils.SHARE_VALUE,false);
                mDialog.dismiss();
            }
        });

        mViewAdapter=new ReciteBookViewAdapter(BookRecycler,this,ItemGate,Gates,word_gate);
        mViewAdapter.setOnItemClickListener(new AbsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, AbsRecyclerViewAdapter.ClickableViewHolder holder) {
                if(PreferencesUtils.getBoolean(ConstantUtils.SAVE_STATUS,true)){
                    SaveFrame.setVisibility(View.VISIBLE);
                    SaveMessage.setText(String.format(getResources().getString(R.string.save_message),PreferencesUtils.getInt(ConstantUtils.SAVE_GATE,1)));
                    SaveRelative.startAnimation(mTopEnterAnim);
                    SaveRelative.setVisibility(View.VISIBLE);
                }else{
                    ReciteDetailsActivity.launch(ReciteWordActivity.this,word_gate);
                }

            }
        });
        BookRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        BookRecycler.setAdapter(mViewAdapter);
        BookRecycler.scrollToPosition(word_gate);
    }

    private void updateGate(){
        Gates.clear();
        int length;
        if(book_gate>NowGate*20&&book_gate<(NowGate+1)*20){
            length=book_gate;
        }else{
            length=(NowGate+1)*20;
        }
        for (int i=NowGate*20;i<length;i++){
            gate=new DefaultGateInfo();
            gate.setGate_star(gates.get(i).getGate_star());
            gate.setGate_id(gates.get(i).getGate_id());
            gate.setGate_uid(gates.get(i).getGate_uid());
            Gates.add(gate);
        }
        Log.e("recite","Gates="+Gates.size());
    }

    private void initBack(){
        InputStream back= AssetsUtils.getAssetsOpen(getBaseContext(),"themebg/theme0/bg_desert@2x.png");
        mBackgroundImg= BitmapFactory.decodeStream(back);
        InputStream is= AssetsUtils.getAssetsOpen(getBaseContext(),"mission_assets@2x.png");
        mMissionImg= BitmapFactory.decodeStream(is);
        RefreshView.setBackground(new BitmapDrawable(mBackgroundImg));

        BookImg.setImageBitmap(Bitmap.createBitmap(mMissionImg,100,520,95,95,mMatrix,false));

        mMatrix.setRotate(-90);

        StarImg.setImageBitmap(Bitmap.createBitmap(mMissionImg,105,315,45,45));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int count;
        if((count=PreferencesUtils.getInt(ConstantUtils.GATE_WORD,0))>0){
            Utils.ShowToast(getBaseContext(),"新增"+count+"个单词");
            PreferencesUtils.putInt(ConstantUtils.GATE_WORD,0);
        }
        if(PreferencesUtils.getBoolean(ConstantUtils.SHARE_VALUE,true)){
            mDialog.show();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}












