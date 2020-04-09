package com.tramp.word.api.api;

import com.tramp.word.entity.main.HomeCheckInfo;
import com.tramp.word.entity.main.HomeFindInfo;
import com.tramp.word.entity.main.HomeReciteInfo;
import com.tramp.word.entity.main.ReciteCheckInfo;
import com.tramp.word.entity.main.ReciteOpenInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/04/15
 * version:1.0
 */

public interface MainService {
    @GET("home_recite.html")
    Call<HomeReciteInfo> getHomeReciteInfo(@Query("user_id") int user_id);

    @GET("home_check.html")
    Call<HomeCheckInfo> getHomeCheckInfo();

    @GET("home_find.html")
    Call<HomeFindInfo> getHomeFindInfo();

    @GET("recite_check.html")
    Call<ReciteCheckInfo> getReciteCheckInfo(@Query("user_id") int user_id);

    @GET("recite_open.html")
    Call<ReciteOpenInfo> getReciteOpenInfo(@Query("user_id") int user_id,@Query("book_id") int book_id);

}
