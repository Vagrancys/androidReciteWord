package com.tramp.word.module.home.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.UserTimeAdapter;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.module.setting.MeSettingActivity;
import com.tramp.word.port.HomeMeSignInterFace;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.MeSignDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/1/9.
 */

public class HomeMeFragment extends RxLazyFragment implements HomeMeSignInterFace{
    private final int WED_STATUS=100;
    private final int PHOTO_STATUS=101;
    private final int PIC_STATUS=102;
    private final int RESULT_OK=10;
    @BindView(R.id.user_time_pager)
    ViewPager mUserTimePager;
    @BindView(R.id.user_time_sliding)
    SlidingTabLayout mUserTimeSliding;
    @BindView(R.id.user_time_share)
    ImageView mUserTimeShare;
    @BindView(R.id.user_signature)
    TextView mUserSignText;
    @BindView(R.id.user_signature_alter)
    ImageView mUserSignImg;
    @BindView(R.id.user_money)
    ImageView mUserMoney;
    @BindView(R.id.user_avatar)
    ImageView mUserAvatar;
    @BindView(R.id.user_gift)
    ImageView mUserGift;
    @BindView(R.id.user_setting)
    ImageView mUserSetting;
    @BindView(R.id.user_sign)
    ImageView mUserSign;
    @BindView(R.id.user_sign_text)
    TextView mUserSignTitle;
    @BindView(R.id.user_money_number)
    TextView mUserMoneyNumber;
    private UserTimeAdapter mUserTimeAdapter;
    private MeSignDialog mMeSign;
    private PopupWindow mPopupWindow;
    private Uri uriTemp;
    private final String fileTemp="/sdcard/Avatar/";
    private Bitmap head;
    private MainAnimInterFace mFace;
    public static HomeMeFragment newInstance(){
        return new HomeMeFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_me;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared=true;
        lazyLoad();

        mFace=(MainAnimInterFace) getActivity();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared||!isVisible){
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared=false;
    }


    @Override
    protected void initRefreshLayout() {
        loadData();
    }

    @Override
    protected void loadData() {
        finishTask();
    }

    @Override
    protected void finishTask() {
        mUserTimeAdapter=new UserTimeAdapter(getFragmentManager(),getContext());
        mUserTimePager.setOffscreenPageLimit(2);
        mUserTimePager.setAdapter(mUserTimeAdapter);

        mUserTimePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    mUserTimeShare.setVisibility(View.GONE);
                }else{
                    mUserTimeShare.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mUserTimeSliding.setViewPager(mUserTimePager);
        mUserTimeSliding.setCurrentTab(0);

        mUserTimeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActivity().startActivity(new Intent(getSupportActivity(),MeShareActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });
    }

    @Override
    protected void initRecyclerView() {
        mMeSign=new MeSignDialog(getSupportActivity());
        mMeSign.setCancelable(false);

        mMeSign.setCancelOnClickListener("取消", new MeSignDialog.CancelOnClickListener() {
            @Override
            public void onCancelClick() {
                mMeSign.dismiss();
            }
        });

        mMeSign.setOkOnClickListener("修改宣言", new MeSignDialog.OkOnClickListener() {
            @Override
            public void onOkClick(String text) {
                mUserSignText.setText(text);
                mMeSign.dismiss();
            }
        });

        mUserSignImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeSign.show();
            }
        });

        mUserMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), WedCommonActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mPopupWindow=new PopupWindow();
        View view= LayoutInflater.from(getSupportActivity()).inflate(R.layout.item_me_avatar_pop,null);
        mPopupWindow.setContentView(view);

        TextView photo=(TextView) view.findViewById(R.id.item_me_avatar_photo);
        TextView pic=(TextView) view.findViewById(R.id.item_me_avatar_pic);

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pics=new Intent(Intent.ACTION_PICK,null);
                pics.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(pics,PIC_STATUS);
                mPopupWindow.dismiss();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent photos=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    uriTemp= Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"avatar.jpg"));
                    photos.putExtra(MediaStore.EXTRA_OUTPUT, uriTemp);
                    startActivityForResult(photos,PHOTO_STATUS);
                }catch (Exception e){
                    Utils.ShowToast(getSupportActivity(),"相机无法启动,请先开启相机权限");
                }
                mPopupWindow.dismiss();
            }
        });

        mUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPopupWindow.showAtLocation(mUserAvatar, Gravity.CENTER,0,0);
            }
        });

        mUserGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), MeGiftActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), MeSettingActivity.class));
                getSupportActivity().overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
            }
        });

        mUserSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFace.ShowSignLayout(2);
            }
        });
    }

    @Override
    public void StartSign(int money) {
        mUserSignTitle.setText("已签到");
        mUserMoneyNumber.setText("3"+money);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PIC_STATUS:
                if(resultCode==RESULT_OK){
                    cropPhoto(data.getData());
                }
                break;
            case PHOTO_STATUS:
                if(resultCode==RESULT_OK){
                    File temp=new File(Environment.getExternalStorageDirectory()+"/avatar.jpg");
                    cropPhoto(Uri.fromFile(temp));
                }
                break;
            case 3:
                if(data!=null){
                    Bundle extras=data.getExtras();
                    head=extras.getParcelable("data");
                    if(head !=null){
                        setPicView(head);
                        mUserAvatar.setImageBitmap(head);
                    }
                }

        }
    }

    public void cropPhoto(Uri uri){
        Intent intent =new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,3);
    }

    private void setPicView(Bitmap bitmap){
        String sdStatus=Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){
            return;
        }
        FileOutputStream b=null;
        File file=new File(fileTemp);
        file.mkdirs();
        String fileName=fileTemp+"avatar.jpg";
        try {
            b=new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,b);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                b.flush();
                b.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

























