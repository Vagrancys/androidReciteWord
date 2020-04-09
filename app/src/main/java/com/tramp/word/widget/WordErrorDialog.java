package com.tramp.word.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.adapter.WordErrorViewAdapter;

import java.util.List;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/05/06
 * version:1.0
 */
public class WordErrorDialog extends Dialog {
    private TextView ok;
    private TextView cancel;
    private RecyclerView ErrorRecycler;
    private String[] Title={
            "单词发音","单词拼音","单词释义","列句","其他"
    };
    private String okStr;
    private String cancelStr;
    private OkOnClickListener OkOnClickListener;
    private CancelOnClickListener CancelOnClickListener;
    private WordErrorViewAdapter mAdapter;
    private HintClickListener HintClick;

    public void setCancelOnClickListener(String str,CancelOnClickListener cancelOnclickListener) {
        if(str !=null){
            cancelStr=str;
        }
        this.CancelOnClickListener = cancelOnclickListener;
    }

    public void setHintClick(HintClickListener hintClick){
        this.HintClick=hintClick;
    }

    public void setOkOnClickListener(String str,OkOnClickListener okOnclickListener) {
        if(str!=null){
            okStr=str;
        }
        this.OkOnClickListener = okOnclickListener;
    }

    public WordErrorDialog(Context context){
        super(context, R.style.WordManageDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_error_layout);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initEvent(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkOnClickListener !=null){
                    OkOnClickListener.onOkClick();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CancelOnClickListener !=null){
                    CancelOnClickListener.onCancelClick();
                }
            }
        });
        this.setHintClick(new HintClickListener() {
            @Override
            public void onHintClick(int number) {
                if(number==0){
                    ok.setClickable(false);
                    ok.setTextColor(getContext().getResources().getColor(R.color.blue));
                }else{
                    ok.setClickable(true);
                    ok.setTextColor(getContext().getResources().getColor(R.color.blue_1));
                }
            }
        });
    }

    private void initView(){
        ok=(TextView) findViewById(R.id.error_ok);
        cancel=(TextView) findViewById(R.id.error_cancel);
        ErrorRecycler=(RecyclerView) findViewById(R.id.error_recycler);
        mAdapter=new WordErrorViewAdapter(ErrorRecycler,Title,HintClick);
    }

    public List<Integer> getCheckAll(){
        return mAdapter.getCheck();
    }

    public String getEdit(){
        return mAdapter.getEdit();
    }

    public interface HintClickListener{
        void onHintClick(int number);
    }

    public interface OkOnClickListener{
        void onOkClick();
    }

    public interface CancelOnClickListener{
        void onCancelClick();
    }
}
