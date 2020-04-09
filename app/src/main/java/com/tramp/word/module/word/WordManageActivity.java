package com.tramp.word.module.word;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.WordManageViewAdapter;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.book.DefaultWordInfo;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.WordManageDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/25.
 */

public class WordManageActivity extends RxBaseActivity {
    private static final String LOG="WordManageActivity";
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;
    @BindView(R.id.manage_check)
    CheckBox ManageCheck;
    @BindView(R.id.view_manage_recycler)
    RecyclerView ManageRecycler;
    @BindView(R.id.word_manage_start)
    TextView ManageStart;
    private int manage_status;
    private int update_status=0;
    private WordManageViewAdapter mManageAdapter;
    private WordManageDialog mAlert;
    private UserSqlHelper mUser;
    private DefaultWordInfo word;
    private ArrayList<DefaultWordInfo> words=new ArrayList<>();
    private UpdateWordTask mUpdateWordTask;
    private Handler mHandler=new Handler();
    @Override
    public int getLayoutId() {
        return R.layout.activity_recite_word_manage;
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.word_manage_text));
    }

    @Override
    public void initView(Bundle save) {
        Intent intent=getIntent();
        if(intent!=null){
            manage_status=intent.getIntExtra(ConstantUtils.MANAGE_STATUS,0);
        }
        if(manage_status==1){
            update_status=0;
        }else{
            update_status=1;
        }
        mUser=new UserSqlHelper(getBaseContext());
        initData();
        loadData();
        initClick();
    }

    private void loadData(){
        words.clear();
        new Thread(()->{
            Cursor cursor=mUser.WordManageAll(mUser.WordId(),manage_status);
            while (cursor.moveToNext()){
                word=new DefaultWordInfo();
                word.setWord_id(cursor.getInt(cursor.getColumnIndex("word_id")));
                word.setWord_name(cursor.getString(cursor.getColumnIndex("word_name")));
                words.add(word);
            }
            cursor.close();
            mHandler.post(()->{
                finishTask();
            });

        }).start();
    }

    public void finishTask(){
        ManageStart.setClickable(false);
        mManageAdapter.initCheckMap();
        mManageAdapter.notifyDataSetChanged();
    }
    private void initData(){
        mManageAdapter=new WordManageViewAdapter(ManageRecycler,words);
        ManageRecycler.setLayoutManager(new LinearLayoutManager(this));
        ManageRecycler.setAdapter(mManageAdapter);
        mAlert=new WordManageDialog(this);

        if(manage_status==0){
            ManageStart.setText(getResources().getString(R.string.word_manage_repeat_start));
        }else if(manage_status==1){
            ManageStart.setText(getResources().getString(R.string.word_manage_know_start));
        }
        mAlert.setCancelable(false);
    }

    private void initClick(){
        ManageCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DefaultTitle.setText("已选"+words.size()+"词");
                    mManageAdapter.checkAll();
                    mManageAdapter.notifyDataSetChanged();
                    ManageStart.setClickable(true);
                    ManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start));

                }else{
                    DefaultTitle.setText(getResources().getString(R.string.word_manage_text));
                    mManageAdapter.cancelAll();
                    mManageAdapter.notifyDataSetChanged();
                    ManageStart.setClickable(false);
                    ManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start_un));
                }
            }
        });

        mManageAdapter.setCheckedChangeListener(new WordManageViewAdapter.CheckedChangeListener() {
            @Override
            public void onCheckedChanged(int position, CompoundButton buttonView, boolean isChecked) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(mManageAdapter.getCheckNum()!=0){
                            DefaultTitle.setText("已选"+mManageAdapter.getCheckNum()+"词");
                            ManageStart.setClickable(true);
                            ManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start));
                        }else{
                            DefaultTitle.setText(getResources().getString(R.string.word_manage_text));
                            ManageStart.setClickable(false);
                            ManageStart.setBackground(getResources().getDrawable(R.drawable.btn_word_manage_start_un));
                        }

                    }
                });
            }
        });

        mAlert.setCancelOnClickListener("取消", new WordManageDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mAlert.dismiss();
            }
        });

        mAlert.setOkOnClickListener("确定", new WordManageDialog.OkOnClickListener() {
            @Override
            public void onOkClick() {
                mAlert.dismiss();
                updateList();
                finish();
            }
        });

        ManageStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manage_status==0){
                    mAlert.setTitle(getResources().getString(R.string.word_manage_repeat_start));
                    mAlert.setMessage("确定要将已选的"+mManageAdapter.getCheckNum()+"个单词移至“已认识”吗？");
                }else if(manage_status==1){
                    mAlert.setTitle(getResources().getString(R.string.word_manage_know_start));
                    mAlert.setMessage("确定要将已选的"+mManageAdapter.getCheckNum()+"个单词移至“需复习”吗？");
                }
                mAlert.show();
            }
        });
    }

    public static void launch(Activity activity,int manage_status){
        Intent intent=new Intent(activity,WordManageActivity.class);
        intent.putExtra(ConstantUtils.MANAGE_STATUS,manage_status);
        activity.startActivity(intent);
        Utils.StarActivityInAnim(activity);
    }

    private void updateList(){
        List<Integer> ids=mManageAdapter.getCheckId();
        new UpdateWordTask().execute(ids);
    }

    class UpdateWordTask extends AsyncTask<List<Integer>,Void,Integer>{
        private static final int FAIL=0;
        private static final int SUCCESS=1;
        private static final int REPEAT=2;
        @Override
        protected Integer doInBackground(List<Integer>... lists) {
            List<Integer> numbers=lists[0];
            for (Integer number:numbers){
                try {
                    Log.e(LOG,"word_id="+number+"manage="+update_status);
                    mUser.UpdateWordLife(number,update_status);
                }catch (Exception e){
                    e.printStackTrace();
                    return FAIL;
                }
            }
            return SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            String msg="";
            switch (integer){
                case FAIL:
                    msg="由于一些原因修改失败";
                    break;
                case SUCCESS:
                    msg="修改成功";
                    mManageAdapter.cancelAll();
                    break;
            }
            Utils.ShowToast(WordManageActivity.this,msg);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
    }
}












