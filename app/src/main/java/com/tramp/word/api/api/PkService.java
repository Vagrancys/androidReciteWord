package com.tramp.word.api.api;

import com.tramp.word.entity.pk.PkDataInfo;
import com.tramp.word.entity.pk.PkDetailsInfo;
import com.tramp.word.entity.pk.PkKnowInfo;
import com.tramp.word.entity.pk.PkModernInfo;
import com.tramp.word.entity.pk.PkSearchInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Vagrancy
 * e-mail: 18050829067@163.com
 * time  : 2019/03/31
 * version:1.0
 */

public interface PkService {
    @GET("pk_data.html")
    Call<PkDataInfo> getPkDataInfo(@Query("user_id") int user_id);

    @GET("pk_modern.html")
    Call<PkModernInfo> getPkModernInfo(@Query("user_id") int user_id);

    @GET("pk_know.html")
    Call<PkKnowInfo> getPkKnowInfo(@Query("modern_id") int modern_id);

    @GET("pk_details.html")
    Call<PkDetailsInfo> getPkDetailsInfo(@Query("details_id") int details_id);

    @GET("pk_win.html")
    Call<PkDetailsInfo> getPkWinInfo(@Query("user_id") int user_id);
    @GET("pk_error.html")
    Call<PkDetailsInfo> getPkErrorInfo(@Query("user_id") int user_id);

    @GET("pk_search.html")
    Call<PkSearchInfo> getPkSearchInfo();
}
