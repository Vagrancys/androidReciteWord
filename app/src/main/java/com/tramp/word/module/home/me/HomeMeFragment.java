package com.tramp.word.module.home.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.tramp.word.R;
import com.tramp.word.adapter.MeBadgeAdapter;
import com.tramp.word.adapter.UserTimeAdapter;
import com.tramp.word.api.Retrofits;
import com.tramp.word.base.RxLazyFragment;
import com.tramp.word.db.UserSqlHelper;
import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.user.UserMainInfo;
import com.tramp.word.module.common.WedCommonActivity;
import com.tramp.word.module.group.GroupContentActivity;
import com.tramp.word.module.group.GroupDetailsActivity;
import com.tramp.word.module.setting.MeSettingActivity;
import com.tramp.word.module.user.MeShareActivity;
import com.tramp.word.port.HomeMeSignInterFace;
import com.tramp.word.port.MainAnimInterFace;
import com.tramp.word.utils.Utils;
import com.tramp.word.widget.MeSignDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextView UserSignText;
    @BindView(R.id.user_signature_alter)
    ImageView mUserSignImg;
    @BindView(R.id.user_money)
    ImageView UserMoney;
    @BindView(R.id.user_money_number)
    TextView UserMoneyNumber;
    @BindView(R.id.user_avatar)
    ImageView UserAvatar;
    @BindView(R.id.user_gift)
    ImageView mUserGift;
    @BindView(R.id.user_setting)
    ImageView mUserSetting;
    @BindView(R.id.user_sign)
    ImageView mUserSign;
    @BindView(R.id.user_sign_text)
    TextView mUserSignTitle;
    @BindView(R.id.user_part_text)
    TextView UserPartText;
    @BindView(R.id.user_group)
    RelativeLayout UserGroup;
    @BindView(R.id.user_group_add)
    LinearLayout UserGroupAdd;
    @BindView(R.id.user_name)
    TextView UserName;
    @BindView(R.id.user_group_img)
    ImageView UserGroupImg;
    @BindView(R.id.user_group_title)
    TextView UserGroupTitle;
    @BindView(R.id.user_group_level)
    ImageView UserGroupLevel;
    @BindView(R.id.user_group_recycler)
    RecyclerView UserGroupRecycler;
    @BindView(R.id.user_group_badge)
    TextView UserGroupBadge;
    private UserTimeAdapter mUserTimeAdapter;
    private MeSignDialog mMeSign;
    private PopupWindow mPopupWindow;
    private Uri uriTemp;
    private final String fileTemp="/sdcard/Avatar/";
    private Bitmap head;
    private MainAnimInterFace mFace;
    private int user_id;
    private UserMainInfo.main Main;
    private ArrayList<UserMainInfo.main.Group.Medal> Medals=new ArrayList<>();
    private MeBadgeAdapter mMeBadgeAdapter;
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
        user_id=new UserSqlHelper(getContext()).UserId();
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
        Retrofits.getUserAPI()
                .getUserMainInfo(user_id)
                .enqueue(new Callback<UserMainInfo>() {
                    @Override
                    public void onResponse(Call<UserMainInfo> call, Response<UserMainInfo> response) {
                        if(response.body() !=null &&response.body().getCode()==200){
                            Main=response.body().getMains();
                            Medals=response.body().getMains().getGroup().getMedals();
                            finishTask();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserMainInfo> call, Throwable t) {
                        Utils.ShowToast(getContext(),"网络失效了!");

                    }
                });
    }

    @Override
    protected void finishTask() {
        UserMoneyNumber.setText(Main.getUser_money());
        Glide.with(getContext())
                .load(Main.getUser_avatar())
                .placeholder(R.drawable.user_avater)
                .into(UserAvatar);
        UserName.setText(Main.getUser_name());
        UserSignText.setText(Main.getUser_sign());
        if(Main.getUser_group()==0){
            UserGroupAdd.setVisibility(View.VISIBLE);
            UserGroup.setVisibility(View.GONE);
        }else{
            UserGroupAdd.setVisibility(View.GONE);
            UserGroup.setVisibility(View.VISIBLE);
            initGroup();
        }
    }

    public void initGroup(){
        Glide.with(getSupportActivity())
        .load(Main.getGroup().getGroup_img())
        .placeholder(R.drawable.user_avater)
                .into(UserGroupImg);
        UserGroupLevel.setImageResource(Utils.getGroupLevelImg(Main.getGroup().getGroup_level()));
        UserGroupTitle.setText(Main.getGroup().getGroup_name());
        UserGroupBadge.setText("共"+Main.getGroup().getGroup_number()+"枚");

        mMeBadgeAdapter=new MeBadgeAdapter(UserGroupRecycler,Medals);
        UserGroupRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        UserGroupRecycler.setAdapter(mMeBadgeAdapter);
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
                Retrofits.getUserAPI().getSummaryAddInfo(user_id,text)
                        .enqueue(new Callback<DefaultInfo>() {
                            @Override
                            public void onResponse(Call<DefaultInfo> call, Response<DefaultInfo> response) {
                                if(response.body() !=null&& response.body().getCode()==200){
                                    Utils.ShowToast(getContext(),"宣言修改完成!");
                                    UserSignText.setText(text);
                                }else {
                                    Utils.ShowToast(getContext(),"宣言修改失败!");
                                }
                            }

                            @Override
                            public void onFailure(Call<DefaultInfo> call, Throwable t) {
                                Utils.ShowToast(getContext(),getResources().getString(R.string.forget_net_error));

                            }
                        });
                mMeSign.dismiss();
            }
        });

        mUserSignImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeSign.show();
            }
        });

        UserMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), WedCommonActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        mPopupWindow=new PopupWindow(getContext());
        View view= LayoutInflater.from(getSupportActivity()).inflate(R.layout.item_me_avatar_pop,null);
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
                    Utils.ShowToast(getSupportActivity(),"相机无法启动,请先开启相机权限");
                }
                mPopupWindow.dismiss();
            }
        });

        UserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPopupWindow.showAtLocation(UserAvatar, Gravity.CENTER,0,0);
            }
        });

        mUserGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(),WedCommonActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        mUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), MeSettingActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        UserPartText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getSupportActivity(), GroupContentActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

        mUserSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFace.ShowSignLayout(2);
            }
        });

        UserGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Main !=null&&Utils.getNetWork(getSupportActivity())){
                    GroupDetailsActivity.launch(getSupportActivity(),Main.getUser_group());
                }
            }
        });
        mUserTimeAdapter=new UserTimeAdapter(getFragmentManager(),getContext(),user_id);
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
                getSupportActivity().startActivity(new Intent(getSupportActivity(), MeShareActivity.class));
                Utils.StarActivityInAnim(getSupportActivity());
            }
        });

    }

    @Override
    public void StartSign(int money) {
        mUserSignTitle.setText("已签到");
        UserMoneyNumber.setText("3"+money);
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
                        UserAvatar.setImageBitmap(head);
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

























