package com.tramp.word.module.setting.account;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tramp.word.R;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxBaseActivity;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.user.SettingAccountInfo;
import com.tramp.word.module.common.LoginActivity;
import com.tramp.word.utils.ConstantUtils;
import com.tramp.word.utils.PreferencesUtils;
import com.tramp.word.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.account_name_title)
    TextView AccountNameTitle;
    @BindView(R.id.account_nickname)
    RelativeLayout AccountNickName;
    @BindView(R.id.account_nickname_title)
    TextView AccountNickNameTitle;
    @BindView(R.id.account_safety)
    RelativeLayout AccountSafety;
    @BindView(R.id.account_phone)
    RelativeLayout AccountPhone;
    @BindView(R.id.account_phone_title)
    TextView AccountPhoneTitle;
    @BindView(R.id.account_qq)
    RelativeLayout AccountQq;
    @BindView(R.id.account_qq_title)
    TextView AccountQqTitle;
    @BindView(R.id.account_weibo)
    RelativeLayout AccountWeiBo;
    @BindView(R.id.account_weibo_title)
    TextView AccountWeiBoTitle;
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
    private UserSqlHelper mUserHelper;
    private SettingAccountInfo.Account account;
    private final int NAME_CODE=10;
    private final int NICKNAME_CODE=11;
    private final int PHONE_CODE=12;
    @Override
    public int getLayoutId() {
        return R.layout.activity_setting_account;
    }

    @Override
    public void initView(Bundle save) {
        mUserHelper=new UserSqlHelper(getBaseContext());
        loadData();
        initData();
        initClick();
        initAvatar();
    }
    private void initData(){
        Cursor cursor=mUserHelper.UserQuery();
        Glide.with(getBaseContext())
                .load(cursor.getString(cursor.getColumnIndex("avatar")))
                .placeholder(R.drawable.user_avater)
                .into(AccountAvatarImg);
        AccountNameTitle.setText(cursor.getString(cursor.getColumnIndex("user_name")));
    }

    private void loadData(){
        Retrofits.getUserAPI().getSettingAccountInfo(mUserHelper.UserId())
                .enqueue(new Callback<SettingAccountInfo>() {
                    @Override
                    public void onResponse(Call<SettingAccountInfo> call, Response<SettingAccountInfo> response) {
                        if(response.body()!=null&&response.body().getCode()==200){
                            account=response.body().getAccount();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<SettingAccountInfo> call, Throwable t) {
                        Utils.ShowToast(getBaseContext(),getResources().getString(R.string.forget_net_error));
                    }
                });
    }

    private void finishTask(){
        Glide.with(getBaseContext())
                .load(account.getUser_avatar())
                .placeholder(R.drawable.user_avater)
                .into(AccountAvatarImg);
        if(!account.getUser_name().equals("")){
            AccountNameTitle.setText(account.getUser_name());
        }
        AccountNickNameTitle.setText(account.getUser_title());
        if(!account.getUser_phone().equals("")){
            AccountPhoneTitle.setText(account.getUser_phone());
            AccountPhoneTitle.setTextColor(getResources().getColor(R.color.blue));
        }

        if(!account.getUser_qq().equals("")){
            AccountQqTitle.setText(account.getUser_qq());
            AccountQqTitle.setTextColor(getResources().getColor(R.color.blue));
        }

        if(!account.getUser_weibo().equals("")){
            AccountWeiBoTitle.setText(account.getUser_weibo());
            AccountWeiBoTitle.setTextColor(getResources().getColor(R.color.blue));
        }
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
                mPopupWindow.showAsDropDown(AccountAvatar,0,0,Gravity.CENTER);
                break;
            case R.id.account_name:
                AccountNameActivity.launch(SettingAccountActivity.this,
                        AccountNameTitle.getText().toString(),NAME_CODE);
                break;
            case R.id.account_nickname:
                AccountNickNameActivity.launch(SettingAccountActivity.this,
                        AccountNickNameTitle.getText().toString(),NICKNAME_CODE);
                break;
            case R.id.account_safety:
                mIntent.setClass(SettingAccountActivity.this,AccountSafetyActivity.class);
                startActivity(mIntent);
                StartActivityAnim();
                break;
            case R.id.account_phone:
                AccountPhoneActivity.launch(SettingAccountActivity.this,AccountPhoneTitle.getText().toString(),PHONE_CODE);
                break;
            case R.id.account_qq:
                Utils.ShowToast(getBaseContext(),"开始绑定qq");
                break;
            case R.id.account_weibo:
                Utils.ShowToast(getBaseContext(),"开始绑定微博");
                break;
            case R.id.setting_account_cancel:
                PreferencesUtils.putBoolean(ConstantUtils.LOGIN_STATIC,false);
                mUserHelper.DeleteUser();
                ExitActivity();
                startActivity(new Intent(SettingAccountActivity.this, LoginActivity.class));
                break;
        }
    }

    private void initAvatar(){
        mPopupWindow=new PopupWindow(getBaseContext());
        View view= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_me_avatar_pop,null);
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_background_radius));
        mPopupWindow.setAnimationStyle(R.style.popup_recite_style_anim);
        mPopupWindow.setFocusable(true);
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
                break;
            case NAME_CODE:
                if(resultCode==31&&data!=null){
                    AccountNameTitle.setText(data.getStringExtra(ConstantUtils.ACCOUNT_NAME));
                    AccountNameTitle.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
            case NICKNAME_CODE:
                if(resultCode==32&&data!=null){
                    AccountNickNameTitle.setText(data.getStringExtra(ConstantUtils.ACCOUNT_NICKNAME));
                    AccountNickNameTitle.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
            case PHONE_CODE:
                if(resultCode==34&&data!=null){
                    AccountPhoneTitle.setText(data.getStringExtra(ConstantUtils.ACCOUNT_PHONE));
                    AccountPhoneTitle.setTextColor(getResources().getColor(R.color.blue));
                }
                break;
                default:
                    break;
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








