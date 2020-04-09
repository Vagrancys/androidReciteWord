package com.tramp.word.api.api;

import com.tramp.word.entity.DefaultInfo;
import com.tramp.word.entity.friend.FriendInfo;
import com.tramp.word.entity.friend.FriendMainInfo;
import com.tramp.word.entity.user.FriendSearchInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/31
 * version:1.0
 */

public interface FriendService {
    @GET("friend.html")
    Call<FriendInfo> getFriendInfo(@Query("user_id") int user_id);

    @GET("friend_search.html")
    Call<FriendSearchInfo> getFriendSearchInfo(@Query("user_name") String user_name, @Query("page") int page,@Query("user_id") int user_id);

    @GET("friend_main.html")
    Call<FriendMainInfo> getFriendMainInfo(@Query("user_id") int user_id,@Query("friend_id") int friend_id);

    @GET("friend_add.html")
    Call<DefaultInfo> getFriendAddInfo(@Query("user_id") int user_id,@Query("friend_id") int friend_id);
}
