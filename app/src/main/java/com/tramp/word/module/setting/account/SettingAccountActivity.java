package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tramp.word.R;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.module.common.LoginActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/2/28.
 */

public class SettingAccountActivity extends RxBaseActivity implements View.OnClickListener{
    @BindView(R.id.default_out)
    ImageView DefaultOut;
    @BindView(R.id.default_title)
    TextView DefaultTitle;

    @BindView(R.id.account_avatar)
    RelativeLayout AccountAvatar;
    @BindView(R.id.account_avatar_img)
    ImageView AccountAvatarImg;

    @BindView(R.id.account_name)
    RelativeLayout AccountName;
    @BindView(R.id.account_nickname)
    RelativeLayout AccountNickName;
    @BindView(R.id.account_safety)
    RelativeLayout AccountSafety;
    @BindView(R.id.account_phone)
    RelativeLayout AccountPhone;
    @BindView(R.id.account_qq)
    RelativeLayout AccountQq;
    @BindView(R.id.account_weibo)
    RelativeLayout AccountWeiBo;
    @BindView(R.id.setting_account_cancel)
    TextView SettingAccountCancel;
    private PopupWindow mPopupWindow;
    private Uri uriTemp;
    private final String fileTemp="/sdcard/Avatar/";
    private final int PHOTO_STATUS=101;
    private final int PIC_STATUS=102;
    private final int RESULT_OK=10;
    private Bitmap head;
    private Intent mIntent=new Intent();
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_account;
    }

    @Override
    public void initView(Bundle save) {
        initClick();
        initAvatar();
    }
    private void initClick(){
        AccountAvatar.setOnClickListener(this);
        AccountName.setOnClickListener(this);
        AccountNickName.setOnClickListener(this);
        AccountSafety.setOnClickListener(this);
        AccountPhone.setOnClickListener(this);
        AccountQq.setOnClickListener(this);
        AccountWeiBo.setOnClickListener(this);
        SettingAccountCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_avatar:
                mPopupWindow.showAtLocation(AccountAvatar, Gravity.CENTER,0,0);
                break;
            case R.id.account_name:
                mIntent.setClass(SettingAccountActivity.this,AccountNameActivity.class);
                mIntent.putExtra(ConstantUtils.ACCOUNT_NAME,getResources().getString(R.string.account_name_title));
                startActivityForResult(mIntent,ConstantUtils.NAME_CODE);
                StartActivityAnim();
                break;
            case R.id.account_nickname:
                mIntent.setClass(SettingAccountActivity.this,AccountNickNameActivity.class);
                mIntent.putExtra(ConstantUtils.ACCOUNT_NICKNAME,getResources().getString(R.string.account_name_title));
                startActivityForResult(mIntent,ConstantUtils.NICKNAME_CODE);
                StartActivityAnim();
                break;
            case R.id.account_safety:
                mIntent.setClass(SettingAccountActivity.this,AccountSafetyActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
            case R.id.account_phone:
                mIntent.setClass(SettingAccountActivity.this,AccountPhoneActivity.class);
                mIntent.putExtra(ConstantUtils.ACCOUNT_PHONE,getResources().getString(R.string.account_phone_title));
                startActivityForResult(mIntent,ConstantUtils.PHONE_CODE);
                break;
            case R.id.account_qq:
                Utils.ShowToast(getBaseContext(),"开始绑定qq");
                break;
            case R.id.account_weibo:
                Utils.ShowToast(getBaseContext(),"开始绑定微博");
                break;
            case R.id.setting_account_cancel:
                PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,false);
                startActivity(new Intent(SettingAccountActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void initAvatar(){
        mPopupWindow=new PopupWindow();
        View view= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_me_avatar_pop,null);
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
                    Utils.ShowToast(getBaseContext(),"相机无法启动,请先开启相机权限");
                }
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected void initToolBar() {
        DefaultOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DefaultTitle.setText(getResources().getString(R.string.me_setting_account_text));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_stay,R.anim.activity_out_anim);
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
                        AccountAvatarImg.setImageBitmap(head);
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

    public void StartActivityAnim(){
        overridePendingTransition(R.anim.activity_in_anim,R.anim.activity_stay);
    }
}








