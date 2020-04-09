package com.tramp.word.api.api;

import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.user.LoginInfo;
import com.tramp.word.entity.user.SettingAccountInfo;
import com.tramp.word.entity.user.UserDataInfo;
import com.tramp.word.entity.user.UserFriendInfo;
import com.tramp.word.entity.user.UserMainInfo;
import com.tramp.word.entity.user.UserMoneyInfo;
import com.tramp.word.entity.user.UserPhotoInfo;
import com.tramp.word.entity.user.UserShareInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/22
 * version:1.0
 */

public interface UserService {
    @FormUrlEncoded
    @POST("api_login.html")
    Call<LoginInfo> getLoginInfo(@Field("name") String name, @Field("password") String password);

    @POST("forget_short.html")
    Call<DefaultInfo> getForgetShortInfo(@Field("user_name") String user_name,@Field("user_code") String user_code);

    @POST("register_name.html")
    Call<DefaultInfo> getRegisterNameInfo(@Field("user_name") String user_name,@Field("user_pass") String user_code);

    @POST("update_pass.html")
    Call<DefaultInfo> getForgetPassInfo(@Field("user_name") String user_name,@Field("user_pass") String user_pass);

    @GET("forget_email.html")
    Call<DefaultInfo> getForgetEmailInfo(@Query("user_email") String user_email);

    @GET("user_main.html")
    Call<UserMainInfo> getUserMainInfo(@Query("user_id") int user_id);

    @GET("user_summary_add.html")
    Call<DefaultInfo> getSummaryAddInfo(@Query("user_id") int user_id,@Query("summary_text")String summary_text);

    @GET("user_share.html")
    Call<UserShareInfo> getUserShareInfo(@Query("user_id") int user_id);

    @GET("user_data.html")
    Call<UserDataInfo> getUserDataInfo(@Query("user_id") int user_id);

    @GET("word_data.html")
    Call<UserDataInfo> getWordDataInfo(@Query("user_id") int user_id, @Query("user_year") int user_year, @Query("user_month") int user_month);

    @GET("user_friend.html")
    Call<UserFriendInfo> getUserFriendInfo(@Query("user_id") int user_id);

    @GET("friend_update.html")
    Call<DefaultInfo> getFriendUpdateInfo(@Query("item_id") int item_id,@Query("item_status") int item_status);

    @GET("setting_account.html")
    Call<SettingAccountInfo> getSettingAccountInfo(@Query("user_id") int user_id);

    @GET("update_name.html")
    Call<DefaultInfo> getUpdateNameInfo(@Query("user_id") int user_id,@Query("user_name") String user_name);

    @GET("update_nickname.html")
    Call<DefaultInfo> getUpdateNickNameInfo(@Query("user_id") int user_id,@Query("user_nickname") String user_nickname);

    @GET("update_pass.html")
    Call<DefaultInfo> getUpdatePassInfo(@Query("user_id") int user_id,@Query("user_pass") String user_pass);

    @GET("update_phone.html")
    Call<DefaultInfo> getUpdatePhoneInfo(@Query("user_id") int user_id,@Query("user_phone") String user_phone);

    @GET("user_money.html")
    Call<UserMoneyInfo> getUserMoneyInfo(@Query("user_id") int user_id);

    @GET("user_photo.html")
    Call<UserPhotoInfo> getUserPhotoInfo();
}















